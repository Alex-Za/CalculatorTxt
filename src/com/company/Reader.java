package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private List<String> data;
    private String filePath;

    public Reader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getData() throws IOException {
        if (data == null)
            read(filePath);
        return data;
    }

    private void read(String fileName) throws IOException {
        BufferedReader br = null;
        List<String> sb = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            br = new BufferedReader(fileReader);

            String line = br.readLine();

            while (line != null) {
                sb.add(line);
                line = br.readLine();
            }

            String input = sb.toString();
            System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        data = sb;
    }
}
