package com.nibado.projects.advent;


import java.util.Arrays;
import java.util.List;

public class AdventOfCode {
    public static void main(String... argv) {
        List<Runnable> solutions = Arrays.asList(new Day01(), new Day02(), new Day03(), new Day04(), new Day05(), new Day06(), new Day07(), new Day08(), new Day09(), new Day10());

        solutions.forEach(Runnable::run);
    }
}
