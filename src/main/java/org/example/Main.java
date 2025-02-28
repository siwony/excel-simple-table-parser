package org.example;

import org.example.sample.SampleParser;
import org.example.sample.model.SampleRecord;

import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        final var parser = new SampleParser();

        List<SampleRecord> parse = parser.parse();
        for (SampleRecord sampleRecord : parse) {
            System.out.println(sampleRecord);
        }
    }
}