package org.example;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileHandler {
    private static final String FILE_NAME = "data.txt";
    private static final String TEMP_FILE_NAME = "temp.txt";

    @BeforeEach
    void setUp() throws IOException {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        File tempFile = new File(TEMP_FILE_NAME);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testCreateFile() {
        createFile();
        File file = new File(FILE_NAME);

        assertTrue(file.exists(), "File should be created.");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            String line3 = reader.readLine();

            // Debug output
            System.out.println("Line 1: " + line1);
            System.out.println("Line 2: " + line2);
            System.out.println("Line 3: " + line3);

            assertEquals("Nama: John Doe", line1.trim(), "First line should match.");
            assertEquals("NIM: 12345", line2.trim(), "Second line should match.");
            assertEquals("Semester: 3", line3.trim(), "Third line should match.");
        } catch (IOException e) {
            fail("Failed to read the file.");
        }
    }

    private static void createFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME, false)) {
            writer.write("Nama: John Doe\n");
            writer.write("NIM: 12345\n");
            writer.write("Semester: 3\n");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    @Test
    void testReadFile() {
        createFile();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        readFile();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Nama: John Doe"), "Output should contain 'Nama: John Doe'");
        assertTrue(output.contains("NIM: 12345"), "Output should contain 'NIM: 12345'");
        assertTrue(output.contains("Semester: 3"), "Output should contain 'Semester: 3'");

        System.setOut(System.out);
    }

    private static void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    @Test
    void testDeleteFile() {
        createFile();

        deleteFile();
        File file = new File(FILE_NAME);

        assertFalse(file.exists(), "File should be deleted.");
    }

    private static void deleteFile() {
        File file = new File(FILE_NAME);
        if (file.delete()) {
            System.out.println("File deleted.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        File tempFile = new File(TEMP_FILE_NAME);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }
}
