package com.nibado.projects.advent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nibado.projects.advent.Util.printAnswer;
import static com.nibado.projects.advent.Util.readResource;


public class Day07 implements Runnable {
    private static final Pattern GATE = Pattern.compile("^((?<w1>[0-9a-z]{1,3}) )?((?<gate>AND|OR|NOT|RSHIFT|LSHIFT) )?(?<w2>[0-9a-z]{1,5}) -> (?<wO>[a-z]{1,2})$");
    private static final Pattern LITERAL = Pattern.compile("[0-9]{1,5}");

    public static void main(String... argv) {
        new Day07().run();
    }

    @Override
    public void run() {
        List<String> input = readResource("/day07.txt");

        GateFactory factory = new GateFactory();
        input.stream().forEach(factory::add);

        int value = factory.get("a").solve();
        printAnswer(value);

        factory = new GateFactory();
        input.stream().forEach(factory::add);

        factory.get("b").value = value;
        printAnswer(factory.get("a").solve());
    }

    public static class GateFactory {
        private Map<String, Wire> wires = new HashMap<>();

        public Wire get(String wireId) {
            if(wireId == null) {
                return null;
            }
            else if(LITERAL.matcher(wireId).matches()) {
                Wire w = new Wire();
                w.value = Integer.parseInt(wireId);

                return w;
            }
            if(!wires.containsKey(wireId)) {
                wires.put(wireId, new Wire());
            }

            return wires.get(wireId);
        }

        public void add(String line) {
            Matcher m = GATE.matcher(line);
            if(!m.matches()) {
                throw new IllegalArgumentException(line + " doesn't match gate regex");
            }

            Wire wireOut = get(m.group("wO"));

            wireOut.gate = new Gate();
            wireOut.gate.type = m.group("gate");
            wireOut.gate.wire1 = get(m.group("w1"));
            wireOut.gate.wire2 = get(m.group("w2"));
        }
    }

    public static class Wire {
        private int value = -1;
        private Gate gate = null;

        public int solve() {
            if(value < 0) {
                value = gate.solve();
            }

            return value;
        }
    }

    public static class Gate {
        private String type;
        private Wire wire1;
        private Wire wire2;

        public int solve() {
            if(type == null) {
                return wire2.solve();
            }
            switch(type) {
                case "AND": return wire1.solve() & wire2.solve();
                case "OR": return wire1.solve() | wire2.solve();
                case "NOT": return ~wire2.solve() & 0xFFFF;
                case "LSHIFT": return wire1.solve() << wire2.solve();
                case "RSHIFT": return wire1.solve() >> wire2.solve();
                default: throw new IllegalArgumentException("Unknown gate: " + type);
            }
        }
    }
}
