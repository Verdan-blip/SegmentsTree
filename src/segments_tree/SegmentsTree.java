package segments_tree;

public class SegmentsTree {

    private int[] segmentsArray;

    private int inputArrayCapacity;

    private int inputArrayLength;

    private int operationsCount;

    private int pow(int value, int pow) {
        int num = 1;
        for (int i = 0; i < pow; i++)
            num *= value;
        return num;
    }

    private int getNormalizedSize(int inputArrayLength) {
        int power = (int) Math.ceil(Math.log(inputArrayLength) / Math.log(2));
        return pow(2, power);
    }

    public int[] getInputArray() {
        int[] array = new int[inputArrayCapacity];
        int arrayIndex = segmentsArray.length - inputArrayCapacity;
        for (int i = 0; i < inputArrayLength; i++) {
            array[i] = segmentsArray[arrayIndex + i];
        }
        return array;
    }

    private void buildTree(int left, int right, int i) {
        if (left < right - 1) {
            operationsCount++;
            int mid = (right + left) / 2;
            buildTree(left, mid, 2 * i + 1);
            buildTree(mid, right, 2 * i + 2);
            segmentsArray[i] = segmentsArray[2 * i + 1] + segmentsArray[2 * i + 2];
            operationsCount++;
        }
    }

    private void updateTreeByValue(int left, int right, int i, final int index, int value) {
        if (left == right - 1) {
            operationsCount++;
            segmentsArray[i] = value;
        } else if (left < right - 1) {
            int mid = (right + left) / 2;
            if (index < mid)
                updateTreeByValue(left, mid, 2 * i + 1, index, value);
            else
                updateTreeByValue(mid, right, 2 * i + 2, index, value);
            segmentsArray[i] = segmentsArray[2 * i + 1] + segmentsArray[2 * i + 2];
            operationsCount+=4;
        }
    }

    private int sumInterval(int left, int right, int i, int inputLeft, int inputRight) {
        if (inputLeft >= inputRight) {
            operationsCount++;
            return 0;
        }
        if (left == inputLeft && right == inputRight) {
            operationsCount++;
            return segmentsArray[i];
        }
        int mid = (right + left) / 2;
        operationsCount++;
        int leftSon = sumInterval(left, mid, 2 * i + 1, inputLeft, Math.min(mid, inputRight));
        int rightSon = sumInterval(mid, right, 2 * i + 2, Math.max(inputLeft, mid), inputRight);
        operationsCount+=3;
        return leftSon + rightSon;
    }

    public SegmentsTree(int[] inputArray) {

        inputArrayLength = inputArray.length;
        inputArrayCapacity = getNormalizedSize(inputArray.length);

        segmentsArray = new int[inputArrayCapacity * 2 - 1];

        for (int i = 0; i < inputArrayLength; i++) {
            segmentsArray[inputArrayCapacity + i  - 1] = inputArray[i];
        }

        buildTree(0, inputArrayCapacity, 0);
    }

    public void set(int index, int value) {
        operationsCount = 0;
        updateTreeByValue(0, inputArrayCapacity, 0, index, value);
    }

    public int sum(int inputLeft, int inputRight) {
        operationsCount = 0;
        return sumInterval(0, inputArrayCapacity, 0, inputLeft, inputRight);
    }

    public void insert(int index, int elem) {
        operationsCount = 0;
        if (index < 0 || index > inputArrayLength)
            throw new IndexOutOfBoundsException();

        //Points to the beginning of inputArray in tree

        int[] inputArray = getInputArray();
        inputArrayLength++;

        operationsCount += 2;

        if (inputArrayLength > inputArrayCapacity) {
            inputArrayCapacity *= 2;
            segmentsArray = new int[getNormalizedSize(inputArrayLength) * 2 - 1];
            operationsCount++;
        }

        int arrayBegIndex = segmentsArray.length - getNormalizedSize(inputArrayLength);
        int insertingArrayIndex = arrayBegIndex + index;

        operationsCount += 2;

        for (int i = arrayBegIndex; i < inputArrayLength + arrayBegIndex; i++) {
            if (i < insertingArrayIndex)
                segmentsArray[i] = inputArray[i - arrayBegIndex];
            if (i == insertingArrayIndex)
                segmentsArray[i] = elem;
            if (i > insertingArrayIndex) {
                segmentsArray[i] = inputArray[i - arrayBegIndex - 1];
            }
            operationsCount++;
        }

        buildTree(0, inputArrayCapacity, 0);

    }

    public void remove(int index) {
        operationsCount = 0;
        if (index < 0 || index > inputArrayLength)
            throw new IndexOutOfBoundsException();

        operationsCount++;

        int arrayBegIndex = segmentsArray.length - getNormalizedSize(inputArrayLength);
        int removingArrayIndex = arrayBegIndex + index;
        inputArrayLength--;

        operationsCount += 3;

        for (int i = removingArrayIndex; i < arrayBegIndex + inputArrayLength; i++) {
            segmentsArray[i] = segmentsArray[i + 1];
            operationsCount++;
        }
        segmentsArray[arrayBegIndex + inputArrayLength] = 0;

        operationsCount++;

        buildTree(0, inputArrayCapacity, 0);

    }

    public int find(int elem) {
        operationsCount = 0;
        int arrayBegIndex = segmentsArray.length - inputArrayCapacity;
        operationsCount++;
        for (int i = arrayBegIndex; i < arrayBegIndex + inputArrayLength; i++) {
            operationsCount++;
            if (segmentsArray[i] == elem)
                return i - arrayBegIndex;
        }
        return -1;
    }

    public int[] getSegmentsArray() {
        return segmentsArray;
    }

    public int size() {
        return segmentsArray.length;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

}
