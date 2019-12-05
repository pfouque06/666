package _666_;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Table table = new Table();
		Scanner sc = new Scanner(System.in);
		//sc.nextLine();
		int roulette=0;
		int phase=0, nbrRoulette=0, nbrRouletteTotal=0;
		int jetons=0, jetonsMax=0, jetonLimite=250, coef=1, nbrMise=0;
		int gain=0, gainTotal=0;
		String _tours_="  0/  0", _jetons_="  0/  0", _gains_="  0/  0", bets="", win="", input = "";

		while (true) {
			//display Table 
			System.out.println();
			System.out.print("Table ");
			//System.out.println((input.isBlank() ? "" : "[" + input + "]") + ":");
			System.out.println((table.isStoreEmpty() ? "" : table.getStore(20) ) + ":");
			System.out.println();
			System.out.print(table.betToString());

			// display Global Status
			System.out.println();
			System.out.print("Phase: " + String.format("%2s", phase) + " | ");
			System.out.print("Tour: " + _tours_ + " | ");
			System.out.print("Jetons: "+ _jetons_ +" | ");
			//System.out.print(win);
			System.out.print("Gains: "+ _gains_ +" | ");
			//System.out.println(colorText.RESET);
			System.out.print(bets);

			// check if table is full (all values are completed)
			if ( table.isFull() ) {
				System.out.println();
				System.out.println(colorText.BLACK_BOLD + "Game is over" + colorText.RESET);
				System.out.println("--- later featuren: reset old stored value until next bet suggestion");
				return;
			}

			// check if mise total > limite de jeton : 250 jetons
			//if ( (jetons + nbrMise) > (jetonLimite + gainTotal) ) {
//			if ( (jetons + nbrMise) > jetonLimite ) {
//				System.out.println();
//				System.out.print(colorText.RED_BACKGROUND + "NO MORE JETONS TO BET !!!!!" + colorText.RESET);
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
						return;
						//System.exit(0); //if (input == "q") System.exit(0);
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
			_jetons_ = ( jetons > jetonLimite ? colorText.RED_BACKGROUND + colorText.WHITE_BOLD + _jetons_ + colorText.RESET : _jetons_);
			_jetons_ = String.format("%3s", jetons) + "/" + _jetons_;

			// set win consequences
			win="";
			if ( table.betsContains(roulette) ) {
				gain = 36*coef-jetons;
				gainTotal+=gain;
				nbrRoulette=0;
				jetons=0;
				coef=1;
				//win= colorText.GREEN_BOLD + " !!! WIN !!! " + colorText.RESET;
				win= colorText.GREEN_BACKGROUND + colorText.WHITE_BOLD;
			}
			_gains_ = win + String.format("%3s", gain) +"/"+ String.format("%3s", gainTotal)  + colorText.RESET;

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
				//System.out.println();
				//System.out.println(bets);
				//System.out.println("--> mise: "+nbrMise);
			}

		}
	}

}
