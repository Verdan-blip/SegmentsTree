package data_handling;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    private Scanner scanner;

    public DataReader(InputStream in) {
        scanner = new Scanner(in);
    }

    public int[] readData() {
        List<Integer> integerList = new LinkedList<>();
        while (scanner.hasNext()) {
            integerList.add(scanner.nextInt());
        }
        return integerList
                .stream()
                .mapToInt(elem -> elem)
                .toArray();
    }

}
