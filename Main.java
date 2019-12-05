package _666_;

import java.io.File;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {


	public static void argOpt(String[] pArgs) {
		boolean flag = false;
		int count = -1;
		File file = null;

		for (int i = 0; i < pArgs.length; i++) {
			if ("--help".equals(pArgs[i]) || "-h".equals(pArgs[i])) {
				usage(System.out); // use STDOUT when help is requested
				return;
			}
			if ("--flag".equals(pArgs[i]) || "-x".equals(pArgs[i])) {
				flag = !flag; // toggle
			}
			else if ("--count".equals(pArgs[i]) || "-c".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					usage(System.err);
					return;
				}
				try {
					count = Integer.parseInt(pArgs[++i]);
				} catch (NumberFormatException ex) {
					System.err.println("Error: argument for " + pArgs[i] +
							" must be a number");
					usage(System.err);
					return;
				}
			}
			else if ("--file".equals(pArgs[i]) || "-f".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					usage(System.err);
					return;
				}
				file = new File(pArgs[++i]);
			}
		}
		// omitted: validate that required arguments were specified
	}

	private static void usage(PrintStream ps) {
		ps.println("Usage: myapp [-x|--flag] --count=NUM --file=FILE");
		ps.println("Does something with the specified file");
		ps.println("Options:");
		ps.println("  -x, --flag        Does things differently when this flag is set");
		ps.println("  -c, --count=NUM   How often to do things");
		ps.println("  -f, --file=FILE   The file to process");
		ps.println("  -h, --help        Prints this help message and exits");
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Table table = new Table();
		Scanner sc = new Scanner(System.in);
		//sc.nextLine();
		int roulette=0;
		int phase=0, nbrRoulette=0, nbrRouletteTotal=0;
		int jetons=0, jetonsMax=0, jetonLimite=250, coef=1, nbrMise=0;
		int gain=0, gainTotal=0;
		String _tours_="  0/  0", _jetons_="  0/  0", _gains_="  0/  0", bets="", winFront="", winBack="" , input = "";
		String c_black_bold = "[["; //colorText.BLACK_BOLD;
		String c_red_bold = "[["; //colorText.RED_BOLD;
		String c_red_background = "[["; //colorText.RED_BACKGROUND;
		String c_green_bold = "[["; //colorText.GREEN_BOLD;
		String c_green_background = "[["; //colorText.GREEN_BACKGROUND;
		String c_white_bold = ""; //colorText.WHITE_BOLD;
		String c_reset = "]]"; //colorText.RESET;

		while (true) {
			//display Table 
			System.out.println();
			System.out.print("Table ");
			System.out.println((table.isStoreEmpty() ? "" : table.getStore(20) ) + ":");
			System.out.println();
			System.out.print(table.betToString());

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
			if ( nbrRoulette == 0 ) phase++;
			nbrRoulette++;
			nbrRouletteTotal++;
			jetons+=nbrMise;
			jetonsMax=(jetons > jetonsMax ? jetons : jetonsMax);
			_tours_ = String.format("%3s", nbrRoulette) + "/" + String.format("%3s", nbrRouletteTotal);
			_jetons_ = String.format("%3s", jetonsMax);
			_jetons_ = ( jetonsMax > jetonLimite ? c_red_background + c_white_bold + _jetons_ + c_reset : _jetons_);
			_jetons_ = String.format("%3s", jetons) + "/" + _jetons_;

			// set win consequences
			winFront=""; winBack="";
			if ( table.betsContains(roulette) ) {
				gain = 36*coef-jetons;
				gainTotal+=gain;
				nbrRoulette=0;
				jetons=0;
				coef=1;
				//win= c_green_bold + " !!! WIN !!! " + c_reset;
				winFront = c_green_background + c_white_bold;
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
