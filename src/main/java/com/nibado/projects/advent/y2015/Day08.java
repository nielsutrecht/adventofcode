package com.nibado.projects.advent.y2015;

import java.util.List;
import java.util.stream.Collectors;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;

public class Day08 implements Runnable {
    @Override
    public void run() {
        List<String> list = readResource("/2015/day08.txt");

        int result1 = list.stream().collect(Collectors.summingInt(s -> s.length() - (unescape(s).length())));
        printAnswer(result1);

        int result2 = list.stream().collect(Collectors.summingInt(s -> escape(s).length() - s.length()));
        printAnswer(result2);
    }

    public String unescape(String input) {
        StringBuilder out = new StringBuilder(input.length());

        for(int i = 1;i < input.length() - 1;i++) {
            char c = input.charAt(i);
            if(c == '\\') {
                i++;
                char c2 = input.charAt(i);
                if(c2 == '\\' || c2 == '"') {
                    out.append(c2);
                }
                else if(c2 == 'x') {
                    i++;
                    c2 = (char)Integer.parseInt(input.substring(i, i + 2), 16);
                    out.append(c2);
                    i += 1;
                }
            }
            else {
                out.append(c);
            }
        }

        return out.toString();
    }

    public String escape(String input) {
        StringBuilder out = new StringBuilder(input.length() * 2);
        out.append('"');
        for(char c : input.toCharArray()) {
            if(c == '"' || c == '\\') {
                out.append('\\');
            }
            out.append(c);
        }

        out.append('"');

        return out.toString();
    }

    public static void main(String... argv) {
        new Day08().run();
    }
}
