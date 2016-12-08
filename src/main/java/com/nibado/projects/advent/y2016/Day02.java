package com.nibado.projects.advent.y2016;

import java.util.List;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day02 implements Runnable {
    @Override
    public void run() {
        List<String> input = readResource("/2016/day02.txt");
        Numpad pad = new Numpad();
        String code = "";
        for(String i : input) {
            pad.apply(i);
            code += pad.get();
        }

        printAnswer(code);
    }

    public static void main(String... argv) {
        new Day02().run();
    }

    public static class Numpad {
        public int x;
        public int y;

        public static final char[][] numbers = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};

        public Numpad() {
            this.x = 0;
            this.y = 0;
        }

        public void apply(String directions) {
            String[] parts = directions.split("");

            for(String p : parts) {
                Direction d = Direction.valueOf(p);
                apply(d);
            }
        }

        public void apply(Direction... directions) {
            for(Direction direction : directions) {
                x += direction.x;
                y += direction.y;

                if (x > 1) {
                    x = 1;
                }
                if (x < -1) {
                    x = -1;
                }
                if (y > 1) {
                    y = 1;
                }
                if (y < -1) {
                    y = -1;
                }
            }
        }

        public char get() {
            return numbers[y + 1][x + 1];
        }
    }

    public enum Direction {
        U(0, -1),
        R(1, 0),
        D(0, 1),
        L(-1, 0);

        public final int x;
        public final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
