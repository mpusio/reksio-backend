package com.reksio.restbackend.advertisement.location;

import com.google.maps.model.LatLng;
import com.reksio.restbackend.collection.advertisement.Advertisement;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Advertisement> {

    private final LatLng waypoint;

    public DistanceComparator(LatLng waypoint) {
        this.waypoint = waypoint;
    }

    @Override
    public int compare(Advertisement advertisement, Advertisement t1) {
        if (waypoint == null) {
            return 0;
        }

        double x1 = advertisement.getAddress().getLat();
        double y1 = advertisement.getAddress().getLng();

        LatLng point1 = new LatLng(x1, y1);

        double x2 = t1.getAddress().getLat();
        double y2 = t1.getAddress().getLng();

        LatLng point2 = new LatLng(x2, y2);

        double distance1 = calculateStraightDistance(waypoint, point1);
        double distance2 = calculateStraightDistance(waypoint, point2);

        return Double.compare(distance1, distance2);
    }

    private double calculateStraightDistance(LatLng p1, LatLng p2){
        return Math.sqrt(
                Math.pow ((p2.lat - p1.lat), 2) +
                        Math.pow ((p2.lng - p1.lng), 2));
    }
}
