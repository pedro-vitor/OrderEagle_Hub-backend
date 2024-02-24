package com.order.eagle.hub.back.services.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.order.eagle.hub.back.entities.enums.TypeUpload;

public class ToolsService {

	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyyMMddHHmmssSS");
	
	private static final String PATH_UPLOAD_LOGO = "src/main/resources/static/logo";
	
	private static final String PATH_UPLOAD_BANNER = "src/main/resources/static/banner";
	
	private static final String NAME_IMG = "img" + FORMAT_DATE.format(new Date());
	
	private static final List<String> TYPE_IMG = Arrays.asList("png", "jpg", "jpeg");
	
	public static String saveImg(MultipartFile file, TypeUpload type) {
		try {
			
			var chosenType = type == TypeUpload.LOGO ? PATH_UPLOAD_LOGO : PATH_UPLOAD_BANNER;
			
			Path directoryUploads = Paths.get(chosenType);
			if(!Files.exists(directoryUploads)) {
				Files.createDirectories(directoryUploads);
			}
			
			
			Path pathFile = directoryUploads.resolve(renameFiles(file));
			Files.copy(file.getInputStream(), pathFile);
			
			return pathFile.toString();
		} catch (IOException e) {
			throw new Error(e.getMessage());
		}
	}
	
	private static String renameFiles(MultipartFile file) {
		var currentName = file.getOriginalFilename();
		var extension = getExtension(currentName);
		verifyExtension(extension);
		return NAME_IMG + "." + extension;
	}

	private static String getExtension(String nameFile) {
		if(!nameFile.contains("."))
			throw new IllegalArgumentException("Arquivo sem extenção");
		return nameFile.substring(nameFile.lastIndexOf(".") + 1).toLowerCase();
	}
	
	private static void verifyExtension(String extension) {
		var result = TYPE_IMG.stream().filter(ex -> extension.equals(ex)).collect(Collectors.toList());
		if(result.isEmpty())
			throw new IllegalArgumentException("Tipo de arquivo inválido");
	}
}
