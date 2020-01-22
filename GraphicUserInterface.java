package _666_;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashSet;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import javaTools.Logger;
import javaTools.Observer;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class GraphicUserInterface extends JFrame implements ActionListener {

	// logger
	Logger logger = Main.logger;

	private JPanel container = new JPanel();
	private JPanel labelPan = new JPanel();
	private JLabel jetonLabel = new JLabel();
	private JLabel gainLabel = new JLabel();
	private JPanel storePan = new JPanel();
	private JLabel storeLabel = new JLabel();
	private JLabel tableLabel = new JLabel();
	private JPanel misePan = new JPanel();
	private JLabel betsLabel = new JLabel();
	private JLabel coefLabel = new JLabel();
	private JLabel miseLabel = new JLabel();
	private JLabel cycleLabel = new JLabel();
	private JLabel tourLabel = new JLabel();
	private JPanel actionPan = new JPanel();
	private JToggleButton autoButton = new JToggleButton("Auto", Main.autoMode) {
		@Override
		protected boolean processKeyBinding(KeyStroke ks, KeyEvent ke, int i, boolean bln) {
			boolean b = super.processKeyBinding(ks, ke, i, bln);
			if (b && ks.getKeyCode() == KeyEvent.VK_A)
				requestFocusInWindow();
			return b;
		}
	};
	private JButton optionsButton = new JButton("Options") {
		@Override
		protected boolean processKeyBinding(KeyStroke ks, KeyEvent ke, int i, boolean bln) {
			boolean b = super.processKeyBinding(ks, ke, i, bln);
			if (b && ks.getKeyCode() == KeyEvent.VK_O)
				requestFocusInWindow();
			return b;
		}
	};
	private JButton miseButton = new JButton("Mise") {
		@Override
		protected boolean processKeyBinding(KeyStroke ks, KeyEvent ke, int i, boolean bln) {
			boolean b = super.processKeyBinding(ks, ke, i, bln);
			if (b && ks.getKeyCode() == KeyEvent.VK_M)
				requestFocusInWindow();
			return b;
		}
	};
	private JButton randButton = new JButton("Rand") {
		@Override
		protected boolean processKeyBinding(KeyStroke ks, KeyEvent ke, int i, boolean bln) {
			boolean b = super.processKeyBinding(ks, ke, i, bln);
			if (b && ks.getKeyCode() == KeyEvent.VK_R)
				requestFocusInWindow();
			return b;
		}
	};
	private JButton spinButton = new JButton("Spin") {
		@Override
		protected boolean processKeyBinding(KeyStroke ks, KeyEvent ke, int i, boolean bln) {
			boolean b = super.processKeyBinding(ks, ke, i, bln);
			if (b && ks.getKeyCode() == KeyEvent.VK_S)
				requestFocusInWindow();
			return b;
		}
	};

	// Core instance
	private Core core;

	public GraphicUserInterface() {
		// TODO Auto-generated constructor stub
		logger.logging("GUI>>GraphicUserInterface()");

        // Add window listener by implementing WindowAdapter class to
        // the frame instance. To handle the close event we just need
        // to implement the windowClosing() method.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logger.logging("GUI>>addWindowListener(WindowAdapter.windowClosing(" + e.getID() + "))");

				// throw exit request to Core
				if (!core.processAction("Quit"))
					core.processExit();
				// System.exit(0);
			}
		});

		// catch keybinding and process associated action
		AbstractAction keybindAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				logger.logging("GUI>>keybindAction.actionPerformed(" + ae.getActionCommand() + ")");
				//String buttonTitle = "";
				switch (ae.getActionCommand()) {
				case "r": //buttonTitle = "Rand";
					randButton.doClick();
					break;
				case "s": //buttonTitle = "Spin";
					spinButton.doClick();
					break;
				case "a": //buttonTitle = "Auto";
					autoButton.doClick();
					break;
				case "o": //buttonTitle = "Options";
					optionsButton.doClick();
					break;
				case "m": //buttonTitle = "Mise";
					miseButton.doClick();
					break;
				default:
					break;
				}
				//core.processAction(buttonTitle);
			}
		};

		// On initialise la JFrame
		this.setTitle("_666_");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null); // set location to display Frame in middel of screen like :
		//logger.logging(this.getX() +"-"+ this.getWidth() + "/2 = "  + (this.getX() - this.getWidth()/2));
		//logger.logging(this.getY() +"-"+ this.getHeight() + "/2 = "  + (this.getY() - this.getHeight()/2));
		//this.setLocation(this.getX() - this.getWidth()/2, this.getY() - this.getHeight() /2);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setAlwaysOnTop(false);

		// labelPan init
		//Dimension subLabelDim = new Dimension(100, 40);
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

		autoButton.setLocation(40, 140);
		autoButton.setSize(70, 30);
		autoButton.setPreferredSize(new Dimension(70, 30));
		autoButton.addActionListener(this);
		autoButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "a");
		autoButton.getActionMap().put("a", keybindAction);
		autoButton.setVisible(Main.simMode);

		miseButton.setLocation(120, 140);
		miseButton.setSize(70, 30);
		miseButton.setPreferredSize(new Dimension(70, 30));
		miseButton.addActionListener(this);
		miseButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "m");
		miseButton.getActionMap().put("m", keybindAction);
		miseButton.setVisible(true);

		optionsButton.setLocation(100, 130);
		optionsButton.setSize(90, 30);
		optionsButton.setPreferredSize(new Dimension(90, 30));
		optionsButton.addActionListener(this);
		optionsButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0), "o");
		optionsButton.getActionMap().put("o", keybindAction);
		optionsButton.setVisible(false);

		misePan.setPreferredSize(BetsDim);
		misePan.setLayout(null);
		misePan.add(betsLabel);
		misePan.add(coefLabel);
		misePan.add(miseLabel);
		misePan.add(optionsButton);
		misePan.add(autoButton);
		misePan.add(miseButton);

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

		tableLabel.setBackground(Color.WHITE);
		tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tableLabel.setLocation(30, 50);
		tableLabel.setSize(new Dimension(140, 100));
		//tableLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "table",
		//		TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		tableLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Font tFont = new Font("DS-digital", Font.PLAIN, 12);
		tableLabel.setFont(tFont);
		tableLabel.setText("");

		storePan.setPreferredSize(StoreDim);
		storePan.setLayout(null);
		storePan.add(storeLabel);
		storePan.add(tableLabel);

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
		randButton.setLocation(240, 10);
		randButton.setSize(70, 30);
		randButton.setPreferredSize(subActionlDim);
		randButton.addActionListener(this);
		randButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "r");
		randButton.getActionMap().put("r", keybindAction);
		randButton.setVisible(Main.simMode);

		// rollButton.setBounds(200, 10, 70, 30);
		spinButton.setLocation(320, 10);
		spinButton.setSize(70, 30);
		spinButton.setPreferredSize(subActionlDim);
		spinButton.addActionListener(this);
		spinButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "s");
		spinButton.getActionMap().put("s", keybindAction);
		spinButton.setVisible(true);

		// actionPan.setSize(ActionDim);
		actionPan.setPreferredSize(ActionDim);
		actionPan.setLayout(null);
		actionPan.add(cycleLabel);
		actionPan.add(tourLabel);
		actionPan.add(randButton);
		actionPan.add(spinButton);

		// container panel of JFrame setup
		container.setLayout(new BorderLayout());
		// container.setBorder(new EmptyBorder(2, 2, 2, 2));
		container.add(labelPan, BorderLayout.NORTH);
		container.add(storePan, BorderLayout.WEST);
		container.add(misePan, BorderLayout.EAST);
		container.add(actionPan, BorderLayout.SOUTH);
		this.setContentPane(container);
		if (randButton.isVisible())
			this.getRootPane().setDefaultButton(randButton);
		else
			this.getRootPane().setDefaultButton(miseButton);
		this.setVisible(true);

		// Core instance
		core = new Core();
		core.addObserver(new Observer() {
			@Override
			public void update(LinkedHashSet<String[]> pLHS) {
				// TODO Auto-generated method stub
				logger.logging("GUI>>update(pLHS)");

				// default display
				jetonLabel.setForeground(Color.black);
				gainLabel.setForeground(Color.black);
				betsLabel.setForeground(Color.black);
				coefLabel.setForeground(Color.black);
				miseLabel.setForeground(Color.black);
				betsLabel.setText("");
				coefLabel.setText("");
				miseLabel.setText("");
				storeLabel.setText("");
				autoButton.setSelected(false);

				// updating provided LHS
				for (String[] item : pLHS) {
					logger.logging("[" + item[0] + ":" + item[1] + "]");
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
					case "table":
						tableLabel.setText(item[1]);
						break;
					case "cycle":
						cycleLabel.setText(item[1]);
						break;
					case "tour":
						tourLabel.setText(item[1]);
						break;
					case "auto":
						autoButton.setSelected(true);
						break;
					default:
						break;
					}
				}
				// redraw container ....
				container.setVisible(false);
				container.setVisible(true);
				// this.setVisible(true);
			}
		});
	}

	public boolean run() {
		logger.logging("GUI>>run()");
		return core.run();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String buttonTitle = arg0.getActionCommand();
		logger.logging("GUI>>actionPerformed(" + buttonTitle + ")");
		// JButton buttonHit= (JButton) arg0.getSource();
		// String buttonTitle = buttonHit.getName();
		switch (buttonTitle) {
		default:
			core.processAction(buttonTitle);
			break;
		}
	}

}
