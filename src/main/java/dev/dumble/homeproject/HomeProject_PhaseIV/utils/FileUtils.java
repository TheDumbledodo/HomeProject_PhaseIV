package dev.dumble.homeproject.HomeProject_PhaseIV.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@UtilityClass
public class FileUtils {

	private final String JPG_PATTERN = "\\S+\\.jpg";

	private boolean checkIfJpeg(MultipartFile file) {
		var name = file.getOriginalFilename();
		
		return name != null && !name.isBlank() && name.matches(JPG_PATTERN);
	}

	public byte[] convertImageToBytes(MultipartFile multipartFile) {
		try {
			if (multipartFile == null) return null;
			if (!FileUtils.checkIfJpeg(multipartFile)) return null;

			return multipartFile.getBytes();

		} catch (IOException exception) {
			return null;
		}
	}
}
