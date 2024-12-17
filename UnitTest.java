import org.example.FileHandler;
import org.example.Robot;
import org.example.Vac;
import org.example.VacMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private List<Vac> vacs;
    private String testTxtFile = "test_vacs.txt";
    private String testJsonFile = "test_vacs.json";
    private String testXmlFile = "test_vacs.xml";
    private String testEncryptedJsonFile = "test_encrypted_vacs.json";
    private String testZipFile = "test_vacs.zip";

    @BeforeEach
    void setUp() throws Exception {
        vacs = new ArrayList<>();
        vacs.add(new Robot("1", "ModelX", 999.99, 1200, format.parse("2023-01-01")));
        vacs.add(new Robot("2", "ModelY", 899.99, 1000, format.parse("2022-01-01")));
    }


    @Test
    void testSaveAndLoadVacsToJson() throws Exception {
        FileHandler.saveVacsToJson(vacs, testJsonFile);
        assertTrue(new File(testJsonFile).exists(), "JSON file should be created");

        List<Vac> loadedVacs = FileHandler.loadVacsFromJson(testJsonFile);

        assertEquals(vacs.size(), loadedVacs.size(), "Loaded vacs size should match saved");
        assertEquals(vacs.get(1).getPrice(), loadedVacs.get(1).getPrice(), "Price should match");
    }


    @Test
    void testSaveAndLoadEncryptedJson() throws Exception {
        FileHandler.saveEncryptedJson(vacs, testEncryptedJsonFile);
        assertTrue(new File(testEncryptedJsonFile).exists(), "Encrypted JSON file should be created");

        List<Vac> loadedVacs = FileHandler.loadEncryptedJson(testEncryptedJsonFile);

        assertEquals(vacs.size(), loadedVacs.size(), "Loaded vacs size should match saved");
        assertEquals(vacs.get(0).getModel(), loadedVacs.get(0).getModel(), "Model should match");
    }

    @Test
    void testZipFile() throws Exception {
        FileHandler.saveVacsToTxt(vacs, testTxtFile);
        FileHandler.zipFile(testTxtFile, testZipFile);

        assertTrue(new File(testZipFile).exists(), "ZIP file should be created");
    }

    @Test
    void testVacMapOperations() {
        VacMap<Robot> vacMap = new VacMap<>();
        Robot robot = new Robot("3", "ModelZ", 799.99, 800, new Date());

        vacMap.addVac(robot);
        assertEquals(robot, vacMap.getVacById("3"), "Vac should be retrievable by ID");

        vacMap.removeVac(robot);
        assertNull(vacMap.getVacById("3"), "Vac should be removed");
    }

    @Test
    void testInvalidJsonLoading() {
        try {
            Files.write(Paths.get(testJsonFile), "Invalid JSON content".getBytes());
            List<Vac> loadedVacs = FileHandler.loadVacsFromJson(testJsonFile);
            assertTrue(loadedVacs.isEmpty(), "Loaded vacs should be empty on invalid JSON");
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
}
