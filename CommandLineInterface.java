package _666_;

import java.util.Scanner;

import java_tools.colorText;

class CommandLineInterface {

	Scanner scan = new Scanner(System.in);
	
	String _phase_ = phaseToString(0, 0);
	String _tours_ = tourToString(0, 0, 0);
	String _jetons_ = jetonToString(Main.deposit, Main.deposit, Main.deposit);
	String _gains_ = gainToString(0, 0, 0, 0);
	String bets = "", input = "";

	String c_black_bold() { return (Main.colorMode ? colorText.BLACK_BOLD : "[["); }
	String c_red() { return (Main.colorMode ? colorText.RED : "[["); }
	String c_red_bold() { return (Main.colorMode ? colorText.RED_BOLD : "[["); }
	String c_red_background() { return (Main.colorMode ? colorText.RED_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	String c_green() { return (Main.colorMode ? colorText.GREEN : "[["); }
	String c_green_bold() { return (Main.colorMode ? colorText.GREEN_BOLD : "[["); }
	String c_green_background() { return (Main.colorMode ? colorText.GREEN_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	String c_blue() { return (Main.colorMode ? colorText.BLUE : "[["); }
	String c_blue_bold() { return (Main.colorMode ? colorText.BLUE_BOLD : "[["); }
	String c_blue_background() { return (Main.colorMode ? colorText.BLUE_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	String c_purple_background() { return (Main.colorMode ? colorText.CYAN_BACKGROUND + colorText.WHITE_BOLD : "[["); }
	String c_reset() { return (Main.colorMode ? colorText.RESET : "]]"); }

	String alert(String pAlert) {
		return c_red_bold() + pAlert + c_reset();
	}

	String raise(String pRaise) {
		return c_green_bold() + pRaise + c_reset();
	}

	String phaseToString(int pPhase, int pPhaseFull) {
		String _phase_ = "";
		_phase_ = String.format("%2s", pPhase) + "/" + String.format("%2s", pPhaseFull);
		return _phase_;
	}

	String tourToString(int pTours, int pToursTotal, int pToursFull) {
		String _tours_ = "";
		_tours_ = String.format("%3s", pTours) + "/" + String.format("%3s", pToursTotal) + "/"
				+ String.format("%3s", pToursFull);
		return _tours_;
	}

	String jetonToString(int pJetons, int pJetonsTotal, int pJetonsMax) {
		String _jetons_ = "", _jetonsTotal_ = "", _jetonsMax_ = "";
		_jetons_ = String.format("%3s", pJetons);
		_jetonsTotal_ = String.format("%3s", pJetonsTotal);
		_jetonsMax_ = String.format("%3s", pJetonsMax);
		if (Main.warning != 0) {
			_jetons_ = ((pJetons <= Main.warning) ? c_purple_background() + _jetons_ + c_reset() : _jetons_);
			_jetonsTotal_ = ((pJetonsTotal <= Main.warning) ? c_purple_background() + _jetonsTotal_ + c_reset()
					: _jetonsTotal_);
			_jetonsMax_ = ((pJetonsMax <= Main.warning) ? c_purple_background() + _jetonsMax_ + c_reset()
					: _jetonsMax_);
		}
		if (Main.jetonLimite != 0) {
			_jetons_ = ((pJetons <= Main.jetonLimite) ? c_red_background() + _jetons_ + c_reset() : _jetons_);
			_jetonsTotal_ = ((pJetonsTotal <= Main.jetonLimite) ? c_red_background() + _jetonsTotal_ + c_reset()
					: _jetonsTotal_);
			_jetonsMax_ = ((pJetonsMax <= Main.jetonLimite) ? c_red_background() + _jetonsMax_ + c_reset()
					: _jetonsMax_);
		}
		_jetons_ = _jetons_ + "/" + _jetonsTotal_ + "/" + _jetonsMax_;
		return _jetons_;
	}

	String gainToString(int win, int pGain, int pGainTotal, int pGainFull) {
		String _gains_ = "", buffer = "";
		String winFront = win > 0 ? c_green_background() : "";
		String winBack = win > 0 ? c_reset() : "";
		buffer = String.format("%3s", pGain);
		_gains_ = (pGain < 0 ? c_red_background() + buffer + c_reset() : winFront + buffer + winBack);
		buffer = String.format("%3s", pGainTotal);
		_gains_ += "/" + (pGainTotal < 0 ? c_red_background() + buffer + c_reset() : winFront + buffer + winBack);
		buffer = String.format("%3s", pGainFull);
		_gains_ += "/" + (pGainFull < 0 ? c_red_background() + buffer + c_reset() : winFront + buffer + winBack);
		return _gains_;
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

	// display Dashboard Status
	void displayDashboardTable(String pStore, String pTable) {
		System.out.println();
		System.out.println("Table " + pStore);
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
		System.out.println(bets);
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


		switch (pAlert) {
		case "bet": // alert = bet
			System.out.println();
			System.out.println("--> " + c_red_background() + "CAN'T BET THIS AMOUNT !!!!!" + c_reset());
			break;
		case "jeton": // alert = jeton
			System.out.print("--> " + c_red_background() + "WALLET LIMITE REACHED !!!!!" + c_reset());
			if (pGain >= 0)
				System.out.println("--> Gains: " + c_green_background() + String.format("%3s", pGain) + c_reset());
			else
				System.out.println("--> Gains: " + c_red_background() + String.format("%3s", pGain) + c_reset());
			break;
		default:
			System.out.println();
			System.out.println("--> " + c_black_bold() + "Game is over" + c_reset());
		}
	}

	// display GameOver Menu
	String displayGameOverMenu() {
		String buffer = "";

		String autoMode_ = "|(a)uto: " + (Main.autoMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		String colorMode_ = "|(m)ode: " + (Main.colorMode ? c_green() + "color" : c_red() + "mono") + c_reset();
		System.out.print("--> [(q)uit|(CR|r)estart|(p)urge store|(o)ptions" + colorMode_ + autoMode_ + "]: ");
		buffer = scan.nextLine();
		buffer = (buffer.isEmpty() ? "r" : buffer.substring(0, 1));
		return buffer;
	}

	// display set Option Menu
	int displaySetOptionsMenu() {
		String buffer = "";
		int newJeton = 0;

		do {
			// display actual options :
			System.out.println("options:" + Main.optsToString());

			// get new options args_
			System.out.print("--> new options (CR: exit): ");
			buffer = scan.nextLine();
			// System.out.println("buffer=" + buffer);
			if (buffer.isEmpty())
				break;

			// construct new args
			String[] args_ = buffer.split(" ");

			// store initial Main.deposit value and reset Main.deposit
			int depositHold = Main.deposit;

			// parse options args_ and set options
			if (!Main.setOpts(args_))
				System.out.println();;

			// return added jetons
			newJeton = Main.deposit - depositHold;

		} while (!buffer.isEmpty());

		//scan.close();
		return newJeton;
	}

	// display GameOver Menu
	String displayCycleMenu() {
		String buffer = "";

		System.out.println();
		System.out.print("--> Roulette [num|(CR|r)andom|(a)uto|(q)uit]: ");
		buffer = scan.nextLine();
		if (buffer.matches("\\d+"))
			return buffer;
		return (buffer.isEmpty() ? "r" : buffer.substring(0, 1));		
	}
	
	void close() {
		scan.close();
	}

	
}
