package com.reksio.restbackend.advertisement.filter;

import com.reksio.restbackend.collection.advertisement.Advertisement;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    private List<Advertisement> advertisements;

    private List<Filter> filters = new ArrayList<>();

    private int counter = 0;

    private FilterChain(){ }

    public FilterChain(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public void addFilter(Filter filter){
        this.filters.add(filter);
    }

    public void addFilters(List<Filter> filters){
        this.filters.addAll(filters);
    }

    public FilterChain runFilters(){
        while (counter != filters.size()){
            advertisements = filters.get(counter).filter(advertisements);
            counter++;
        }
        return this;
    }

    public List<Advertisement> getAdvertisements(){
        return advertisements;
    }
}