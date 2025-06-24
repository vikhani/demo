package com.example.demo.service.report_converter.model;

public class StaticField<T> implements FieldSource<T> {
    private final String value;

    public StaticField(String value) {
        this.value = value;
    }

    @Override
    public String getValue(T data) {
        return value;
    }
}
