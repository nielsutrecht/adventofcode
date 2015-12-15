package com.nibado.projects.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;
import static java.lang.Integer.parseInt;

public class Day15 implements Runnable {
    private static final Pattern PATTERN = Pattern.compile("(?<i>[A-Za-z]+): capacity (?<cap>[-0-9]+), durability (?<dur>[-0-9]+), flavor (?<fla>[-0-9]+), texture (?<tex>[-0-9]+), calories (?<cal>[-0-9]+)");
    @Override
    public void run() {
        List<Ingredient> ingredients = readResource("/day15.txt").stream().map(Ingredient::of).collect(Collectors.toList());

        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> counts = ingredients.stream().map(i -> 0).collect(Collectors.toList());

        combinations(100, 0, combinations, counts);

        int answer1 = combinations.stream().map(amounts -> sumAll(ingredients, amounts)).max(Integer::compare).get();
        printAnswer(15, "One", answer1);
        int answer2 = combinations.stream().filter(amounts -> sum(ingredients, amounts, i -> i.calories) == 500).map(amounts -> sumAll(ingredients, amounts)).max(Integer::compare).get();
        printAnswer(15, "Two", answer2);
    }

    public static int sumAll(List<Ingredient> list, List<Integer> amounts) {
        return sum(list, amounts, i -> i.capacity) * sum(list, amounts, i -> i.durability) *sum(list, amounts, i -> i.flavor) *sum(list, amounts, i -> i.texture);
    }

    public static int sum(List<Ingredient> list, List<Integer> amounts, Function<Ingredient, Integer> property) {
        int sum = 0;
        for(int i = 0;i < list.size();i++) {
            sum += property.apply(list.get(i)) * amounts.get(i);
        }

        return Math.max(sum, 0);
    }

    public void combinations(int target, int index, List<List<Integer>> results, List<Integer> list) {
        for(int i = 0;i <= target;i++) {
            List<Integer> newList = new ArrayList<>(list);
            newList.set(index, i);
            if(index < list.size() - 1) {
                combinations(target, index + 1, results, newList);
            }
            if(index == list.size() - 1 && newList.stream().collect(Collectors.summingInt(v -> v)) == target) {
                results.add(newList);
            }
        }
    }

    public static void main(String... argv) {
        new Day15().run();
    }

    public static class Ingredient {
        public final String name;
        public final int capacity;
        public final int durability;
        public final int flavor;
        public final int texture;
        public final int calories;

        public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        public static Ingredient of(String line) {
            Matcher m = PATTERN.matcher(line);
            if(!m.matches()) {
                throw new IllegalArgumentException("Doesn't match: " + line);
            }

            return new Ingredient(m.group("i"), parseInt(m.group("cap")), parseInt(m.group("dur")), parseInt(m.group("fla")), parseInt(m.group("tex")), parseInt(m.group("cal")));
        }
    }
}
