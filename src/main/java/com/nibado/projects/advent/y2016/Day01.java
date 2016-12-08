package com.nibado.projects.advent.y2016;

import java.util.HashSet;
import java.util.Set;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResourceAsString;

public class Day01 implements Runnable {
    @Override
    public void run() {
        String[] input = readResourceAsString("/2016/day01.txt").replaceAll(" ", "").split(",");
        Set<Position> positions = new HashSet<>();

        Position initial = new Position(0, 0);
        Position position = initial;
        Position firstTwice = null;

        Direction direction = Direction.N;

        positions.add(initial);
        for(String s : input) {
            int steps = Integer.parseInt(s.substring(1));
            direction = s.charAt(0) == 'R' ? direction.right() : direction.left();
            for (int i = 0; i < steps; i++) {
                position = direction.apply(position);
                if(firstTwice == null && positions.contains(position)) {
                    firstTwice = position;
                }
                positions.add(position);
            }
        }

        printAnswer(position.distanceTo(initial));
        printAnswer(firstTwice.distanceTo(initial));
    }

    public static void main(String... argv) {
        new Day01().run();
    }

    public static class Position {
        public final int x;
        public final int y;

        public Position(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if(o == null || !(o instanceof Position)) {
                return false;
            }

            Position other = (Position) o;

            return other.x == this.x && other.y == this.y;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(x) ^ Integer.hashCode(y);
        }

        public int distanceTo(Position other) {
            return Math.abs(other.x - x) + Math.abs(other.y - y);
        }
    }

    public enum Direction {
        N(0, 1),
        E(1, 0),
        S(0, -1),
        W(-1, 0);

        public final int x;
        public final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Direction left() {
            switch(this) {
                case N: return W;
                case E: return N;
                case S: return E;
                case W: return S;
                default: throw new IllegalStateException();
            }
        }

        public Direction right() {
            switch(this) {
                case N: return E;
                case E: return S;
                case S: return W;
                case W: return N;
                default: throw new IllegalStateException();
            }
        }

        public Position apply(Position position) {
            return new Position(position.x + x, position.y + y);
        }
    }
}
