/*
package com.growcontrol.server.app.steps;

import com.growcontrol.server.gcServer;
import com.poixson.app.steps.xAppSteps_Logo;
import com.poixson.logger.xLog;
import com.poixson.tools.AsciiArtBuilder;


public class gcServer_Logo extends xAppSteps_Logo {



	public gcServer_Logo(final gcServer app) {
		super(app);
	}



// 1 |      PoiXson                                                    |
// 2 |    GROWCONTROL     _                                            |
// 3 |      Server      _(_)_                          wWWWw   _       |
// 4 | <---version---> (_)@(_)   vVVVv     _     @@@@  (___) _(_)_     |
// 5 |            wWWWw  (_)\    (___)   _(_)_  @@()@@   Y  (_)@(_)    |
// 6 |     @@@@   (___)     `|/    Y    (_)@(_)  @@@@   \|/   (_)\     |
// 7 |    @@()@@    Y       \|    \|/    /(_)    \|      |/      |/    |
// 8 |    \@@@@   \ |/       | / \ | /  \|/       |/    \|      \|/    |
// 9 |    \\|//   \\|///  \\\|//\\\|/// \|///  \\\|//  \\|//  \\\|//   |
//10 |^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^|
//11 |/////////////////////////////////////////////////////////////////|
//   0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6
//   0         1         2         3         4         5         6
	@Override
	protected void displayLogo(final xLog log) {
		final String version = this.getAppVersionPadded();
		// define colors
		final String COLOR_BG = "black";
		final String COLOR_PXN_P    = "bold,green";
		final String COLOR_PXN_OI   = "bold,blue";
		final String COLOR_PXN_X    = "bold,green";
		final String COLOR_PXN_SON  = "bold,blue";
		final String COLOR_GROW     = "bold,green";
		final String COLOR_CONTROL  = "bold,white";
		final String COLOR_SOFTWARE = "cyan";
		final String COLOR_VERSION  = "cyan";
		final String COLOR_GRASS    = "green";
		final String COLOR_FLOWER_STEM     = "green";
		final String COLOR_FLOWER_A_PEDALS = "red";
		final String COLOR_FLOWER_A_CENTER = "red";
		final String COLOR_FLOWER_B_PEDALS = "magenta";
		final String COLOR_FLOWER_C_PEDALS = "bold,yellow";
		final String COLOR_FLOWER_C_CENTER = "yellow";
		final String COLOR_FLOWER_D_PEDALS = "bold,magenta";
		final String COLOR_FLOWER_E_PEDALS = "bold,yellow";
		final String COLOR_FLOWER_E_CENTER = "yellow";
		final String COLOR_FLOWER_F_PEDALS = "blue";
		final String COLOR_FLOWER_F_CENTER = "bold,blue";
		final String COLOR_FLOWER_G_PEDALS = "magenta";
		final String COLOR_FLOWER_H_PEDALS = "bold,yellow";
		final String COLOR_FLOWER_H_CENTER = "yellow";
		// ascii art
		final AsciiArtBuilder art =
			new AsciiArtBuilder(
				"      PoiXson                                                    ",
				"    GROWCONTROL     _                                            ",
				"      Server      _(_)_                          wWWWw   _       ",
				" "+version+" (_)@(_)   vVVVv     _     @@@@  (___) _(_)_     ",
				"            wWWWw  (_)\\    (___)   _(_)_  @@()@@   Y  (_)@(_)    ",
				"     @@@@   (___)     `|/    Y    (_)@(_)  @@@@   \\|/   (_)\\     ",
				"    @@()@@    Y       \\|    \\|/    /(_)    \\|      |/      |/    ",
				"    \\@@@@   \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/    ",
				"    \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|//   ",
				"^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^",
				"/////////////////////////////////////////////////////////////////"
			);
		art.setIndent(INDENT);
		art.setBgColor(COLOR_BG);
		// line 1    color                   x   y
		art.setColor(COLOR_PXN_P,            6,  0);
		art.setColor(COLOR_PXN_OI,           7,  0);
		art.setColor(COLOR_PXN_X,            9,  0);
		art.setColor(COLOR_PXN_SON,         10,  0);
		// line 2    color                   x   y
		art.setColor(COLOR_GROW,             4,  1);
		art.setColor(COLOR_CONTROL,          8,  1);
		art.setColor(COLOR_FLOWER_C_PEDALS, 20,  1);
		// line 3    color                   x   y
		art.setColor(COLOR_SOFTWARE,         6,  2);
		art.setColor(COLOR_FLOWER_C_PEDALS, 18,  2);
		art.setColor(COLOR_FLOWER_G_PEDALS, 49,  2);
		art.setColor(COLOR_FLOWER_H_PEDALS, 57,  2);
		// line 4    color                   x   y
		art.setColor(COLOR_VERSION,          3,  3);
		art.setColor(COLOR_FLOWER_C_PEDALS, 17,  3);
		art.setColor(COLOR_FLOWER_C_CENTER, 20,  3);
		art.setColor(COLOR_FLOWER_C_PEDALS, 21,  3);
		art.setColor(COLOR_FLOWER_D_PEDALS, 27,  3);
		art.setColor(COLOR_FLOWER_E_PEDALS, 37,  3);
		art.setColor(COLOR_FLOWER_F_PEDALS, 43,  3);
		art.setColor(COLOR_FLOWER_G_PEDALS, 49,  3);
		art.setColor(COLOR_FLOWER_H_PEDALS, 55,  3);
		// line 5    color                   x   y
		art.setColor(COLOR_FLOWER_B_PEDALS, 12,  4);
		art.setColor(COLOR_FLOWER_C_PEDALS, 19,  4);
		art.setColor(COLOR_FLOWER_STEM,     22,  4);
		art.setColor(COLOR_FLOWER_D_PEDALS, 27,  4);
		art.setColor(COLOR_FLOWER_E_PEDALS, 35,  4);
		art.setColor(COLOR_FLOWER_F_PEDALS, 42,  4);
		art.setColor(COLOR_FLOWER_F_CENTER, 44,  4);
		art.setColor(COLOR_FLOWER_F_PEDALS, 46,  4);
		art.setColor(COLOR_FLOWER_G_PEDALS, 51,  4);
		art.setColor(COLOR_FLOWER_H_PEDALS, 54,  4);
		art.setColor(COLOR_FLOWER_H_CENTER, 57,  4);
		art.setColor(COLOR_FLOWER_H_PEDALS, 58,  4);
		// line 6    color                   x   y
		art.setColor(COLOR_FLOWER_A_PEDALS,  5,  5);
		art.setColor(COLOR_FLOWER_A_CENTER,  7,  5);
		art.setColor(COLOR_FLOWER_A_PEDALS,  8,  5);
		art.setColor(COLOR_FLOWER_B_PEDALS, 12,  5);
		art.setColor(COLOR_FLOWER_STEM,     22,  5);
		art.setColor(COLOR_FLOWER_D_PEDALS, 29,  5);
		art.setColor(COLOR_FLOWER_E_PEDALS, 34,  5);
		art.setColor(COLOR_FLOWER_E_CENTER, 37,  5);
		art.setColor(COLOR_FLOWER_E_PEDALS, 38,  5);
		art.setColor(COLOR_FLOWER_F_PEDALS, 43,  5);
		art.setColor(COLOR_FLOWER_STEM,     50,  5);
		art.setColor(COLOR_FLOWER_H_PEDALS, 56,  5);
		art.setColor(COLOR_FLOWER_STEM,     59,  5);
		// line 7    color                   x   y
		art.setColor(COLOR_FLOWER_A_PEDALS,  4,  6);
		art.setColor(COLOR_FLOWER_A_CENTER,  6,  6);
		art.setColor(COLOR_FLOWER_A_PEDALS,  8,  6);
		art.setColor(COLOR_FLOWER_B_PEDALS, 14,  6);
		art.setColor(COLOR_FLOWER_STEM,     22,  6);
		art.setColor(COLOR_FLOWER_E_PEDALS, 36,  6);
		art.setColor(COLOR_FLOWER_STEM,     43,  6);
		// line 8    color                   x   y
		art.setColor(COLOR_FLOWER_STEM,      4,  7);
		art.setColor(COLOR_FLOWER_A_PEDALS,  5,  7);
		art.setColor(COLOR_FLOWER_STEM,     12,  7);
		// line 9    color                   x   y
		art.setColor(COLOR_FLOWER_STEM,      4,  8);
		// line 10   color                   x   y
		art.setColor(COLOR_GRASS,            0,  9);
		// line 11   color                   x   y
		art.setColor(COLOR_GRASS,            0, 10);
		// display generated art
		log.publish(art.build());
	}



}
*/
