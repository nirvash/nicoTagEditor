package com.github.nirvash.nicoTagEditor;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemSelectionListener implements ListSelectionListener {
	private JPanel parent;
	private DefaultListModel model = null;

	public ItemSelectionListener(JPanel panel, DefaultListModel fileListModel) {
		parent = panel;
		model = fileListModel;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) return;
		int index = ((JList)e.getSource()).getSelectedIndex();
		
		updatePreviewImage(index);
	}

	public void updatePreviewImage(int index) {
		if (index < 0) {
			return;
		}
		
		ListItem listItem = (ListItem)model.get(index);
	}
}
