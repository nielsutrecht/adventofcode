package com.nibado.projects.advent.y2015;


import java.util.Arrays;
import java.util.List;

public class AdventOfCode {
    public static void main(String... argv) {
        List<Runnable> solutions = Arrays.asList(
                new Day01J(),
                new Day02J(),
                new Day03J(),
                new Day04J(),
                new Day05J(),
                new Day06J(),
                new Day07J(),
                new Day08J(),
                new Day09J(),
                new Day10J(),
                new Day11J(),
                new Day12J(),
                new Day13J(),
                new Day14J(),
                new Day15J(),
                new Day16J());

        solutions.forEach(Runnable::run);
    }
}
