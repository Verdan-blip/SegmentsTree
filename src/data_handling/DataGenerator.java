package data_handling;

import java.util.Arrays;

public class DataGenerator {

    public int generateInt(int from, int to) {
        return (int) (Math.random() * (to - from)) + from;
    }

    public int[] generateIntArray(int count, int from, int to) {
        int[] generatedArray = new int[count];
        return Arrays
                .stream(generatedArray)
                .map(elem -> generateInt(from, to))
                .toArray();
    }

}
