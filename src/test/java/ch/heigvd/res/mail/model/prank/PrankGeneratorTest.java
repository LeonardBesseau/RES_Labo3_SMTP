package ch.heigvd.res.mail.model.prank;

import ch.heigvd.res.mail.config.ConfigManager;
import ch.heigvd.res.mail.model.mail.Group;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrankGeneratorTest {
    static PrintStream original = System.err;

    @Test
    public void groupNumberTooHighShouldBeModifiedAndWarned() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ConfigManager configManager = new ConfigManager("./test_files/prankGenerator/groupNumberTooHigh");
        PrankGenerator prankGenerator = new PrankGenerator(configManager);
        System.setErr(ps);

        List<Group> list = prankGenerator.generateGroups(configManager.getTargets(), configManager.getNbGroups());

        assertEquals(list.size(),2);
        assertEquals(baos.toString(), "Not enough targets to generate enough groups. Continue using 2 groups");

        System.setErr(original);
    }


    @AfterClass
    public static void cleanAll() throws IOException {
        System.setErr(original);
    }
}
