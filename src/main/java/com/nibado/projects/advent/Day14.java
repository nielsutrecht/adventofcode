package com.nibado.projects.advent;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;
import static java.lang.Integer.parseInt;

public class Day14 implements Runnable {
    private static final Pattern INPUT = Pattern.compile("(?<n>[A-Za-z]+) can fly (?<s>[0-9]+) km/s for (?<d>[0-9]+) seconds, but then must rest for (?<r>[0-9]+) seconds\\.");

    @Override
    public void run() {
        List<Reindeer> reindeer = readResource("/day14.txt").stream().map(Reindeer::of).collect(Collectors.toList());

        Reindeer comet = new Reindeer("Comet", 14, 10, 127);

        for(int i = 0;i < 1000;i++) {
            comet.tick();
        }

        //System.out.println(comet.getDistance());

        int winner1 = reindeer.stream().map(r -> new AbstractMap.SimpleEntry<>(r, r.simulate2(2503))).max((a, b) -> Integer.compare(a.getValue(), b.getValue())).get().getValue();
        printAnswer(14, "One", winner1); //2660
    }

    public static void main(String... argv) {
        new Day14().run();
    }

    private static class Reindeer {
        public final String name;
        public final int speed;
        public final int duration;
        public final int rest;

        private int distance;
        private boolean resting;
        private int counter = Integer.MIN_VALUE;
        private int second = 0;

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
            second++;
            if(counter == Integer.MIN_VALUE) {
                resting = false;
                counter = duration;
            }

            counter--;
            distance += resting ? 0 : speed;
            if(counter <= 0) {
                System.out.printf("%4s %8s switch\n", second, name);
                resting = !resting;
                counter = resting ? rest: duration;
            }



            System.out.printf("%4s %8s%1s %s\n", second, name, resting ? " " : "+", distance);
        }

        public int getDistance() {
            return distance;
        }

        public int simulate2(int seconds) {
            for(int i = 0;i < seconds;i++) {
                tick();
            }

            return distance;
        }

        public int simulate(int seconds) {
            int distance = 0;
            int dur = duration;
            boolean resting = false;
            for(int i = 1;i <= seconds;) {
                dur--;
                if(dur >= 0) {
                    distance += resting ? 0 : speed;
                    i++;
                }
                else {
                    resting = !resting;
                    dur = resting ? rest: duration;
                }
            }

            return distance;
        }
    }
}
