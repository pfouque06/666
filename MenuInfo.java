package _666_;

public class MenuInfo {

	private String deposit, limite, warning, gain;
	private String phase, tour, bet;
	private boolean simMode, autoMode;

	public MenuInfo() {
		super();
	}

	public MenuInfo(String deposit, String limite, String warning, String gain, String phase, String tour, String bet,
			boolean simMode, boolean autoMode) {
		super();
		this.deposit = deposit;
		this.limite = limite;
		this.warning = warning;
		this.gain = gain;
		this.phase = phase;
		this.tour = tour;
		this.bet = bet;
		this.simMode = simMode;
		this.autoMode = autoMode;
	}

	/**
	 * @return the deposit
	 */
	public String getDeposit() {
		return deposit;
	}

	/**
	 * @param deposit the deposit to set
	 */
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	/**
	 * @return the limite
	 */
	public String getLimite() {
		return limite;
	}

	/**
	 * @param limite the limite to set
	 */
	public void setLimite(String limite) {
		this.limite = limite;
	}

	/**
	 * @return the warning
	 */
	public String getWarning() {
		return warning;
	}

	/**
	 * @param warning the warning to set
	 */
	public void setWarning(String warning) {
		this.warning = warning;
	}

	/**
	 * @return the gain
	 */
	public String getGain() {
		return gain;
	}

	/**
	 * @param gain the gain to set
	 */
	public void setGain(String gain) {
		this.gain = gain;
	}

	/**
	 * @return the phase
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(String phase) {
		this.phase = phase;
	}

	/**
	 * @return the tour
	 */
	public String getTour() {
		return tour;
	}

	/**
	 * @param tour the tour to set
	 */
	public void setTour(String tour) {
		this.tour = tour;
	}

	/**
	 * @return the bet
	 */
	public String getBet() {
		return bet;
	}

	/**
	 * @param bet the bet to set
	 */
	public void setBet(String bet) {
		this.bet = bet;
	}

	/**
	 * @return the simMode
	 */
	public boolean isSimMode() {
		return simMode;
	}

	/**
	 * @param simMode the simMode to set
	 */
	public void setSimMode(boolean simMode) {
		this.simMode = simMode;
	}

	/**
	 * @return the autoMode
	 */
	public boolean isAutoMode() {
		return autoMode;
	}

	/**
	 * @param autoMode the autoMode to set
	 */
	public void setAutoMode(boolean autoMode) {
		this.autoMode = autoMode;
	}

	@Override
	public String toString() {
		return "MenuInfo :" + 
				"\ndeposit=" + deposit + 
				"\nwarning=" + warning + 
				"\nlimite=" + limite + 
				"\ngain=" + gain
				+ "\nphase=" + phase + 
				"\ntour=" + tour + 
				"\nbet=" + bet + 
				"\nsimMode=" + simMode + 
				"\nautoMode=" + autoMode;
	}

	public String toArgOpts() {
		String args = "";
		args += (! simMode ? "" : " -S");
		args += (! autoMode ? "" : " -a");
		args += (deposit.equals("0") ? "" : " -d " + deposit);
		args += (warning.equals("0") ? "" : " -w " + warning);
		args += (limite.equals("0") ? "" : " -l " + limite);
		args += (gain.equals("0") ? "" : " -g " + gain);
		args += (phase.equals("0") ? "" : " -p " + phase);
		args += (tour.equals("0") ? "" : " -t " + tour);
		args += (bet.equals("0") ? "" : " -b " + bet);
		
		// trim and reduce internal whitepaces and return result
		return args.trim().replaceAll(" +", " ");
	}
}
