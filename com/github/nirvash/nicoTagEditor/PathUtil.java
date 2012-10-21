package com.github.nirvash.nicoTagEditor;

import java.io.File;
import java.io.FileFilter;
import java.util.TreeSet;

public class PathUtil {

	public static boolean isMp3File(File file) {
		return file.isFile() && file.canRead() && hasExtension(file, ".mp3");
	}

	private static boolean hasExtension(File file, String ext) {
		return file.getName().toLowerCase().endsWith(ext);
	}
	
	public static File[] getFiles(File dir, FileFilter filter) {
		TreeSet<File> set = new TreeSet<File>();
		getFiles(set, dir, filter);
		return (File[])set.toArray(new File[set.size()]);
	}
	
	private static void getFiles(TreeSet<File> set, File dir, FileFilter filter) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (filter.accept(file)) {
				set.add(file);
			} else if (file.isDirectory()) {
				getFiles(set, file, filter);
			}
		}
	}
}
