package _666_;

import javaTools.ColorText;
import javaTools.ScanTools;

// CLI : Command Line Interface Class
class CLI {

	String _phase_ = phaseToString(0, 0);
	String _tours_ = tourToString(0, 0, 0);
	String _jetons_ = jetonToString(Main.deposit, Main.deposit, Main.deposit);
	String _gains_ = gainToString(0, 0, 0, 0);
	String bets = "", coef = "", mise = "", input = "";

	String c_black_bold() { return (Main.colorMode ? ColorText.BLACK_BOLD : ""); }
	String c_red() { return (Main.colorMode ? ColorText.RED : ""); }
	String c_red_bold() { return (Main.colorMode ? ColorText.RED_BOLD : ""); }
	String c_red_background() { return (Main.colorMode ? ColorText.RED_BACKGROUND + ColorText.WHITE_BOLD : ""); }
	String c_green() { return (Main.colorMode ? ColorText.GREEN : ""); }
	String c_green_bold() { return (Main.colorMode ? ColorText.GREEN_BOLD : ""); }
	String c_green_background() { return (Main.colorMode ? ColorText.GREEN_BACKGROUND + ColorText.WHITE_BOLD : ""); }
	String c_blue() { return (Main.colorMode ? ColorText.BLUE : ""); }
	String c_blue_bold() { return (Main.colorMode ? ColorText.BLUE_BOLD : ""); }
	String c_blue_background() { return (Main.colorMode ? ColorText.BLUE_BACKGROUND + ColorText.WHITE_BOLD : ""); }
	String c_purple_background() { return (Main.colorMode ? ColorText.CYAN_BACKGROUND + ColorText.WHITE_BOLD : ""); }
	String c_reset() { return (Main.colorMode ? ColorText.RESET : ""); }

	String alert(String pAlert) {
		return c_red_bold() + pAlert + c_reset();
	}

	String raise(String pRaise) {
		return c_green_bold() + pRaise + c_reset();
	}

	String phaseToString(int pPhase, int pPhaseFull) {
		String buffer = String.format("%2s", pPhase)
				+ "/" + String.format("%2s", pPhaseFull);
		return buffer;
	}

	String tourToString(int pTours, int pToursTotal, int pToursFull) {
		String buffer = String.format("%3s", pTours)
				+ "/" + String.format("%3s", pToursTotal)
				+ "/" + String.format("%3s", pToursFull);
		return buffer;
	}

	String jetonToString(int pJetons, int pJetonsTotal, int pJetonsMax) {
		String buffer = "", bufferTotal = "", bufferMax = "";
		buffer = String.format("%3s", pJetons);
		bufferTotal = String.format("%3s", pJetonsTotal);
		bufferMax = String.format("%3s", pJetonsMax);
		int spent = Main.deposit - pJetons;
		int spentTotal = Main.deposit - pJetonsTotal;
		int spentMax = Main.deposit - pJetonsMax;
		if (Main.warning != 0) {
			buffer = ((spent >= Main.warning) ?
					c_purple_background() + buffer + c_reset() : buffer);
			bufferTotal = ((spentTotal >= Main.warning) ?
					c_purple_background() + bufferTotal + c_reset() : bufferTotal);
			bufferMax = ((spentMax >= Main.warning) ?
					c_purple_background() + bufferMax + c_reset() : bufferMax);
		}
		if (Main.jetonLimite != 0) {
			buffer = ((spent >= Main.jetonLimite) ?
					c_red_background() + buffer + c_reset() : buffer);
			bufferTotal = ((spentTotal >= Main.jetonLimite) ?
					c_red_background() + bufferTotal + c_reset() : bufferTotal);
			bufferMax = ((spentMax >= Main.jetonLimite) ?
					c_red_background() + bufferMax + c_reset() : bufferMax);
		}
		return buffer + "/" + bufferTotal + "/" + bufferMax;
	}

	String gainToString(int win, int pGain, int pGainTotal, int pGainFull) {
		String buffer = "", bufferTotal = "", bufferFull = "";
		String winFront = win > 0 ? c_green_background() : "";
		String winBack = win > 0 ? c_reset() : "";
		//String winBack = c_reset();

		buffer = String.format("%3s", pGain);
		buffer = (pGain < 0 ? c_red_background() + buffer + c_reset() : winFront + buffer + winBack);

		bufferTotal = String.format("%3s", pGainTotal);
		bufferTotal = (pGainTotal < 0 ? c_red_background() + bufferTotal + c_reset() : winFront + bufferTotal + winBack);

		bufferFull = String.format("%3s", pGainFull);
		bufferFull = (pGainFull < 0 ? c_red_background() + bufferFull + c_reset() : winFront + bufferFull + winBack);

		return buffer + "/" + bufferTotal + "/" + bufferFull;
	}

	void updatePhaseString(int pPhase, int pPhaseFull) {
		_phase_ = phaseToString(pPhase, pPhaseFull);
	}

	void updateTourString(int pTours, int pToursTotal, int pToursFull) {
		_tours_ = tourToString(pTours, pToursTotal, pToursFull);
	}

	void updateJetonString(int pJetons, int pJetonsTotal, int pJetonsMax) {
		_jetons_ = jetonToString(pJetons, pJetonsTotal, pJetonsMax);
	}

	void updateGainString(int win, int pGain, int pGainTotal, int pGainFull) {
		_gains_ = gainToString(win, pGain, pGainTotal, pGainFull);
	}

	void updateBets(String pBets) {
		bets = pBets;
	}

	void updateBets(String pTable, int pCoef, int pNbrMise, boolean pNewTable, boolean pNewCoef) {
		// "--> Bets  : " + table.getBets() + " (x" + coef + ") => mise: " + nbrMise)
		if (pTable.isEmpty()) {
			bets = coef = mise = "";
			return;
		}
		bets  = pNewTable ? c_blue_bold() + pTable + c_reset() : "" + pTable;
		coef = "x" + (pNewCoef ? c_blue_bold() + pCoef + c_reset() : "" + pCoef);
		mise = ( pNewCoef || pNewTable )? c_blue_bold() + pNbrMise + c_reset() : "" + pNbrMise;
	}

	// display Dashboard Status
	void displayDashboardTable(String pStore, String pTable) {
		System.out.println();
		System.out.println("Table " + pStore + ":");
		System.out.println();
		System.out.print(pTable);
	}

	// display Dashboard Status
	void displayDashboardStatus() {
		System.out.println();
		System.out.print("Phase: " + _phase_ + " | ");
		System.out.print("Tour: " + _tours_ + " | ");
		System.out.print("Jetons: " + _jetons_ + " | ");
		System.out.print("Gains: " + _gains_ + " | ");
		if (! bets.isEmpty())
			System.out.print("--> Bets  : " + bets + " " + coef + "  => mise: " + mise);
		System.out.println();
	}

	// displayFullDashboard : display Table and status
	void displayFullDashboard(String pStore, String pTable) {
		// display Dashboard Table
		displayDashboardTable(pStore, pTable);

		// display Dashboard Status
		displayDashboardStatus();
	}

	// display GameOver Label
	void displayGameOverLabel(String pAlert, int pGain) {

		String gain_ = "";
		if (pGain >= 0)
			gain_ = c_green_background() + String.format("%3s", pGain) + c_reset();
		else
			gain_ = c_red_background() + String.format("%3s", pGain) + c_reset();

		switch (pAlert) {
		case "bet": // alert = bet
			//System.out.println();
			System.out.println("--> " + c_red_background() + "WALLET CAN'T BET THIS AMOUNT !!!!!" + c_reset());
			System.out.println("--> Gains: " + gain_);
			break;
		case "limite": // alert = jeton
			System.out.println("--> " + c_red_background() + "WALLET LIMITE REACHED !!!!!" + c_reset());
			System.out.println("--> Gains: " + gain_);
			break;
		default:
			//System.out.println();
			System.out.println("--> " + c_black_bold() + "Game is over" + c_reset());
		}
	}

	// display GameOver Menu
	String displayGameOverMenu() {
		String buffer = "";

		String colorMode_ = "|(m)ode: " + (Main.colorMode ? c_green() + "color" : c_red() + "mono") + c_reset();
		String autoMode_ = "|(a)uto: " + (Main.autoMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		String prompt_ = "--> [(q)uit|(CR|r)estart|(p)urge store|(o)ptions" + colorMode_ + autoMode_ + "]: ";
		//System.out.print("--> [(q)uit|(CR|r)estart|(p)urge store|(o)ptions" + colorMode_ + autoMode_ + "]: ");
		//buffer = scan.nextLine();
		//System.out.println();
		buffer = ScanTools.scanMatchedBuffer(prompt_, ".*");
		buffer = (buffer.isEmpty() ? "r" : buffer.substring(0, 1));
		return buffer;
	}

	// display set Option Menu
	String displaySetOptionsMenu() {
		String buffer = "";
		// display actual options :
		//System.out.println("options:" + Main.optsToString());
		String prompt_ = "--> options:" + Main.optsToString() + " | new options (CR: exit): ";
		// get new options args_
		//System.out.print("--> new options (CR: exit): ");
		//buffer = scan.nextLine();
		buffer = ScanTools.scanMatchedBuffer(prompt_, ".*");
		return buffer;
		
	}

	// display GameOver Menu
	String displayCycleMenu(boolean autoMode) {
		String buffer = "";

		// System.out.println();
		// System.out.print("--> Roulette [num|(CR|r)andom|(a)uto|(q)uit]: ");
		// buffer = scan.nextLine();
		
		String simMode_ = Main.simMode ? "|(CR|r)andom" : "";
		String colorMode_ = "|(m)ode: " + (Main.colorMode ? c_green() + "color" : c_red() + "mono") + c_reset();
		//String autoMode_ = "|(a)uto: " + (Main.autoMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		String autoMode_ = "|(a)uto: " + (autoMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		String prompt_ = "--> [(q)uit|roulette num" + simMode_ + "|(o)ptions" + colorMode_ + autoMode_ + "]: ";
		// "OFF") + c_reset();
		//System.out.println();
		buffer = ScanTools.scanMatchedBuffer(prompt_, ".*");
		if (buffer.matches("\\d+") && Integer.valueOf(buffer) < 36)
			return buffer;
		return (buffer.isEmpty() ? "r" : buffer.substring(0, 1)); // shorten to first letter
	}
	
	void close() {
		ScanTools.close();
	}
	
}
