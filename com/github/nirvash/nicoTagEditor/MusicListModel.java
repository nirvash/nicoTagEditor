package com.github.nirvash.nicoTagEditor;

import javax.swing.DefaultListModel;

public class MusicListModel extends DefaultListModel {
	public void updateItem(int begin, int end) {
		fireContentsChanged(this, begin, end);
	}
}
