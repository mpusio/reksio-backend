package com.reksio.restbackend.advertisement.filter;

import com.reksio.restbackend.advertisement.filter.type.CategoryFilter;
import com.reksio.restbackend.advertisement.filter.type.PriceFilter;
import com.reksio.restbackend.exception.filter.FilterNotFoundException;

public class FilterFactory {
    private FilterFactory(){ }

    public static Filter getFilter(String parameter, String value) {
        if (parameter.equals("price"))
            return new PriceFilter(value);
        else if (parameter.equals("category"))
            return new CategoryFilter(value);

        throw new FilterNotFoundException("Cannot find filter. ");
    }
}
