package com.nibado.projects.advent;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day06 implements Runnable {
    private static int WIDTH = 1000;
    private static int HEIGHT = 1000;
    private static Map<String, Function<Boolean, Boolean>> BOOL_OPERATIONS = new HashMap<>();
    private static Map<String, Function<Integer, Integer>> INT_OPERATIONS = new HashMap<>();

    private static Pattern PATTERN = Pattern.compile("(toggle|turn on|turn off) ([0-9]{1,3}),([0-9]{1,3}) through ([0-9]{1,3}),([0-9]{1,3})");

    static {
        BOOL_OPERATIONS.put("turn on", b -> true);
        BOOL_OPERATIONS.put("turn off", b -> false);
        BOOL_OPERATIONS.put("toggle", b -> !b);

        INT_OPERATIONS.put("turn on", i -> i + 1);
        INT_OPERATIONS.put("turn off", i -> Math.max(0, i - 1));
        INT_OPERATIONS.put("toggle", i -> i + 2);
    }

    @Override
    public void run() {
        List<Action> actions = readResource("/day06.txt").stream().map(Action::from).collect(Collectors.toList());

        BitSet boolLamps = new BitSet(WIDTH * HEIGHT);
        actions.forEach(a -> a.apply(boolLamps));
        printAnswer(boolLamps.stream().count());

        int[] intLamps = new int[WIDTH * HEIGHT];
        actions.forEach(a -> a.apply(intLamps));
        printAnswer(Arrays.stream(intLamps).sum());
    }

    public static void main(String... argv) {
        new Day06().run();
    }

    private static class Action {
        private String operation;
        private Point from;
        private Point to;

        public static Action from(String line) {
            Matcher m = PATTERN.matcher(line);

            m.matches();

            Action a = new Action();
            a.operation = m.group(1);
            a.from = new Point(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
            a.to = new Point(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
            return a;
        }

        public void apply(BitSet lamps) {
            Function<Boolean, Boolean> operation = BOOL_OPERATIONS.get(this.operation);
            for(int y = from.y;y <= to.y;y++) {
                for(int x = from.x;x <= to.x;x++) {
                    int i = toIndex(x, y);

                    lamps.set(i, operation.apply(lamps.get(i)));
                }
            }
        }

        public void apply(int[] lamps) {
            Function<Integer, Integer> operation = INT_OPERATIONS.get(this.operation);
            for(int y = from.y;y <= to.y;y++) {
                for(int x = from.x;x <= to.x;x++) {
                    int i = toIndex(x, y);

                    lamps[i] = operation.apply(lamps[i]);
                }
            }
        }

        private int toIndex(int x, int y) {
            return x + y * WIDTH;
        }
    }
}
