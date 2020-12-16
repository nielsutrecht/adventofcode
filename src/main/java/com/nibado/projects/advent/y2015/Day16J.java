package com.nibado.projects.advent.y2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day16J implements Runnable {
    @Override
    public void run() {
        List<Sue> sues = readResource("/2015/day16.txt").stream().map(Sue::of).collect(Collectors.toList());
        Sue target = new Sue().add("children", 3).add("cats", 7).add("samoyeds", 2).add("pomeranians", 3).add("akitas", 0).add("vizslas", 0).add("goldfish", 5).add("trees", 3).add("cars", 2).add("perfumes", 1);

        printAnswer(sues.stream().filter(s -> s.matches(target, 1)).findFirst().get().num);
        printAnswer(sues.stream().filter(s -> s.matches(target, 2)).findFirst().get().num);
    }

    public static void main(String... argv) {
        new Day16J().run();
    }

    public static class Sue {
        private int num;
        private final Map<String, Integer> properties = new HashMap<>();

        public static Sue of(String line) {
            String[] parts = line.split(":", 2);

            Sue sue = new Sue();
            sue.num = Integer.parseInt(parts[0].substring(4));

            for(String prop: parts[1].trim().split(",")) {
                parts = prop.split(": ");
                sue.add(parts[0].trim(), Integer.parseInt(parts[1]));
            }

            return sue;
        }

        public Sue add(String property, int value) {
            properties.put(property, value);
            return this;
        }

        public int get(String property) {
            return properties.getOrDefault(property, -1);
        }

        public boolean matches(Sue other, int part) {
            return matches(other, "akitas", Mode.EQ) &&
                    matches(other, "cars", Mode.EQ) &&
                    matches(other, "cats", part == 1 ? Mode.EQ : Mode.GT) &&
                    matches(other, "children", Mode.EQ) &&
                    matches(other, "goldfish", part == 1 ? Mode.EQ : Mode.LT) &&
                    matches(other, "perfumes", Mode.EQ) &&
                    matches(other, "pomeranians", part == 1 ? Mode.EQ : Mode.LT) &&
                    matches(other, "samoyeds", Mode.EQ) &&
                    matches(other, "trees", part == 1 ? Mode.EQ : Mode.GT) &&
                    matches(other, "vizslas", Mode.EQ);
        }

        private boolean matches(Sue other, String property, Mode mode) {
            if(get(property) == -1 || other.get(property) == -1) {
                return true;
            }
            switch(mode) {
                case EQ: return get(property) == other.get(property);
                case LT: return get(property) < other.get(property);
                case GT: return get(property) > other.get(property);
                default: throw new IllegalStateException("Should not happen");
            }
        }

        public enum Mode {
            EQ,
            LT,
            GT
        }
    }
}
