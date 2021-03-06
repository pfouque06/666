package _666_;

import java.util.LinkedHashSet;

import javaTools.ColorText;
import javaTools.GetOpts;
import javaTools.Logger;

public class Main {

	// public global argument mgt
	static final String[] optionArray = { "##### DO NOT FORGET FOLLOWING HEADER LINE !! #####",
			"TYPE:KEY:KEYWORD:VALUENAME:VALUETYPE:DETAIL:ACTION:", "F:h:help:usage:-:prints this help message:true:",
			"F:G:gui:guiMode:boolean:set GUI mode (default mode):true:",
			"F:T:text:guiMode:boolean:set CLI mode (default is GUI mode):false:",
			"F:S:sim:simMode:boolean:set simulation mode ON (default is OFF):true:",
			"F:R:real:simMode:boolean:set simulation mode OFF (default):false:",
			"F:a:auto:autoMode:boolean:set auto mode with random roulette (default is step mode):true:",
			"F:s:step:autoMode:boolean:set step by step mode with roulette (default):false:",
			"F:c:color:colorMode:boolean:set color mode (default is color):true:",
			"F:m:mono:colorMode:boolean:set monocolor mode (default is color):false:",
			"F:L:log:logging:boolean:set logging mode (to console/terminal):true:",
			"V:d:deposit:deposit:int:add jetons to deposit - NON Zero amount required to start a game (default is 0):-:",
			"V:w:warning:jetonWarning:int:set limit value of spent jetons to alert gamer (default is half of deposit):-:",
			"V:l:limite:jetonLimite:int:set maximum spent jetons to quit game cycle :-:",
			"V:p:phase:phaseMax:int:set maximum phase to quit game cycle:-:",
			"V:t:tour:tourMax:int:set maximum total tours to quit game cycle:-:",
			"V:g:gain:gainMax:int:set maximum total gain to quit game cycle:-:",
			"V:b:bet:betMax:int:set maximum bets allowed per tour:-:", };
	public static GetOpts options;

	// set options values according to system args parsing
	public static boolean setOptions(LinkedHashSet<String[]> pList) {

		// option status before processing
		if (! options.isEnabled())
			return false;

		// Loop on each options of pList
		for (String[] fields : pList) {
			// System.out.println("fields="+fields.toString() );
			switch (fields[2]) {
			case "guiMode": // define text or gui mode
				guiMode = fields[3].equals("true");
				// System.out.println("colorMode=" + colorMode);
				break;
			case "simMode": // define simulation mode
				simMode = fields[3].equals("true");
				// System.out.println("colorMode=" + colorMode);
				break;
			case "colorMode":
				colorMode = fields[3].equals("true");
				// System.out.println("colorMode=" + colorMode);
				break;
			case "autoMode":
				autoMode = fields[3].equals("true");
				// System.out.println("auto=" + auto);
				break;
				// logging
			case "logging":
				logger.setHandler("syso");
				// System.out.println("logging to System.out");
				break;
			case "deposit":
				deposit += Integer.valueOf(fields[3]);
				// System.out.println("deposit=" + deposit);
				break;
			case "jetonWarning":
				warning = Integer.valueOf(fields[3]);
				// System.out.println("warning=" + warning);
				break;
			case "jetonLimite":
				jetonLimite = Integer.valueOf(fields[3]);
				// System.out.println("jetonLimite=" + jetonLimite);
				break;
			case "phaseMax":
				phaseMax = Integer.valueOf(fields[3]);
				// System.out.println("phaseMax=" + phaseMax);
				break;
			case "tourMax":
				tourMax = Integer.valueOf(fields[3]);
				// System.out.println("tourMax=" + tourMax);
				break;
			case "gainMax":
				gainMax = Integer.valueOf(fields[3]);
				// System.out.println("gainMax=" + gainMax);
				break;
			case "betMax":
				betMax = Integer.valueOf(fields[3]);
				// System.out.println("betMax=" + betMax);
				break;
			default:
				System.err.println("Error: option " + fields[2] + " unknown");
			case "usage":
				return false;
			}
		}
		if (warning == 0)
			warning = deposit / 2;
		return true;
	}

	// provide artifact setOptions public method to CLI
	public static boolean setOptions(String[] pArgs) {
		// parse options pArgs
		if (!options.setOptionList(pArgs)) {
			logger.logging("getOpts Error : Parsing error, please retry or use -h, --help to get usage ...");
			return false;
		}
		// check options status and set options according to args parsing
		if ( ! setOptions(options.getOptionList()) ) {
			System.out.println(options.getUsage()); // display usage is requested
			return false;
		}
		return true;
	}

	// option value display
	public static String optsToString() {
		String buffer = "";

		buffer += " gui: " + (guiMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		buffer += " sim: " + (simMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		buffer += " auto: " + (autoMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		buffer += " mode: " + (colorMode ? c_green() + "color" : c_red() + "mono") + c_reset();
		if (deposit > 0)
			buffer += " deposit: " + c_blue() + deposit + c_reset();
		if (warning > 0)
			buffer += " warning: " + c_blue() + warning + c_reset();
		if (jetonLimite > 0)
			buffer += " jetonLimite: " + c_blue() + jetonLimite + c_reset();
		if (betMax > 0)
			buffer += " betMax: " + c_blue() + betMax + c_reset();
		if (gainMax > 0)
			buffer += " gainMax: " + c_blue() + gainMax + c_reset();
		if (phaseMax > 0)
			buffer += " phaseMax: " + c_blue() + phaseMax + c_reset();
		if (tourMax > 0)
			buffer += " toursMax: " + c_blue() + tourMax + c_reset();
		return buffer;

	}

	// init logger
	static Logger logger = new Logger();

	// public global variables
	public static boolean guiMode = true, simMode = false, colorMode = true, autoMode = false;
	public static int deposit = 0, betMax = 0, jetonLimite = 0, warning = 0, gainMax = 0, phaseMax = 0, tourMax = 0;

	// local methods
	static String c_red() { return (colorMode ? ColorText.RED : "[["); }
	static String c_green() { return (colorMode ? ColorText.GREEN : "[["); }
	static String c_blue() { return (colorMode ? ColorText.BLUE : "[["); }
	static String c_reset() { return (colorMode ? ColorText.RESET : "]]"); }

	public static void main(String[] args) {

		// initiate getOpts options and parse args :
		options = new GetOpts(optionArray, args);
		// check options status and set options according to args parsing
		if ( ! setOptions(options.getOptionList()) ) {
			System.out.println(options.getUsage()); // display usage is requested
			return;
		}
		logger.logging("options:" + optsToString());

//		if (guiMode) {
//			// force colorMode to false, instantiate GUI JFrame and run it
//			colorMode = false;
//			GUI frame = new GUI();
//			frame.run();
//		} else {
//			// Core instance and run
//			Core core = new Core();
//			core.run();
//		}

		// Core instance and run
		Core core = new Core();
		core.run();
	}

}
