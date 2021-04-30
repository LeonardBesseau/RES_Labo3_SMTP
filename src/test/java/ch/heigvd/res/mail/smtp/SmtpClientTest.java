package ch.heigvd.res.mail.smtp;

import ch.heigvd.res.mail.config.ConfigManager;
import ch.heigvd.res.mail.model.prank.Prank;
import ch.heigvd.res.mail.model.prank.PrankGenerator;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SmtpClientTest {
    @Test
    public void connectionFailureShouldThrow() throws IOException {
        ConfigManager configManager = new ConfigManager("./test_files/smtp/serverNotResponding");
        PrankGenerator prankGenerator = new PrankGenerator(configManager);
        SmtpClient smtpClient = new SmtpClient(configManager.getSmtpServerAddress(), configManager.getServerPort());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            for (Prank prank : prankGenerator.generatePranks()) {
                smtpClient.sendMessage(prank.generateMessage());
            }
        });

        assertEquals(exception.getMessage(), "SMTP server is not available or reachable. Connection refused (Connection refused)");
    }
}
