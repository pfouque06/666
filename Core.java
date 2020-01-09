package _666_;

class Core {

	CommandLineInterface cli = new CommandLineInterface();
	Table table = new Table();
	int roulette = 0;
	int phase = 0, phaseFull = 0;
	int tours = 0, toursTotal = 0, toursFull = 0;
	int deposit = Main.deposit, jetons = Main.deposit, jetonsTotal = Main.deposit, jetonsMax = Main.deposit, coef = 1, nbrMise = 0;
	int win = 0, gain = 0, gainTotal = 0, gainFull = 0;
	int nbrMiseOrigin = 0, coefOrigin = 0;
	boolean newMise = false, newCoef = false;
	boolean gameOver = false, autoPlay = Main.autoMode;
	String alert = "", input = "", betsOrigin = "";
	// Main global variables :
	// Main.betMax, Main.gainMax, Main.phaseMax, Main.tourMax, Main.jetonLimite

	boolean Run() {

		while (true) {
			// ---------------------
			// display dashboard
			// ---------------------
			String storeLine = (table.isStoreEmpty() ? "" : table.getStore(20)) + ":";
			String betTable = table.betToString(Main.colorMode);
			// display Dashboard
			cli.displayDashboardTable(storeLine, betTable);
			cli.displayDashboardStatus();
			
			// ---------------------
			// exit conditions check
			// ---------------------
			// if table is full (all values are completed)
			// or if Main.gainMax, Main.phaseMax or Main.tourMax are reached
			if (table.isFull()
					|| (Main.gainMax != 0 ? gainTotal >= Main.gainMax : false)
					|| (Main.phaseMax != 0 && tours == 0 ? phase >= Main.phaseMax : false)
					|| (Main.tourMax != 0 && tours == 0 ? toursTotal >= Main.tourMax : false)) {
				gameOver = true; // launch exit menu
			}

			// Check Mise versus Jetons
			if ( jetons < nbrMise ) {
				gameOver = true; // launch exit menu
				alert = "bet";  // set alert
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
							System.out.println( cli.alert("Alert is raised") + " .. Can't purge store, sorry !");
							input = "";
						} else if (!table.reduceStore()) {
							System.out.println( cli.alert("Store is empy") + " .. Can't purge store, sorry !");
							input = "";
						}
						break;
					case "r": // restart table requested
						// System.out.println("##1.3 - reset table");
						table.resetTable();
						roulette = 0; phase = 0; tours = 0; toursTotal = 0; gain = 0; gainTotal = 0;
						coef = 1; nbrMise = 0; 
						if (! alert.isEmpty()) {
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
						if ( newJeton > 0 ) {
							jetons += newJeton;
							jetonsTotal += newJeton;
							jetonsMax += newJeton;
							deposit += newJeton;
							
							switch (alert) {
							case "bet": // alert = bet
								// Check Mise versus Jetons
								if ( jetons >= nbrMise) {
									System.out.println("--> " +cli.raise("wallet is up and alive again !!"));
									input = "cycle back on";
								}
								break;
							case "limite": // alert = jeton
								// Check Jetons limites
								//if ((deposit - jetons) < Main.jetonLimite) {
								if ( newJeton > Main.jetonLimite) {
									System.out.println("--> " +cli.raise("wallet is up and alive again !!"));
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
					coef = 1;
				}
				
				// update deposit value
				deposit = (jetons > deposit) ? jetons : deposit;
				
				// prepare display
				cli.updatePhaseString(phase, phaseFull);
				cli.updateTourString(tours, toursTotal, toursFull);
				cli.updateJetonString(jetons, jetonsTotal, jetonsMax);
				cli.updateGainString(win, gain, gainTotal, gainFull);


				// add roulette value to table
				table.addOccurence(roulette);
			}

			// get Bets suggestions
			cli.updateBets("");
			betsOrigin = table.getBets();
			//nbrMiseOrigin = table.getBetsSize();
			coefOrigin = coef;
			table.setBets();
			if (!table.isBetsEmpty()) {
				nbrMise = table.getBetsSize();
				newMise = (! betsOrigin.equals(table.getBets())) ? true : false;
				int delta = deposit - jetons;
				delta = ( delta > 0 ? delta : 0);
				coef = delta / ( 36 - nbrMise ) + 1; // ceiling(( deposit - jetons ) / (36 - NbrMise ))
				nbrMise *= coef;
				newCoef = coef != coefOrigin ? true : false;
				cli.updateBets(table.getBets(), coef , newCoef, nbrMise, newMise);
			}
		}
	}
}
