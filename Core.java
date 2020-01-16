package _666_;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.swing.JOptionPane;

import javaTools.Observed;
import javaTools.Observer;

public class Core implements Observed {

	// Observer Array list
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private LinkedHashSet<String[]> observerUpdateList = new LinkedHashSet<String[]>();

	// CLI aggregation
	CommandLineInterface cli = new CommandLineInterface();

	// Core attributs
	Table table = new Table();
	int roulette = 0;
	int phase = 0, phaseFull = 0;
	int tours = 0, toursTotal = 0, toursFull = 0;
	int deposit = Main.deposit, jetons = Main.deposit, jetonsTotal = Main.deposit, jetonsMax = Main.deposit, coef = 1, nbrMise = 0;
	int win = 0, gain = 0, gainTotal = 0, gainFull = 0, storeLineSize = 0;
	int miseOrigin = 0, coefOrigin = 0;
	boolean newBets = false, newCoef = false;
	boolean gameOver = false, autoPlay = Main.autoMode;
	String alert = "", input = "", betsOrigin = "";
	// Main global variables :
	// Main.betMax, Main.gainMax, Main.phaseMax, Main.tourMax, Main.jetonLimite

	boolean runGUI() {
		// GUI init
		storeLineSize = 6;

		// ---------------------
		// display dashboard
		// ---------------------
		// prepare display & update OBSERVER
		prepareDisplay();
		updateObserver();
		return true;
	}

	boolean runCLI() {

		// CLI init
		storeLineSize = 20;

		while (true) {
			// ---------------------
			// display dashboard
			// ---------------------
			String storeLine = (table.isStoreEmpty() ? "" : table.getStore(storeLineSize));
			String betTable = table.betToString(Main.colorMode);
			// display Dashboard
			cli.displayDashboardTable(storeLine, betTable);
			cli.displayDashboardStatus();

			// ---------------------
			// exit conditions check
			// ---------------------
			// if table is full (all values are completed)
			// or if Main.gainMax, Main.phaseMax or Main.tourMax are reached
			if (table.isFull() || (Main.gainMax != 0 ? gainTotal >= Main.gainMax : false)
					|| (Main.phaseMax != 0 && tours == 0 ? phase >= Main.phaseMax : false)
					|| (Main.tourMax != 0 && tours == 0 ? toursTotal >= Main.tourMax : false)) {
				gameOver = true; // launch exit menu
			}

			// Check Mise versus Jetons
			if (jetons < nbrMise) {
				gameOver = true; // launch exit menu
				alert = "bet"; // set alert
			}

			// Check Jetons limites
			if (Main.jetonLimite != 0 && (deposit - jetons) >= Main.jetonLimite) {
				gameOver = true; // launch exit menu
				alert = "limite"; // set alert
			}

			// run exit prompt
			if (gameOver) {
				cli.displayGameOverLabel(alert, (gainTotal - (deposit - jetons)));
				input = "";
				do {
					input = cli.displayGameOverMenu();
					switch (input) {
					case "q": // quit
						// System.out.println("##1.1 - exit");
						System.out.println("\nExiting...");
						cli.close();
						return false;
					case "p": // purge table's store requested
						// System.out.println("##1.2 - start reducing Store");
						if (!alert.isEmpty()) {
							System.out.println(cli.alert("Alert is raised") + " .. Can't purge store, sorry !");
							input = "";
						} else if (!table.reduceStore()) {
							System.out.println(cli.alert("Store is empty") + " .. Can't purge store, sorry !");
							input = "";
						}
						break;
					case "r": // restart table requested
						// System.out.println("##1.3 - reset table");
						table.resetTable();
						roulette = phase = tours = toursTotal = gain = gainTotal = coef = nbrMise = 0;
						if (!alert.isEmpty()) {
							gainFull -= deposit - jetons;
						}
						deposit = Main.deposit;
						jetons = Main.deposit;
						jetonsTotal = Main.deposit;
						cli.updatePhaseString(0, phaseFull);
						cli.updateTourString(0, 0, toursFull);
						cli.updateJetonString(jetons, jetonsTotal, jetonsMax);
						cli.updateGainString(win, 0, 0, gainFull);
						cli.updateBets("");
						break;
					case "o": // option menu requested
						// System.out.println("##1.4 - option menu");
						// reset input in order to jump back to menu
						input = "";

						// display Options menu and get new jetons if any
						int newJeton = cli.displaySetOptionsMenu();

						// check new jetons value, add them to deposit and check bet value again
						if (newJeton > 0) {
							jetons += newJeton;
							jetonsTotal += newJeton;
							jetonsMax += newJeton;
							deposit += newJeton;

							switch (alert) {
							case "bet": // alert = bet
								// Check Mise versus Jetons
								if (jetons >= nbrMise) {
									System.out.println("--> " + cli.raise("wallet is up and alive again !!"));
									input = "cycle back on";
								}
								break;
							case "limite": // alert = jeton
								// Check Jetons limites
								// if ((deposit - jetons) < Main.jetonLimite) {
								if (newJeton > Main.jetonLimite) {
									System.out.println("--> " + cli.raise("wallet is up and alive again !!"));
									input = "cycle back on";
								}
								break;
							}

						}
						cli.updateJetonString(jetons, jetonsTotal, jetonsMax);

						break;
					case "m": // toggle Main.colorMode
						// System.out.println("##1.5 - toggle Main.colorMode");
						Main.colorMode = (Main.colorMode ? false : true);
						input = "";
						break;
					case "a": // toggle Main.auto mode
						// System.out.println("##1.6 - toggle Main.auto mode");
						Main.autoMode = (Main.autoMode ? false : true);
						input = "";
						break;
					default:
						// System.out.println("##1.4 - default case");
						input = "";
						break;
					}

					if (input.isEmpty()) {
						// display Global Status
						cli.displayDashboardStatus();
					}

				} while (input.isEmpty());
				// System.out.println("##2.1");
				autoPlay = Main.autoMode;
				gameOver = false;
				alert = "";
			}
			// System.out.println("##3");

			// game is not over, need now to get standard input from user
			else {
				// System.out.println("##4.1");
				do {
					do {
						input = "";
						if (autoPlay) // autoplay ON
							input = "r";
						else // display displayPhaseMenu()
							input = cli.displayCycleMenu();

						switch (input) {
						case "q":
							System.out.println("\nExiting...");
							cli.close();
							return false;
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

				// process win consequences
				win = 0;
				if (table.betsContains(roulette)) {
					win = 36 * coef;
					jetons += win;
					gain = jetons - deposit;
					gainTotal += gain;
					gainFull += gain;
					tours = 0;

					// update deposit value
					deposit = (jetons > deposit) ? jetons : deposit;
				}

				// add roulette value to table
				table.addOccurence(roulette);
			}

			// get Bets suggestions
			cli.updateBets("");
			coef = nbrMise = 0;
			table.setBets();
			if (!table.isBetsEmpty()) {
				nbrMise = table.getBetsSize();
				int delta = deposit - jetons;
				delta = (delta > 0 ? delta : 0);
				coef = delta / (36 - nbrMise) + 1; // ceiling(( deposit - jetons ) / (36 - NbrMise ))
				nbrMise *= coef;
				newBets = (!betsOrigin.equals(table.getBets())) ? true : false;
				newCoef = (coefOrigin != coef) ? true : false;
				// if ( newCoef ) System.out.println("Core>>--> coef: old: "+coefOrigin +", new:
				// "+ coef);
				// if ( newBets) System.out.println("Core>>--> bets: old: "+ betsOrigin +", new:
				// "+table.getBets());
			}
			// store next Origin values
			// betsOrigin = cli.bets;
			betsOrigin = table.getBets();
			coefOrigin = coef;
			// miseOrigin = nbrMise;

			// prepare display
			cli.updatePhaseString(phase, phaseFull);
			cli.updateTourString(tours, toursTotal, toursFull);
			cli.updateJetonString(jetons, jetonsTotal, jetonsMax);
			cli.updateGainString(win, gain, gainTotal, gainFull);
			cli.updateBets(table.getBets(), coef, nbrMise, newBets, newCoef);
		}
	}

	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub
		observers.add(obs);

	}

	@Override
	public void updateObserver() {
		// TODO Auto-generated method stub
		System.out.println("Core>>updateObserver()");
		// for(String[] item : observerUpdateList) System.out.println("["+item[0] + ":" + item[1] + "]");
		for (Observer obs : observers) {
			obs.update(observerUpdateList);
		}
		observerUpdateList.clear();
	}

	@Override
	public void delObserver() {
		// TODO Auto-generated method stub
		observers = new ArrayList<Observer>();
	}

	public void processAction(String buttonTitle) {
		// TODO Auto-generated method stub
		System.out.println("Core>>processAction()");
		System.out.println("Core>>-->buttonTitle= " + buttonTitle);
		switch (buttonTitle) {
		case "Spin":
			operateSpin(randomSpin());
			break;
		case "Bille":
			operateSpin(expectSpin());
			break;
		case "Bets":
		case "Options":
			break;
		}
	}

	String randomSpin() {
		System.out.println("Core>>randomSpin()");
		int delay = 0; // 0.1sec per delay
		return input = String.valueOf(table.getRandomRoulette(delay));
	}

	String expectSpin() {
		System.out.println("Core>>expectSpin()");
		System.out.println("Core>>-->Spin DialogBox ");
		// return String.valueOf(ScanTools.scanIntRange("--> Roulette [0-36]", 0, 36));
		// JOptionPane jop = new JOptionPane();
		// String input = JOptionPane.showInputDialog
		String input;
		do {
			input = JOptionPane.showInputDialog(null, "Spin ? (0 - 36)", "Spin", JOptionPane.QUESTION_MESSAGE);
			if (input == null) {
				System.out.println("Core>>-->Spin DialogBox canceled");
				return "";
			}
			if (!input.matches("(^[0-2]?[0-9]$)|(^3[0-6]$)")) {
				input = "";
			}
		} while (input.isEmpty());
		System.out.println("Core>>-->Spin to (" + input + ")");
		return input;
	}

	void prepareDisplay() {
		System.out.println("Core>>prepareDisplay()");

		// prepare display
		cli.updatePhaseString(phase, phaseFull);
		cli.updateTourString(tours, toursTotal, toursFull);
		cli.updateJetonString(jetons, jetonsTotal, jetonsMax);
		cli.updateGainString(win, gain, gainTotal, gainFull);
		cli.updateBets(table.getBets(), coef, nbrMise, newBets, newCoef);
		String jetonLabel = cli._jetons_;
		String gainLabel = cli._gains_;
		String storeLabel = (table.isStoreEmpty() ? "" : table.getStore(storeLineSize));
		String betLabel = cli.bets;
		String coefLabel = cli.coef;
		String miseLabel = cli.mise;
		String cycleLabel = cli._phase_;
		String tourLabel = cli._tours_;

		// update OBSERVER
		observerUpdateList.add(new String[] { "jeton", jetonLabel });
		observerUpdateList.add(new String[] { "gain", gainLabel });
		observerUpdateList.add(new String[] { "cycle", cycleLabel });
		observerUpdateList.add(new String[] { "tour", tourLabel });
		if (win > 0)
			observerUpdateList.add(new String[] { "win", "" });
		if (newBets)
			observerUpdateList.add(new String[] { "newMise", "" });
		if (newCoef)
			observerUpdateList.add(new String[] { "newCoef", "" });
		if (!storeLabel.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "store", storeLabel });
		if (!betLabel.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "bets", betLabel });
		if (!coefLabel.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "coef", coefLabel });
		if (!miseLabel.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "mise", miseLabel });
	}

	void processCycle() {
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
	}

	boolean processWin() {
		// process win
		win = 0;
		if (table.betsContains(roulette)) {
			win = 36 * coef;
			jetons += win;
			gain = jetons - deposit;
			gainTotal += gain;
			gainFull += gain;
			tours = 0;

			// update deposit value
			deposit = (jetons > deposit) ? jetons : deposit;
			return true;
		}
		return false;
	}

	void processBetsSuggestion() {
		// get Bets suggestions
		cli.updateBets("");
		coef = nbrMise = 0;
		table.setBets();
		if (!table.isBetsEmpty()) {
			nbrMise = table.getBetsSize();
			int delta = deposit - jetons;
			delta = (delta > 0 ? delta : 0);
			coef = delta / (36 - nbrMise) + 1; // ceiling(( deposit - jetons ) / (36 - NbrMise ))
			nbrMise *= coef;
			newBets = (!betsOrigin.equals(table.getBets())) ? true : false;
			newCoef = (coefOrigin != coef) ? true : false;
			if (newCoef)
				System.out.println("Core>>--> coef: old: " + coefOrigin + ", new: " + coef);
			if (newBets)
				System.out.println("Core>>--> bets: old: " + betsOrigin + ", new: " + table.getBets());
		}
		// store next Origin values
		betsOrigin = table.getBets(); // betsOrigin = cli.bets;
		coefOrigin = coef;
	}

	boolean checkGameOverConditions() {
		// ---------------------
		// exit conditions check
		// ---------------------
		// if table is full (all values are completed)
		// or if Main.gainMax, Main.phaseMax or Main.tourMax are reached
		boolean result = false;

		if (table.isFull() || (Main.gainMax != 0 ? gainTotal >= Main.gainMax : false)
				|| (Main.phaseMax != 0 && tours == 0 ? phase >= Main.phaseMax : false)
				|| (Main.tourMax != 0 && tours == 0 ? toursTotal >= Main.tourMax : false)) {
			result = true;
		}

		// Check Mise versus Jetons
		if (jetons < nbrMise) {
			alert = "bet"; // set alert
			result = true; // launch exit menu
		}

		// Check Jetons limites
		if (Main.jetonLimite != 0 && (deposit - jetons) >= Main.jetonLimite) {
			alert = "limite"; // set alert
			result = true; // launch exit menu
		}

		return result;
	}

	int gameOverDialog() {
		// JOptionPane jop = new JOptionPane();
		String title = "Game Over";
		String message = "";
		String gain_ = "--> Gains: " + String.format("%3s", gainTotal);
		// String[] optionButtons = { "Quit", "Purge", "Restart" };
		String[] optionButtons = { "Purge", "Restart" };
		switch (alert) {
		case "bet": // alert = bet
			message = "--> CAN'T BET THIS AMOUNT !!!!!";
			message += "\n" + gain_;
			break;
		case "limite": // alert = jeton
			message = "--> WALLET LIMITE REACHED !!!!!";
			message += "\n" + gain_;
			break;
		default:
			message = "--> Game is over";
			message += "\n" + gain_;
		}
		int index = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
				(alert.isEmpty() ? JOptionPane.QUESTION_MESSAGE : JOptionPane.WARNING_MESSAGE), null, optionButtons,
				optionButtons[1]);
		if (index >= 0)
			System.out.println("--> action[" + index + "]: " + optionButtons[index]);
		return index;
	}

	void operateSpin(String input) {
		System.out.println("Core>>operateSpin(" + input + ")");
		if (input.isEmpty())
			return;
		if (!gameOver) {

			// get roulette value
			roulette = Integer.valueOf(input);
			// process Cycle and Win check
			processCycle();
			processWin();

			// add roulette value to table and get Bets suggestions
			table.addOccurence(roulette);
			processBetsSuggestion();

			// prepare display & update OBSERVER
			prepareDisplay();
			updateObserver();

			// GameOver check
			gameOver = checkGameOverConditions();
		}

		// run exit prompt if required
		if (gameOver) {
			// game over dialog
			int index = gameOverDialog();
			switch (index) {
			case -1:
				System.out.println("--> cancel process");
				break;
			case 0:
				// System.out.println("--> quit process");
				// cli.close();
				// return;
				// case 1:
				System.out.println("--> purge process");
				String message;
				if (!alert.isEmpty()) {
					message = "Alert is raised";
					message += "\n .. Can't purge store, sorry !";
					JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.WARNING_MESSAGE);
				} else if (!table.reduceStore()) {
					message = "Store is empty";
					message += "\n .. Can't purge store, sorry !";
					JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case 1:
				// case 2:
				System.out.println("--> restart process");
				table.resetTable();
				roulette = phase = tours = toursTotal = gain = gainTotal = coef = nbrMise = 0;
				if (!alert.isEmpty()) {
					gainFull -= deposit - jetons;
				}
				deposit = Main.deposit;
				jetons = Main.deposit;
				jetonsTotal = Main.deposit;
				break;
			}

			// prepare display and update observer
			switch (index) {
			case 0:
			case 1:
				// case 2:
				gameOver = false;
				alert = "";

				// get Bets suggestions
				processBetsSuggestion();

				// prepare display & update OBSERVER
				prepareDisplay();
				updateObserver();
			}
		}
	}

}
