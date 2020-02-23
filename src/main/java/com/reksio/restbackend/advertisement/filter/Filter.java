package com.reksio.restbackend.advertisement.filter;

import com.reksio.restbackend.collection.advertisement.Advertisement;

import java.util.List;

public interface Filter {
    List<Advertisement> filter(List<Advertisement> advertisements);
}
