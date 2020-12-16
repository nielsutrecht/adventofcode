package com.nibado.projects.advent.y2015;

import java.util.HashSet;
import java.util.Set;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResourceAsString;

public class Day03J implements Runnable {

    public static void main(String... argv) {
        new Day03J().run();
    }

    @Override
    public void run() {
        String input = readResourceAsString("/2015/day03.txt");

        Point current = new Point();
        Set<Point> visited = new HashSet<>();
        visited.add(current);

        for(int i = 0;i < input.length();i++) {
            current = current.next(input.charAt(i));
            visited.add(current);
        }

        printAnswer(visited.size());

        Point santa = new Point();
        Point robot = new Point();
        visited = new HashSet<>();
        visited.add(santa);

        for(int i = 0;i < input.length();i++) {
            santa = santa.next(input.charAt(i));
            robot = robot.next(input.charAt(++i));
            visited.add(santa);
            visited.add(robot);
        }

        printAnswer(visited.size());
    }

    public static class Point {
        public final int x;
        public final int y;

        public Point() {
            this.x = 0;
            this.y = 0;
        }

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point next(char input) {
            switch(input) {
                case '>': return new Point(x + 1, y);
                case '<': return new Point(x - 1, y);
                case '^': return new Point(x, y - 1);
                case 'v': return new Point(x, y + 1);

                default: throw new IllegalArgumentException("Invalid direction: " + input);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
