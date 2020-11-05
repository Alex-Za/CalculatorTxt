package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Reader reader = new Reader("test.txt");
        Parser parser = new Parser();
        Writer writer = new Writer(reader, parser);

        writer.write();

    }
}
