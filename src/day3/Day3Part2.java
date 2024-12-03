package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {

    private static String readInput() {
        String line = "";
        //BufferedReader runtime for bigboy: 633,36 seconds total
        //Files.readAllBytes runtime for bigboy: 14,91 sec total, 156 ms filereading
        //Optimized solution: 1153 ms total
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

        Pattern combinedPattern = Pattern.compile("mul\\(\\d{1,3},\\d{0,3}\\)|do\\(\\)|don't\\(\\)");

        Matcher matcher = combinedPattern.matcher(s);

        boolean doMultiply = true;

        int sum = 0;
        while (matcher.find()) {
            String str = matcher.group();
            if (str.charAt(0) == 'd') {
                if (str.charAt(2) == 'n') {
                    doMultiply = false;
                } else {
                    doMultiply = true;
                }
                continue;
            }

            if (!doMultiply) {
                continue;
            }

            boolean first = true;
            boolean second = false;
            int firstNum = 0;
            int secondNum = 0;
            int left = 0;
            int right = 0;
            int index = 4;
            while (first && left <= MAX_DIGITS) {
                char c = str.charAt(index);
                if (Character.isDigit(c)) { //no significant performance gain using (c >= '0' && c <= '9')
                    firstNum = firstNum * 10 + c - '0';
                    left++;
                }
                else {
                    index = str.indexOf(',');
                    first = false;
                    second = true;
                }
                index++;
            }
            while (second && right <= MAX_DIGITS) {
                char c = str.charAt(index);
                if (Character.isDigit(c)) {
                    secondNum = secondNum * 10 + c - '0';
                    right++;
                } else {
                    break;
                }
                index++;
            }
            sum += (firstNum * secondNum);
        }

        System.out.println("Answer: " + sum);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println((duration / 1_000_000) + " milliseconds (filereading excl.)");
        System.out.println((duration / 1_000_000) + (duration2 / 1_000_000) + " total runtime");
    }
}

