package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part1 {

    private static String[] readInput() {
        String[] strArr = new String[6];
        try(BufferedReader br = new BufferedReader(new FileReader("src/day3/input.txt"))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                strArr[i++] = line;
            }
        } catch (IOException e) {
            System.err.println("Error eading from file: " + e.getMessage());
            System.exit(1);
        }
        return strArr;
    }

    public static void main(String[] args) {
        //TODO: lära mig regex
        final int MAX_DIGITS = 3;
        String[] strs = readInput();

        String regex = "mul\\(\\d{1,3}\\,\\d{0,3}\\)";
        Pattern pattern = Pattern.compile(regex);


        List<String> matches = new ArrayList<>();
        List<Integer> sums = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            Matcher matcher = pattern.matcher(strs[i]);
            while (matcher.find()) {
                String str = matcher.group();
                boolean first = true;
                boolean second = false;
                StringBuilder firstNumStr = new StringBuilder();
                StringBuilder secondNumStr = new StringBuilder();
                int left = 0;
                int right = 0;
                int index = 4;
                while (first && left <= MAX_DIGITS) {
                    char c = str.charAt(index);
                    if (Character.isDigit(c)) {
                        firstNumStr.append(c);
                        left++;
                    }
                    if (c == ',') {
                        index = str.indexOf(',');
                        first = false;
                        second = true;
                    }
                    index++;
                }
                while (second && right <= MAX_DIGITS) {
                    char c = str.charAt(index);
                    if (Character.isDigit(c)) {
                        secondNumStr.append(c);
                        right++;
                    }
                    if (c == ')') {
                        break;
                    }
                    index++;
                }
                sums.add(Integer.parseInt(firstNumStr.toString()) * Integer.parseInt(secondNumStr.toString()));
            }
        }

        int added = 0;
        for (int sum : sums) {
            added += sum;
        }
        System.out.println(added);
    }
}
