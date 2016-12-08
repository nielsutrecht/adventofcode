package com.nibado.projects.advent.y2016;

import java.util.Arrays;
import java.util.List;

public class AdventOfCode {
    public static void main(String... argv) {
        List<Runnable> solutions = Arrays.asList(
                new Day01(),
                new Day02());

        solutions.forEach(Runnable::run);
    }
}
