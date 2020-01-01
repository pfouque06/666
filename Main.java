package _666_;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java_tools.colorText;
import java_tools.getOpts;

public class Main {
	// get global variables :
	getOpts options;
	static String[] optionArray = {
			"##### DO NOT FORGET FOLLOWING HEADER LINE !! #####",
			"TYPE:KEY:KEYWORD:VALUENAME:VALUETYPE:DETAIL:ACTION:",
			"F:h:help:usage:-:prints this help message:true:",
			"F:a:auto:autoMode:boolean:set auto mode with random roulette (default is OFF):true:",
			"F:c:color:colorMode:boolean:set color mode (default is color):true:",
			"F:m:mono:colorMode:boolean:set monocolor mode (default is color):false:",
			"V:d:deposit:deposit:int:set jetons for deposit required to start a game (default is 0):-:",
			"V:w:warning:jetonWarning:int:set warning spent before alerting per phase (default is 200):-:",
			"V:j:jeton:jetonMax:int:set maximum spent to quit game:-:",
			"V:p:phase:phaseMax:int:set maximum phase to quit game:-:",
			"V:t:tour:tourMax:int:set maximum total tours to quit game:-:",
			"V:g:gain:gainMax:int:set maximum total gain to quit game:-:",
			"V:b:bet:betMax:int:set maximum bets allowed per tour:-:",
			};

	// betMax, jetonLimite, warning, gainMax, phaseMax, toursMax
	public static boolean colorMode = true, autoMode = false;
	public static int deposit=0, betMax = 0, jetonLimite = 0, warning = 200, gainMax = 0, phaseMax = 0, tourMax = 0;

	static String c_black_bold() { return (colorMode ? colorText.BLACK_BOLD : "[["); }
	static String c_red() { return (colorMode ? colorText.RED : "[["); }
	static String c_red_bold() { return (colorMode ? colorText.RED_BOLD : "[["); }
	static String c_red_background() { return (colorMode ? colorText.RED_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	static String c_green() { return (colorMode ? colorText.GREEN : "[["); }
	static String c_green_bold() { return (colorMode ? colorText.GREEN_BOLD : "[["); }
	static String c_green_background() { return (colorMode ? colorText.GREEN_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	static String c_blue() { return (colorMode ? colorText.BLUE : "[["); }
	static String c_blue_bold() { return (colorMode ? colorText.BLUE_BOLD : "[["); }
	static String c_blue_background() { return (colorMode ? colorText.BLUE_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	static String c_purple_background() { return (colorMode ? colorText.CYAN_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	static String c_reset() { return (colorMode ? colorText.RESET : "]]"); }

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
				deposit = Integer.valueOf(fields[3]);
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

	public static String phaseToString(int pPhase, int pPhaseFull) {
		String _phase_ = "";
		_phase_ = String.format("%2s", pPhase) + "/" + String.format("%2s", pPhaseFull);
		return _phase_;
	}
	
	public static String tourToString(int pTours, int pToursTotal, int pToursFull) {
		String _tours_ = "";
		_tours_ = String.format("%3s", pTours)
				+ "/" + String.format("%3s", pToursTotal)
				+ "/" + String.format("%3s", pToursFull);
		return _tours_;
	}
	
	public static String jetonToString(int pJetons, int pJetonsTotal, int pJetonsMax) {
		String _jetons_ = "", _jetonsTotal_ = "", _jetonsMax_ = "";
		_jetons_ = String.format("%3s", pJetons);
		_jetonsTotal_ = String.format("%3s", pJetonsTotal);
		_jetonsMax_ = String.format("%3s", pJetonsMax);
		if (warning != 0) {
			_jetons_ = ((pJetons <= warning) ? c_purple_background() + _jetons_ + c_reset() : _jetons_);
			_jetonsTotal_ = ((pJetonsTotal <= warning) ? c_purple_background() + _jetonsTotal_ + c_reset()
			: _jetonsTotal_);
			_jetonsMax_ = ((pJetonsMax <= warning) ? c_purple_background() + _jetonsMax_ + c_reset()
			: _jetonsMax_);
		}
		if (jetonLimite != 0) {
			_jetons_ = ((pJetons <= jetonLimite) ? c_red_background() + _jetons_ + c_reset() : _jetons_);
			_jetonsTotal_ = ((pJetonsTotal <= jetonLimite) ? c_red_background() + _jetonsTotal_ + c_reset()
					: _jetonsTotal_);
			_jetonsMax_ = ((pJetonsMax <= jetonLimite) ? c_red_background() + _jetonsMax_ + c_reset()
					: _jetonsMax_);
		}
		_jetons_ = _jetons_ + "/" + _jetonsTotal_ + "/" + _jetonsMax_;
		return _jetons_;
	}

	public static String gainToString(int win, int pGain, int pGainTotal, int pGainFull) {
		String _gains_ = "", buffer = "";
		String winFront = win > 0 ? c_green_background() : "";
		String winBack = win > 0 ? c_reset() : "";
		buffer = String.format("%3s", pGain);
		_gains_ = ( pGain < 0 ? c_red_background()  + buffer + c_reset() : winFront + buffer + winBack);
		buffer = String.format("%3s", pGainTotal);
		_gains_ += "/" + ( pGainTotal < 0 ? c_red_background()  + buffer + c_reset() : winFront + buffer + winBack);
		buffer = String.format("%3s", pGainFull);
		_gains_ += "/" + ( pGainFull < 0 ? c_red_background()  + buffer + c_reset() : winFront + buffer + winBack);
		return _gains_;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// parse args :
		// initiate getOpts class and parse args according to file getOptsTable.txt
		// (default)
		// getOpts options = new getOpts();
		// //System.out.println("optionTable=\n"+options.optionTable_toString());
		getOpts options = new getOpts(optionArray); // System.out.println("optionTable=\n"+options.optionTable_toString());
		if (!options.setOptionList(args)) {
			options.getUsage(System.out); // use STDOUT when help is requested
			return;
		}
		// System.out.println("optionList=\n"+options.optionList_toString());
		if (!setOpts(options.getOptionList())) {
			options.getUsage(System.out); // use STDOUT when help is requested
			return;
		}

		Table table = new Table(betMax);
		Scanner sc = new Scanner(System.in);
		int roulette = 0;
		int phase = 0, phaseFull = 0;
		int tours = 0, toursTotal = 0, toursFull = 0;
		int depositOrigin = deposit, jetons = deposit, jetonsTotal = deposit, jetonsMax = deposit, coef = 1, nbrMise = 0;
		int win = 0, gain = 0, gainTotal = 0, gainFull = 0;
		boolean gameOver = false, autoPlay = autoMode;
		String alert = "";
		// get global variables :
		// betMax, gainMax, phaseMax, toursMax, jetonLimite
		String _phase_ = phaseToString(0, 0),
				_tours_ = tourToString(0, 0, 0),
				_jetons_ = jetonToString(jetons, jetonsTotal, jetonsMax),
				_gains_ = gainToString(0, 0, 0, 0), bets = "", input = "";

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
			if (table.isFull()
					|| (gainMax != 0 ? gainTotal >= gainMax : false)
					|| (phaseMax != 0 && tours == 0 ? phase >= phaseMax : false)
					|| (tourMax != 0 && tours == 0 ? toursTotal >= tourMax : false)) {
				System.out.println();
				gameOver = true; // launch exit menu
			}

			// Check Mise versus Jetons
			if ( jetons < nbrMise ) {
				System.out.println();
				System.out.println("--> " + c_red_background() + "CAN'T BET THIS AMOUNT !!!!!" + c_reset());
				gameOver = true; // launch exit menu
				alert = "bet";  // set alert
			}
				
			// Check Jetons limites
			if (jetonLimite != 0 && jetons <= jetonLimite) {
				System.out.print("--> " + c_red_background() + "WALLET LIMITE REACHED !!!!!" + c_reset());
				//gainTotal -= jetons;
				//gainFull -= jetons;
				if (gainTotal >= 0)
					System.out.println("--> Gains: " + c_green_background() + String.format("%3s", gainTotal) + c_reset());
				else
					System.out.println("--> Gains: " + c_red_background() + String.format("%3s", gainTotal) + c_reset());
				gameOver = true; // launch exit menu
				alert = "jeton"; // set alert
			}

			// run exit prompt
			if (gameOver) {
				input = "";
				if (alert.isEmpty()) System.out.println("--> " + c_black_bold() + "Game is over" + c_reset());
				do {
					String autoMode_ = "|(a)uto: " + (autoMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
					String colorMode_ = "|(m)ode: " + (colorMode ? c_green() + "color" : c_red() + "mono") + c_reset();
					System.out.print("--> [(q)uit|(CR|r)estart|(p)urge store|(o)ptions"	+  colorMode_ + autoMode_ + "]: ");
					input = sc.nextLine();
					input = (input.isEmpty() ? "r" : input.substring(0, 1));
					switch (input) {
					case "q":
						// System.out.println("##1.1");
						sc.close();
						return;
					case "p":
						// System.out.println("##1.2 - start reducing Store");
						if (alert.isEmpty()) {
							if (!table.reduceStore()) {
								System.out.println(
										c_red_bold() + "Store is empy" + c_reset() + " .. Can't purge store, sorry !");
								input = "";
							}
						} else {
							System.out.println(
									c_red_bold() + "Alert is raised" + c_reset() + " .. Can't purge store, sorry !");
							input = "";
						}
						break;
					case "r":
						// System.out.println("##1.3 - reset table");
						table.resetTable();
						roulette = 0;
						phase = 0;
						tours = 0;
						toursTotal = 0;
						coef = 1;
						nbrMise = 0;
						gain = 0;
						gainTotal = 0;
						if (! alert.isEmpty()) {
							gainFull -= deposit - jetons;
						}
						deposit = depositOrigin;
						jetons = deposit;
						jetonsTotal = deposit;
						_phase_ = phaseToString(0, phaseFull);
						_tours_ = tourToString(0, 0, toursFull);
						_jetons_ = jetonToString(jetons, jetonsTotal, jetonsMax);
						_gains_ = gainToString(win, 0, 0, gainFull);
						bets = "";
						break;
					case "o":
						// System.out.println("##1.4");
						// reset input in order to jump back to menu
						input = "";
						String buffer = "";

						do {
							// display actual options :
							System.out.println("options:" + optsToString());

							// get new options args_
							System.out.print("--> new options (CR: exit): ");
							buffer = sc.nextLine();
							// System.out.println("buffer=" + buffer);
							if (buffer.isEmpty())
								break;

							// String[] args_=buffer.split("(?!^)");
							String[] args_ = buffer.split(" ");

							// get initial deposit value and reset deposit
							int depositHold = deposit;
							deposit = 0;
							
							// parse options args_
							if (!options.setOptionList(args_))
								System.out.println("Parsing error, please retry or use -h, --help to get usage ...");
							else if (!setOpts(options.getOptionList()))
								options.getUsage(System.out); // use STDOUT when help is requested
					
							// check if deposit is added
							if ( deposit > 0) {
								// add new deposit to jetons and to initial value
								jetons += deposit;
								deposit += depositHold;
								_jetons_ = jetonToString(jetons, jetonsTotal, jetonsMax);

								// Check Mise versus Jetons
								if (alert.equals("bet"))
									if ( jetons >= nbrMise) {
										System.out.println("--> " +c_green_bold() + "wallet is up and alive again !!" + c_reset());
										input = "go on";
									}
							} else // return initial deposit value
								deposit = depositHold;

						} while (!buffer.isEmpty());
						break;
					case "m":
						// System.out.println("##1.5");
						// toggle colorMode
						colorMode = (colorMode ? false : true);
						input = "";
						break;
					case "a":
						// System.out.println("##1.6");
						// toggle auto mode
						autoMode = (autoMode ? false : true);
						input = "";
						break;
					default:
						// System.out.println("##1.4 - default case");
						input = "";
						break;
					}
					
					if (input.isEmpty()) {
						// display Global Status
						System.out.println();
						System.out.print("Phase: " + _phase_ + " | ");
						System.out.print("Tour: " + _tours_ + " | ");
						System.out.print("Jetons: " + _jetons_ + " | ");
						System.out.print("Gains: " + _gains_ + " | ");
						System.out.println(bets);
					}

				} while (input.isEmpty());
				// System.out.println("##2.1");
				autoPlay = autoMode;
				gameOver = false;
				alert = "";
				// System.out.println("##2.2");
				// break;
			}
			// System.out.println("##3");

			// get input from user
			else {
				// System.out.println("##4.1");
				do {
					do {
						input = "";
						if (autoPlay)
							input = "r";
						else {
							System.out.println();
							System.out.print("--> Roulette [num|(CR|r)andom|(a)uto|(q)uit]: ");
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
							autoPlay = true;
						case "r":
							int delay = 0; // 0.1sec per delay
							input = String.valueOf(table.getRandomRoulette(delay));
							break;
						}
					} while (!input.matches("\\d+"));
					roulette = Integer.valueOf(input);
				} while (roulette > 36);

				// increments phase, counters and set display
				if (tours == 0) {
					phase++;
					phaseFull++;
				}
				tours++;
				toursTotal++;
				toursFull++;
				jetons -= nbrMise;
				jetonsTotal = (jetons < jetonsTotal ? jetons : jetonsTotal);
				jetonsMax = (jetons < jetonsMax ? jetons : jetonsMax);

				// prepare display
				_phase_ = phaseToString(phase, phaseFull);
				_tours_ = tourToString(tours, toursTotal, toursFull);

				// set win consequences
				win = 0;
				if (table.betsContains(roulette)) {
					win = 36 * coef;
					jetons += win;
					gain = jetons - deposit;
					deposit = (jetons > deposit) ? jetons : deposit;
					gainTotal += gain;
					gainFull += gain;
					tours = 0;
					coef = 1;
				}
				_jetons_ = jetonToString(jetons, jetonsTotal, jetonsMax);
				_gains_ = gainToString(win, gain, gainTotal, gainFull);

				// add roulette value to table
				table.addOccurence(roulette);
			}

			// get Bets suggestions
			bets = "";
			table.setBets();
			if (!table.isBetsEmpty()) {
				nbrMise = table.getBetsSize();
				int delta = deposit - jetons;
				delta = ( delta > 0 ? delta : 0);
				coef = ( delta + nbrMise * coef) / 36 + 1;
				nbrMise *= coef;
				bets = "--> Bets  : " + table.getBets() + " (x" + coef + ") => mise: " + nbrMise;
			}
		}
	}
}
