package com.growcontrol.client.app.steps;

import com.growcontrol.client.gcClient;
import com.poixson.app.xApp;
import com.poixson.app.steps.xAppSteps_Logo;
import com.poixson.logger.xLog;
import com.poixson.tools.AsciiArtBuilder;
import com.poixson.utils.StringUtils;


public class gcClientLogo extends xAppSteps_Logo {



	public gcClientLogo(final gcClient client) {
		super(client);
	}



//       A       B      C      D     E         F      G         H
//1 |       PoiXson   <---version--->                                   |
//2 |     GROWCONTROL   _ _        ,`--',                     " ' "     |
//3 |    _  Client     (_\_)      . _\/_ .                  " \ | / "   |
//4 |  _(_)_          (__<_{)     `. /\ .'   .\|/.         ' --(:)-- '  |
//5 | (_)@(_)          {_/_}        "||"     -(:)-          " / | \ "   |
//6 |   (_)\.         |\ |           || /\   "/|\"           " '|' "    |
//7 |     . |/| .vVv.  \\| /| \V/  /\||//\)   '|'    `@'   |\   |       |
//8 |     |\|/   \#/    \|//  `|/ (/\||/    .\ | ,   \|/   /_ \ |  /`|  |
//9 |      \|   \\|//    |/  \\|//   ||     /-\|/_\ \\|//,   /-\|/_//   |
//10 | ^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^ |
//11 | ///////////////////////////////////////////////////////////////// |
//0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8
//0         1         2         3         4         5         6
	@Override
	protected void displayLogo(final xApp app, final xLog log) {
		final String version =
			StringUtils.PadCenter(
				VERSION_SPACE,
				app.getVersion(),
				' '
			);
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
		final String COLOR_FLOWER_A_PEDALS = "blue";
		final String COLOR_FLOWER_B_PEDALS = "bold,red";
		final String COLOR_FLOWER_C_PEDALS = "magenta";
		final String COLOR_FLOWER_D_PEDALS = "red";
		final String COLOR_FLOWER_E_PEDALS = "yellow";
		final String COLOR_FLOWER_F_PEDALS = "bold,white";
		final String COLOR_FLOWER_G_PEDALS = "yellow";
		final String COLOR_FLOWER_H_PEDALS = "bold,white";
		// display ascii art
		final AsciiArtBuilder art =
			new AsciiArtBuilder(
				"      PoiXson   "+version+"                                  ",
				"    GROWCONTROL   _ _        ,`--',                     \" ' \"    ",
				"   _  Client     (_\\_)      . _\\/_ .                  \" \\ | / \"  ",
				" _(_)_          (__<_{)     `. /\\ .'   .\\|/.         ' --(:)-- ' ",
				"(_)@(_)          {_/_}        \"||\"     -(:)-          \" / | \\ \"  ",
				"  (_)\\.         |\\ |           || /\\   \"/|\\\"           \" '|' \"   ",
				"    . |/| .vVv.  \\\\| /| \\V/  /\\||//\\)   '|'    `@'   |\\   |      ",
				"    |\\|/   \\#/    \\|//  `|/ (/\\||/    .\\ | ,   \\|/   /_ \\ |  /`| ",
				"     \\|   \\\\|//    |/  \\\\|//   ||     /-\\|/_\\ \\\\|//,   /-\\|/_//  ",
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
		art.setColor(COLOR_VERSION,         17,  0);
		// line 2    color                   x   y
		art.setColor(COLOR_GROW,             4,  1);
		art.setColor(COLOR_CONTROL,          8,  1);
		art.setColor(COLOR_FLOWER_C_PEDALS, 18,  1); // Flower C
		art.setColor(COLOR_FLOWER_E_PEDALS, 29,  1); // Flower E
		art.setColor(COLOR_FLOWER_H_PEDALS, 56,  1); // Flower H
		// line 3    color                   x   y
		art.setColor(COLOR_FLOWER_A_PEDALS,  3,  2);
		art.setColor(COLOR_SOFTWARE,         6,  2);
		art.setColor(COLOR_FLOWER_C_PEDALS, 17,  2); // Flower C
		art.setColor(COLOR_FLOWER_E_PEDALS, 28,  2); // Flower E
		art.setColor(COLOR_FLOWER_H_PEDALS, 54,  2); // Flower H
		// line 4    color                   x   y
		art.setColor(COLOR_FLOWER_A_PEDALS,  1,  3); // Flower A
		art.setColor(COLOR_FLOWER_C_PEDALS, 16,  3); // Flower C
		art.setColor(COLOR_FLOWER_E_PEDALS, 28,  3); // Flower E
		art.setColor(COLOR_FLOWER_F_PEDALS, 39,  3); // Flower F
		art.setColor(COLOR_FLOWER_H_PEDALS, 54,  3); // Flower H
		// line 5    color                   x   y
		art.setColor(COLOR_FLOWER_A_PEDALS,  0,  4); // Flower A
		art.setColor(COLOR_FLOWER_C_PEDALS, 17,  4); // Flower C
		art.setColor(COLOR_FLOWER_E_PEDALS, 30,  4); // Flower E
		art.setColor(COLOR_FLOWER_STEM,     31,  4);
		art.setColor(COLOR_FLOWER_E_PEDALS, 33,  4);
		art.setColor(COLOR_FLOWER_F_PEDALS, 39,  4); // Flower F
		art.setColor(COLOR_FLOWER_H_PEDALS, 55,  4); // Flower H
		// line 6    color                   x   y
		art.setColor(COLOR_FLOWER_A_PEDALS,  2,  5); // Flower A
		art.setColor(COLOR_FLOWER_STEM,      6,  5);
		art.setColor(COLOR_FLOWER_F_PEDALS, 39,  5); // Flower F
		art.setColor(COLOR_FLOWER_H_PEDALS, 56,  5); // Flower H
		art.setColor(COLOR_FLOWER_STEM, 58,  5);
		art.setColor(COLOR_FLOWER_H_PEDALS, 59,  5);
		// line 7    color                   x   y
		art.setColor(COLOR_FLOWER_STEM,      4,  6); // Flower A
		art.setColor(COLOR_FLOWER_B_PEDALS, 10,  6); // Flower B
		art.setColor(COLOR_FLOWER_STEM,     17,  6); // Flower C
		art.setColor(COLOR_FLOWER_D_PEDALS, 24,  6); // Flower D
		art.setColor(COLOR_FLOWER_STEM,     29,  6); // Flower E
		art.setColor(COLOR_FLOWER_F_PEDALS, 40,  6); // Flower F
		art.setColor(COLOR_FLOWER_STEM,     41,  6);
		art.setColor(COLOR_FLOWER_F_PEDALS, 42,  6);
		art.setColor(COLOR_FLOWER_G_PEDALS, 47,  6); // Flower G
		art.setColor(COLOR_FLOWER_STEM,     53,  6); // Flower H
		// line 8    color                   x   y
		art.setColor(COLOR_FLOWER_STEM,      4,  7);
		// line 9    color                   x   y
		art.setColor(COLOR_FLOWER_STEM,      5,  8);
		// line 10   color                   x   y
		art.setColor(COLOR_GRASS,            0,  9);
		// line 11   color                   x   y
		art.setColor(COLOR_GRASS,            0, 10);
		// display ascii art
		for (final String line : art.build()) {
			log.publish(line);
		}
	}



}
