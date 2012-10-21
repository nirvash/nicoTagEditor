package com.github.nirvash.nicoTagEditor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;

public class ListItem {
	private File file;
	AudioFile mp3file;
	
	public ListItem(File file) throws IOException {
		this.file = file;
		try {
			this.mp3file = AudioFileIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
			this.mp3file = null;
		}
	}

	@Override
	public String toString() {
		return file.getName();
	}
	
	public File getFile() {
		return file;
	}

	public String getAlbum() {
		Tag tag = mp3file.getTag();
		return convert(tag.getFirst(FieldKey.ALBUM));
	}

	public String getTitle() {
		Tag tag = mp3file.getTag();
		return convert(tag.getFirst(FieldKey.TITLE));
	}

	public String getArtist() {
		Tag tag = mp3file.getTag();
		return convert(tag.getFirst(FieldKey.ARTIST));
	}
	
	private String convert(String in) {
		return in;
	}

	private void setMetadata(FieldKey key, String value) {
		try {
			Tag tag = mp3file.getTag();
			tag.setField(key, value);
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
	}
	
	public void setAlbum(String album) {
		setMetadata(FieldKey.ALBUM, album);
	}
	
	public void setTitle(String title) {
		setMetadata(FieldKey.TITLE, title);
	}
	
	public void setArtist(String artist) {
		setMetadata(FieldKey.ARTIST, artist);
	}
	
	public void save() {
		try {
			mp3file.commit();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		}
	}

	public String getUrl() {
		String url = "http://www.nicovideo.jp/watch/";
		Pattern p = Pattern.compile("(sm\\d*).*");
		Matcher m = p.matcher(file.getName());
		while (m.find()) {
			String id = m.group(1);
			url += id;
		}
		return url;
	}



}
