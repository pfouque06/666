package _666_;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java_tools.colorText;
//import javaUtils.argOpts;

public class Main {

	// get global variables :
	// betMax, jetonLimite, warning, gainMax, phaseMax, toursMax
	public static boolean colorMode = true, auto = false;
	public static int betMax = 0, jetonLimite = 0, warning = 200, gainMax = 0, phaseMax = 0, toursMax = 0;
	static String c_black_bold() { return (colorMode ? colorText.BLACK_BOLD : "[["); }
	static String c_red() { return ( colorMode ? colorText.RED : "[["); }
	static String c_red_bold () { return ( colorMode ? colorText.RED_BOLD : "[["); }
	static String c_red_background () { return (colorMode ? colorText.RED_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	static String c_green () { return  ( colorMode ? colorText.GREEN : "[["); }
	static String c_green_bold () { return  ( colorMode ? colorText.GREEN_BOLD : "[["); }
	static String c_green_background () { return  (colorMode ? colorText.GREEN_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	static String c_blue () { return  ( colorMode ? colorText.BLUE : "[["); }
	static String c_blue_bold () { return  ( colorMode ? colorText.BLUE_BOLD : "[["); }
	static String c_blue_background () { return  (colorMode ? colorText.BLUE_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	static String c_reset () { return  (colorMode ? colorText.RESET : "]]"); }


	public static boolean argOpt(String[] pArgs) {
		String buffer = "";
		// File file = null;

		//argOpts.argOpt(pArgs);
		for (int i = 0; i < pArgs.length; i++) {
			if ("--help".equals(pArgs[i]) || "-h".equals(pArgs[i])) {
				usage(System.out); // use STDOUT when help is requested
				return false;
			}

			// set colorMode flag
			else if ("--mono".equals(pArgs[i]) || "-m".equals(pArgs[i])) {
				colorMode = false; // toggle
				System.out.println("colorMode=" + colorMode);
			}

			// set auto flag
			else if ("--auto".equals(pArgs[i]) || "-a".equals(pArgs[i])) {
				auto = true; // toggle
				System.out.println("auto=" + auto);
			}

			// set betMax
			else if ("--bet".equals(pArgs[i]) || "-b".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					// usage(System.err);
					return false;
				}
				buffer = pArgs[++i];
				if (!buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					// usage(System.err);
					return false;
				}
				betMax = Integer.valueOf(buffer);
				System.out.println("betMax=" + betMax);
			}

			// set jetonMax
			else if ("--jetons".equals(pArgs[i]) || "-j".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					// usage(System.err);
					return false;
				}
				buffer = pArgs[++i];
				if (!buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					// usage(System.err);
					return false;
				}
				jetonLimite = Integer.valueOf(buffer);
				System.out.println("jetonMax=" + jetonLimite);
			}

			// set gainMax
			else if ("--gain".equals(pArgs[i]) || "-g".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					// usage(System.err);
					return false;
				}
				buffer = pArgs[++i];
				if (!buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					// usage(System.err);
					return false;
				}
				gainMax = Integer.valueOf(buffer);
				System.out.println("gainMax=" + gainMax);
			}

			// set phaseMax
			else if ("--phase".equals(pArgs[i]) || "-p".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					// usage(System.err);
					return false;
				}
				buffer = pArgs[++i];
				if (!buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					// usage(System.err);
					return false;
				}
				phaseMax = Integer.valueOf(buffer);
				System.out.println("phaseMax=" + phaseMax);
			}

			// set toursMax
			else if ("--tours".equals(pArgs[i]) || "-t".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					// usage(System.err);
					return false;
				}
				buffer = pArgs[++i];
				if (!buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					// usage(System.err);
					return false;
				}
				toursMax = Integer.valueOf(buffer);
				System.out.println("toursMax=" + toursMax);
			}

			// set warning
			else if ("--warning".equals(pArgs[i]) || "-w".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					// usage(System.err);
					return false;
				}
				buffer = pArgs[++i];
				if (!buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					// usage(System.err);
					return false;
				}
				warning = Integer.valueOf(buffer);
				System.out.println("warning=" + warning);
			}

			// else if ("--file".equals(pArgs[i]) || "-f".equals(pArgs[i])) {
			// if (i == pArgs.length - 1) {
			// System.err.println("Error: missing argument for " + pArgs[i]);
			// usage(System.err);
			// return;
			// }
			// file = new File(pArgs[++i]);
			// }
		}
		return true;
	}

	public static void argToString() {

		System.out.print("");
		System.out.print(" auto: " + (auto? c_green() + "ON" : c_red() + "OFF" ) + c_reset());
		System.out.print(" mode: " + (colorMode? c_green() + "color" : c_red() + "mono" ) + c_reset());
		if (warning > 0 )
			System.out.print(" warning: " + c_blue() + warning + c_reset());
		if (jetonLimite > 0 )
			System.out.print(" jetonLimite: " + c_blue() + jetonLimite + c_reset());
		if (betMax > 0 )
			System.out.print(" betMax: " + c_blue() + betMax + c_reset());
		if (gainMax > 0 )
			System.out.print(" gainMax: " + c_blue() + gainMax + c_reset());
		if (phaseMax > 0 )
			System.out.print(" phaseMax: " + c_blue() + phaseMax + c_reset());
		if (toursMax > 0 )
			System.out.print(" toursMax: " + toursMax );
		System.out.println();

	}

	private static void usage(PrintStream ps) {
		ps.println("Usage: 666 [-m] [-a] [-b|--bet <betMax>] [-j|--jetons <jetonMax>] [-g|--gain <gainMax>] [-p|--phase <phaseMax>] [-t|--tours <toursMax>] [-w|--warning <jetons>]");
		ps.println("Options:");
		ps.println("	-m, --mono				set monocolor mode (color mode par default");
		ps.println("	-a, --auto				set auto mode with random roulette");
		ps.println("	-b, --bet <betMax>		set maximum bets allowed per tour");
		ps.println("	-j, --jetons <jetonMax>	set maximum jeton before alerting per phase");
		ps.println("	-g, --gain <gainMax>	set maximum total gain before quit game");
		ps.println("	-p, --phase <phaseMax>	set maximum phase before quit game");
		ps.println("	-t, --tours <toursMax>	set maximum total tours before quit game");
		ps.println("	-w, --warning <jetons>	set jetons in warning (200 par défaut)");
		ps.println();
		ps.println("	-h, --help				Prints this help message and exits");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// parse agrs
		if (!argOpt(args))
			return;

		Table table = new Table(betMax);
		Scanner sc = new Scanner(System.in);
		int roulette = 0;
		int phase = 0, phaseFull = 0;
		int tours = 0, toursTotal = 0, toursFull = 0;
		int jetons = 0, jetonsTotal = 0, jetonsMax = 0, coef = 1, nbrMise = 0;
		int gain = 0, gainTotal = 0, gainFull = 0;
		boolean gameOver = false, autoMode = auto;
		String alert = "";
		// get global variables :
		// betMax, gainMax, phaseMax, toursMax, jetonLimite
		String _phase_ = " 0/ 0", _tours_ = "  0/  0/  0", _jetons_ = "  0/  0/  0", _jetonsTotal_ = "", _jetonsMax_ = "", _gains_ = "  0/  0/   0",
				bets = "", winFront = "", winBack = "", input = "";

		while (true) {
			// display Table
			System.out.println();
			System.out.print("Table ");
			System.out.println((table.isStoreEmpty() ? "" : table.getStore(20)) + ":");
			System.out.println();
			System.out.print(table.betToString(colorMode));

			// display Global Status
			System.out.println();
			System.out.print("Phase: " + _phase_ + " | ");
			System.out.print("Tour: " + _tours_ + " | ");
			System.out.print("Jetons: " + _jetons_ + " | ");
			System.out.print("Gains: " + _gains_ + " | ");
			System.out.println(bets);

			// ---------------------
			// exit conditions check
			// ---------------------

			// if table is full (all values are completed)
			// if gainMax, phaseMax or toursMax are reached
			if (table.isFull() || (gainMax != 0 ? gainTotal >= gainMax : false)
					|| (phaseMax != 0 && tours == 0 ? phase >= phaseMax : false)
					|| (toursMax != 0 && tours == 0 ? toursTotal >= toursMax : false)) {
				System.out.println();
				gameOver = true;
			}

			// Check Jetons limites
			if (jetonLimite != 0 && jetons >= jetonLimite) {
				System.out.print("--> " + c_red_background() + "WALLET LIMITE REACHED !!!!!" + c_reset());
				gainTotal -= jetons;
				gainFull -= jetons;
				if (gainTotal >= 0)
					System.out.println("--> Gains: " + c_green_background() + String.format("%3s", gainTotal) + c_reset());
				else
					System.out.println("--> Gains: " + c_red_background() + String.format("%3s", gainTotal) + c_reset());
				gameOver = true;
				alert = "jeton";
			}

			// run exit prompt
			if (gameOver) {
				input = "";
				System.out.println(c_black_bold() + "Game is over" + c_reset());
				do {
					String auto_ = (auto? c_green() + "ON" : c_red() + "OFF" ) + c_reset();
					String colorMode_ = (colorMode? c_green() + "color" : c_red() + "mono" ) + c_reset();
					System.out.print("--> [(q)uit|(CR|r)estart|(p)urge store|(o)ptions|(m)ode: "+colorMode_+"|(a)uto: " + auto_ +"]: ");
					input = sc.nextLine();
					input = (input.isEmpty() ? "r" : input.substring(0, 1));
					switch (input) {
					case "q":
						//System.out.println("##1.1");
						sc.close();
						return;
					case "p":
						//System.out.println("##1.2 - start reducing Store");
						if (alert.isEmpty()) {
							if ( ! table.reduceStore()) {
								System.out.println(c_red_bold() + "Store is empy" + c_reset() + " .. Can't purge store, sorry !");
								input = "";
							}
						}
						else {
							System.out.println(c_red_bold() + "Alert is raised" + c_reset() + " .. Can't purge store, sorry !");
							input = "";
						}
						break;
					case "r":
						// System.out.println("##1.3 - reset table");
						table.resetTable();
						roulette = 0; phase = 0; tours = 0; toursTotal = 0;
						jetons = 0; jetonsTotal = 0; coef = 1; nbrMise = 0;
						gain = 0; gainTotal = 0;
						_phase_ = " 0";
						_tours_ = "  0/  0" + "/" + String.format("%3s", toursFull);
						_jetons_ = "  0/  0"; _jetonsTotal_ = "";
						_gains_ = "  0/  0/" + String.format("%3s", gainFull);
						bets = "";
						break;
					case "o":
						//System.out.println("##1.4");
						//reset input in order to jump back to menu
						input = "";
						String buffer = "";

						do {
							// display actual options :
							System.out.print("options:"); argToString();

							// get new options args_
							System.out.print("--> new options (CR: exit): ");
							buffer = sc.nextLine();
							//System.out.println("buffer=" + buffer);
							if (buffer.isEmpty()) break;

							//String[] args_=buffer.split("(?!^)");
							String[] args_=buffer.split(" ");
							//if (args_.length > 0) {
							//	for (String arg_ : args_) System.out.print(arg_ + " ");
							//	System.out.println();
							//}

							// parse options args_
							if (!argOpt(args_))
								System.out.println("Parsing error, please retry ...");
						} while ( ! buffer.isEmpty() );

						// display new options :
						//System.out.print("options:"); argToString();
						break;
					case "m":
						// System.out.println("##1.5");
						// toggle colorMode
						colorMode = (colorMode? false : true );
						input = "";
						break;
					case "a":
						// System.out.println("##1.6");
						// toggle auto mode
						auto = (auto? false : true );
						// autoMode = auto;
						input = "";
						break;
					default:
						// System.out.println("##1.4 - default case");
						input = "";
						break;
					}
				} while (input.isEmpty());
				// System.out.println("##2.1");
				autoMode = auto; gameOver = false; alert = "";
				// System.out.println("##2.2");
				// break;
			}
			// System.out.println("##3");

			// get input from user
			else {
				//System.out.println("##4.1");
				do {
					do {
						input = "";
						System.out.println();
						System.out.print("--> Roulette [num|(CR|r)andom|(a)uto|(q)uit]: ");
						if (autoMode)
							input = "r";
						else {
							input = sc.nextLine();
							if (input.matches("\\d+"))
								break;
							input = (input.isEmpty() ? "r" : input.substring(0, 1));
						}
						switch (input) {
						case "q":
							System.out.println("\nExiting...");
							sc.close();
							return;
						case "a":
							autoMode = true;
						case "r":
							// System.out.print("get Random Roulette...");
							roulette = new Random().nextInt(37);
							int delay = 0; // 0.1sec per delay
							while (delay-- > 0) {
								try {
									TimeUnit.MILLISECONDS.sleep(100);
								} // or try { TimeUnit.SECONDS.sleep(1); }
								catch (InterruptedException e) {
								/* empty */ } // or e.printStackTrace();
								// System.out.print(".");
							}
							;
							input = String.valueOf(roulette);
							// System.out.println(" ["+input+"]");
							break;
						}
					} while (!input.matches("\\d+"));
					roulette = Integer.valueOf(input);
				} while (roulette > 36);

				// increments phase, counters and set display
				if (tours == 0) {
					phase++;
					phaseFull ++;
				}
				tours++; toursTotal++; toursFull++;
				jetons += nbrMise;
				jetonsTotal = (jetons > jetonsTotal ? jetons : jetonsTotal);
				jetonsMax = (jetons > jetonsMax ? jetons : jetonsMax);
				_phase_ = String.format("%2s", phase) + "/" + String.format("%2s", phaseFull);
				_tours_ = String.format("%3s", tours) + "/" + String.format("%3s", toursTotal) + "/" + String.format("%3s", toursFull);
				_jetons_ = String.format("%3s", jetons);
				_jetonsTotal_ = String.format("%3s", jetonsTotal);
				_jetonsMax_ = String.format("%3s", jetonsMax);
				if (warning != 0) {
					_jetons_ = ((jetons > warning) ? c_red_background() + _jetons_ + c_reset() : _jetons_);
					_jetonsTotal_ = ((jetonsTotal > warning) ? c_red_background() + _jetonsTotal_ + c_reset() : _jetonsTotal_);
					_jetonsMax_ = ((jetonsMax > warning) ? c_red_background() + _jetonsMax_ + c_reset() : _jetonsMax_);
				}
				_jetons_ = _jetons_ + "/" + _jetonsTotal_ + "/" + _jetonsMax_;

				// set win consequences
				winFront = "";
				winBack = "";
				if (table.betsContains(roulette)) {
					gain = 36 * coef - jetons;
					gainTotal += gain;
					gainFull += gain;
					tours = 0;
					jetons = 0;
					coef = 1;
					// win= c_green_bold + " !!! WIN !!! " + c_reset;
					winFront = c_green_background();
					if ( (gain < 0) || (gainTotal < 0) || (gainFull < 0) )
						winFront = c_red_background();
					winBack = c_reset();
				}
				_gains_ = winFront + String.format("%3s", gain) + "/" + String.format("%3s", gainTotal) + "/"
						+ String.format("%4s", gainFull) + winBack;

				// add roulette value to table
				table.addOccurence(roulette);
			}

			// get Bets suggestions
			bets = "";
			table.setBets();
			if (!table.isBetsEmpty()) {
				nbrMise = table.getBetsSize();
				coef = (jetons + nbrMise * coef) / 36 + 1;
				nbrMise *= coef;
				bets = "--> Bets  : " + table.getBets() + " (x" + coef + ") => mise: " + nbrMise;
			}
		}
	}
}
