package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {
    private Reader reader;
    private Parser parser;

    public Writer(Reader reader, Parser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public void write() {
        try (FileWriter fileWriter = new FileWriter("test2.txt")) {
            for (String s:reader.getData()) {
                if (s.isEmpty()) continue;
                List<String> expression = parser.parse(s);
                boolean flag = parser.flag;
                if (flag) {
                    for (String x : expression) System.out.print(x + " ");
                    System.out.println();
                    System.out.println(parser.calc(expression));
                    fileWriter.write(parser.calc(expression).toString() + "\n");
                } else {
                    fileWriter.write( expression.get(expression.size() - 1)+ "\n");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
