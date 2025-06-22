package com.example.demo.mapper;

// пункт 4 делаем дженерик маппер, чтобы можно было подсовывать спрингу разные мапперы
public interface Mapper<D, E> {
    E toEntity(D dto);
}