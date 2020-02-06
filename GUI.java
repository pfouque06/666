package _666_;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import javaTools.Logger;
import javaTools.Observer;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

// GUI : Graphic User Interface Class
@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, KeyListener {

	// logger
	Logger logger = Main.logger;

	private JPanel container = new JPanel();
	private JLabel jetonLabel = new JLabel();
	private JLabel gainLabel = new JLabel();
	private JLabel storeLabel = new JLabel();
	private JLabel tableLabel = new JLabel();
	private JLabel betsLabel = new JLabel();
	private JLabel coefLabel = new JLabel();
	private JLabel miseLabel = new JLabel();
	private JLabel cycleLabel = new JLabel();
	private JLabel tourLabel = new JLabel();
	private JToggleButton autoButton = new JToggleButton("Auto", Main.autoMode);
	private JButton miseButton = new JButton("mIse");
	private JButton menuButton = new JButton("Menu");
	private JButton randButton = new JButton("Rand");
	private JButton spinButton = new JButton("Spin");

	// Core instance
	private Core core;

	public GUI() {
		// TODO Auto-generated constructor stub
		logger.logging("GUI>>GraphicUserInterface()");

		// On initialise la JFrame
		this.setTitle("_666_");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null); // set location to display Frame in middle of screen like :
		// logger.logging(this.getX() +"-"+ this.getWidth() + "/2 = " + (this.getX() -
		// this.getWidth()/2));
		// logger.logging(this.getY() +"-"+ this.getHeight() + "/2 = " + (this.getY() -
		// this.getHeight()/2));
		// this.setLocation(this.getX() - this.getWidth()/2, this.getY() -
		// this.getHeight() /2);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setAlwaysOnTop(false);

		// label pan : new Dimension(400, 50);

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

		container.add(jetonLabel);
		container.add(gainLabel);

		// betPan init : new Dimension(200, 100);
		Dimension subBetsDim = new Dimension(180, 40);

		betsLabel.setBackground(Color.WHITE);
		betsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		betsLabel.setLocation(210, 50);
		betsLabel.setSize(subBetsDim);
		betsLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "à miser",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		betsLabel.setText("");

		coefLabel.setBackground(Color.WHITE);
		coefLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		coefLabel.setLocation(210, 100);
		coefLabel.setSize(new Dimension(60, 40));
		coefLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "coef",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		coefLabel.setText("");

		miseLabel.setBackground(Color.WHITE);
		miseLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		miseLabel.setLocation(330, 100);
		miseLabel.setSize(new Dimension(60, 40));
		miseLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "mises",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		miseLabel.setText("");

		autoButton.setLocation(240, 190);
		autoButton.setSize(70, 30);
		autoButton.setPreferredSize(new Dimension(70, 30));
		autoButton.addActionListener(this);
		//autoButton.setVisible(Main.simMode);

		menuButton.setLocation(320, 190);
		menuButton.setSize(70, 30);
		menuButton.setPreferredSize(new Dimension(70, 30));
		menuButton.addActionListener(this);
		menuButton.setVisible(true);
		logger.logging(menuButton.getFont().getName() + " " + menuButton.getFont().getSize());
		Font menuButtonFont = menuButton.getFont();
		menuButtonFont = new Font(menuButtonFont.getName(), menuButtonFont.getStyle(), menuButtonFont.getSize() - 1);
		menuButton.setFont(menuButtonFont);

		miseButton.setLocation(300, 150);
		miseButton.setSize(70, 30);
		miseButton.setPreferredSize(new Dimension(90, 30));
		miseButton.addActionListener(this);
		miseButton.setVisible(false);

		container.add(betsLabel);
		container.add(coefLabel);
		container.add(miseLabel);
		container.add(miseButton);
		container.add(autoButton);
		container.add(menuButton);

		// table init : new Dimension(200, 100);
		Dimension subStoreDim = new Dimension(180, 40);

		storeLabel.setBackground(Color.WHITE);
		storeLabel.setHorizontalAlignment(SwingConstants.LEADING);
		storeLabel.setLocation(10, 50);
		storeLabel.setSize(subStoreDim);
		storeLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "store",
				TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		storeLabel.setText("");

		tableLabel.setBackground(Color.WHITE);
		tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tableLabel.setLocation(40, 100);
		tableLabel.setSize(new Dimension(120, 100));
		// tableLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED,
		// null, null, null, null), "table",
		// TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		tableLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Font tFont = new Font("DS-digital", Font.PLAIN, 12);
		tableLabel.setFont(tFont);
		tableLabel.setText("");

		container.add(storeLabel);
		container.add(tableLabel);

		// action Pan : new Dimension(400, 50);
		Dimension subActionlDim = new Dimension(70, 30);

		cycleLabel.setBackground(Color.WHITE);
		cycleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		cycleLabel.setLocation(10, 220);
		cycleLabel.setSize(new Dimension(60, 40));
		cycleLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "cycles",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		cycleLabel.setText("");

		tourLabel.setBackground(Color.WHITE);
		tourLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		tourLabel.setLocation(80, 220);
		tourLabel.setSize(new Dimension(100, 40));
		tourLabel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "tours",
				TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		tourLabel.setText("");

		randButton.setLocation(240, 230);
		randButton.setSize(70, 30);
		randButton.setPreferredSize(subActionlDim);
		randButton.addActionListener(this);
		//randButton.setVisible(Main.simMode);

		spinButton.setLocation(320, 230);
		spinButton.setSize(70, 30);
		spinButton.setPreferredSize(subActionlDim);
		spinButton.addActionListener(this);
		spinButton.setVisible(true);

		container.add(cycleLabel);
		container.add(tourLabel);
		container.add(randButton);
		container.add(spinButton);

		// container panel of JFrame setup
		container.setLayout(null); // null = Absolute Layout
		// container.setBorder(new EmptyBorder(2, 2, 2, 2));

		setSimulationViewModel();
		this.setContentPane(container);
		this.setVisible(true);

		// request focus in order to listen to keyevent
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
		// implement then keyReleased(KeyEvent ke), keyTyped(KeyEvent ke) and
		// essentially keyPressed(KeyEvent ke)

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
					case "viewModel":
						setSimulationViewModel();
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

	public void setSimulationViewModel() {
		
		// define Simulation viw model
		autoButton.setVisible(Main.simMode);
		randButton.setVisible(Main.simMode);
		
		// update default Button acording to view model
		if (randButton.isVisible())
			this.getRootPane().setDefaultButton(randButton);
		else
			this.getRootPane().setDefaultButton(spinButton);
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

	@Override
	public void keyReleased(KeyEvent ke) {
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// catch keybinding and process associated action
		logger.logging("GUI>>KeyListener.keyPressed(KeyEvent ke)");
		int keyCode = ke.getKeyCode();
		char keyChar = ke.getKeyChar();
		String keyText = KeyEvent.getKeyText(ke.getKeyCode());
		logger.logging("GUI>> --> keyCode(" + keyCode + ").keyPressed(" + keyText + ").keyChar(" + keyChar + ")");

		// keypressed to action mapping
		String keyPressed = "";
		switch (keyChar) {
		case 'r': // buttonTitle = "Rand";
			keyPressed = "Rand";
			break;
		case 's': // buttonTitle = "Spin";
			keyPressed = "Spin";
			break;
		case 'a': // buttonTitle = "Auto";
			keyPressed = "Auto";
			break;
		case 'i': // buttonTitle = "mIse";
			keyPressed = "mIse";
			break;
		case 'm': // buttonTitle = "Menu";
			keyPressed = "Menu";
			break;
		default: // not binded key
			// logger.logging(">> --> not binded");
			return;
		}
		processKeytoButtonBinding(keyPressed);
	}

	void processKeytoButtonBinding(String keyPressed) {
		logger.logging("GUI>>keybindAction.processKeytoButtonBinding(" + keyPressed + ")");

		switch (keyPressed) {
		case "Rand": // buttonTitle = "Rand";
			randButton.doClick();
			break;
		case "Spin": // buttonTitle = "Spin";
			spinButton.doClick();
			break;
		case "Auto": // buttonTitle = "Auto";
			autoButton.doClick();
			break;
		case "mIse": // buttonTitle = "mIse";
			miseButton.doClick();
			break;
		case "Menu": // buttonTitle = "Menu";
			menuButton.doClick();
			break;
		default: // not binded key
			// logger.logging(">> --> not binded");
			break;
		}

		// keep requesting focus in order to listen to keyevent
		this.setFocusable(true);
		this.requestFocus();
	}
}
