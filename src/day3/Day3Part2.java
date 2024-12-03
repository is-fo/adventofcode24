package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {

    private static String readInput() {
        String line = "";
        byte[] bytes;
        try {
            //~152ms filereading
            bytes = Files.readAllBytes(Paths.get("src/day3/bigboy.txt")); //uncommited 94MB txt file
            line = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static void main(String[] args) {

        String s = readInput();

        long startTime = System.nanoTime();
        //largest performance gain here by combining the patterns...
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
                //...then exiting early if it wasnt == "mul(x,y)"
                continue;
            }

            if (!doMultiply) {
                continue;
            }

            int firstNum = 0;
            int secondNum = 0;
            int index = 4;
            while (firstNum / 1000 == 0) {
                char c = str.charAt(index++);
                if (Character.isDigit(c)) {
                    firstNum = firstNum * 10 + c - '0';
                }
                else {
                    index = str.indexOf(',');
                    break;
                }
            }
            while (secondNum / 1000 == 0) {
                char c = str.charAt(++index);
                if (Character.isDigit(c)) {
                    secondNum = secondNum * 10 + c - '0';
                } else {
                    break;
                }
            }
            sum += (firstNum * secondNum);
        }

        System.out.println("Answer: " + sum);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println((duration / 1_000_000) + " milliseconds (filereading excl.)");
    }
}

