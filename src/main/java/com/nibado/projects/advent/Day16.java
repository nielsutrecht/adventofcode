package com.nibado.projects.advent;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day16 implements Runnable {
    @Override
    public void run() {
        List<Sue> sues = readResource("/day16.txt").stream().map(Sue::of).collect(Collectors.toList());
        Sue target = new Sue();

        target.children = 3;
        target.cats = 7;
        target.samoyeds = 2;
        target.pomeranians = 3;
        target.akitas = 0;
        target.vizslas = 0;
        target.goldfish = 5;
        target.trees = 3;
        target.cars = 2;
        target.perfumes = 1;

        printAnswer(16, "One", sues.stream().filter(s -> s.matches(target, 1)).findFirst().get().num);
        printAnswer(16, "Two", sues.stream().filter(s -> s.matches(target, 2)).findFirst().get().num);
    }

    public static void main(String... argv) {
        new Day16().run();
    }

    public static class Sue {
        private int num;
        private int children = -1;
        private int cats = -1;
        private int samoyeds = -1;
        private int pomeranians = -1;
        private int akitas = -1;
        private int vizslas = -1;
        private int goldfish = -1;
        private int trees = -1;
        private int cars = -1;
        private int perfumes = -1;

        public static Sue of(String line) {
            String[] parts = line.split(":", 2);

            Sue sue = new Sue();
            sue.num = Integer.parseInt(parts[0].substring(4));

            for(String prop: parts[1].trim().split(",")) {
                parts = prop.split(": ");
                int amount = Integer.parseInt(parts[1]);

                switch(parts[0].trim()) {
                    case "children": sue.children = amount;break;
                    case "cats": sue.cats = amount;break;
                    case "samoyeds": sue.samoyeds = amount;break;
                    case "pomeranians": sue.pomeranians = amount;break;
                    case "akitas": sue.akitas = amount;break;
                    case "vizslas": sue.vizslas = amount;break;
                    case "goldfish": sue.goldfish = amount;break;
                    case "trees": sue.trees = amount;break;
                    case "cars": sue.cars = amount;break;
                    case "perfumes": sue.perfumes = amount;break;
                    default: throw new IllegalArgumentException("Unknown property: " + parts[0].trim());
                }
            }

            return sue;
        }

        public boolean matches(Sue other, int part) {
            return matches(other, s -> s.akitas) &&
                    matches(other, s -> s.cars) &&
                    matches(other, s -> s.cats, part == 1 ? Mode.EQ : Mode.GT) &&
                    matches(other, s -> s.children) &&
                    matches(other, s -> s.goldfish, part == 1 ? Mode.EQ : Mode.LT) &&
                    matches(other, s -> s.perfumes) &&
                    matches(other, s -> s.pomeranians, part == 1 ? Mode.EQ : Mode.LT) &&
                    matches(other, s -> s.samoyeds) &&
                    matches(other, s -> s.trees, part == 1 ? Mode.EQ : Mode.GT) &&
                    matches(other, s -> s.vizslas);
        }

        private boolean matches(Sue other, Function<Sue, Integer> property) {
            return matches(other, property, Mode.EQ);
        }

        private boolean matches(Sue other, Function<Sue, Integer> property, Mode mode) {
            if(property.apply(this) == -1 || property.apply(other) == -1) {
                return true;
            }
            switch(mode) {
                case EQ: return Objects.equals(property.apply(this), property.apply(other));
                case LT: return property.apply(this) < property.apply(other);
                case GT: return property.apply(this) > property.apply(other);
            }

            return false;
        }

        public enum Mode {
            EQ,
            LT,
            GT
        }
    }
}
