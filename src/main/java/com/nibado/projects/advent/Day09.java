package com.nibado.projects.advent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day09 implements Runnable {
    private static final Pattern PATTERN = Pattern.compile("([A-Za-z]+) to ([A-Za-z]+) = ([0-9]+)");
    private Map<String, Map<String, Integer>> distances = new HashMap<>();
    private Set<String> cities = new HashSet<>();

    @Override
    public void run() {
        readResource("/day09.txt").forEach(s -> {
            Matcher m = PATTERN.matcher(s);
            if(!m.matches()) {
                System.out.println("No match:" + s);
            }

            if(!distances.containsKey(m.group(1))) {
                distances.put(m.group(1), new HashMap<>());
            }
            distances.get(m.group(1)).put(m.group(2), Integer.parseInt(m.group(3)));
            cities.add(m.group(1));
            cities.add(m.group(2));
        });

        Set<Integer> distances = permutations(cities).stream().map(this::distance).collect(Collectors.toSet());

        printAnswer(distances.stream().min(Integer::compare).get());
        printAnswer(distances.stream().max(Integer::compare).get());
    }

    private int distance(List<String> names) {
        int distance = 0;
        for(int i = 0;i < names.size() - 1;i++) {
            String n1 = names.get(i);
            String n2 = names.get(i + 1);

            if(distances.containsKey(n1) && distances.get(n1).containsKey(n2)) {
                distance += distances.get(n1).get(n2);
            }
            else {
                distance += distances.get(n2).get(n1);
            }
        }

        return distance;
    }

    public static List<List<String>> permutations(Collection<String> names) {
        List<List<String>> permutations = new ArrayList<>();

        permutations(new ArrayList<>(), new ArrayList<>(names), permutations);

        return permutations;
    }

    public static void permutations(List<String> head, List<String> tail, List<List<String>> permutations) {
        if(tail.size() == 0) {
            permutations.add(head);
            return;
        }
        for(int i = 0;i < tail.size();i++) {
            List<String> newHead = new ArrayList<>(head.size() + 1);
            List<String> newTail = new ArrayList<>(tail);

            newHead.addAll(head);
            newHead.add(newTail.remove(i));

            permutations(newHead, newTail, permutations);
        }
    }

    public static void main(String... argv) {
        new Day09().run();
    }

}
