package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> data = new ArrayList<>();
    private static final Map<String, List<Integer>> map = new HashMap<>();
    private static String dataFile;

    private static void search() {
        List<String> matches = new ArrayList<>();

        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine().trim().toLowerCase();
        System.out.println();

        System.out.println("Enter a name or email to search all suitable people:");
        String query = scanner.nextLine().trim().toLowerCase();
        System.out.println();

        if (strategy.equals("any")) matches = getMatchesAny(query);
        if (strategy.equals("all")) matches = getMatchesAll(query);
        if (strategy.equals("none")) matches = getMatchesNone(query);

        if (matches.size() == 0) {
            System.out.println("No matching people found.");
        } else {
            System.out.println("Found people:");
            matches.forEach(System.out::println);
        }
        System.out.println();
    }

    private static List<String> getMatchesAny(String q) {
        List<String> matches = new ArrayList<>();
        for (String query : q.trim().toLowerCase().split("\\s+")) {
            List<Integer> indices = map.get(query);
            if (indices != null) for (int index : indices) matches.add(data.get(index));
        }
        return matches;
    }

    private static List<String> getMatchesAll(String q) {
        List<String> matches = new ArrayList<>();
        List<Integer> matchIndices = new ArrayList<>();
        for (String query : q.trim().toLowerCase().split("\\s+")) {
            List<Integer> indices = map.get(query);
            if (matchIndices.size() == 0 && indices != null) matchIndices = indices;
            else {
                matchIndices = removeIfNotContains(matchIndices, indices);
            }
        }
        for (int index : matchIndices) {
            matches.add(data.get(index));
        }
        return matches;
    }

    private static List<Integer> removeIfNotContains(List<Integer> first, List<Integer> second) {
        List<Integer> list = new ArrayList<>();
        for (Integer integer : first) {
            for (Integer integer1 : second) {
                if (second.contains(integer1) && first.contains(integer) && !list.contains(integer)) list.add(integer);
            }
        }
        return list;
    }

    private static List<String> getMatchesNone(String q) {
        List<String> matches = new ArrayList<>();
        List<Integer> matchIndices = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) matchIndices.add(i);
        for (String query : q.trim().toLowerCase().split("\\s+")) {
            List<Integer> indices = map.get(query);
            if (matchIndices.size() != 0) matchIndices = removeIfContains(matchIndices, indices);
        }
        for (int index : matchIndices) {
            matches.add(data.get(index));
        }
        return matches;
    }

    private static List<Integer> removeIfContains(List<Integer> first, List<Integer> second) {
        List<Integer> list = new ArrayList<>();
        for (Integer integer : first) {
            if (second.contains(integer)) list.remove(integer);
            else list.add(integer);
        }
        return list;
    }

    private static void enterData() {
        File file = new File(dataFile.toLowerCase());
        Scanner reader;
        try {
            reader = new Scanner(file);
            int index = 0;
            while (reader.hasNext()) {
                String input = reader.nextLine();
                data.add(input);
                for (String v : input.trim().toLowerCase().split("\\s")) {
                    if (map.containsKey(v)) {
                        map.get(v).add(index);
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(index);
                        map.put(v, list);
                    }
                }
                index++;
            }
            System.out.println();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int printMenuAndOption() {
        String menu = "=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit\n";
        System.out.print(menu);
        String option = scanner.nextLine();
        while (true) {
            if (option.matches("\\d+")) {
                int o = Integer.parseInt(option);
                if (o <= 2 && o >= 0) {
                    return o;
                }
            }
            System.out.println("\nIncorrect option! Try again.\n");
            System.out.print(menu);
            option = scanner.nextLine();
        }
    }

    private static void printData() {
        System.out.println("=== List of people ===");
        data.forEach(System.out::println);
        System.out.println();
    }

    private static void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--data") && i != args.length - 1) {
                dataFile = args[i + 1];
            }
        }
    }

    public static void main(String[] args) {
        parseArgs(args);
        enterData();
        int option = -1;

        while (option != 0) {
            option = printMenuAndOption();

            if (option == 1) search();
            if (option == 2) printData();
        }
    }
}
