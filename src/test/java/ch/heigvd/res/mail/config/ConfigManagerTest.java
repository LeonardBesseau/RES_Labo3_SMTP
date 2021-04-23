package ch.heigvd.res.mail.config;

import org.junit.Test;


import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigManagerTest {
    @Test
    public void missingServerNameInPropertiesShouldThrow() {
        System.out.println(new File(".").getAbsolutePath());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/missing_server");
        });
        assertEquals(exception.getMessage(), "Mail server cannot be empty");
    }

    @Test
    public void missingServerPortInPropertiesShouldThrow() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/missing_port");
        });

    }

    @Test
    public void missingGroupNumberInPropertiesShouldThrow() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/missing_group_number");
        });

        assertEquals(exception.getMessage(), "The number of group must be a number and non-empty");
    }

    @Test
    public void missingBCCInPropertiesShouldNotThrow() {
        assertDoesNotThrow(() -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/missing_bcc");
        });
    }
    @Test
    public void wrongEmailInBccShouldThrow() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/wrong_bcc");
        });

        assertEquals(exception.getMessage(), "Email in Bcc must be valid");
    }



    @Test
    public void wrongPortShouldThrow() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/wrong_port");
        });

        assertEquals(exception.getMessage(), "Port number must be between 1 and 65535. Careful port 1-1000 need admin privileges");
    }

    @Test
    public void wrongGroupNumberShouldThrow() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/wrong_group");
        });

        assertEquals(exception.getMessage(), "The number of group should be greater than 0");
    }

    @Test
    public void configShouldWorkWhenParamAreValid() {
        assertDoesNotThrow(() -> {
            ConfigManager configManager = new ConfigManager("./test_files/properties_missing/correct");
        });
    }
}
