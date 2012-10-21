package com.github.nirvash.nicoTagEditor;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


//VS4E -- DO NOT REMOVE THIS LINE!
public class mainPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JSplitPane jSplitPane0;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JButton jButton0;
	private JPanel jPanel3;
	private JList jList0;
	private JScrollPane jScrollPane0;	
	private ItemSelectionListener itemSelectionListener;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	public mainPanel() {
		initComponents();
	}

	private void initComponents() {
		add(getJPanel0(), BorderLayout.NORTH);
		add(getJSplitPane0(), BorderLayout.CENTER);
		setSize(468, 272);
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJList0());
		}
		return jScrollPane0;
	}

	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();
			DefaultListModel fileListModel = new DefaultListModel();
			jList0 = new JList(fileListModel);
		    jList0.setTransferHandler(new FileDropHandler(fileListModel));
		    jList0.setCellRenderer(new FileRenderer());
		    jList0.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		    jList0.setDropMode(DropMode.INSERT);
		    jList0.setDragEnabled(true);
		    jList0.setBackground(Color.WHITE);
		    jList0.setSelectionBackground(new Color(200, 200, 255));
		    jList0.addListSelectionListener(getItemSelectionListener(fileListModel));
			jList0.setModel(fileListModel);
		}
		return jList0;
	}

	private ItemSelectionListener getItemSelectionListener(DefaultListModel fileListModel) {
		if (itemSelectionListener == null) {
			itemSelectionListener = new ItemSelectionListener(getJPanel2(), fileListModel);
		}
		return itemSelectionListener;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setPreferredSize(new Dimension(100, 30));
			jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.X_AXIS));
			jPanel3.add(getJButton0());
		}
		return jPanel3;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("jButton0");
		}
		return jButton0;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
		}
		return jPanel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel3(), BorderLayout.SOUTH);
			jPanel1.add(getJScrollPane0(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	private JSplitPane getJSplitPane0() {
		if (jSplitPane0 == null) {
			jSplitPane0 = new JSplitPane();
			jSplitPane0.setDividerLocation(200);
			jSplitPane0.setDividerSize(5);
			jSplitPane0.setLeftComponent(getJPanel1());
			jSplitPane0.setRightComponent(getJPanel2());
		}
		return jSplitPane0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setPreferredSize(new Dimension(100, 30));
			jPanel0.setLayout(new BoxLayout(jPanel0, BoxLayout.X_AXIS));
		}
		return jPanel0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainPanel frame = new mainPanel();
				frame.setDefaultCloseOperation(mainPanel.EXIT_ON_CLOSE);
				frame.setTitle("nicoTagEditor");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}
