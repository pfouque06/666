package _666_;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

	// get global variables :
	// betMax, jetonLimite, warning, gainMax, phaseMax, toursMax
	public static boolean colorMode=true, auto=false;
	public static int betMax=0, jetonLimite=0, warning=200, gainMax=0, phaseMax=0, toursMax=0;

	public static boolean argOpt(String[] pArgs) {
		String buffer="";
		//File file = null;

		for (int i = 0; i < pArgs.length; i++) {
			if ("--help".equals(pArgs[i]) || "-h".equals(pArgs[i])) {
				usage(System.out); // use STDOUT when help is requested
				return false;
			}

			// set colorMode flag
			else if ("--mono".equals(pArgs[i]) || "-m".equals(pArgs[i])) {
				colorMode = false; // toggle
				System.out.println("colorMode="+colorMode);
			}

			// set auto flag
			else if ("--auto".equals(pArgs[i]) || "-a".equals(pArgs[i])) {
				auto = true; // toggle
				System.out.println("auto="+auto);
			}

			// set betMax
			else if ("--bet".equals(pArgs[i]) || "-b".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
				betMax=Integer.valueOf(buffer);
				System.out.println("betMax="+betMax);
			}

			// set jetonMax
			else if ("--jetons".equals(pArgs[i]) || "-j".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
				jetonLimite=Integer.valueOf(buffer);
				System.out.println("jetonMax="+jetonLimite);
			}

			// set gainMax
			else if ("--gain".equals(pArgs[i]) || "-g".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
				gainMax=Integer.valueOf(buffer);
				System.out.println("gainMax="+gainMax);
			}

			// set phaseMax
			else if ("--phase".equals(pArgs[i]) || "-p".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
				phaseMax=Integer.valueOf(buffer);
				System.out.println("phaseMax="+phaseMax);
			}

			// set toursMax
			else if ("--tours".equals(pArgs[i]) || "-t".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
				toursMax=Integer.valueOf(buffer);
				System.out.println("toursMax="+toursMax);
			}

			// set warning
			else if ("--warning".equals(pArgs[i]) || "-w".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
				warning=Integer.valueOf(buffer);
				System.out.println("warning="+warning);
			}

			//			else if ("--file".equals(pArgs[i]) || "-f".equals(pArgs[i])) {
			//				if (i == pArgs.length - 1) {
			//					System.err.println("Error: missing argument for " + pArgs[i]);
			//					usage(System.err);
			//					return;
			//				}
			//				file = new File(pArgs[++i]);
			//			}
		}
		return true;
	}

	private static void usage(PrintStream ps) {
		ps.println("Usage: myapp [-x|--flag] --count=NUM --file=FILE");
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
		if( ! argOpt(args) ) return;

		Table table = new Table(betMax);
		Scanner sc = new Scanner(System.in);
		int roulette=0;
		int phase=0, phaseFull=0;
		int tours=0, toursTotal=0;
		int jetons=0, jetonsMax=0, coef=1, nbrMise=0;
		int gain=0, gainTotal=0, gainFull=0;
		boolean gameOver = false, autoMode = auto;
		// get global variables :
		// betMax, gainMax, phaseMax, toursMax, jetonLimite
		String _phase_=" 0/ 0", _tours_="  0/  0", _jetons_="  0/  0", _jetonsMax_="", _gains_="  0/  0/  0", bets="", winFront="", winBack="" , input = "";
		String c_black_bold = ( colorMode ? colorText.BLACK_BOLD : "[[");
		String c_red_background = ( colorMode ? colorText.RED_BACKGROUND + colorText.WHITE_BOLD : "[[");
		String c_green_background = ( colorMode ? colorText.GREEN_BACKGROUND + colorText.WHITE_BOLD : "[[");
		String c_reset = ( colorMode ? colorText.RESET : "]]");
		//String c_red_bold = ( colorMode ? colorText.RED_BOLD : "[[");
		//String c_green_bold = ( colorMode ? colorText.GREEN_BOLD : "[[");

		while (true) {
			//display Table 
			System.out.println();
			System.out.print("Table ");
			System.out.println((table.isStoreEmpty() ? "" : table.getStore(20) ) + ":");
			System.out.println();
			System.out.print(table.betToString(colorMode));

			// display Global Status
			System.out.println();
			System.out.print("Phase: " + _phase_ + " | ");
			System.out.print("Tour: " + _tours_ + " | ");
			System.out.print("Jetons: "+ _jetons_ +" | ");
			System.out.print("Gains: "+ _gains_ +" | ");
			System.out.println(bets);

			// ---------------------
			// exit conditions check
			// ---------------------

			// if table is full (all values are completed)
			// if gainMax, phaseMax or toursMax are reached
			if ( table.isFull() 
					|| ( gainMax != 0 ? gainTotal >= gainMax : false )
					|| ( phaseMax != 0 && tours == 0 ? phase >= phaseMax : false )
					|| ( toursMax != 0 && tours == 0 ? toursTotal >= toursMax : false ) ) {
				System.out.println();
				gameOver = true;
			}

			// Check Jetons limites
			if ( jetonLimite != 0 && jetons >= jetonLimite ) {
				System.out.print("--> " + c_red_background + "WALLET LIMITE REACHED !!!!!" + c_reset);
				gainTotal -= jetons; gainFull += gainTotal;
				if ( gainTotal >= 0 )
					System.out.println("--> Gains: "+ c_green_background + String.format("%3s", gainTotal) + c_reset);
				else
					System.out.println("--> Gains: "+ c_red_background + String.format("%3s", gainTotal) + c_reset);
				gameOver = true;
			}

			// run exit prompt
			if (gameOver) {
				input = "";
				System.out.println(c_black_bold + "Game is over" + c_reset);
				do {
					//System.out.print("--> [q)uit|(r)estart|(p)urge store]: ");
					System.out.print("--> [q)uit|(CR[r)estart]: ");
					input=sc.nextLine();
					input = ( input.isEmpty() ? "r" : input.substring(0, 1));
					switch (input) {
					case "q" :
						sc.close();
						return;
					case "r" :
						System.out.println("##1");
						table.resetTable(); roulette=0; phase=0; tours=0; toursTotal=0;
						jetons=0; jetonsMax=0; coef=1; nbrMise=0; gain=0; gainTotal=0;
						gameOver = false; autoMode = auto;
						_phase_=" 0";_tours_="  0/  0"; _jetons_="  0/  0"; _jetonsMax_=""; _gains_="  0/  0"; bets="";
						break;
					case "p" :
					default :
						System.out.println("##2");
						input = "";
						break;
					}
				} while (input.isEmpty());
				System.out.println("##3");
				//break; 	
			}
			//System.out.println("##4");

			// get input from user
			else { 
				do {
					do {
						input = "";
						System.out.println();
						System.out.print("--> Roulette [num|(CR[r)andom|(a)uto|(q)uit]: ");
						if (autoMode) input="r";
						else {
							input=sc.nextLine();
							if (input.matches("\\d+")) break;
							input = ( input.isEmpty() ? "r" : input.substring(0, 1));
						}
						switch (input) {
						case "q" :
							System.out.println("\nExiting...");
							sc.close();
							return;
						case "a" :
							autoMode = true;
						case "r" :
							//System.out.print("get Random Roulette...");
							roulette = new Random().nextInt(37);
							int delay=0; // 0.1sec per delay
							while (delay-->0) {
								try { TimeUnit.MILLISECONDS.sleep(100); } // or try { TimeUnit.SECONDS.sleep(1); }
								catch (InterruptedException e) { /* empty */ } // or e.printStackTrace();
								//System.out.print(".");
							};
							input = String.valueOf(roulette);
							//System.out.println("  ["+input+"]");
							break;
						}
					} while (! input.matches("\\d+"));
					roulette = Integer.valueOf(input);
				} while ( roulette > 36);

				// increments phase, counters and set display
				if ( tours == 0 ) {
					phase++; phaseFull += phase;
				}
				tours++;
				toursTotal++;
				jetons+=nbrMise;
				jetonsMax=(jetons > jetonsMax ? jetons : jetonsMax);
				_phase_ = String.format("%2s", phase) + "/" + String.format("%2s", phaseFull);;
				_tours_ = String.format("%3s", tours) + "/" + String.format("%3s", toursTotal);
				_jetons_ = String.format("%3s", jetons);
				_jetonsMax_ = String.format("%3s", jetonsMax);
				if (warning != 0 ) {
					_jetons_ = ( ( jetons > warning) ? c_red_background + _jetons_ + c_reset : _jetons_);
					_jetonsMax_ = ( ( jetonsMax > warning) ? c_red_background + _jetonsMax_ + c_reset : _jetonsMax_);
				}
				_jetons_ = _jetons_ + "/" + _jetonsMax_;

				// set win consequences
				winFront=""; winBack="";
				if ( table.betsContains(roulette) ) {
					gain = 36*coef-jetons;
					gainTotal+=gain;
					gainFull += gainTotal;
					tours=0;
					jetons=0;
					coef=1;
					//win= c_green_bold + " !!! WIN !!! " + c_reset;
					winFront = c_green_background;
					winBack = c_reset;
				}
				_gains_ = winFront + String.format("%3s", gain) +"/"+ String.format("%3s", gainTotal) +"/"+ String.format("%3s", gainFull) + winBack;

				//add roulette value to table
				table.addOccurence(roulette);

				//get Bets suggestions
				bets="";
				table.setBets();
				if (! table.isBetsEmpty()) {
					nbrMise=table.getBetsSize();
					coef = (jetons + nbrMise*coef) / 36 +1;
					nbrMise*=coef;
					bets="--> Bets  : "+table.getBets()+" (x"+coef+") => mise: "+nbrMise;
				}
			}
		}
	}
}
