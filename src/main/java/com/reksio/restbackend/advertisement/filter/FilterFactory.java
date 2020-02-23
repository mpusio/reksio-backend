package com.reksio.restbackend.advertisement.filter;

import com.reksio.restbackend.advertisement.filter.type.*;
import com.reksio.restbackend.exception.filter.FilterNotFoundException;

public class FilterFactory {
    private FilterFactory(){ }

    public static Filter getFilter(String parameter, String value) {
        switch (parameter) {
            case "price":
                return new PriceFilter(value);
            case "category":
                return new CategoryFilter(value);
            case "gender":
                return new PetGenderFilter(value);
            case "type":
                return new PetTypeFilter(value);
            case "age":
                return new PetAgeFilter(value);
        }
        throw new FilterNotFoundException("Cannot find filter by parameter " + parameter);
    }
}
