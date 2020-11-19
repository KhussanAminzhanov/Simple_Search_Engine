import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static int searchIndexOfKOccurrence(int[] numbers, int value, int k) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == value) indices.add(i);
        }
        if (indices.size() == 0 || indices.size() < k) return -1;
        return indices.get(k - 1);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int[] numbers;
        final int k;
        final int value;
        if (scanner.hasNextInt()) {
            numbers = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            value = Integer.parseInt(scanner.nextLine());
            k = Integer.parseInt(scanner.nextLine());
        } else {
            numbers = new int[0];
            value = 0;
            k = 1;
        }
        System.out.println(searchIndexOfKOccurrence(numbers, value, k));
    }
}