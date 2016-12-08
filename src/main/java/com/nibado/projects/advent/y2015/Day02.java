package com.nibado.projects.advent.y2015;

import java.util.List;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day02 implements Runnable {
    @Override
    public void run() {
        List<Dimension> dimensions = readResource("/2015/day02.txt")
                .stream()
                .map(Dimension::of)
                .collect(Collectors.toList());

        int surfaceSum =
                dimensions.stream().collect(Collectors.summingInt(Day02::surfaceArea));

        printAnswer(surfaceSum);

        int ribbonSum = dimensions.stream().collect(Collectors.summingInt(Day02::ribbonLength));

        printAnswer(ribbonSum);
    }

    public static void main(String... argv) {
        new Day02().run();
    }

    private static int surfaceArea(Dimension dim) {
        return surfaceArea(dim.h, dim.l, dim.w);
    }

    private static int surfaceArea(int h, int l, int w) {
        int side1 = l * w;
        int side2 = w * h;
        int side3 = h * l;

        int min = Math.min(side1, Math.min(side2, side3));


        return 2 * side1 + 2 * side2 + 2 * side3 + min;
    }

    private static int ribbonLength(Dimension dim) {
        return ribbonLength(dim.h, dim.l, dim.w);
    }

    private static int ribbonLength(int h, int l, int w) {
        int side1 = l + l + w + w;
        int side2 = w + w + h + h;
        int side3 = h + h + l + l;

        int volume = h * l * w;

        return Math.min(side1, Math.min(side2, side3)) + volume;
    }

    private static class Dimension {
        private int h;
        private int l;
        private int w;

        private static Dimension of(String line) {
            String[] parts = line.split("x");

            Dimension dim = new Dimension();

            dim.l = Integer.parseInt(parts[0]);
            dim.w = Integer.parseInt(parts[1]);
            dim.h = Integer.parseInt(parts[2]);

            return dim;
        }
    }
}
