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

public class ToolsService {

	private static  SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyyMMddHHmmssSS");
	
	private static final List<String> TYPE_IMG = Arrays.asList("png", "jpg", "jpeg");
	
	public static String saveImg(MultipartFile file, String pathToSaveImg) {
		try {
			
			Path directoryUploads = Paths.get(pathToSaveImg);
			if(!Files.exists(directoryUploads)) {
				Files.createDirectories(directoryUploads);
			}
			
			deleteFilesIfExistsInFolder(directoryUploads);
			
			Path pathFile = directoryUploads.resolve(renameFiles(file));
			Files.copy(file.getInputStream(), pathFile);
			
			return pathFile.toString();
		} catch (IOException e) {
			throw new IllegalArgumentException("Erro no Upload do arquivo");
		}
	}
	
	private static String renameFiles(MultipartFile file) {
		final String NAME_IMG = "img" + FORMAT_DATE.format(new Date());
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
	
	private static void deleteFilesIfExistsInFolder(Path pathToUploads) throws IOException {
		var listFiles = Files.list(pathToUploads).collect(Collectors.toList());
		if(!listFiles.isEmpty())
			listFiles.forEach(file -> { 
				try {
					Files.deleteIfExists(file);
				} catch (IOException e) {
					throw new IllegalArgumentException("Erro ao apagar o Arquivo");
				}
			});
	}
}
