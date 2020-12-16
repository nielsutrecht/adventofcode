package com.nibado.projects.advent.y2015;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.readResource;

public class Day17J implements Runnable {
    @Override
    public void run() {
        List<Integer> containers = readResource("/2015/day17.txt").stream().map(Integer::parseInt).collect(Collectors.toList());

        List<List<Integer>> combinations = new ArrayList<>();
        //combinations(150, 0, combinations, )
    }

    public static List<List<Integer>> permutations(Collection<Integer> ints) {
        List<List<Integer>> permutations = new ArrayList<>();

        //permutations(new ArrayList<>(), new ArrayList<>(ints), permutations);

        return permutations;
    }

    public static void permutations(int target, int size, List<Integer> head, List<Integer> tail, List<List<Integer>> permutations) {
        if(tail.size() == 0) {
            permutations.add(head);
            return;
        }
        for(int i = 0;i < tail.size();i++) {
            List<Integer> newHead = new ArrayList<>(head.size() + 1);
            List<Integer> newTail = new ArrayList<>(tail);

            newHead.addAll(head);
            newHead.add(newTail.remove(i));


            //permutations(newHead, newTail, permutations);
        }
    }

    public void combinations(int target, int index, List<List<Integer>> results, List<Integer> list) {
        for(int i = 0;i <= target;i++) {
            List<Integer> newList = new ArrayList<>(list);
            newList.set(index, i);

            if(index < list.size() - 1 && newList.subList(0, index).stream().collect(Collectors.summingInt(v -> v)) <= target) {
                combinations(target, index + 1, results, newList);
            }
            if(index == list.size() - 1 && newList.stream().collect(Collectors.summingInt(v -> v)) == target) {
                results.add(newList);
            }
        }
    }

    public void main(String... argv) {
        new Day17J().run();
    }
}
