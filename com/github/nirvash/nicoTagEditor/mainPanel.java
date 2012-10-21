package com.github.nirvash.nicoTagEditor;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.github.nirvash.nicoTagEditor.ItemSelectionListener.viewUpdateListener;


public class mainPanel extends JFrame {

	public class ViewUpdateListener implements viewUpdateListener {
		@Override
		public void updateView(ListItem item) {
			getJTextFieldAlbum().setText(item.getAlbum());
			getJTextFieldTitle().setText(item.getTitle());
			getJTextFieldArtist().setText(item.getArtist());
		}

		@Override
		public void commit(ListItem item) {
			mainPanel.this.commit(item);
		}

	}

	private static final long serialVersionUID = 1L;
	private JPanel jPanelTop;
	private JButton jButtonSave;

	
	private JSplitPane jSplitPane0;
	private JPanel jPanel1;
	private JButton jButton0;
	private JPanel jPanel3;
	private JList jList0;
	private JScrollPane jScrollPane0;	
	private ItemSelectionListener itemSelectionListener;

	private JPanel jPanelTagEdit;
	
	private JLabel jLabelAlbum;
	private JLabel jLabelTitle;
	private JLabel jLabelArtist;
	private JTextField jTextFieldAlbum;
	private JTextField jTextFieldTitle;
	private JTextField jTextFieldArtist;	

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	static {
	}
	
	public mainPanel() {
		initComponents();
	}

	private void initComponents() {
		add(getJPanelTop(), BorderLayout.NORTH);
		add(getJSplitPane0(), BorderLayout.CENTER);
		setSize(750, 272);
	}
	
	private JTextField getJTextFieldAlbum() {
		if (jTextFieldAlbum == null) {
			jTextFieldAlbum = new JTextField();
			jTextFieldAlbum.setText("");
			jTextFieldAlbum.setAutoscrolls(true);
			jTextFieldAlbum.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ListItem listItem = (ListItem)jList0.getSelectedValue();
					listItem.setAlbum(jTextFieldAlbum.getText());
				}
			});
		}
		return jTextFieldAlbum;
	}

	private JTextField getJTextFieldTitle() {
		if (jTextFieldTitle == null) {
			jTextFieldTitle = new JTextField();
			jTextFieldTitle.setText("");
			jTextFieldTitle.setAutoscrolls(true);
			jTextFieldTitle.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ListItem listItem = (ListItem)jList0.getSelectedValue();
					listItem.setTitle(jTextFieldTitle.getText());
				}
			});
		}
		return jTextFieldTitle;
	}

	private JTextField getJTextFieldArtist() {
		if (jTextFieldArtist == null) {
			jTextFieldArtist = new JTextField();
			jTextFieldArtist.setText("");
			jTextFieldArtist.setAutoscrolls(true);
			jTextFieldArtist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ListItem listItem = (ListItem)jList0.getSelectedValue();
					listItem.setArtist(jTextFieldArtist.getText());
				}
			});
		}
		return jTextFieldArtist;
	}


	private JLabel getJLabelAlbum() {
		if (jLabelAlbum == null) {
			jLabelAlbum = new JLabel();
			jLabelAlbum.setText("Album");
		}
		return jLabelAlbum;
	}

	private JLabel getJLabelTitle() {
		if (jLabelTitle == null) {
			jLabelTitle = new JLabel();
			jLabelTitle.setText("Title");
		}
		return jLabelTitle;
	}
	
	private JLabel getJLabelArtist() {
		if (jLabelArtist == null) {
			jLabelArtist = new JLabel();
			jLabelArtist.setText("Artist");
		}
		return jLabelArtist;
	}

	private JPanel getJPanelTagEdit() {
		if (jPanelTagEdit == null) {
			jPanelTagEdit = new JPanel();
			jPanelTagEdit.setLayout(new GroupLayout());
			jPanelTagEdit.add(getJLabelAlbum(), new Constraints(new Leading(12, 12, 12), new Leading(15, 10, 10)));
			jPanelTagEdit.add(getJTextFieldAlbum(), new Constraints(new Bilateral(88, 9, 12), new Leading(9, 12, 12)));
			jPanelTagEdit.add(getJLabelTitle(), new Constraints(new Leading(12, 12, 12), new Leading(52, 10, 10)));
			jPanelTagEdit.add(getJTextFieldTitle(), new Constraints(new Bilateral(88, 9, 12), new Leading(46, 12, 12)));
			jPanelTagEdit.add(getJLabelArtist(), new Constraints(new Leading(12, 12, 12), new Leading(91, 10, 10)));
			jPanelTagEdit.add(getJTextFieldArtist(), new Constraints(new Bilateral(88, 9, 12), new Leading(85, 12, 12)));
		}
		return jPanelTagEdit;
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
			itemSelectionListener = new ItemSelectionListener(new ViewUpdateListener(), fileListModel);
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
			jSplitPane0.setDividerLocation(300);
			jSplitPane0.setDividerSize(5);
			jSplitPane0.setLeftComponent(getJPanel1());
			jSplitPane0.setRightComponent(getJPanelTagEdit());
		}
		return jSplitPane0;
	}

	private JPanel getJPanelTop() {
		if (jPanelTop == null) {
			jPanelTop = new JPanel();
			jPanelTop.setPreferredSize(new Dimension(100, 30));
			jPanelTop.setLayout(new BoxLayout(jPanelTop, BoxLayout.X_AXIS));
			jPanelTop.add(getJButtonSave());
		}
		return jPanelTop;
	}
	
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setText("Save");
			jButtonSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ListItem item = (ListItem)jList0.getSelectedValue();
					commit(item);
					
					save();
				}
			});
		}
		return jButtonSave;
	}


	protected void save() {
		DefaultListModel model = (DefaultListModel)jList0.getModel();
		for (Object obj : model.toArray()) {
			ListItem item = (ListItem)obj;
			item.save();
		}
	}

	private void commit(ListItem item) {
		if (item != null) {
			item.setAlbum(getJTextFieldAlbum().getText());
			item.setTitle(getJTextFieldTitle().getText());
			item.setArtist(getJTextFieldArtist().getText());
		}
	}

	@SuppressWarnings("unused")
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
