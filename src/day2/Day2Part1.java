package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2Part1 {

    private List<List<Integer>> readInput() {
        List<List<Integer>> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/day2/input.txt"))) {
            while (br.ready()) {
                String line = br.readLine();
                String[] nums = line.split(" ");
                List<Integer> row = new ArrayList<>();
                for (int i = 0; i < nums.length; i++) {
                    row.add(Integer.parseInt(nums[i]));
                }
                input.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-34);
        }
        return input;
    }

    public static void main(String[] args) {
        Day2Part1 d = new Day2Part1();
        List<List<Integer>> input = d.readInput();

        int safeReports = 0;
        for (int i = 0; i < input.size(); i++) {
            List<Integer> report = input.get(i);
            boolean safe = true;
            int[] tracker = new int[report.size()];
            for (int j = report.size() - 1; j > 0; j--) {
                tracker[j] = report.get(j) - report.get(j - 1);
                if (Math.abs(report.get(j) - report.get(j - 1)) > 3
                || report.get(j).equals(report.get(j - 1))) {
                    safe = false;
                    break;
                }
                if (j != report.size() - 1) {
                    if ((tracker[j] & 32) != (tracker[j + 1] & 32)) {
                        safe = false;
                        break;
                    }
                }
            }
            if (safe) {
                safeReports++;
            }
        }
        System.out.println(safeReports);
    }
}
