package com.nibado.projects.advent;

import java.util.ArrayList;
import java.util.List;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResourceAsString;

public class Day10 implements Runnable {

    @Override
    public void run() {
        String input = readResourceAsString("/day10.txt");

        printAnswer(10, "One", apply(40, input));
        printAnswer(10, "Two", apply(50, input));
    }

    private static int apply(int times, String input) {
        for(int i = 0;i < times;i++) {
            input = next(input);
        }

        return input.length();
    }

    private static String next(String input) {
        List<String> splitted = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for(char c : input.toCharArray()) {
            if(builder.length() > 0 && c != builder.charAt(0)) {
                splitted.add(builder.toString());
                builder.setLength(0);
            }
            builder.append(c);
        }

        splitted.add(builder.toString());

        builder.setLength(0);

        splitted.stream().map(s -> s.length() + Character.toString(s.charAt(0))).forEach(builder::append);

        return builder.toString();
    }

    public static void main(String... argv) {
        new Day10().run();
    }
}
