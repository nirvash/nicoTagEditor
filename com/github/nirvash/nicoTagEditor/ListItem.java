package com.github.nirvash.nicoTagEditor;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

public class ListItem {
	private File file;
	MP3File mp3file;
	ID3v23Tag tag;
	boolean isDirty = false;
	
	public ListItem(File file) throws IOException {
		this.file = file;
		try {
			this.mp3file = (MP3File)AudioFileIO.read(file);
			ID3v24Tag tag24 = mp3file.getID3v2TagAsv24();
			this.tag = new ID3v23Tag(tag24);
			mp3file.setID3v2Tag(this.tag);
		} catch (Exception e) {
			e.printStackTrace();
			this.mp3file = null;
			this.tag = null;
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
		return convert(tag.getFirst(FieldKey.ALBUM));
	}

	public String getTitle() {
		return convert(tag.getFirst(FieldKey.TITLE));
	}

	public String getArtist() {
		return convert(tag.getFirst(FieldKey.ARTIST));
	}
	
	private String convert(String in) {
		return in;
	}

	private void setMetadata(FieldKey key, String value) {
		try {
			tag.setField(key, value);
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
	}
	
	public void setAlbum(String album) {
		if (!getAlbum().equals(album)) {
			setMetadata(FieldKey.ALBUM, album);
			isDirty = true;
			
		}
	}
	
	public void setTitle(String title) {
		if (!getTitle().equals(title)) {
			setMetadata(FieldKey.TITLE, title);
			isDirty = true;
		}
	}
	
	public void setArtist(String artist) {
		if (!getArtist().equals(artist)) {
			setMetadata(FieldKey.ARTIST, artist);
			isDirty = true;
		}
	}
	
	public void save() {
		if (isDirty) {
			try {
				mp3file.commit();
				isDirty = false;
			} catch (CannotWriteException e) {
				e.printStackTrace();
			}
		}
	}

	public String getUrl() {
		String url = "http://www.nicovideo.jp/watch/";
		Pattern p = Pattern.compile("((sm|nm)\\d*).*");
		Matcher m = p.matcher(file.getName());
		if (m.find()) {
			String id = m.group(1);
			url += id;
		}
		return url;
	}
	
	private class NicoPattern {
		public String patternText;
		public Pattern pattern;
		public int index_artist;
		public int index_title;
		public NicoPattern(String patternText, int index_artist, int index_title) {
			this.patternText = patternText;
			this.pattern = Pattern.compile(patternText);
			this.index_artist = index_artist;
			this.index_title = index_title;
		}
	}

	private NicoPattern[] patterns = new NicoPattern[] {
			new NicoPattern(".*�y(.*)�z\\s*(.*)\\s*�y.*�z.*mp3", 1, 2)
		};
	
	public void analyzeTag() {
		patterns = new NicoPattern[] {
				new NicoPattern(".*�y(.*)�z[\\s�@�u]*(.*?)[\\s�@�v]*�y.*�z.*mp3", 1, 2),
				new NicoPattern(".*�y(.*)�z[\\s�@�u]*(.*?)[\\s�@�v�i]+.*", 1, 2),
				new NicoPattern(".*?_(.*)�u(.*)�v.*", 1, 2),
				new NicoPattern(".*?\\[(.*?)\\][\\s�@]*(.*?)[\\s�@]*\\[.*\\]", 1, 2),
				new NicoPattern(".*_(.*?)[\\s�@]*�y(.*)�z.*", 2, 1)
		};
		
		boolean foundArtist = false;
		boolean foundTitle = false;

		for (NicoPattern pat : patterns) {
			Matcher m = pat.pattern.matcher(file.getName());
			if (m.find()) {
				if (pat.index_artist != 0) {
					String artist = m.group(pat.index_artist);
					artist = removeNGwordForArtist(artist);
					if (artist.length()>0 && !isNGArtist(artist)) {
						setArtist(artist);
						foundArtist = true;
					}
				}
				if (pat.index_title != 0) {
					String title = m.group(pat.index_title);
					title = removeNGwordForTitle(title);
					if (title.length()>0) {
						setTitle(title);
						foundTitle = true;
					}
				}
				break;
			}
		}
		
		if (!foundArtist) {
			Pattern p = Pattern.compile(".*?([^\\s^\\-^�@^�^]*?)[\\s�@]*feat\\.[\\s�@]*(.*)[\\s�@]*.*\\.mp3");
			Matcher m = p.matcher(file.getName());
			if (m.find()) {
				String artist = m.group(2);
				artist = removeNGwordForArtist(artist);
				if (artist.length()>0) {
					setArtist(artist);
					foundArtist = true;
				}
				String album = m.group(1);
				if (album.length()>0) {
					setAlbum(album);
				}
			}
		}
		
		if (!foundTitle) {
			Pattern p = Pattern.compile("sm\\d*_(.*).mp3");
			Matcher m = p.matcher(file.getName());
			if (m.find()) {
				String title = m.group(1);
				title = removeNGwordForTitle(title);
				if (title.length()>0) {
					setTitle(title);
					foundTitle = true;
				}
			}	
		}
	}

	private String removeNGwordForTitle(String title) {
		title = replaceAll(title, "�I���W�i����", "");
		title = replaceAll(title, "�I���W�i��", "");
		title = replaceAll(title, "�j�R�J��", "");
		title = replaceAll(title, "fullver", "");
		title = replaceAll(title, "��-��-��", "");
		return title;
	}

	private String removeNGwordForArtist(String artist) {
		artist = replaceAll(artist, "�I���W�i����", "");
		artist = replaceAll(artist, "�I���W�i��", "");
		artist = replaceAll(artist, "�j�R�J��", "");
		artist = replaceAll(artist, "sweet", "");
		artist = replaceAll(artist, "power", "");
		artist = replaceAll(artist, "solid", "");
		artist = replaceAll(artist, "soft", "");
		return artist;
	}
	
	private String replaceAll(String text, String from, String replacement) {
		Pattern p = Pattern.compile(from, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);

		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			String g = m.group();
			m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
		}
		m.appendTail(sb);

		return sb.toString();
	}

	private boolean isNGArtist(String artist) {
		return false;
	}

	public boolean isModified() {
		return this.isDirty;
	}



}
