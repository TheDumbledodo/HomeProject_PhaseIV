package dev.dumble.homeproject.HomeProject_PhaseIV.utils;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.NotPermittedException;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@UtilityClass
public class Utility {

	public final String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";

	public LocalDateTime getPresentDate() {
		return LocalDateTime.now();
	}

	public LocalDateTime getDate(int year, int month, int day) {
		return LocalDateTime.of(year, month, day, 0, 0);
	}

	public void checkSpecialistStatus(boolean predicate) {
		if (predicate) return;
		throw new NotPermittedException();
	}

	public void isManager(UserRole role) {
		if (role == UserRole.MANAGER) return;
		throw new NotPermittedException();
	}

	public void executeScript(DataSource dataSource, String scriptFile) {
		try {
			var connection = dataSource.getConnection();
			ScriptUtils.executeSqlScript(connection, new ClassPathResource(scriptFile));

		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
}
