package com.nibado.projects.advent.y2015;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;
import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;

public class Day14J implements Runnable {
    private static final Pattern INPUT = Pattern.compile("(?<n>[A-Za-z]+) can fly (?<s>[0-9]+) km/s for (?<d>[0-9]+) seconds, but then must rest for (?<r>[0-9]+) seconds\\.");
    private static final int SECONDS = 2503;

    @Override
    public void run() {
        List<Reindeer> reindeer = readResource("/2015/day14.txt").stream().map(Reindeer::of).collect(Collectors.toList());

        int winner1 = reindeer.stream().map(r -> new AbstractMap.SimpleEntry<>(r, r.simulate(SECONDS))).max((a, b) -> compare(a.getValue(), b.getValue())).get().getValue();
        printAnswer(winner1);

        List<AbstractMap.SimpleEntry<Reindeer, AtomicInteger>> scores = readResource("/2015/day14.txt").stream().map(Reindeer::of).map(r -> new AbstractMap.SimpleEntry<>(r, new AtomicInteger())).collect(Collectors.toList());

        for(int i = 0;i < SECONDS;i++) {
            scores.forEach(e -> e.getKey().tick());

            List<AbstractMap.SimpleEntry<Reindeer, AtomicInteger>> sorted = scores.stream().sorted((a, b) -> compare(b.getKey().distance, a.getKey().distance)).collect(Collectors.toList());

            for (AbstractMap.SimpleEntry<Reindeer, AtomicInteger> e : sorted) {
                if (e.getKey().distance != sorted.get(0).getKey().distance) {
                    break;
                }
                e.getValue().incrementAndGet();
            }
        }

        int winner2 = scores.stream().max((a, b) -> compare(a.getValue().get(), b.getValue().get())).get().getValue().get();
        printAnswer(winner2);
    }

    public static void main(String... argv) {
        new Day14J().run();
    }

    private static class Reindeer {
        public final String name;
        public final int speed;
        public final int duration;
        public final int rest;

        public int distance;
        private boolean resting;
        private int counter = Integer.MIN_VALUE;

        private Reindeer(String name, int speed, int duration, int rest) {
            this.name = name;
            this.speed = speed;
            this.duration = duration;
            this.rest = rest;
        }

        private static Reindeer of(String input) {
            Matcher m = INPUT.matcher(input);
            if(!m.matches()) {
                throw new IllegalArgumentException("Invalid input: " + input);
            }

            return new Reindeer(m.group("n"), parseInt(m.group("s")), parseInt(m.group("d")), parseInt(m.group("r")));
        }

        public void tick() {
            if(counter == Integer.MIN_VALUE) {
                resting = false;
                counter = duration;
            }

            counter--;
            distance += resting ? 0 : speed;
            if(counter <= 0) {
                resting = !resting;
                counter = resting ? rest: duration;
            }
        }

        public int simulate(int seconds) {
            for(int i = 0;i < seconds;i++) {
                tick();
            }

            return distance;
        }
    }
}
