package com.example.demo.service.report_converter.model;

import java.util.*;

public class ReportSection<T> {
    private final String title;
    private final List<ReportLine<T>> lines = new ArrayList<>();
    private final Map<String, ReportLine<T>> byKey = new HashMap<>();
    public ReportSection(String title) {
        this.title = title;
    }

    public void addLine(ReportLine<T> line) {
        lines.add(line);
        if (line.getKey() != null) {
            byKey.put(line.getKey(), line);
        }
    }

    public ReportLine<T> getLine(String key) {
        ReportLine<T> line = byKey.get(key);
        if (line == null) throw new IllegalArgumentException("No line: " + key);
        return line;
    }

    public List<String> render(T data, int width) {
        List<String> result = new ArrayList<>();
        result.add("********* " + title + " *********");
        for (ReportLine<T> line : lines) {
            result.add(line.render(data, width));
        }
        return result;
    }
}
