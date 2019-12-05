package _666_;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

	// get global variables :
	// betMax, gainMax, phaseMax, toursMax, jetonLimite
	public static boolean colorMode=true;
	public static int betMax, gainMax, phaseMax, toursMax, jetonLimite=250;

	public static boolean argOpt(String[] pArgs) {
		String buffer="";
		//File file = null;

		for (int i = 0; i < pArgs.length; i++) {
			if ("--help".equals(pArgs[i]) || "-h".equals(pArgs[i])) {
				usage(System.out); // use STDOUT when help is requested
				return false;
			}
			else if ("--mono".equals(pArgs[i]) || "-m".equals(pArgs[i])) {
				colorMode = false; // toggle
			}
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
		if (! colorMode) System.out.println("colorMode="+colorMode);
		if (betMax != 0) System.out.println("betMax="+betMax);
		return true;
	}

	private static void usage(PrintStream ps) {
		ps.println("Usage: myapp [-x|--flag] --count=NUM --file=FILE");
		ps.println("Options:");
		ps.println("	-m, --mono		Set monocolor mode (color mode par default");
		ps.println("	-b, --bet		maximum bets allowed per tour");
		ps.println("	-h, --help		Prints this help message and exits");
		// -t	<tours>		1 tour = 1'30, 10 tours = 15', 40 tours = 1h 60, tours = 1h30, 80 tours 2h
		// -g	<gain>		arrêt de partie une fois <gain> atteint
		// -j	<jetons>	arrêt de partie une fois <jetons> misés par phase (250 par default)
		// -p	<phase>		arrêt de partie une fois <phase> phases finalisées
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Table table = new Table();
		Scanner sc = new Scanner(System.in);

		// parse agrs
		if( ! argOpt(args) ) {
			sc.close();
			return;
		};
		if (! colorMode) System.out.println("colorMode="+colorMode);
		if (betMax != 0) System.out.println("betMax="+betMax);


		//sc.nextLine();
		int roulette=0;
		int phase=0;
		int tours=0, toursTotal=0;
		int jetons=0, jetonsMax=0, coef=1, nbrMise=0;
		int gain=0, gainTotal=0;
		// get global variables :
		// betMax, gainMax, phaseMax, toursMax, jetonLimite
		String _tours_="  0/  0", _jetons_="  0/  0", _jetonsMax_="", _gains_="  0/  0", bets="", winFront="", winBack="" , input = "";
		String c_black_bold = ( colorMode ? colorText.BLACK_BOLD : "[[");
		String c_red_bold = ( colorMode ? colorText.RED_BOLD : "[[");
		String c_red_background = ( colorMode ? colorText.RED_BACKGROUND + colorText.WHITE_BOLD : "[[");
		String c_green_bold = ( colorMode ? colorText.GREEN_BOLD : "[[");
		String c_green_background = ( colorMode ? colorText.GREEN_BACKGROUND + colorText.WHITE_BOLD : "[[");
		String c_reset = ( colorMode ? colorText.RESET : "]]");

		while (true) {
			//display Table 
			System.out.println();
			System.out.print("Table ");
			System.out.println((table.isStoreEmpty() ? "" : table.getStore(20) ) + ":");
			System.out.println();
			System.out.print(table.betToString(colorMode));

			// display Global Status
			System.out.println();
			System.out.print("Phase: " + String.format("%2s", phase) + " | ");
			System.out.print("Tour: " + _tours_ + " | ");
			System.out.print("Jetons: "+ _jetons_ +" | ");
			System.out.print("Gains: "+ _gains_ +" | ");
			System.out.print(bets);

			// check if table is full (all values are completed)
			if ( table.isFull() ) {
				System.out.println();
				System.out.println(c_black_bold + "Game is over" + c_reset);
				System.out.println("--- later featuren: reset old stored value until next bet suggestion");
				sc.close();
				return;
			}

			// check if mise total > limite de jeton : 250 jetons
			//if ( (jetons + nbrMise) > (jetonLimite + gainTotal) ) {
			//			if ( (jetons + nbrMise) > jetonLimite ) {
			//				System.out.println();
			//				System.out.print(c_red_background + "NO MORE JETONS TO BET !!!!!" + c_reset);
			//				gainTotal -= jetons;
			//				System.out.println(" Gains: "+ String.format("%3s", gainTotal));
			//				return;
			//			}

			// get input from user
			do {
				do {
					System.out.println();
					System.out.print("--> Roulette [num/(r)andom/(q)uit]: ");
					input=sc.nextLine();
					if (input.matches("q")) {
						System.out.println("\nExiting...");
						sc.close();
						return;
					}
					if (input.matches("r") || input.isBlank()) {
						//System.out.print("get Random Roulette...");
						roulette = new Random().nextInt(37);
						int delay=0; // 0.1sec per delay
						while (delay-->0) {
							try { TimeUnit.MILLISECONDS.sleep(100); }
							//try { TimeUnit.SECONDS.sleep(1); }
							catch (InterruptedException e) { /* empty */ } //e.printStackTrace();
							//System.out.print(".");
						};
						//System.out.println("  ["+roulette+"]");
						input = String.valueOf(roulette);
					}
				} while ( ! input.matches("\\d+"));
				roulette = Integer.valueOf(input);
			} while ( roulette > 36);

			// increments counters and set display
			if ( tours == 0 ) phase++;
			tours++;
			toursTotal++;
			jetons+=nbrMise;
			jetonsMax=(jetons > jetonsMax ? jetons : jetonsMax);
			_tours_ = String.format("%3s", tours) + "/" + String.format("%3s", toursTotal);
			_jetons_ = String.format("%3s", jetons);
			_jetonsMax_ = String.format("%3s", jetonsMax);
			if (jetonLimite != 0 ) {
				_jetons_ = ( ( jetons > jetonLimite) ? c_red_background + _jetons_ + c_reset : _jetons_);
				_jetonsMax_ = ( ( jetonsMax > jetonLimite) ? c_red_background + _jetonsMax_ + c_reset : _jetonsMax_);
			}
			_jetons_ = _jetons_ + "/" + _jetonsMax_;

			// set win consequences
			winFront=""; winBack="";
			if ( table.betsContains(roulette) ) {
				gain = 36*coef-jetons;
				gainTotal+=gain;
				tours=0;
				jetons=0;
				coef=1;
				//win= c_green_bold + " !!! WIN !!! " + c_reset;
				winFront = c_green_background;
				winBack = c_reset;
			}
			_gains_ = winFront + String.format("%3s", gain) +"/"+ String.format("%3s", gainTotal)  + winBack;

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
