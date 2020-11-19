import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        int index = 0;
        while (scanner.hasNext()) {
            int num = scanner.nextInt();
            if (index % 2 != 0) list.add(num);
            index++;
        }
        Collections.reverse(list);
        list.forEach(num -> System.out.print(num + " "));
    }
}