package com.nibado.projects.advent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nibado.projects.advent.y2015.AdventOfCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Pattern YEAR_DAY_CAPTURE = Pattern.compile("com.nibado.projects.advent.y([0-9]{4}).Day([0-9]{2})");

    private static Map<Integer, DayResult> results = new LinkedHashMap<>();
    private static long start = -1L;

    public static String readResourceAsString(String resource) {
        StringBuilder builder = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(AdventOfCode.class.getResourceAsStream(resource)))) {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }

    public static List<String> readResource(String resource) {
        String line;
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(AdventOfCode.class.getResourceAsStream(resource)))) {
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static JsonNode readResourceAsJson(String resource) {
        try {
            return new ObjectMapper().readTree(Util.class.getResourceAsStream(resource));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printAnswer(Object answer) {
        if (start == -1L) {
            start = System.currentTimeMillis();
        }
        int year;
        int day;
        //Dirty trick to find the year/day number of whoever is calling us.
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            Matcher m = YEAR_DAY_CAPTURE.matcher(e.getStackTrace()[1].getClassName());

            m.matches();
            year = Integer.parseInt(m.group(1));
            day = Integer.parseInt(m.group(2));
        }

        DayResult dayResult = getResult(day);
        dayResult.addAnswer(answer);
        int part = getResult(day).answers.size();

        System.out.printf(Locale.ROOT, "Day %2s-%s: %10s (%4s ms)\n", day, part, answer, dayResult.answerMs(part - 1));
    }

    private static DayResult getResult(int day) {
        if (!results.containsKey(day)) {
            results.put(day, new DayResult(day));
        }

        return results.get(day);
    }

    private static class DayResult {
        private final int day;
        private final List<Object> answers = new ArrayList<>(2);
        private final List<Long> times = new ArrayList<>(2);

        public DayResult(int day) {
            this.day = day;
        }

        public void addAnswer(Object answer) {
            answers.add(answer);
            times.add(System.currentTimeMillis());
        }

        public long answerMs(int i) {
            if (i > 0) {
                return times.get(i) - times.get(i - 1);
            } else {
                if (day == 1) {
                    return times.get(0) - start;
                } else {
                    DayResult prev = getResult(day - 1);
                    return times.get(0) - prev.times.get(prev.times.size() - 1);
                }
            }
        }
    }
}
