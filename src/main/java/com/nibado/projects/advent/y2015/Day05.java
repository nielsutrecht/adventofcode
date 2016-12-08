package com.nibado.projects.advent.y2015;

import java.util.*;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day05 implements Runnable {
    private static final Set<Character> VOWELS = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    private static final String[] BAD = {"ab", "cd", "pq", "xy"};

    @Override
    public void run() {
        List<String> input = readResource("/2015/day05.txt");
        int result1 = (int)input.stream()
                .filter(Day05::vowelCheck)
                .filter(Day05::doubleCheck)
                .filter(Day05::badCheck)
                .count();

        printAnswer(result1);

        int result2 = (int)input.stream()
                .filter(Day05::doublePairCheck)
                .filter(Day05::doubleWithBetweenCheck)
                .count();

        printAnswer(result2);
    }

    public static void main(String... argv) {
        new Day05().run();
    }

    public static boolean vowelCheck(String line) {
        int count = 0;
        for(int i = 0;i < line.length();i++) {
            if(VOWELS.contains(line.charAt(i))) {
                count++;
            }
            if(count >= 3) {
                return true;
            }
        }

        return false;
    }

    public static boolean doubleCheck(String line) {
        for(int i = 0;i < line.length() - 1;i++) {
            if(line.charAt(i) == line.charAt(i + 1)) {
                return true;
            }
        }

        return false;
    }

    public static boolean doubleWithBetweenCheck(String line) {
        for(int i = 0;i < line.length() - 2;i++) {
            if(line.charAt(i) == line.charAt(i + 2)) {
                return true;
            }
        }

        return false;
    }

    public static boolean doublePairCheck(String line) {
        for(int i = 0;i < line.length() - 1;i++) {
            String pair = line.substring(i, i + 2);

            if(line.lastIndexOf(pair) > i + 1) {
                return true;
            }
        }

        return false;
    }

    public static boolean badCheck(String line) {
        for(String bad : BAD) {
            if(line.contains(bad)) {
                return false;
            }
        }

        return true;
    }
}
