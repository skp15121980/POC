package com.jpmorgan.ctrac.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jpmorgan.ctrac.util.FileUtil;

@Controller
// @RequestMapping("/")
public class FileController {
	
	private static final String SOURCE_FILE_PATH = "C:/sourceFolder/";
	private static final String DESTINATION_FILE_PATH = "D:/destinationFolder/";

	@RequestMapping(value = "/fileList", method = RequestMethod.GET)
	public String getFiles(/* @PathVariable String name, */ ModelMap model) {
		model.addAttribute("fileList", FileUtil.fetchAllFiles(FileUtil.getPath(SOURCE_FILE_PATH).getAbsolutePath()));
		return "hello";
	}

	@RequestMapping(value = "/moveFile", method = RequestMethod.POST)
	public String moveFile(/* @PathVariable String name, */ ModelMap model) {

		Path movefrom = FileSystems.getDefault().getPath(FileUtil.getPath(SOURCE_FILE_PATH).getAbsolutePath());
		Path target = FileSystems.getDefault().getPath(DESTINATION_FILE_PATH+"/"+FileUtil.getPath(SOURCE_FILE_PATH).getName());
		try {
			Path path = Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
			//Path path=Files.copy(movefrom, target);
			model.addAttribute("message", path.getFileName() + "   Files moved Successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("message", "Files not moved Successfully"+e.getMessage());
		}
		return "result";
	}

	
}