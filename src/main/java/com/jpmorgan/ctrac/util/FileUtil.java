package com.jpmorgan.ctrac.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.jpmorgan.ctrac.bean.FileDetails;

public class FileUtil {
	public static List<FileDetails> fetchAllFiles(String path) {

		File file = new File(path);
		ZipFile zipfile = null;
		List<FileDetails> fileDetails = new ArrayList<FileDetails>();
		if (file.exists()) {

			try {
				zipfile = new ZipFile(file);
			} catch (ZipException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			ZipEntry zipentry;

			int fileNumber = 0;
			for (Enumeration<? extends ZipEntry> e = zipfile.entries(); e.hasMoreElements(); fileNumber++) {
				zipentry = e.nextElement();

				if (!zipentry.isDirectory()) {
					fileDetails.add(new FileDetails(Integer.valueOf(fileNumber), extractFileName(zipentry.getName())));
				}
			}
		}
		return fileDetails;
	}

	public static String extractFileName(String filePathName) {
		if (filePathName == null)
			return null;

		int dotPos = filePathName.lastIndexOf('.');
		int slashPos = filePathName.lastIndexOf('\\');
		if (slashPos == -1)
			slashPos = filePathName.lastIndexOf('/');

		if (dotPos > slashPos) {
			return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0, dotPos);
		}
		return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
	}

	public static File getPath(String path) {
		File file = new File(path);
		File absolutePath = null;
		if (file != null && file.exists()) {
			File[] listOfFiles = file.listFiles();

			if (listOfFiles != null) {

				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						absolutePath = listOfFiles[i];
					}
				}
			}
		}
		return absolutePath;
	}
}
