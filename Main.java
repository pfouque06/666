package _666_;

import java.util.LinkedHashSet;
import java_tools.colorText;
import java_tools.getOpts;

public class Main {
	// public global argument mgt
	static final String[] optionArray = {
		"##### DO NOT FORGET FOLLOWING HEADER LINE !! #####",
		"TYPE:KEY:KEYWORD:VALUENAME:VALUETYPE:DETAIL:ACTION:",
		"F:h:help:usage:-:prints this help message:true:",
		"F:a:auto:autoMode:boolean:set auto mode with random roulette (default is OFF):true:",
		"F:c:color:colorMode:boolean:set color mode (default is color):true:",
		"F:m:mono:colorMode:boolean:set monocolor mode (default is color):false:",
		"V:d:deposit:deposit:int:add jetons to deposit - NON Zero amount required to start a game (default is 0):-:",
		"V:w:warning:jetonWarning:int:set warning spent before alerting per phase (default is half of deposit):-:",
		"V:j:jeton:jetonMax:int:set maximum spent to quit game:-:",
		"V:p:phase:phaseMax:int:set maximum phase to quit game:-:",
		"V:t:tour:tourMax:int:set maximum total tours to quit game:-:",
		"V:g:gain:gainMax:int:set maximum total gain to quit game:-:",
		"V:b:bet:betMax:int:set maximum bets allowed per tour:-:",
		};
	public static getOpts options = new getOpts(optionArray);
	
	// public global variables
	public static boolean colorMode = true, autoMode = false;
	public static int deposit=0, betMax = 0, jetonLimite = 0, warning = 0, gainMax = 0, phaseMax = 0, tourMax = 0;
	
	// local methods
	static String c_red() { return (colorMode ? colorText.RED : "[["); }
	static String c_green() { return (colorMode ? colorText.GREEN : "[["); }
	static String c_blue() { return (colorMode ? colorText.BLUE : "[["); }
	static String c_reset() { return (colorMode ? colorText.RESET : "]]"); }

	public static boolean setOpts(String[] pArgs) {

		//System.out.println("optionTable=\n"+options.optionTable_toString());
		// parse options pArgs
		if (!options.setOptionList(pArgs)) {
			System.out.println("Parsing error, please retry or use -h, --help to get usage ...");
			return false;
		}
		//System.out.println("optionList=\n"+options.optionList_toString());
		// set options
		if (!setOpts(options.getOptionList())) {
			options.getUsage(System.out); // use STDOUT when help is requested
			return false;
		}
		//System.out.println("options:" + optsToString());
		return true;
	}
	
	public static boolean setOpts(LinkedHashSet<String[]> pList) {
		// Loop on each options of pList
		for (String[] fields : pList) {
			//System.out.println("fields="+fields.toString() );
			switch (fields[2]) {
			case "colorMode":
				colorMode = fields[3].equals("true");
				// System.out.println("colorMode=" + colorMode);
				break;
			case "autoMode":
				autoMode = fields[3].equals("true");
				// System.out.println("auto=" + auto);
				break;
			case "deposit":
				deposit += Integer.valueOf(fields[3]);
				// System.out.println("deposit=" + deposit);
				break;
			case "jetonWarning":
				warning = Integer.valueOf(fields[3]);
				// System.out.println("warning=" + warning);
				break;
			case "jetonMax":
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
				System.err.println("Error: option > valuename unknown");
			case "usage":
				return false;
			}
		}
		if (warning == 0)
			warning = deposit / 2;
		return true;
	}

	public static String optsToString() {

		String buffer = "";

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// parse args :
		// initiate getOpts class and parse args according to optionArray
		if (!setOpts(args))
				return;

		// Core instance
		Core core = new Core();
		core.Run();

	}

//	static String c_black_bold() { return (colorMode ? colorText.BLACK_BOLD : "[["); }
//	static String c_red_bold() { return (colorMode ? colorText.RED_BOLD : "[["); }
//	static String c_red_background() { return (colorMode ? colorText.RED_BACKGROUND + colorText.WHITE_BOLD : "[["); }
//	static String c_green_bold() { return (colorMode ? colorText.GREEN_BOLD : "[["); }
//	static String c_green_background() { return (colorMode ? colorText.GREEN_BACKGROUND + colorText.WHITE_BOLD : "[["); }
//	static String c_blue_bold() { return (colorMode ? colorText.BLUE_BOLD : "[["); }
//	static String c_blue_background() { return (colorMode ? colorText.BLUE_BACKGROUND + colorText.WHITE_BOLD : "[["); }
//	static String c_purple_background() { return (colorMode ? colorText.CYAN_BACKGROUND + colorText.WHITE_BOLD : "[["); }

}
