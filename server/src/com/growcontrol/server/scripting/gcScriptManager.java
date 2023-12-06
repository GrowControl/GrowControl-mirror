package com.growcontrol.server.scripting;

import com.poixson.app.xApp;
import com.poixson.tools.abstractions.xStartStop;


public class gcScriptManager implements xStartStop {

	protected final xApp app;



	public gcScriptManager(final xApp app) {
		this.app = app;
	}



	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}



}
/*
//https://docs.oracle.com/javase/6/docs/technotes/guides/scripting/programmer_guide/

	private static volatile gcScriptManager instance = null;
	private static final Object instanceLock = new Object();

	protected volatile xThreadPool pool = null;
	protected final AtomicInteger nextId = new AtomicInteger(1);

	protected final ScriptEngineManager factory;
	protected final Map<String, gcScript> scripts = new HashMap<String, gcScript>();



		this.factory = new ScriptEngineManager();
//TODO:
//		this.factory.put("ServerVersion", gcServer.get().getVersion());
	}



	public xThreadPool getThreadPool() {
		final xThreadPool pool = this.pool;
		if(pool == null)
			return xThreadPool.getMainPool();
		return pool;
	}
	public ScriptEngine getEngine() {
		return this.factory.getEngineByName(LANGUAGE);
	}



	public int getNextId() {
		return this.nextId.getAndIncrement();
	}



	public void loadAll() {
		this.loadAll((String) null);
	}
	public void loadAll(final String dir) {
		this.loadAll(new File(
			IsEmpty(dir) ? "scripts/" : dir
		));
	}
	// load all scripts from dir
	public void loadAll(final File dir) {
		if(dir == null) throw new RequiredArgumentException("dir");
		// create scripts dir if needed
		if(!dir.isDirectory())
			dir.mkdir();
		// list dir contents
		final File[] files = utilsDirFile.listContents(dir, ".script");
		if(files == null) throw new RuntimeException("Failed to get files list!");
		// no scripts found
		if(files.length == 0) {
			this.log().warning("No scripts found to load.");
			return;
		}
		// load script files
		int count = 0;
		synchronized(this.scripts) {
			for(final File f : files) {
				final gcScript script = this.load(f);
				if(script == null) continue;
				count++;
			}
		}
		this.log().info("Found [ "+Integer.toString(count)+" ] scripts.");
	}
	public gcScript load(final File file) {
		if(file == null) throw new RequiredArgumentException("file");
		if(!file.exists()) {
			this.log().warning("Script file not found: "+file.toString());
			return null;
		}
		final String name = utilsString.getFirstPart(
				".",
				utilsString.getLastPart(
						File.separator,
						file.toString()
				)
		);
		final gcScript script = new gcScript(name);
		try {
			script.queue(file);
		} catch (FileNotFoundException e) {
			this.log().trace(e);
			return null;
		}
		synchronized(this.scripts) {
			// script already loaded
			if(this.scripts.containsKey(name)) {
				this.log().warning("Script already loaded with name: "+name);
				return null;
			}
			this.scripts.put(name, script);
		}
		this.log().finer("Loaded script file: "+file.toString());
		return script;
	}



	public void StartAll() {
		if(this.scripts.isEmpty()) return;
		synchronized(this.scripts) {
			for(final gcScript script : this.scripts.values()) {
				script.Start();
			}
		}
	}
	public void StopAll() {
		if(this.scripts.isEmpty()) return;
		synchronized(this.scripts) {
			for(final gcScript script : this.scripts.values()) {
				script.Stop();
			}
		}
	}



	// logger
	public xLog log() {
		return xLog.getRoot().get(LOG_NAME);
	}



}
*/
