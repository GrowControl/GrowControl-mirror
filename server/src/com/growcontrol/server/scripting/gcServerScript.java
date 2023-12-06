package com.growcontrol.server.scripting;

import com.poixson.tools.abstractions.xStartable;


public class gcScript implements xStartable {
	public static final String LOG_NAME = "SCRIPT";
	public static final String LANGUAGE = "JavaScript";



	public gcScript() {
	}



	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
	@Override
	public boolean isRunning() {
return false;
	}
	@Override
	public boolean isStopping() {
return false;
	}



}
/*
	public final String name;
	public final int id;

	protected volatile ScriptEngine engine = null;
	protected final Object engineLock = new Object();

	protected final AtomicBoolean running    = new AtomicBoolean(false);
	protected final AtomicBoolean processing = new AtomicBoolean(false);

	protected final ConcurrentLinkedQueue<ScriptCode> queue = new ConcurrentLinkedQueue<ScriptCode>();



	public gcScript() {
		this(null);
	}
	public gcScript(final String name) {
		final gcScriptManager manager = gcScriptManager.get();
		this.id   = manager.getNextId();
		this.name = (IsEmpty(name) ? "script-"+Integer.toString(this.id) : name);
		this.queue.add(
				new ScriptCode_Text(
						"function log() {"+
						"    return com.poixson.commonjava.xLogger.xLog.getRoot().get('script').get(ScriptName);"+
						"}"+
						"function print(text) {"+
						"    log().info(text);"+
						"}"+
						"log().info('Starting script..')"
				)
		);
	}
	protected void init() {
		if(this.engine != null) return;
		synchronized(this.engineLock){
			if(this.engine != null) return;
			final gcScriptManager manager = gcScriptManager.get();
			this.engine  = manager.getEngine();
			if(this.engine == null) {
				this.log().severe("Failed to load "+gcScriptManager.LANGUAGE+" scripting engine!");
				throw new RuntimeException("Failed to load scripting engine!");
			}
			this.engine.put("ScriptName", this.name);
		}
	}



	protected static interface ScriptCode {
		public void eval(final ScriptEngine engine) throws ScriptException;
	}

	protected static class ScriptCode_File implements ScriptCode {
		protected final File file;
		public ScriptCode_File(final File file) throws FileNotFoundException {
			if(file == null)   throw new RequiredArgumentException("file");
			if(!file.isFile()) throw new FileNotFoundException("Script file not found: "+file.toString());
			this.file = file;
		}
		@Override
		public void eval(final ScriptEngine engine) throws ScriptException {
			if(engine == null) throw new RequiredArgumentException("engine");
			try {
				engine.eval(new FileReader(this.file));
			} catch (FileNotFoundException e) {
				throw new ScriptException(e);
			}
		}
	}

	protected static class ScriptCode_Text implements ScriptCode {
		protected final String text;
		public ScriptCode_Text(final String text) {
			if (IsEmpty(text)) throw new RequiredArgumentException("text");
			this.text = text;
		}
		@Override
		public void eval(final ScriptEngine engine) throws ScriptException {
			if(engine == null) throw new RequiredArgumentException("engine");
			engine.eval(this.text);
		}
	}



	// queue a file to be run
	public void queue(final File file) throws FileNotFoundException {
		if(file == null) throw new RequiredArgumentException("file");
		if(!file.isFile()) throw new FileNotFoundException("Script file not found: "+file.toString());
		this.queue.add(
				new ScriptCode_File(file)
		);
		this.queue();
	}
	// queue code to be run
	public void queue(final String code) {
		this.queue.add(
				new ScriptCode_Text(code)
		);
	}
	protected void queue() {
		if(!this.running.get())   return;
		if(this.processing.get()) return;
		this.getThreadPool()
			.runLater(this);
	}



	@Override
	public void Start() {
		// only start once
		if(!this.running.compareAndSet(false, true))
			return;
		this.init();
		this.queue();
	}
	@Override
	public void Stop() {
		this.running.set(false);
	}
	@Override
	public boolean isRunning() {
		return this.running.get();
	}
	public boolean isProcessing() {
		return this.processing.get();
	}



	@Override
	public void run() {
		if(!this.running.get()) return;
		if(!this.processing.compareAndSet(false, true)) return;
		// eval queued code
		final Iterator<ScriptCode> it = this.queue.iterator();
		while(it.hasNext()) {
			if(!this.running.get()) break;
			final ScriptCode code = it.next();
			try {
				// eval file
				if(code instanceof ScriptCode_File) {
					final File file = ((ScriptCode_File) code).file;
					try {
						this.engine.eval(
								new FileReader(file)
						);
					} catch (FileNotFoundException e) {
						this.log().trace(e);
					}
				} else
				// eval text
				if(code instanceof ScriptCode_Text) {
					this.engine.eval(
							((ScriptCode_Text) code).text
					);
				} else {
					this.log().severe("Invalid queued code type: "+code.getClass().getName());
				}
			} catch (ScriptException e) {
				this.log().trace(e);
			}
			it.remove();
// lets not hold up the thread pool
break;
		}
		this.processing.set(false);
		// just in case
		if(!this.queue.isEmpty())
			this.queue();
	}



//	public Object InvokeFunction(final String name) {
//		if(!this.running.get()) throw new IllegalStateException("Script not running");
//		try {
//			return ((Invocable) this.script).invokeFunction(name);
//		} catch (NoSuchMethodException e) {
//			this.log().trace(e);
//		} catch (ScriptException e) {
//			this.log().trace(e);
//		}
//		return null;
//	}



	public xThreadPool getThreadPool() {
		return gcScriptManager.get().getThreadPool();
	}
	public String getName() {
		final String name = this.name;
		if (IsEmpty(name))
			return "script-"+Integer.toString(this.id);
		return name;
	}



	// logger
	public xLog log() {
		return gcScriptManager.get()
				.log().get(this.getName());
	}



}
*/
