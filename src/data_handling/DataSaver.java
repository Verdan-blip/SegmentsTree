package data_handling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class DataSaver {

    public static final String PATH_TO_FATA_FILE = Paths.get("res/data.txt").toString();

    public static final int GENERATING_COUNT = 10_000;

    public static void main(String[] args) {

        DataGenerator dataGenerator = new DataGenerator();

        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH_TO_FATA_FILE)) {

            PrintWriter printWriter = new PrintWriter(fileOutputStream);

            for (int i = 0; i < GENERATING_COUNT; i++) {
                int generatedInt = dataGenerator.generateInt(0, 1_000_000);
                printWriter.println(generatedInt);
            }

            printWriter.flush();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        System.out.println("Data has been successfully recorded");

    }

}
