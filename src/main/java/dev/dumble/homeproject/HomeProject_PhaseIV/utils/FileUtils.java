package dev.dumble.homeproject.HomeProject_PhaseIV.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@UtilityClass
public class FileUtils {

	public boolean checkIfExceededFileLimit(MultipartFile file) {
		return file != null && file.getSize() / 1024 > 300;
	}

	@SneakyThrows
	public void deleteFile(String path) {
		Files.delete(Paths.get(path));
	}

	public byte[] convertImageToBytes(MultipartFile multipartFile) {
		try {
			if (FileUtils.checkIfExceededFileLimit(multipartFile)) return null;

			return multipartFile.getBytes();

		} catch (IOException exception) {
			return null;
		}
	}

	@SneakyThrows
	public void convertBytesToImage(byte[] data, String fileName) {
		var inputStream = new ByteArrayInputStream(data);
		var image = ImageIO.read(inputStream);

		ImageIO.write(image, "jpg", new File("src/main/resources/profiles/%s".formatted(fileName)));
	}

	@SneakyThrows
	public boolean profilesContainsPicture(String fileName) {
		return Files.exists(Paths.get("src/main/resources/profiles/%s".formatted(fileName)));
	}
}
