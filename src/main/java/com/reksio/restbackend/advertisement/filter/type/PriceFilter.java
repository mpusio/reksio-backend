package com.reksio.restbackend.advertisement.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.collection.advertisement.Advertisement;

import java.util.List;
import java.util.stream.Collectors;

public class PriceFilter implements Filter {

    private final String value; //possible values: 100, <100, >100,

    public PriceFilter(String value) {
        this.value = value;
    }

    @Override
    public List<Advertisement> filter(List<Advertisement> advertisements) {
        return advertisements.stream()
                .filter(ad -> {

                    if (value.charAt(0)=='<'){
                        int price = Integer.parseInt(value.substring(1));
                        return ad.getPrice() > price;
                    }
                    else if (value.charAt(0)=='>'){
                        int price = Integer.parseInt(value.substring(1));
                        return ad.getPrice() < price;
                    }
                    else return value.equals(String.valueOf(ad.getPrice()));
                })
                .collect(Collectors.toList());
    }
}
