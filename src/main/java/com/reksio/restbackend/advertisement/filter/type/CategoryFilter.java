package com.reksio.restbackend.advertisement.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.collection.advertisement.Advertisement;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements Filter {

    private final String value;

    public CategoryFilter(String value) {
        this.value = value;
    }

    @Override
    public List<Advertisement> filter(List<Advertisement> advertisements) {
        return advertisements.stream()
                .filter(ad -> ad.getCategory().name().equalsIgnoreCase(value))
                .collect(Collectors.toList());
    }
}
