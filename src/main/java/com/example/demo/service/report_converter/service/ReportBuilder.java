package com.example.demo.service.report_converter.service;

import com.example.demo.service.report_converter.model.ReportSection;

import java.util.*;

public class ReportBuilder<T> {
    private final List<ReportSection<T>> sections = new ArrayList<>();

    public void addSection(ReportSection<T> section) {
        sections.add(section);
    }

    public List<String> build(T data) {
        List<String> output = new ArrayList<>();
        for (ReportSection<T> section : sections) {
            output.addAll(section.render(data, 40));
            output.add(""); // spacing
        }
        return output;
    }
}
