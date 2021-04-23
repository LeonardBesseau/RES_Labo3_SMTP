package ch.heigvd.res.mail;

import ch.heigvd.res.mail.config.ConfigManager;
import ch.heigvd.res.mail.model.prank.Prank;
import ch.heigvd.res.mail.model.prank.PrankGenerator;
import ch.heigvd.res.mail.smtp.SmtpClient;

import java.io.IOException;

public class Mail {

    public static void main(String[] args) throws IOException {

        ConfigManager configManager = null;
        try {
            configManager = new ConfigManager("./config/");
        }catch (java.io.FileNotFoundException e){
            System.err.println("Missing Files. See documentation for how to use the prank generator");
            System.exit(1);
        }catch (RuntimeException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        PrankGenerator prankGenerator = new PrankGenerator(configManager);
        SmtpClient smtpClient= new SmtpClient(configManager.getSmtpServerAddress(), configManager.getServerPort());
        for (Prank prank : prankGenerator.generatePranks()) {
            smtpClient.sendMessage(prank.generateMessage());
        }
    }
}
