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
		System.out.println("Core>>runGUI()");

		// GUI init
		storeLineSize = 6;

		// prepare display dashboard & update OBSERVER
		prepareDisplay();
		updateObserver();
		return true;
	}

	void udateCLI() {
		// prepare dashboard information
		String storeLine = (table.isStoreEmpty() ? "" : table.getStore(storeLineSize));
		String betTable = table.betToString(Main.colorMode);
		// display Dashboard
		cli.displayFullDashboard(storeLine, betTable);
		//cli.displayDashboardStatus();
	}

	boolean runCLI() {
		// CLI init
		storeLineSize = 20;

		// prepare display and update CLI dahsboard
		prepareDisplay();
		udateCLI();

		while (true) {
			// if game is not over, need now to get input from user from cycle Menu
			if (!gameOver) {
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

				// process Cycle and Win check
				processCycle();
				processWin();

				// add roulette value to table
				table.addOccurence(roulette);

				// get Bets suggestions
				processBetsSuggestion();

				// prepare display and update CLI dahsboard
				prepareDisplay();
				udateCLI();

				// GameOver check
				gameOver = checkGameOverConditions();
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
						// process Purge
						if (! processPurge())
							input = "";
						break;
					case "r": // restart table requested
						// System.out.println("##1.3 - reset table");
						// process Restart
						processRestart();
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
				// get Bets suggestions
				processBetsSuggestion();
				// prepare display and update CLI dahsboard
				prepareDisplay();
				udateCLI();
			}
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

		// Prepare Observer update List
		observerUpdateList.add(new String[] { "jeton", cli._jetons_ });
		observerUpdateList.add(new String[] { "gain", cli._gains_ });
		observerUpdateList.add(new String[] { "cycle", cli._phase_ });
		observerUpdateList.add(new String[] { "tour", cli._tours_ });
		String storeLabel = (table.isStoreEmpty() ? "" : table.getStore(storeLineSize));
		if (!storeLabel.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "store", storeLabel });
		if (!cli.bets.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "bets", cli.bets });
		if (!cli.coef.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "coef", cli.coef });
		if (!cli.mise.isEmpty()) // --> Convert to setText("") if not updated !!
			observerUpdateList.add(new String[] { "mise", cli.mise });
		if (win > 0)
			observerUpdateList.add(new String[] { "win", "" });
		if (newBets)
			observerUpdateList.add(new String[] { "newMise", "" });
		if (newCoef)
			observerUpdateList.add(new String[] { "newCoef", "" });

		// update Observers
		// for(String[] item : observerUpdateList) System.out.println("["+item[0] + ":" + item[1] + "]");
		for (Observer obs : observers) {
			obs.update(observerUpdateList);
		}
		observerUpdateList.clear();
	}

	@Override
	public void delObserver() {
		// TODO Auto-generated method stub
		System.out.println("Core>>delObserver()");

		observers = new ArrayList<Observer>();
	}

	public void processAction(String buttonTitle) {
		// TODO Auto-generated method stub
		System.out.println("Core>>processAction(" + buttonTitle + ")");

		switch (buttonTitle) {
		case "Spin":
			do {
				operateSpin(randomSpin());
			// repeat if autoMode is TRUE and game is NOT over
			} while (autoPlay && ! gameOver);
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

		// exit if Game is over
		if (gameOver) return null;
		int delay = 0; // 0.1sec per delay
		return input = String.valueOf(table.getRandomRoulette(delay));
	}

	String expectSpin() {
		System.out.println("Core>>expectSpin()");

		// exit if Game is over
		if (gameOver) return null;
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

		// prepare display dashboard
		cli.updatePhaseString(phase, phaseFull);
		cli.updateTourString(tours, toursTotal, toursFull);
		cli.updateJetonString(jetons, jetonsTotal, jetonsMax);
		cli.updateGainString(win, gain, gainTotal, gainFull);
		cli.updateBets(table.getBets(), coef, nbrMise, newBets, newCoef);
	}

	void processCycle() {
		System.out.println("Core>>processCycle()");

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
		System.out.println("Core>>processWin()");

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
		System.out.println("Core>>processBetsSuggestion()");

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
		System.out.println("Core>>checkGameOverConditions()");

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
		System.out.println("Core>>gameOverDialog()");

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

	boolean processPurge() {
		System.out.println("Core>>processPurge()");

		// process Purge
		System.out.println("--> purge process");
		String message = "";
		if (!alert.isEmpty()) {
			message = "Alert is raised... Can't purge store, sorry !";
		} else if (!table.reduceStore()) {
			message = "Store is empty... Can't purge store, sorry !";
		}
		if (! message.isEmpty()) {
			if (Main.guiMode) // GUI mode
				JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.WARNING_MESSAGE);
			else // CLI mode
				System.out.println(cli.alert(message));
			return false;
		}
		autoPlay = Main.autoMode;
		gameOver = false;
		alert = "";
		return true;
	}

	void processRestart() {
		System.out.println("Core>>processRestart()");

		// process Restart
		table.resetTable();
		roulette = phase = tours = toursTotal = gain = gainTotal = coef = nbrMise = 0;
		if (!alert.isEmpty()) {
			gainFull -= deposit - jetons;
		}
		deposit = Main.deposit;
		jetons = Main.deposit;
		jetonsTotal = Main.deposit;
		autoPlay = Main.autoMode;
		gameOver = false;
		alert = "";
	}

	void operateSpin(String input) {
		System.out.println("Core>>operateSpin(" + input + ")");

		if (!gameOver) {
			if (input.isEmpty())
				return;

			// get roulette value
			roulette = Integer.valueOf(input);

			// process Cycle and Win check
			processCycle();
			processWin();

			// add roulette value to table and get Bets suggestions
			table.addOccurence(roulette);
			processBetsSuggestion();

			// prepare display dashboard & update OBSERVER
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
				return;
			//case 0:
				// System.out.println("--> quit process");
				// cli.close();
				// return;
				// case 1:
			case 0:
				// process Purge
				processPurge();
				break;
			// case 2:
			case 1:
				// process Restart
				processRestart();
				break;
			}

			// get Bets suggestions
			processBetsSuggestion();

			// prepare display dashboard & update OBSERVER
			prepareDisplay();
			updateObserver();
		}
	}

}
