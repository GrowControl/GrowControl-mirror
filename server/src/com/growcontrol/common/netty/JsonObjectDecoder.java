/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.growcontrol.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;


/**
 * Splits a byte stream of JSON objects and arrays into individual objects/arrays and passes them up the
 * {@link ChannelPipeline}.
 *
 * This class does not do any real parsing or validation. A sequence of bytes is considered a JSON object/array
 * if it contains a matching number of opening and closing braces/brackets. It's up to a subsequent
 * {@link ChannelHandler} to parse the JSON text into a more usable form i.e. a POJO.
 */
public class JsonObjectDecoder extends ByteToMessageDecoder {

	private static final int MB = 1024 * 1024; // 1 MB

	private static final int ST_CORRUPTED = -1;
	private static final int ST_INIT = 0;
	private static final int ST_DECODING_NORMAL = 1;
	private static final int ST_DECODING_ARRAY_STREAM = 2;

	private int openBraces = 0;
	private int idx = 0;
	private int state = ST_INIT;
	private boolean insideString = false;

	private final int maxObjectLength;
	private final boolean streamArrayElements;



	public JsonObjectDecoder() {
		this(MB);
	}
	public JsonObjectDecoder(final int maxObjectLength) {
		this(maxObjectLength, false);
	}
	public JsonObjectDecoder(final boolean streamArrayElements) {
		this(MB, streamArrayElements);
	}
	/**
	 * @param maxObjectLength   maximum number of bytes a JSON object/array may use (including braces and all).
	 * Objects exceeding this length are dropped and an {@link TooLongFrameException}
	 * is thrown.
	 * @param streamArrayElements   if set to true and the "top level" JSON object is an array, each of its entries
	 *  is passed through the pipeline individually and immediately after it was fully
	 *  received, allowing for arrays with "infinitely" many elements.
	 *
	 */
	public JsonObjectDecoder(final int maxObjectLength, final boolean streamArrayElements) {
		if(maxObjectLength < 1)
			throw new IllegalArgumentException("maxObjectLength must be a positive int");
		this.maxObjectLength = maxObjectLength;
		this.streamArrayElements = streamArrayElements;
	}



	@Override
	protected void decode(final ChannelHandlerContext ctx,
			final ByteBuf in, final List<Object> out) throws Exception {
		if(this.state == ST_CORRUPTED) {
			in.skipBytes(in.readableBytes());
			return;
		}
		// index of next byte to process.
		int idx = this.idx;
		final int wrtIdx = in.writerIndex();
		if(wrtIdx > this.maxObjectLength) {
			// buffer size exceeded maxObjectLength; discarding the complete buffer.
			in.skipBytes(in.readableBytes());
			reset();
			throw new TooLongFrameException("object length exceeds "+this.maxObjectLength+": "+wrtIdx+" bytes discarded");
		}
		for(/* use current idx */; idx < wrtIdx; idx++) {
			final byte c = in.getByte(idx);
			if(this.state == ST_DECODING_NORMAL) {
				decodeByte(c, in, idx);
				// All opening braces/brackets have been closed. That's enough to conclude
				// that the JSON object/array is complete.
				if(this.openBraces == 0) {
					ByteBuf json = extractObject(ctx, in, in.readerIndex(), idx + 1 - in.readerIndex());
					if(json != null) {
						out.add(json);
					}
					// The JSON object/array was extracted => discard the bytes from
					// the input buffer.
					in.readerIndex(idx + 1);
					// Reset the object state to get ready for the next JSON object/text
					// coming along the byte stream.
					reset();
				}
			} else
			if(this.state == ST_DECODING_ARRAY_STREAM) {
				decodeByte(c, in, idx);
				if(!this.insideString && (this.openBraces == 1 && c == ',' || this.openBraces == 0 && c == ']')) {
					// skip leading spaces. No range check is needed and the loop will terminate
					// because the byte at position idx is not a whitespace.
					for(int i = in.readerIndex(); Character.isWhitespace(in.getByte(i)); i++) {
						in.skipBytes(1);
					}
					// skip trailing spaces.
					int idxNoSpaces = idx - 1;
					while(idxNoSpaces >= in.readerIndex() && Character.isWhitespace(in.getByte(idxNoSpaces))) {
						idxNoSpaces--;
					}
					ByteBuf json = extractObject(ctx, in, in.readerIndex(), idxNoSpaces + 1 - in.readerIndex());
					if(json != null) {
						out.add(json);
					}
					in.readerIndex(idx + 1);
					if(c == ']') {
						reset();
					}
				}
				// JSON object/array detected. Accumulate bytes until all braces/brackets are closed.
			} else
			if(c == '{' || c == '[') {
				initDecoding(c);
				// Discard the array bracket
				if(this.state == ST_DECODING_ARRAY_STREAM)
					in.skipBytes(1);
				// Discard leading spaces in front of a JSON object/array.
			} else
			if(Character.isWhitespace(c)) {
				in.skipBytes(1);
			} else {
				this.state = ST_CORRUPTED;
				throw new CorruptedFrameException("invalid JSON received at byte position "+idx+": "+ByteBufUtil.hexDump(in));
			}
		}
		if(in.readableBytes() == 0)
			this.idx = 0;
		else
			this.idx = idx;
	}



	/**
	 * Override this method if you want to filter the json objects/arrays that get passed through the pipeline.
	 */
	protected ByteBuf extractObject(final ChannelHandlerContext ctx,
			final ByteBuf buffer, final int index, final int length) {
		return buffer.slice(index, length).retain();
	}



	private void decodeByte(final byte c, final ByteBuf in, final int idx) {
		if((c == '{' || c == '[') && !this.insideString) {
			this.openBraces++;
		} else
		if((c == '}' || c == ']') && !this.insideString) {
			this.openBraces--;
		} else
		if(c == '"') {
			// start of a new JSON string. It's necessary to detect strings as they may
			// also contain braces/brackets and that could lead to incorrect results.
			if(!this.insideString) {
				this.insideString = true;
			// If the double quote wasn't escaped then this is the end of a string.
			} else
			if(in.getByte(idx - 1) != '\\') {
				this.insideString = false;
			}
		}
	}



	private void initDecoding(final byte openingBrace) {
		this.openBraces = 1;
		if(openingBrace == '[' && this.streamArrayElements) {
			this.state = ST_DECODING_ARRAY_STREAM;
		} else {
			this.state = ST_DECODING_NORMAL;
		}
	}



	private void reset() {
		this.insideString = false;
		this.state = ST_INIT;
		this.openBraces = 0;
	}



}
