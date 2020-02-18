package _666_;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.swing.JOptionPane;

import javaTools.Logger;
import javaTools.Observed;
import javaTools.Observer;

public class Core implements Observed {

	// logger
	Logger logger = Main.logger;

	// Observer Array list
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private LinkedHashSet<String[]> observerUpdateList = new LinkedHashSet<String[]>();

	// CLI aggregation
	CLI cli = new CLI();

	// Core attributs
	Table table = new Table();
	int roulette = 0;
	int phase = 0, phaseFull = 0;
	int tours = 0, toursTotal = 0, toursFull = 0;
	int deposit = Main.deposit, jetons = Main.deposit, jetonsTotal = Main.deposit, jetonsMax = Main.deposit, coef = 1,
			nbrMise = 0;
	int win = 0, gain = 0, gainTotal = 0, gainFull = 0, storeLineSize = 0;
	int miseOrigin = 0, coefOrigin = 0;
	boolean newBets = false, newCoef = false;
	boolean gameOver = false, autoPlay = Main.autoMode, viewModel = false;
	String alert = "", input = "", betsOrigin = "";
	// Main global variables :
	// Main.betMax, Main.gainMax, Main.phaseMax, Main.tourMax, Main.jetonLimite

	boolean run() {
		logger.logging("Core>>run()");

		if (Main.guiMode)
			return runGUI();
		else
			return runCLI();
	}

	boolean runGUI() {
		logger.logging("Core>>runGUI()");

		// GUI init
		storeLineSize = 6;
		Main.colorMode = false;
		GUI frame = new GUI(this);
		//frame.run();
		
		// prepare display dashboard & update OBSERVER
		prepareDisplay();
		updateObserver();
		return true;
	}

	boolean runCLI() {
		logger.logging("Core>>runCLI()");

		// CLI init
		storeLineSize = 20;

		// prepare display and update CLI dahsboard
		prepareDisplay();
		updateObserver();

		// infinite loop
		while (true)
			// process CLI Cycle Menu() to get roulette value and operate Spin operations
			if (!processCycleMenuCLI())
				break; // exit requested

		// exiting ...
		processExit();
		return false;
	}

	void prepareDisplay() {
		logger.logging("Core>>prepareDisplay()");

		// prepare display dashboard
		cli.updatePhaseString(phase, phaseFull);
		cli.updateTourString(tours, toursTotal, toursFull);
		cli.updateJetonString(jetons, jetonsTotal, jetonsMax);
		cli.updateGainString(win, gain, gainTotal, gainFull);
		cli.updateBets(table.getBets(), coef, nbrMise, newBets, newCoef);
	}

	void updateGUI() {
		logger.logging("Core>>updateGUI()");

		// get betTable in HTML format for Jtable element
		String betTable = table.betToHTML(true);
		
		// Prepare Observer update List
		observerUpdateList.add(new String[] { "jeton", cli._jetons_ });
		observerUpdateList.add(new String[] { "gain", cli._gains_ });
		observerUpdateList.add(new String[] { "cycle", cli._phase_ });
		observerUpdateList.add(new String[] { "tour", cli._tours_ });
		observerUpdateList.add(new String[] { "table", betTable });
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
		if (Main.warning != 0 && ( Main.deposit - jetons ) >= Main.warning )
			observerUpdateList.add(new String[] { "win", "" });
		if (!alert.isEmpty())
			observerUpdateList.add(new String[] { "alert", "" });
		if (newBets)
			observerUpdateList.add(new String[] { "newMise", "" });
		if (newCoef)
			observerUpdateList.add(new String[] { "newCoef", "" });
		if (autoPlay)
			observerUpdateList.add(new String[] { "auto", "" });
		if (viewModel)
			observerUpdateList.add(new String[] { "viewModel", "" });

		// update Observers
		// for(String[] item : observerUpdateList) logger.logging("["+item[0] + ":" +
		// item[1] + "]");
		for (Observer obs : observers) {
			obs.update(observerUpdateList);
		}
		observerUpdateList.clear();

		if (win > 0) {
			// GUI : throw dialog win information box
			String message = new String();
			message += String.format("- %-10s : %d (36x%d)\n", "win", win, (int) win / 36);
			message += String.format("- %-10s : %d\n", "gain", gain);
			message += String.format("- %-12s : %d\n", "gain total", gainTotal);
			JOptionPane.showMessageDialog(null, message, "you got a Win !!!", JOptionPane.INFORMATION_MESSAGE);

			// reset tours and win value
			tours = win = 0;
		}
	}

	void updateCLI() {
		logger.logging("Core>>updateCLI()");

		// prepare dashboard information
		String storeLine = (table.isStoreEmpty() ? "" : table.getStore(storeLineSize));
		String betTable = table.betToString();
		// display Dashboard
		cli.displayFullDashboard(storeLine, betTable);

		// information dialog message if win
		if (win > 0) {
			// CLI : display win information
			String message = new String();
			message += cli.raise("--> you got a Win !!! " + String.format("win : %d (36x%d)", win, (int) win / 36));
			message += cli.raise(String.format("\n--> gain/gain total : %3d/%3d", gain, gainTotal));
			System.out.println(message);

			// reset tours and win value
			tours = win = 0;
		} else
			System.out.println();
	}

	void processExit() {
		logger.logging("Core>>processExit()");
		logger.close();
		// logger.logging("\nExiting...");
		cli.close();
	}

	boolean processCycleMenuCLI() {
		logger.logging("Core>>processCycleMenuCLI()");

		// process Cycle Menu
		String input = cli.displayCycleMenu(autoPlay);

		// commute input to normated action Value
		switch (input) {
		case "q": // exit ....
			input = "Quit";
			break;
		// return false;
		case "r": // random Spin if simMode is ON
			if (Main.simMode) input = "Rand";
			break;
		case "o": // option menu requested
			input = "Menu";
			break;
		case "m": // toggle Main.colorMode
			input = "colorMode";
			break;
		case "a": // auto mode
			input = "Auto";
			break;
		default: // Spin value
			break;
		}

		// call common processAction switch
		if (!processAction(input))
			return false;
		return true;
	}

	void processOptionsMenu() {
		logger.logging("Core>>processOptionsMenu()");

		if (Main.guiMode)
			processOptionsMenuGUI();
		else
			processOptionsMenuCLI();
	}
	
	void processOptionsMenuCLI() {
		logger.logging("Core>>processOptionsMenuCLI()");

		// display Options menu and get new jetons if any
		int newJeton = cli.displaySetOptionsMenu();

		// update and validates toggles
		autoPlay = Main.autoMode;
		
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
					gameOver = false; alert = "";
				}
				break;
			case "limite": // alert = jeton
				// Check Jetons limites
				// if ((deposit - jetons) < Main.jetonLimite) {
				if (newJeton > Main.jetonLimite) {
					System.out.println("--> " + cli.raise("wallet is up and alive again !!"));
					input = "cycle back on";
					gameOver = false; alert = "";
				}
				break;
			default:
				// prepare jetons and display Global Status
				cli.updateJetonString(jetons, jetonsTotal, jetonsMax);
				cli.displayDashboardStatus();
				break;
			}

		} else
			System.out.println();
	}

	void processOptionsMenuGUI() {
		logger.logging("Core>>processOptionsMenuGUI()");
		//logger.logging("Core>> initial options:" + Main.optsToString());
		
		// call MenuDialog
		MenuDialog md = new MenuDialog(null, "Menu", true);
		MenuInfo mi = md.getMenuInfo();
		if (! mi.isDialogEnabled()) {
			logger.logging("Core>> canceled operation");
			logger.logging("Core>> final options:" + Main.optsToString());
			return;
		}
		;
		// get new args
		String args = mi.toArgOpts();
		if (args.isEmpty()) {
			logger.logging("Core>> args: empty ... --> no change, cancelling operation");
			logger.logging("Core>> final options:" + Main.optsToString());
			return;
		}
		//JOptionPane.showMessageDialog(null, args, "Menu", JOptionPane.INFORMATION_MESSAGE);
		logger.logging("Core>> args: " + args);

		// construct new args
		String[] args_ = args.split(" ");
		//logger.logging("Core>> args[] " + Arrays.toString(args_));

		// store initial values and reset simulation and auto modes
		int depositHold = Main.deposit;
		boolean simHold = Main.simMode;
		boolean autoHold = Main.autoMode;
		Main.simMode = Main.autoMode = false;

		// parse options args_ and set options
		if ( ! Main.setOptions(args_)) {
			logger.logging("Core>> getOpts Parsing error");
			logger.logging("Core>> final options:" + Main.optsToString());
			return;
		}
		logger.logging("Core>> final options:" + Main.optsToString());
		
		// check new jetons value if any, add them to deposit and check bet value again
		int newJeton = Main.deposit - depositHold;
		if (newJeton > 0) {
			jetons += newJeton;
			jetonsTotal += newJeton;
			jetonsMax += newJeton;
			deposit += newJeton;
			
			switch (alert) {
			case "bet": // alert = bet
				// Check Mise versus Jetons
				if (jetons >= nbrMise) {
					String message = "wallet is up and alive again !!\n We are back in business, dude !";
					JOptionPane.showMessageDialog(null, message, "Back in business", JOptionPane.INFORMATION_MESSAGE);
					input = "cycle back on";
					gameOver = false; alert = "";
				}
				break;
			case "limite": // alert = jeton
				// Check Jetons limites
				// if ((deposit - jetons) < Main.jetonLimite) {
				if (newJeton > Main.jetonLimite) {
					String message = "wallet is up and alive again !! \\n We are back in business, dude !";
					JOptionPane.showMessageDialog(null, message, "Back in business", JOptionPane.INFORMATION_MESSAGE);
					input = "cycle back on";
					gameOver = false; alert = "";
				}
				break;
			}
		}
		
		// simulation view and auto mode update
		viewModel= false;
		if (Main.simMode != simHold) {
			viewModel= true;
			logger.logging("Core>> --> simMode updated : " + Main.simMode);
		}
		autoPlay = Main.autoMode;
		if (autoPlay != autoHold)
			logger.logging("Core>>--> AutoMode updated : " + Main.autoMode);

		// prepare display dashboard & update OBSERVER
		prepareDisplay();
		updateObserver();
	}
	
	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub
		observers.add(obs);
	}

	@Override
	public void updateObserver() {
		// TODO Auto-generated method stub
		logger.logging("Core>>updateObserver()");

		if (Main.guiMode)
			updateGUI();
		else
			updateCLI();
	}

	@Override
	public void delObserver() {
		// TODO Auto-generated method stub
		logger.logging("Core>>delObserver()");

		observers = new ArrayList<Observer>();
	}

	public boolean processAction(String buttonTitle) {
		// TODO Auto-generated method stub
		logger.logging("Core>>processAction(" + buttonTitle + ")");

		switch (buttonTitle) {
		case "Quit": // Exit requested
			return false;
		case "colorMode" : // toggle Main.colorMode
			// logger.logging("Core>> - toggle Main.colorMode");
			//Main.colorMode = ! Main.colorMode;
			Main.colorMode = (Main.colorMode ? false : true);
			logger.logging("Core>>--> colorMode: " + Main.colorMode);
			// prepare display dashboard & update OBSERVER
			prepareDisplay();
			updateObserver();
			break;
		case "Auto": // auto mode
			autoPlay = !autoPlay;
			logger.logging("Core>>--> AutoMode: " + autoPlay);
			break;
		case "Rand":
			do {
				if (!operateSpin(randomSpin()))
					return false;
				// repeat if autoMode is TRUE and game is NOT over
			} while (autoPlay && !gameOver && (tours != 0));
			break;
		case "Spin":
			if (!operateSpin(expectSpin()))
				return false;
			break;
		case "Menu":
			// display Options menu and get new jetons if any
			processOptionsMenu();
			break;
		case "mIse":
			break;
		default: // roulette value (integer) provided in CLI process
			if (buttonTitle.matches("\\d+") && Integer.valueOf(buttonTitle) < 36)
				if (!operateSpin(buttonTitle))
					return false;
			break;
		}
		return true;
	}

	String randomSpin() {
		logger.logging("Core>>randomSpin()");

		// exit if Game is over
		if (gameOver)
			return null;
		int delay = 0; // 0.1sec per delay
		return input = String.valueOf(table.getRandomRoulette(delay));
	}

	String expectSpin() {
		logger.logging("Core>>expectSpin()");

		// exit if Game is over
		if (gameOver)
			return null;
		logger.logging("Core>>-->Spin DialogBox ");
		// return String.valueOf(ScanTools.scanIntRange("--> Roulette [0-36]", 0, 36));
		// JOptionPane jop = new JOptionPane();
		// String input = JOptionPane.showInputDialog
		String input;
		do {
			input = JOptionPane.showInputDialog(null, "Spin ? (0 - 36)", "Spin", JOptionPane.QUESTION_MESSAGE);
			if (input == null) {
				// logger.logging("Core>>-->Spin DialogBox canceled");
				return "";
			}
			if (!input.matches("(^[0-2]?[0-9]$)|(^3[0-6]$)")) {
				input = "";
			}
		} while (input.isEmpty());
		// logger.logging("Core>>-->Spin to (" + input + ")");
		return input;
	}

	void processJetonsCycleUpdate() {
		logger.logging("Core>>processJetonsUpdate()");

		jetons -= nbrMise;
		jetonsTotal = (jetons < jetonsTotal ? jetons : jetonsTotal);
		jetonsMax = (jetons < jetonsMax ? jetons : jetonsMax);
	}

	boolean processWin() {
		logger.logging("Core>>processWin()");

		// process win
		win = 0;
		if (table.betsContains(roulette)) {
			win = 36 * coef;
			jetons += win;
			gain = jetons - deposit;
			gainTotal += gain;
			gainFull += gain;

			// update deposit value
			deposit = (jetons > deposit) ? jetons : deposit;
			return true;
		}
		return false;
	}

	void processBetsSuggestion() {
		logger.logging("Core>>processBetsSuggestion()");

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
				logger.logging("Core>>--> coef: old: " + coefOrigin + ", new: " + coef);
			if (newBets)
				logger.logging("Core>>--> bets: old: " + betsOrigin + ", new: " + table.getBets());
		}
		// store next Origin values
		betsOrigin = table.getBets(); // betsOrigin = cli.bets;
		coefOrigin = coef;
	}

	boolean checkGameOverConditions() {
		logger.logging("Core>>checkGameOverConditions()");

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

	void processToursCycleUpdate() {
		logger.logging("Core>>processCycle()");

		// increments phase, counters and set display
		if (tours == 0) {
			phase++;
			phaseFull++;
		}
		tours++;
		toursTotal++;
		toursFull++;
	}

	int gameOverDialog() {
		logger.logging("Core>>gameOverDialog()");

		// JOptionPane jop = new JOptionPane();
		String title = "Game Over";
		String message = "";
		String gain_ = "--> Gains: " + String.format("%3s", (jetons - Main.deposit));
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
			logger.logging("--> action[" + index + "]: " + optionButtons[index]);
		return index;
	}

	boolean processPurge() {
		logger.logging("Core>>processPurge()");

		// process Purge
		logger.logging("--> purge process");
		String message = "";
		if (!alert.isEmpty()) {
			message = "Alert is raised... Can't purge store, sorry !";
		} else if (!table.reduceStore()) {
			message = "Store is empty... Can't purge store, sorry !";
		}
		if (!message.isEmpty()) {
			if (Main.guiMode) // GUI mode
				JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.WARNING_MESSAGE);
			else // CLI mode
				logger.logging(cli.alert(message));
			return false;
		}
		// autoPlay = Main.autoMode;
		gameOver = false;
		alert = "";
		return true;
	}

	void processRestart() {
		logger.logging("Core>>processRestart()");

		// process Restart
		table.resetTable();
		roulette = phase = tours = toursTotal = gain = gainTotal = coef = nbrMise = win = 0;
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

	boolean processGameOverMenu() {
		logger.logging("Core>>processGameOverMenu()");

		if (Main.guiMode)
			return processGameOverMenuGUI();
		else
			return processGameOverMenuCLI();
	}

	boolean processGameOverMenuGUI() {
		logger.logging("Core>>processGameOverMenuGUI()");

		// game over dialog
		int index = gameOverDialog();
		switch (index) {
		case -1:
			logger.logging("Core>> --> cancel process");
			break;
		// case 0:
		// logger.logging("--> quit process");
		// cli.close();
		// return false;
		case 0:
			// case 1:
			// process Purge
			processPurge();
			break;
		case 1:
			// case 2:
			// process Restart
			processRestart();
			break;
		}
		return true;
	}

	boolean processGameOverMenuCLI() {
		logger.logging("Core>>processGameOverMenuCLI()");

		// process GameOver Menu
		cli.displayGameOverLabel(alert, (gainTotal - (deposit - jetons)));
		input = "";
		do {
			input = cli.displayGameOverMenu();
			switch (input) {
			case "q": // quit
				return false;
			case "p": // purge table's store requested
				// process Purge
				if (processPurge())
					break;
				// display Global Status
				cli.displayDashboardStatus();
				// Jump back to Menu
				input = "";
				break;
			case "r": // restart table requested
				// process Restart
				processRestart();
				break;
			case "o": // option menu requested
				// display Options menu and get new jetons if any
				processOptionsMenu();
				// Jump back to Menu if game is still over
				if (gameOver)
					input = "";
				break;
			case "m": // toggle Main.colorMode
				// logger.logging("Core>> - toggle Main.colorMode");
				//Main.colorMode = ! Main.colorMode;
				Main.colorMode = (Main.colorMode ? false : true);
				// Jump back to Menu
				input = "";
				break;
			case "a": // toggle Main.auto mode
				// logger.logging("Core>> - toggle Main.autoMode");
				//Main.autoMode = ! Main.autoMode;
				Main.autoMode = (Main.autoMode ? false : true);
				// Jump back to Menu
				input = "";
				break;
			default:
				// logger.logging("##1.4 - default case");
				// display Global Status
				cli.displayDashboardStatus();
				// Jump back to Menu
				input = "";
				break;
			}

		} while (input.isEmpty());
		return true;
	}

	boolean operateSpin(String input) {
		logger.logging("Core>>operateSpin(" + input + ")");

		if (!gameOver) {
			if (input.isEmpty())
				return false; // exit requested

			// get roulette value
			roulette = Integer.valueOf(input);

			// process Cycle updates and Win check
			processJetonsCycleUpdate();
			processToursCycleUpdate();
			processWin();

			// add roulette value to table and get Bets suggestions
			table.addOccurence(roulette);
			processBetsSuggestion();

			// GameOver check
			gameOver = checkGameOverConditions();

			// prepare display dashboard & update OBSERVER
			prepareDisplay();
			updateObserver(); // and finally update tours
		}

		// run exit prompt if required
		if (gameOver) {

			// process GameOver menu
			if (!processGameOverMenu())
				return false; // ext requested

			// get Bets suggestions
			processBetsSuggestion();

			// prepare display dashboard & update OBSERVER
			prepareDisplay();
			updateObserver();
		}
		return true;
	}

}
