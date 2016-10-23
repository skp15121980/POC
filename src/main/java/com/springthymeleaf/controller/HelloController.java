package com.springthymeleaf.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springthymeleaf.bean.FileDetails;

@Controller
// @RequestMapping("/")
public class HelloController {
	
	private static final String SOURCE_FILE_PATH = "C:/sourceFolder/";
	private static final String DESTINATION_FILE_PATH = "D:/destinationFolder/";

	@RequestMapping(value = "/fileList", method = RequestMethod.GET)
	public String getFiles(/* @PathVariable String name, */ ModelMap model) {
		model.addAttribute("fileList", fetchAllFiles(getPath(SOURCE_FILE_PATH).getAbsolutePath()));
		return "hello";
	}

	@RequestMapping(value = "/moveFile", method = RequestMethod.POST)
	public String moveFile(/* @PathVariable String name, */ ModelMap model) {

		Path movefrom = FileSystems.getDefault().getPath(getPath(SOURCE_FILE_PATH).getAbsolutePath());
		Path target = FileSystems.getDefault().getPath(DESTINATION_FILE_PATH+"/"+getPath(SOURCE_FILE_PATH).getName());
		try {
			//Path path = Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
			Path path=Files.copy(movefrom, target);
			model.addAttribute("message", path.getFileName() + "   Files moved Successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("message", "Files not moved Successfully"+e.getMessage());
		}
		return "result";
	}

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
	
	public static File getPath(String path){
		File file = new File(path);
		File absolutePath=null;
	    if(file!=null && file.exists()){
	        File[] listOfFiles = file.listFiles();

	        if(listOfFiles!=null){

	            for (int i = 0; i < listOfFiles.length; i++) {
	                if (listOfFiles[i].isFile()) {
	                	absolutePath=listOfFiles[i];
	                }
	            }
	        }
	    }
		return absolutePath;
	}
}