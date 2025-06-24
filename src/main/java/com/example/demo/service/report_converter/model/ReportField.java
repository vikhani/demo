package com.example.demo.service.report_converter.model;

public class ReportField<T> {
    private final String label; // can be null
    private final FieldSource<T> source;
    private final int column;

    public ReportField(String label, FieldSource<T> source, int column) {
        this.label = label;
        this.source = source;
        this.column = column;
    }

    public String render(T data) {
        String value = source.getValue(data);
        return label != null ? label + ": " + value : value;
    }

    public int getColumn() {
        return column;
    }
}
