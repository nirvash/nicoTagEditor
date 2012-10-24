package com.github.nirvash.nicoTagEditor;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemSelectionListener implements ListSelectionListener {
	private viewUpdateListener listener;
	private MusicListModel model = null;
	private ListItem oldItem = null;
	
	public interface viewUpdateListener {
		void updateView(ListItem item);
		void commit(ListItem listItem);
	}
	
	public ItemSelectionListener(viewUpdateListener listner, MusicListModel fileListModel) {
		this.listener = listner;
		model = fileListModel;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) return;
		int index = ((JList)e.getSource()).getSelectedIndex();
		
		updateOldIndex();
		updateTagEditPanel(index);
		model.updateItem(index, index);
	}
	
	private void updateOldIndex() {
		if (oldItem == null) {
			return;
		}
		
		listener.commit(oldItem);	
	}

	public void updateTagEditPanel(int index) {
		if (index < 0) {
			return;
		}
		
		ListItem listItem = (ListItem)model.get(index);
		listener.updateView(listItem);
		oldItem = listItem;
	}
}
