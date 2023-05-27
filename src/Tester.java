import data_handling.DataGenerator;
import data_handling.DataReader;
import data_handling.Timer;
import segments_tree.SegmentsTree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tester {

    public static final String PATH_TO_DATA_FILE = Paths.get("res/data.txt").toString();

    public static final int INSERTIONS_COUNT = 10_000;

    public static final int REMOVES_COUNT = 1_000;

    public static final int SEARCHINGS_COUNT = 100;

    public static final int UPDATES_COUNT = 10_000;

    public static final int GETTING_SUMS_COUNT = 10_000;

    public static final Path PATH_TO_INSERTION_RESULTS = Paths.get("res/insertions.txt");
    public static final Path PATH_TO_SEARCHINGS_RESULTS = Paths.get("res/searchings.txt");
    public static final Path PATH_TO_UPDATING_RESULTS = Paths.get("res/updates.txt");
    public static final Path PATH_TO_GETTING_SUMS_RESULTS = Paths.get("res/getting_sums.txt");
    public static final Path PATH_TO_REMOVES_RESULTS = Paths.get("res/removes.txt");

    public static void main(String[] args) throws FileNotFoundException {

        Timer timer = new Timer();
        SegmentsTree segmentsTree;

        DataGenerator dataGenerator = new DataGenerator();
        int[] generatedInts;

        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_DATA_FILE)) {
            DataReader dataReader = new DataReader(fileInputStream);
            generatedInts = dataReader.readData();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //Creating empty tree
        segmentsTree = new SegmentsTree(new int[]{});

        //Testing insertions
        try (PrintWriter printWriter = new PrintWriter(PATH_TO_INSERTION_RESULTS.toString())) {
            for (int i = 0; i < INSERTIONS_COUNT; i++) {
                timer.start();
                segmentsTree.insert(i, generatedInts[i]);
                printWriter.println(timer.getElapsed() + "=" + segmentsTree.getOperationsCount());
            }
        }

        //Testing searching elements
        try (PrintWriter printWriter = new PrintWriter(PATH_TO_SEARCHINGS_RESULTS.toString())) {
            for (int i = 0; i < SEARCHINGS_COUNT; i++) {
                int randomIndex = dataGenerator.generateInt(0, generatedInts.length);
                timer.start();
                segmentsTree.find(generatedInts[randomIndex]);
                printWriter.println(timer.getElapsed() + "=" + segmentsTree.getOperationsCount());
            }
        }

        //Testing updating tree by index
        try (PrintWriter printWriter = new PrintWriter(PATH_TO_UPDATING_RESULTS.toString())) {
            for (int i = 0; i < UPDATES_COUNT; i++) {
                int randomElem = dataGenerator.generateInt(0, 1_000_000);
                int randomIndex = dataGenerator.generateInt(0, generatedInts.length);
                timer.start();
                segmentsTree.set(randomIndex, randomElem);
                printWriter.println(timer.getElapsed() + "=" + segmentsTree.getOperationsCount());
            }
        }

        //Testing getting sums
        try (PrintWriter printWriter = new PrintWriter(PATH_TO_GETTING_SUMS_RESULTS.toString())) {
            for (int i = 0; i < GETTING_SUMS_COUNT; i++) {
                int randomLIndex = dataGenerator.generateInt(0, 8_000);
                int randomRIndex = dataGenerator.generateInt(randomLIndex, randomLIndex + 2_000);
                timer.start();
                segmentsTree.sum(randomLIndex, randomRIndex);
                printWriter.println(timer.getElapsed() + "=" + segmentsTree.getOperationsCount());
            }
        }

        //Testing removes count
        try (PrintWriter printWriter = new PrintWriter(PATH_TO_REMOVES_RESULTS.toString())) {
            for (int i = 0; i < REMOVES_COUNT; i++) {
                timer.start();
                segmentsTree.remove(0);
                printWriter.println(timer.getElapsed() + "=" + segmentsTree.getOperationsCount());
            }
        }

    }

}
