package com.growcontrol.client.lab;

import com.growcontrol.client.lab.app.steps.gcLab_Logo;
import com.growcontrol.client.studio.gcStudio;
import com.poixson.app.xAppMoreSteps;
import com.poixson.app.xAppStepType;
import com.poixson.app.steps.xAppSteps_LockFile;
import com.poixson.app.steps.xAppSteps_UserNotRoot;


/*
 * Startup sequence
 *    5 | prevent root     | xAppSteps_UserNotRoot
 *   10 | startup time     | xApp
 *   15 | lock file        | xAppSteps_LockFile
 *   20 | display logo     | gcClient_Logo
 *   50 | load configs
 *  400 | plugin manager
 *  405 | load plugin jars | xAppSteps_Plugins
 *  408 | init plugins     | xAppSteps_Plugins
 *  410 | start plugins    | xAppSteps_Plugins
 *  450 | start scripts
 *
 * Shutdown sequence
 *  450 | stop scripts
 *  410 | stop plugins      | xAppSteps_Plugins
 *  408 | term plugins      | xAppSteps_Plugins
 *  405 | unload plugins    | xAppSteps_Plugins
 *   60 | display uptime    | xApp
 *   50 | stop thread pools | xApp
 *   15 | release lock file | xAppSteps_LockFile
 *   10 | garbage collect   | xApp
 *    1 | exit              | xApp
 */
public class gcLab extends gcStudio {
	public static final String DEFAULT_LOCK_FILE = "gcLab.lock";



	public gcLab(final String[] args) {
		super(args);
	}



	@Override
	@xAppMoreSteps
	public Object[] steps(final xAppStepType type) {
//TODO
		final String lock_file = DEFAULT_LOCK_FILE;
		return new Object[] {
			new xAppSteps_UserNotRoot(),       //  5
			new xAppSteps_LockFile(lock_file), // 15
			new gcLab_Logo(this),              // 20
		};
	}



}
