package _666_;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JProgressBar;
import javax.swing.JFormattedTextField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class Frame {

	private JFrame frame;
	private JTable table;
	private JTextField txtJtextfield;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Auto Mode");
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("JToggleButton");
		tglbtnNewToggleButton.setEnabled(false);
		tglbtnNewToggleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tglbtnNewToggleButton.setAction(action);
		
		JButton btnNewButton = new JButton("JButton");
		
		JTextPane txtpnJtextpane = new JTextPane();
		txtpnJtextpane.setText("JTextPane");
		
		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("");
		
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setRowSelectionAllowed(false);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", "2", "3", "4", "5", "6"},
				{"7", "8", "9", "10", "11", "12"},
				{"13", "14", "15", "16", "17", "18"},
				{"19", "20", "21", "22", "23", "24"},
				{"25", "26", "27", "28", "29", "30"},
				{"31", "32", "33", "34", "35", "36"},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table.getColumnModel().getColumn(5).setPreferredWidth(72);
		
		txtJtextfield = new JTextField();
		txtJtextfield.setText("JTextField");
		txtJtextfield.setColumns(10);
		
		JSlider slider = new JSlider();
		
		JTree tree = new JTree();
		
		JEditorPane editorPane = new JEditorPane();
		
		JTextArea txtrJtextarea = new JTextArea();
		txtrJtextarea.setText("JTextArea");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		
		JProgressBar progressBar = new JProgressBar();
		
		JFormattedTextField frmtdtxtfldJformattedtext = new JFormattedTextField();
		frmtdtxtfldJformattedtext.setText("JFormattedText");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap(42, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(frmtdtxtfldJformattedtext, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
								.addComponent(progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(editorPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtJtextfield, Alignment.LEADING)
								.addComponent(txtrJtextarea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(slider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(40)
							.addComponent(table, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton)
								.addComponent(tglbtnNewToggleButton))
							.addGap(40))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtpnJtextpane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addComponent(chckbxNewCheckBox)
								.addComponent(tree, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
								.addComponent(rdbtnNewRadioButton))
							.addGap(22))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtJtextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(39)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addGap(69)
									.addComponent(frmtdtxtfldJformattedtext, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
									.addComponent(table, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addGap(25))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(174)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(chckbxNewCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnNewRadioButton)))
							.addGap(35)
							.addComponent(tglbtnNewToggleButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(79))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtpnJtextpane, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(tree, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(64)
					.addComponent(txtrJtextarea, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(384, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
