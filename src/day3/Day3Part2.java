package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {

    private static String readInput() {
        String line = "";
        //BufferedReader runtime for bigboy: 633,36 seconds total
        //Files.readAllBytes runtime for bigboy: 14,91 sec total, 156 ms filereading
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get("src/day3/bigboy.txt")); //uncommited 94MB txt file
            line = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static void main(String[] args) {
        final int MAX_DIGITS = 3;
        long startTime2 = System.nanoTime();

        String s = readInput();
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println(duration2 / 1_000_000 + " <-- Filereading");
        long startTime = System.nanoTime();

        List<Integer> multiplied = new ArrayList<>();

        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{0,3}\\)");
        Pattern doPattern = Pattern.compile("do\\(\\)");
        Pattern doNotPattern = Pattern.compile("don't\\(\\)");

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
            multiplied.add(Integer.parseInt(firstNumStr.toString()) * Integer.parseInt(secondNumStr.toString()));
        }

        int added = 0;
        for (int sum : multiplied) {
            added += sum;
        }
        System.out.println(added);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(duration + " nanoseconds (filereading excl.)");
        System.out.println((duration / 1_000_000) + " milliseconds (filereading excl.)");
    }
}

