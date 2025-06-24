package com.example.demo.service.report_converter.model;

import lombok.Getter;

import java.util.*;

@Getter
public class ReportLine<T> {
    private final String key;
    private final List<ReportField<T>> fields = new ArrayList<>();
    public ReportLine(String key) {
        this.key = key;
    }
    public void addField(ReportField<T> field) {
        fields.add(field);
    }

    public String render(T data, int width) {
        fields.sort(Comparator.comparingInt(ReportField::getColumn));
        StringBuilder line = new StringBuilder();
        int currentPos = 0;

        for (ReportField<T> field : fields) {
            while (currentPos < field.getColumn()) {
                line.append(" ");
                currentPos++;
            }
            String part = field.render(data);
            line.append(part);
            currentPos += part.length();
        }

        return line.toString();
    }
}
