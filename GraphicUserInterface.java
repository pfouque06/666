package _666_;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class GraphicUserInterface extends JFrame implements ActionListener {
	
	private JPanel container = new JPanel();
	private JPanel labelPan = new JPanel();
	private JPanel jetonPan = new JPanel();
	private JLabel jetonLabel = new JLabel();
	private JPanel gainPan = new JPanel();
	private JLabel gainLabel = new JLabel();
	private JPanel tablePan = new JPanel();
	private JPanel storePan = new JPanel();
	private JLabel storeLabel = new JLabel();
	private JPanel betsPan = new JPanel();
	private JLabel betsLabel = new JLabel();
	private JPanel actionPan = new JPanel();
	private JButton menuButton = new JButton("Options");
	private JButton betsButton = new JButton("Bets");
	private JButton rollButton = new JButton("Bille");
	private JButton spinButton = new JButton("Spin");
	
	
	public GraphicUserInterface() {
		// TODO Auto-generated constructor stub

		// On initialise la JFrame
		this.setTitle("_666_");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(false);
		//this.setSize(290, 210); // Home setting
		this.setSize(400, 300);
		//this.setResizable(true);
		this.setResizable(true);
		this.setAlwaysOnTop(true);
		
		//labelPan init
		Dimension subLabelDim = new Dimension(100, 40);
		Dimension labelDim = new Dimension(300, 50);
		jetonLabel.setBackground(Color.WHITE);
		jetonLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		jetonLabel.setLocation(10, 0);
		jetonLabel.setSize(new Dimension(120, 40));
		jetonLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "jetons", TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		jetonLabel.setText("0");
		gainLabel.setBackground(UIManager.getColor("Button.light"));
		gainLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		gainLabel.setLocation(224, 0);
		gainLabel.setSize(new Dimension(150, 40));
		gainLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "gains", TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		gainLabel.setText("0");
		//labelPan.setSize(300, 50);
		labelPan.setPreferredSize(labelDim);
		labelPan.setLayout(null);
		labelPan.add(jetonLabel);
		labelPan.add(gainLabel);
		//jetonPan.add(jetonLabel);
		//gainPan.add(gainLabel);
		
		//betPan init
		Dimension subBetsDim = new Dimension(150, 40);
		Dimension BetsDim = new Dimension(170, 100);
		betsLabel.setBackground(Color.WHITE);
		betsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		betsLabel.setLocation(10, 0);
		betsLabel.setSize(subBetsDim);
		betsLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "mises", TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		betsLabel.setText("0");
		//menuButton.setBounds(10, 10, 70, 30);
		betsButton.setLocation(90, 50);
		betsButton.setSize(70, 30);
		betsButton.setPreferredSize(new Dimension(70, 30));
		betsButton.addActionListener(this);
		betsPan.setPreferredSize(BetsDim);
		betsPan.setLayout(null);
		betsPan.add(betsLabel);
		betsPan.add(betsButton);
		
		// table init
		storePan.add(storeLabel);
		tablePan.setLayout(new BorderLayout());
		tablePan.add(storePan);
		
		// action Pan
		Dimension subActionlDim = new Dimension(70, 30);
		Dimension ActionDim = new Dimension(300, 50);
		//menuButton.setBounds(10, 10, 70, 30);
		menuButton.setLocation(10, 10);
		menuButton.setSize(80, 30);
		menuButton.setPreferredSize(subActionlDim);
		menuButton.addActionListener(this);
		//spinButton.setBounds(120, 10, 70, 30);
		spinButton.setLocation(224,10);
		spinButton.setSize(70, 30);
		spinButton.setPreferredSize(subActionlDim);
		spinButton.addActionListener(this);
		//rollButton.setBounds(200, 10, 70, 30);
		rollButton.setLocation(304, 10);
		rollButton.setSize(70, 30);
		rollButton.setPreferredSize(subActionlDim);
		rollButton.addActionListener(this);
		//actionPan.setSize(ActionDim);
		actionPan.setPreferredSize(ActionDim);
		actionPan.setLayout(null);
		actionPan.add(menuButton);
		actionPan.add(spinButton);
		actionPan.add(rollButton);
		
		// container panel of JFrame setup
		container.setLayout(new BorderLayout());
		//container.setBorder(new EmptyBorder(2, 2, 2, 2));
		container.add(labelPan, BorderLayout.NORTH);
		container.add(tablePan, BorderLayout.WEST);
		container.add(betsPan, BorderLayout.EAST);
		container.add(actionPan, BorderLayout.SOUTH);
		this.setContentPane(container);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(">> actionPerformed");
		//JButton buttonHit= (JButton) arg0.getSource();
		//String buttonTitle = buttonHit.getName();
		String buttonTitle = arg0.getActionCommand();
		System.out.println(">> buttonTitle= " + buttonTitle);

	}


}
