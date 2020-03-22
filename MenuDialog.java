package _666_;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import javaTools.Logger;

import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

public class MenuDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// logger
	Logger logger = Main.logger;

	private MenuInfo info = new MenuInfo();
	private JPanel depositBorder, warningBorder, limiteBorder, gainBorder, phaseBorder, tourBorder, betBorder;
	private JSpinner depositSpinner, warningSpinner, limiteSpinner, gainSpinner, phaseSpinner, tourSpinner, betSpinner;
	private JCheckBox chckbxSimulation, chckbxAutoSpin;
	private JButton okButton;
	
	public MenuDialog(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
	    this.setSize(300, 330);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    //this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    //this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	    // Rather catch lose event in addWindowListener.windowClosing(WindowEvent e) method
	    this.buildContent();
	}

	public MenuInfo getMenuInfo(){
		logger.logging("MenuDialog>>getMenuInfo()");
	    this.setVisible(true);      
	    return this.info;
	  }

	public void buildContent() {
		logger.logging("MenuDialog>>buildContent()");
		this.getContentPane().setLayout(null);
		SpinnerNumberModel spinModel;
		
		depositBorder = new JPanel();
		depositBorder.setLayout(null);
		depositBorder.setBorder(new TitledBorder(null, "Deposit (inc)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		depositBorder.setBounds(12, 12, 147, 52);
		this.getContentPane().add(depositBorder);
		
		spinModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		depositSpinner = new JSpinner(spinModel);
		depositSpinner.setBounds(5, 17, 130, 30);
		depositBorder.add(depositSpinner);
		
		warningBorder = new JPanel();
		warningBorder.setLayout(null);
		warningBorder.setBorder(new TitledBorder(null, "Warning", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		warningBorder.setBounds(12, 76, 147, 52);
		this.getContentPane().add(warningBorder);
		
		spinModel = new SpinnerNumberModel(Main.warning, 0, Integer.MAX_VALUE, 1);
		warningSpinner = new JSpinner(spinModel);
		warningSpinner.setBounds(5, 17, 130, 30);
		warningBorder.add(warningSpinner);
		
		limiteBorder = new JPanel();
		limiteBorder.setLayout(null);
		limiteBorder.setBorder(new TitledBorder(null, "Limite", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		limiteBorder.setBounds(12, 140, 147, 52);
		this.getContentPane().add(limiteBorder);
		
		spinModel = new SpinnerNumberModel(Main.jetonLimite, 0, Integer.MAX_VALUE, 1);
		limiteSpinner = new JSpinner(spinModel);
		limiteSpinner.setBounds(5, 17, 130, 30);
		limiteBorder.add(limiteSpinner);
		
		gainBorder = new JPanel();
		gainBorder.setLayout(null);
		gainBorder.setBorder(new TitledBorder(null, "Gain", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gainBorder.setBounds(12, 204, 147, 52);
		this.getContentPane().add(gainBorder);
		
		spinModel = new SpinnerNumberModel(Main.gainMax, 0, Integer.MAX_VALUE, 1);
		gainSpinner = new JSpinner(spinModel);
		gainSpinner.setBounds(5, 17, 130, 30);
		gainBorder.add(gainSpinner);

		phaseBorder = new JPanel();
		phaseBorder.setLayout(null);
		phaseBorder.setBorder(new TitledBorder(null, "phase", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		phaseBorder.setBounds(189, 12, 95, 52);
		this.getContentPane().add(phaseBorder);
		
		spinModel = new SpinnerNumberModel(Main.phaseMax, 0, Integer.MAX_VALUE, 1);
		phaseSpinner = new JSpinner(spinModel);
		phaseSpinner.setBounds(5, 17, 80, 30);
		phaseBorder.add(phaseSpinner);
		
		tourBorder = new JPanel();
		tourBorder.setLayout(null);
		tourBorder.setBorder(new TitledBorder(null, "Tour", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tourBorder.setBounds(189, 76, 95, 52);
		this.getContentPane().add(tourBorder);
		
		spinModel = new SpinnerNumberModel(Main.phaseMax, 0, Integer.MAX_VALUE, 1);
		tourSpinner = new JSpinner(spinModel);
		tourSpinner.setBounds(5, 17, 80, 30);
		tourBorder.add(tourSpinner);
		
		betBorder = new JPanel();
		betBorder.setLayout(null);
		betBorder.setBorder(new TitledBorder(null, "Bet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		betBorder.setBounds(189, 140, 95, 52);
		this.getContentPane().add(betBorder);
		
		spinModel = new SpinnerNumberModel(Main.betMax, 0, Integer.MAX_VALUE, 1);
		betSpinner = new JSpinner(spinModel);
		betSpinner.setBounds(5, 17, 80, 30);
		betBorder.add(betSpinner);

		chckbxAutoSpin = new JCheckBox("Auto Spin");
		chckbxAutoSpin.setBounds(189, 204, 95, 23);
		chckbxAutoSpin.setSelected(Main.autoMode);
		this.getContentPane().add(chckbxAutoSpin);
		
		chckbxSimulation = new JCheckBox("Simulation");
		chckbxSimulation.setBounds(189, 233, 95, 23);
		chckbxSimulation.setSelected(Main.simMode);
		this.getContentPane().add(chckbxSimulation);
		
		okButton = new JButton("OK");
		okButton.setBounds(224, 263, 60, 25);
		this.getContentPane().add(okButton);
		this.getRootPane().setDefaultButton(okButton);
		
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logger.logging("MenuDialog>>okButton.addActionListener.actionPerformed()");
				info = new MenuInfo();
				info.setDeposit(depositSpinner.getValue().toString());
				info.setWarning(warningSpinner.getValue().toString());
				info.setLimite(limiteSpinner.getValue().toString());
				info.setGain(gainSpinner.getValue().toString());
				info.setPhase(phaseSpinner.getValue().toString());
				info.setTour(tourSpinner.getValue().toString());
				info.setBet(betSpinner.getValue().toString());
				info.setSimMode(chckbxSimulation.isSelected());
				info.setAutoMode(chckbxAutoSpin.isSelected());
				info.setDialogEnabled(true);
				
		        setVisible(false);
				
			}
		});
		
		// Add window listener by implementing WindowAdapter class to
		// the frame instance. To handle the close event we just need
		// to implement the windowClosing() method.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logger.logging("MenuDialog>>addWindowListener.windowClosing()");

				info = new MenuInfo();
				info.setDialogEnabled(false);
				
		        setVisible(false);
			}
		});
		
		
	}
}
