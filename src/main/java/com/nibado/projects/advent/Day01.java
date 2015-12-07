package com.nibado.projects.advent;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResourceAsString;

public class Day01 implements Runnable {

    public static void main(String... argv) {
        new Day01().run();
    }

    @Override
    public void run() {
        String input = readResourceAsString("/day01.txt");

        int floor = 0;
        for(int i = 0; i < input.length();i++) {
            if(input.charAt(i) == '(') {
                floor++;
            }
            else if(input.charAt(i) == ')') {
                floor--;
            }
        }

        printAnswer(1, "One", floor);

        int i;
        floor = 0;
        for(i = 0; i < input.length();i++) {
            if(input.charAt(i) == '(') {
                floor++;
            }
            else if(input.charAt(i) == ')') {
                floor--;
            }
            if(floor < 0) {
                break;
            }
        }

        printAnswer(1, "Two", i + 1);
    }
}
