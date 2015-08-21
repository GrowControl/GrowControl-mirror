package com.growcontrol.common.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

import com.growcontrol.common.packets.PacketState;
import com.poixson.commonjava.Utils.xCloseable;
import com.poixson.commonjava.xLogger.xLog;


public abstract class SocketState implements xCloseable {

	// socket session state
	protected volatile SessionState sessionState = SessionState.CLOSED;
	public enum SessionState {
		CLOSED,
		PREAUTH,
		AUTHED
	};

	// socket id
	protected static final AtomicInteger nextSocketId = new AtomicInteger(0);
	public final int id;
	public volatile String key = null;



	public abstract ChannelInboundHandlerAdapter getHandler();
	public abstract PacketState getPacketState();



	public abstract void send(final String data);



	public SocketState() {
		this.id  = getNextId();
	}
	private static int getNextId() {
		return nextSocketId.incrementAndGet();
	}



	// socket state
	public SessionState getSessionState() {
		return this.sessionState;
	}
	public void setSessionState(final SessionState state) {
		switch(state) {
		case CLOSED:
			this.sessionState = SessionState.CLOSED;
			break;
		case PREAUTH:
			if(!SessionState.CLOSED.equals(this.sessionState))
				throw new RuntimeException("Socket in wrong state! Must be in CLOSED state first!");
			this.sessionState = SessionState.PREAUTH;
			break;
		case AUTHED:
			if(!SessionState.PREAUTH.equals(this.sessionState))
				throw new RuntimeException("Socket in wrong state! Must be in PREAUTH state first!");
			this.sessionState = SessionState.AUTHED;
			break;
		default:
			throw new RuntimeException("Unknown socket state! "+state.name());
		}
	}
	@Override
	public boolean isClosed() {
		return SessionState.CLOSED.equals(this);
	}
	public boolean isAuthed() {
		return SessionState.AUTHED.equals(this);
	}



	// state key
	protected abstract String genKey();
	public String getStateKey() {
		if(this.key == null)
			this.key = this.genKey();
		return this.key;
	}
	@Override
	public String toString() {
		return this.getStateKey();
	}



	// logger
	public xLog log() {
		return xLog.getRoot("NET")
				.get(this.getStateKey());
	}



}
