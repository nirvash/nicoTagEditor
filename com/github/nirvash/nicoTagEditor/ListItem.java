package com.github.nirvash.nicoTagEditor;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

public class ListItem {
	private File file;
	MP3File mp3file;
	
	public ListItem(File file) throws IOException, TagException {
		this.file = file;
		this.mp3file = new MP3File(file);
	}

	@Override
	public String toString() {
		return file.getName();
	}
	
	public File getFile() {
		return file;
	}
}
