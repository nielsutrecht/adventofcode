package com.nibado.projects.advent;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResourceAsString;

public class Day10 implements Runnable {

    @Override
    public void run() {
        String input = readResourceAsString("/day10.txt");

        printAnswer(apply(40, input));
        printAnswer(apply(50, input));
    }

    private static int apply(int times, String input) {
        for(int i = 0;i < times;i++) {
            input = next(input);
        }

        return input.length();
    }

    private static String next(String input) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0, count = 0;i < input.length();) {
            while(i + count < input.length() && input.charAt(i) == input.charAt(i + count)) {
                count++;
            }
            builder.append(count).append(input.charAt(i));
            i += count;
            count = 0;
        }

        return builder.toString();
    }

    public static void main(String... argv) {
        new Day10().run();
    }
}
