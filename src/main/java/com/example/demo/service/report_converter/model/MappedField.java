package com.example.demo.service.report_converter.model;

import java.util.function.Function;

public class MappedField<T> implements FieldSource<T> {
    private final Function<T, ?> getter;

    public MappedField(Function<T, ?> getter) {
        this.getter = getter;
    }

    @Override
    public String getValue(T data) {
        Object result = getter.apply(data);
        return result != null ? result.toString() : "";
    }
}
