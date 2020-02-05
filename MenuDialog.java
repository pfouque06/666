package _666_;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenuDialog extends JDialog {
	
	private MenuInfo info = new MenuInfo();
	private boolean sendData;
	private JPanel depositBorder, warningBorder, limiteBorder, gainBorder, phaseBorder, tourBorder, betBorder;
	private JSpinner depositSpinner, warningSpinner, limiteSpinner, gainSpinner, phaseSpinner, tourSpinner, betSpinner;
	private JCheckBox chckbxSimulation, chckbxAutoSpin;
	private JButton btnOk;
	
	public MenuDialog(JFrame parent, String title, boolean modal){
	    super(parent, title, modal);
	    this.setSize(300, 330);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    this.buildContent();
	}
	
	public MenuInfo showMenuInfo(){
	    this.sendData = false;
	    this.setVisible(true);      
	    return this.info;      
	  }
	
	public void buildContent() {
		this.getContentPane().setLayout(null);
		
		depositBorder = new JPanel();
		depositBorder.setLayout(null);
		depositBorder.setBorder(new TitledBorder(null, "Deposit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		depositBorder.setBounds(12, 12, 147, 52);
		this.getContentPane().add(depositBorder);
		
		depositSpinner = new JSpinner();
		depositSpinner.setBounds(5, 17, 130, 30);
		depositBorder.add(depositSpinner);
		
		warningBorder = new JPanel();
		warningBorder.setLayout(null);
		warningBorder.setBorder(new TitledBorder(null, "Warning", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		warningBorder.setBounds(12, 76, 147, 52);
		this.getContentPane().add(warningBorder);
		
		warningSpinner = new JSpinner();
		warningSpinner.setBounds(5, 17, 130, 30);
		warningBorder.add(warningSpinner);
		
		limiteBorder = new JPanel();
		limiteBorder.setLayout(null);
		limiteBorder.setBorder(new TitledBorder(null, "Limite", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		limiteBorder.setBounds(12, 140, 147, 52);
		this.getContentPane().add(limiteBorder);
		
		limiteSpinner = new JSpinner();
		limiteSpinner.setBounds(5, 17, 130, 30);
		limiteBorder.add(limiteSpinner);
		
		gainBorder = new JPanel();
		gainBorder.setLayout(null);
		gainBorder.setBorder(new TitledBorder(null, "Gain", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gainBorder.setBounds(12, 204, 147, 52);
		this.getContentPane().add(gainBorder);
		
		gainSpinner = new JSpinner();
		gainSpinner.setBounds(5, 17, 130, 30);
		gainBorder.add(gainSpinner);

		phaseBorder = new JPanel();
		phaseBorder.setLayout(null);
		phaseBorder.setBorder(new TitledBorder(null, "phase", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		phaseBorder.setBounds(189, 12, 95, 52);
		this.getContentPane().add(phaseBorder);
		
		phaseSpinner = new JSpinner();
		phaseSpinner.setBounds(5, 17, 80, 30);
		phaseBorder.add(phaseSpinner);
		
		tourBorder = new JPanel();
		tourBorder.setLayout(null);
		tourBorder.setBorder(new TitledBorder(null, "Tour", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tourBorder.setBounds(189, 76, 95, 52);
		this.getContentPane().add(tourBorder);
		
		tourSpinner = new JSpinner();
		tourSpinner.setBounds(5, 17, 80, 30);
		tourBorder.add(tourSpinner);
		
		betBorder = new JPanel();
		betBorder.setLayout(null);
		betBorder.setBorder(new TitledBorder(null, "Bet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		betBorder.setBounds(189, 140, 95, 52);
		this.getContentPane().add(betBorder);
		
		betSpinner = new JSpinner();
		betSpinner.setBounds(5, 17, 80, 30);
		betBorder.add(betSpinner);
		

		chckbxSimulation = new JCheckBox("Simulation");
		chckbxSimulation.setBounds(189, 204, 95, 23);
		this.getContentPane().add(chckbxSimulation);
		
		chckbxAutoSpin = new JCheckBox("Auto Spin");
		chckbxAutoSpin.setBounds(189, 233, 95, 23);
		this.getContentPane().add(chckbxAutoSpin);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(224, 263, 60, 25);
		this.getContentPane().add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
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
				
		        setVisible(false);
				
			}
		});
		
	}
}
