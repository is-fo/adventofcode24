package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day1Part1 {

    private int[][] readLists() {
        int[][] lists = new int[2][1000];
        String[][] strLists = new String[2][1000];

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/day1/input.txt"));
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
        Day1Part1 d1 = new Day1Part1();
        int[][] lists = d1.readLists();

        Arrays.sort(lists[0]);
        Arrays.sort(lists[1]);
        int distance = 0;
        for (int i = 0; i < 1000; i++) {
            distance += Math.abs(lists[0][i] - lists[1][i]);
        }

        System.out.println(distance); //3508942
    }
}
