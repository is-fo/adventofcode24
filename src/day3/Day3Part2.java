package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {

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

        final int MAX_DIGITS = 3;
        String[] strs = readInput();

        String regex = "mul\\(\\d{1,3}\\,\\d{0,3}\\)";
        String doRegex = "do\\(\\)";
        String doNotRegex = "don\\'t\\(\\)";
        Pattern pattern = Pattern.compile(regex);
        Pattern doPattern = Pattern.compile(doRegex);
        Pattern doNotPattern = Pattern.compile(doNotRegex);

        List<Integer> sums = new ArrayList<>();
        for (String s : strs) {
            Matcher matcher = pattern.matcher(s);
            Matcher doMatcher = doPattern.matcher(s);
            Matcher doNotMatcher = doNotPattern.matcher(s);
            boolean doMultiply = true;
            int doLastIndex = 0;
            int doNotLastIndex = 0;

            while (matcher.find()) { //wrong: 189527826, 178103639, 12041227, 135975459, 91283613, 69247082
                int currentStart = matcher.start();
                String str = matcher.group();

                doMatcher.region(doLastIndex, currentStart);
                doNotMatcher.region(doNotLastIndex, currentStart);

                while (doMatcher.find()) {
                    doLastIndex = doMatcher.end();
                }
                while (doNotMatcher.find()) {
                    doNotLastIndex = doNotMatcher.end();
                }

                if (doNotLastIndex > doLastIndex) {
                    doMultiply = false;
                } else if (doLastIndex > doNotLastIndex) {
                    doMultiply = true;
                }

                if (!doMultiply) {
                    continue;
                }

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

