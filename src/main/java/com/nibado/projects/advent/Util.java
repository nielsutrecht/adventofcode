package com.nibado.projects.advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {
    public static String readResourceAsString(String resource) {
        StringBuilder builder = new StringBuilder();
        String line;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(AdventOfCode.class.getResourceAsStream(resource)))) {
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }

    public static List<String> readResource(String resource) {
        String line;
        List<String> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(AdventOfCode.class.getResourceAsStream(resource)))) {
            while((line = reader.readLine()) != null) {
                list.add(line);
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static void printAnswer(int day, String sub, Object answer) {
        System.out.printf(Locale.ROOT, "Day %s / %s: %s\n", day, sub, answer);
    }
}
