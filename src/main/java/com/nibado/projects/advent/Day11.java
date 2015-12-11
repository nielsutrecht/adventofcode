package com.nibado.projects.advent;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResourceAsString;

public class Day11 implements Runnable {
    private static final char[] ILLEGAL = "ilo".toCharArray();
    @Override
    public void run() {
        char[] password = readResourceAsString("/day11.txt").toCharArray();

        nextValid(password);
        printAnswer(11, "One", new String(password));
        nextValid(password);
        printAnswer(11, "Two", new String(password));
    }

    public static void nextValid(char[] password) {
        do {
            increment(password);
        }
        while(!hasStraight(password, 3) || containsIllegal(password) || countPairs(password) < 2);
    }

    public static void increment(char[] password) {
        for(int i = password.length - 1;i >= 0;i--) {
            password[i]++;
            if(password[i] > 'z') {
                password[i] = 'a';
            }
            else {
                break;
            }
        }
    }

    public static boolean hasStraight(char[] password, int length) {
        for(int i = 0;i < password.length - length;i++) {
            int count = 0;
            for(int j = 0;j < length;j++) {
                if(password[i + j] == password[i] + (char)j) {
                    count ++;
                }
            }
            if(count == length) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsIllegal(char[] password) {
        for(int i = 0;i < password.length;i++) {
            for(int j = 0;j < ILLEGAL.length;j++) {
                if(password[i] == ILLEGAL[j]) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int countPairs(char[] password) {
        int count = 0;
        for(int i = 0;i < password.length - 1;i++) {
            if(password[i] == password[i + 1]) {
                count++;
                i++;
            }
        }

        return count;
    }

    public static void main(String... argv) {
        new Day11().run();
    }
}
