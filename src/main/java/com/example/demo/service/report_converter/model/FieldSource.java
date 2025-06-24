package com.example.demo.service.report_converter.model;

public interface FieldSource<T> {
    String getValue(T data);
}
