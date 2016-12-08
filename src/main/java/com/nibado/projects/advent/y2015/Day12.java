package com.nibado.projects.advent.y2015;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResourceAsJson;

public class Day12 implements Runnable {

    @Override
    public void run() {
        JsonNode input = readResourceAsJson("/2015/day12.txt");

        printAnswer(sumTree(input, n -> false));
        printAnswer(sumTree(input, n -> hasValue(n, "red")));
    }

    public int sumTree(JsonNode node, Function<JsonNode, Boolean> filter) {
        if(node.isInt()) {
            return node.asInt();
        }
        if(filter.apply(node)) {
            return 0;
        }
        final AtomicInteger sum = new AtomicInteger(0);

        node.forEach(n -> sum.addAndGet(sumTree(n, filter)));

        return sum.get();
    }

    public boolean hasValue(JsonNode node, String value) {
        Iterator<JsonNode> iter = node.iterator();

        if(!node.isObject()) {
            return false;
        }
        while(iter.hasNext()) {
            if(value.equals(iter.next().asText())) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] argv) {
        new Day12().run();
    }
}
