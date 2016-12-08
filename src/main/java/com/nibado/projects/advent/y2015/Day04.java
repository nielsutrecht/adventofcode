package com.nibado.projects.advent.y2015;

import java.security.MessageDigest;

import static com.nibado.projects.advent.Util.printAnswer;

public class Day04 implements Runnable {
    private MessageDigest md5;
    private byte[] input;

    public void setInput(String input) {
        try {
            this.input = input.getBytes("UTF-8");
            md5 = MessageDigest.getInstance("md5");
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... argv) throws Exception {
        new Day04().run();
    }

    public int find(int zeroes) {
        for(int i = 0;i < Integer.MAX_VALUE;i++) {
            byte[] md5 = digest(i);

            for(int j = 0;j < zeroes;j++) {
                int mask = j % 2 == 0 ? 0xF0 : 0x0F;
                if((md5[j / 2] & mask) != 0) {
                    break;
                }

                if(j == zeroes - 1) {
                    return i;
                }
            }
        }

        return -1;
    }

    public byte[] digest(int i) {
        md5.update(input);
        return md5.digest(Integer.toString(i).getBytes());
    }

    @Override
    public void run() {
        setInput("iwrupvqb");
        int result = find(5);
        printAnswer(result);

        result =  find(6);
        printAnswer(result);
    }
}
