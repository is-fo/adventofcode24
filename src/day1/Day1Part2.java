package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Day1Part2 {
    private int[][] readLists() {
        int[][] lists = new int[2][1000];

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/day1/input.txt"));
            String[][] strLists = new String[2][1000];
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" {3}");
                strLists[0][count] = tokens[0];
                strLists[1][count] = tokens[1];
                count++;
            }
            for (int i = 0; i < strLists[0].length; i++) {
                lists[0][i] = Integer.parseInt(strLists[0][i]);
            }
            for (int i = 0; i < strLists[1].length; i++) {
                lists[1][i] = Integer.parseInt(strLists[1][i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading from file: " + e.getMessage());
            System.exit(1);
        }

        return lists;
    }

    public static void main(String[] args) {
        Day1Part2 d2 = new Day1Part2();
        int[][] lists = d2.readLists();

        HashMap<Integer, Integer> occurences = new HashMap<>();
        for (int i = 0; i < lists[1].length; i++) {
            occurences.put(lists[1][i], occurences.getOrDefault(lists[1][i], 0) + 1);
        }

        int[] similarity = new int[1000];
        for (int i = 0; i < lists[0].length; i++) {
            similarity[i] = lists[0][i] * occurences.getOrDefault(lists[0][i], 0);
        }

        int sum = 0;
        for (int i = 0; i < similarity.length; i++) {
            sum += similarity[i];
        }
        System.out.println(sum); //26593248
    }
}
