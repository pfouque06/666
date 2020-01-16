package _666_;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import javaTools.Observer;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class GraphicUserInterface extends JFrame implements ActionListener {

	private JPanel container = new JPanel();
	private JPanel labelPan = new JPanel();
	private JLabel jetonLabel = new JLabel();
	private JLabel gainLabel = new JLabel();
	// private JPanel tablePan = new JPanel();
	private JPanel storePan = new JPanel();
	private JLabel storeLabel = new JLabel();
	private JPanel betsPan = new JPanel();
	private JLabel betsLabel = new JLabel();
	private JLabel coefLabel = new JLabel();
	private JLabel miseLabel = new JLabel();
	private JLabel cycleLabel = new JLabel();
	private JLabel tourLabel = new JLabel();
	private JPanel actionPan = new JPanel();
	private JButton menuButton = new JButton("Options");
	private JButton betsButton = new JButton("Bets");
	private JButton rollButton = new JButton("Bille");
	private JButton spinButton = new JButton("Spin");

	// Core instance
	private Core core;

	public GraphicUserInterface() {
		// TODO Auto-generated constructor stub

		// On initialise la JFrame
		this.setTitle("_666_");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(false);
		this.setSize(400, 300);
		this.setResizable(false);
		this.setAlwaysOnTop(false);

		// labelPan init
		Dimension subLabelDim = new Dimension(100, 40);
		Dimension labelDim = new Dimension(400, 50);
		jetonLabel.setBackground(Color.WHITE);
		jetonLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		jetonLabel.setLocation(10, 0);
		jetonLabel.setSize(new Dimension(180, 40));
		jetonLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "jetons",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		jetonLabel.setText("0");

		gainLabel.setBackground(UIManager.getColor("Button.light"));
		gainLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		gainLabel.setLocation(210, 0);
		gainLabel.setSize(new Dimension(180, 40));
		gainLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "gains",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		gainLabel.setText("0");
		// labelPan.setSize(300, 50);

		labelPan.setPreferredSize(labelDim);
		labelPan.setLayout(null);
		labelPan.add(jetonLabel);
		labelPan.add(gainLabel);
		// jetonPan.add(jetonLabel);
		// gainPan.add(gainLabel);

		// betPan init
		Dimension subBetsDim = new Dimension(180, 40);
		Dimension BetsDim = new Dimension(200, 100);
		betsLabel.setBackground(Color.WHITE);
		betsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		betsLabel.setLocation(10, 0);
		betsLabel.setSize(subBetsDim);
		betsLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "à miser",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		betsLabel.setText("");

		coefLabel.setBackground(Color.WHITE);
		coefLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		coefLabel.setLocation(10, 50);
		coefLabel.setSize(new Dimension(60, 40));
		coefLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "coef",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		coefLabel.setText("");

		miseLabel.setBackground(Color.WHITE);
		miseLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		miseLabel.setLocation(130, 50);
		miseLabel.setSize(new Dimension(60, 40));
		miseLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "mises",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		miseLabel.setText("");

		betsButton.setLocation(10, 130);
		betsButton.setSize(70, 30);
		betsButton.setPreferredSize(new Dimension(70, 30));
		betsButton.addActionListener(this);
		betsButton.setVisible(false);

		menuButton.setLocation(100, 130);
		menuButton.setSize(90, 30);
		menuButton.setPreferredSize(new Dimension(90, 30));
		menuButton.addActionListener(this);
		menuButton.setVisible(false);

		betsPan.setPreferredSize(BetsDim);
		betsPan.setLayout(null);
		betsPan.add(betsLabel);
		betsPan.add(coefLabel);
		betsPan.add(miseLabel);
		betsPan.add(menuButton);
		betsPan.add(betsButton);

		// table init
		Dimension subStoreDim = new Dimension(180, 40);
		Dimension StoreDim = new Dimension(200, 100);
		storeLabel.setBackground(Color.WHITE);
		storeLabel.setHorizontalAlignment(SwingConstants.LEADING);
		storeLabel.setLocation(10, 0);
		storeLabel.setSize(subStoreDim);
		storeLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "store",
				TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		storeLabel.setText("");

		storePan.setPreferredSize(StoreDim);
		storePan.setLayout(null);
		storePan.add(storeLabel);
		// tablePan.add(storePan);
		// tablePan.setPreferredSize(BetsDim);
		// tablePan.setLayout(null);
		// tablePan.add(storePan);

		// action Pan
		Dimension subActionlDim = new Dimension(70, 30);
		Dimension ActionDim = new Dimension(400, 50);
		// menuButton.setBounds(10, 10, 70, 30);
		cycleLabel.setBackground(Color.WHITE);
		cycleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		cycleLabel.setLocation(10, 0);
		cycleLabel.setSize(new Dimension(60, 40));
		cycleLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "cycles",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		cycleLabel.setText("");

		tourLabel.setBackground(Color.WHITE);
		tourLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		tourLabel.setLocation(80, 0);
		tourLabel.setSize(new Dimension(100, 40));
		tourLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "tours",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		tourLabel.setText("");

		// spinButton.setBounds(120, 10, 70, 30);
		spinButton.setLocation(240, 10);
		spinButton.setSize(70, 30);
		spinButton.setPreferredSize(subActionlDim);
		spinButton.addActionListener(this);

		// rollButton.setBounds(200, 10, 70, 30);
		rollButton.setLocation(320, 10);
		rollButton.setSize(70, 30);
		rollButton.setPreferredSize(subActionlDim);
		rollButton.addActionListener(this);

		// actionPan.setSize(ActionDim);
		actionPan.setPreferredSize(ActionDim);
		actionPan.setLayout(null);
		actionPan.add(cycleLabel);
		actionPan.add(tourLabel);
		actionPan.add(spinButton);
		actionPan.add(rollButton);

		// container panel of JFrame setup
		container.setLayout(new BorderLayout());
		// container.setBorder(new EmptyBorder(2, 2, 2, 2));
		container.add(labelPan, BorderLayout.NORTH);
		container.add(storePan, BorderLayout.WEST);
		container.add(betsPan, BorderLayout.EAST);
		container.add(actionPan, BorderLayout.SOUTH);
		this.setContentPane(container);
		this.getRootPane().setDefaultButton(spinButton);
		this.setVisible(true);

		// Core instance
		core = new Core();
		core.addObserver(new Observer() {

			@Override
			public void update(LinkedHashSet<String[]> pLHS) {
				// TODO Auto-generated method stub
				System.out.println("GUI>>update(pLHS)");
				jetonLabel.setForeground(Color.black);
				gainLabel.setForeground(Color.black);
				betsLabel.setForeground(Color.black);
				coefLabel.setForeground(Color.black);
				miseLabel.setForeground(Color.black);
				betsLabel.setText("");
				coefLabel.setText("");
				miseLabel.setText("");
				storeLabel.setText("");
				for (String[] item : pLHS) {
					System.out.println("[" + item[0] + ":" + item[1] + "]");
					switch (item[0]) {
					case "newMise":
						betsLabel.setForeground(Color.blue);
						miseLabel.setForeground(Color.blue);
						break;
					case "newCoef":
						coefLabel.setForeground(Color.blue);
						miseLabel.setForeground(Color.blue);
						break;
					case "win":
						jetonLabel.setForeground(Color.green);
						gainLabel.setForeground(Color.green);
						break;
					case "warning":
						jetonLabel.setForeground(Color.blue);
						gainLabel.setForeground(Color.blue);
						break;
					case "alert":
						jetonLabel.setForeground(Color.red);
						gainLabel.setForeground(Color.red);
						break;
					case "jeton":
						jetonLabel.setText(item[1]);
						break;
					case "gain":
						gainLabel.setText(item[1]);
						break;
					case "bets":
						betsLabel.setText(item[1]);
						break;
					case "coef":
						coefLabel.setText(item[1]);
						break;
					case "mise":
						miseLabel.setText(item[1]);
						break;
					case "store":
						storeLabel.setText(item[1]);
						break;
					case "cycle":
						cycleLabel.setText(item[1]);
						break;
					case "tour":
						tourLabel.setText(item[1]);
						break;
					default:
						break;
					}
				}
				container.setVisible(false);
				container.setVisible(true);
				// this.setVisible(true);

			}

		});

		core.runGUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("GUI>>actionPerformed");
		// JButton buttonHit= (JButton) arg0.getSource();
		// String buttonTitle = buttonHit.getName();
		String buttonTitle = arg0.getActionCommand();
		System.out.println("GUI>>-->buttonTitle= " + buttonTitle);
		switch (buttonTitle) {
		default:
			core.processAction(buttonTitle);
			break;
		}
	}

}
