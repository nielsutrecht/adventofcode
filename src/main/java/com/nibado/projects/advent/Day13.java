package com.nibado.projects.advent;

import java.util.*;
import java.util.regex.Pattern;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day13 implements Runnable {
    private static final Pattern INPUT = Pattern.compile("(?<n1>[A-Za-z]+) would (?<lg>lose|gain) (?<h>[0-9]+) happiness units by sitting next to (?<n2>[A-Za-z]+)\\.");

    @Override
    public void run() {
        Set<String> names = new HashSet<>();
        Map<String, Map<String, Integer>> happiness = new HashMap<>();

        readResource("/day13.txt").stream().map(INPUT::matcher).forEach(m -> {
            if(!m.matches()) {
                throw new RuntimeException("Line in input doesn't match");
            }
            int value = Integer.parseInt(m.group("h"));
            value *= m.group("lg").equals("lose") ? -1 : 1;

            names.add(m.group("n1"));
            names.add(m.group("n2"));

            if(!happiness.containsKey(m.group("n1"))) {
                happiness.put(m.group("n1"), new HashMap<>());
            }

            happiness.get(m.group("n1")).put(m.group("n2"), value);
        });

        List<List<String>> permutations = Day09.permutations(names);

        printAnswer(13, "One", permutations.stream().map(l -> happiness(happiness, l)).max(Integer::compare).get());

        names.add("FooBob");

        permutations = Day09.permutations(names);

        printAnswer(13, "Two", permutations.stream().map(l -> happiness(happiness, l)).max(Integer::compare).get());

    }

    private int happiness(Map<String, Map<String, Integer>> happiness, List<String> names) {
        int sum = 0;
        for(int i = 0;i < names.size();i++) {
            int i2 = i == names.size() - 1 ? 0 : i + 1;

            sum += happiness(happiness, names.get(i), names.get(i2));
            sum += happiness(happiness, names.get(i2), names.get(i));
        }

        return sum;
    }

    private int happiness(Map<String, Map<String, Integer>> happiness, String n1, String n2) {
        if(!happiness.containsKey(n1) || !happiness.get(n1).containsKey(n2)) {
            return 0;
        }

        return happiness.get(n1).get(n2);
    }

    public static void main(String... argv) {
        new Day13().run();
    }
}
