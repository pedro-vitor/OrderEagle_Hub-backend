package com.order.eagle.hub.back.services.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class ToolsService {

	private static final String PATH_UPLOADS = "resources/static/img";
	
	public static String saveImg(MultipartFile file) {
		try {
			Path directoryUploads = Paths.get(PATH_UPLOADS);
			if(!Files.exists(directoryUploads)) {
				Files.createDirectories(directoryUploads);
			}
			
			Path pathFile = directoryUploads.resolve(file.getOriginalFilename());
			Files.copy(file.getInputStream(), pathFile);
			
			return pathFile.toString();
		} catch (IOException e) {
			throw new Error(e.getMessage());
		}
	}
}
