package com.reksio.restbackend.advertisement.location;

import com.google.maps.model.LatLng;

public class Calculator {

    private Calculator(){
    }

    public static double calculateStraightDistance(LatLng p1, LatLng p2){
        return Math.sqrt(
                Math.pow ((p2.lat - p1.lat), 2) +
                Math.pow ((p2.lng - p1.lng), 2));
    }
}
