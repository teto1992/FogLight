/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.utils;

public class Coordinates {
    private double lat, lng;

    public Coordinates (double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }
    
    public double distance (Coordinates coords){
        final int R = 6371; // Radius of the earth
        
        double lat1 = this.lat;
        double lat2 = coords.getLatitude();
        double lon1 = this.lng;
        double lon2 = coords.getLongitude();
        
        Double latDistance = Math.toRadians(lat1- lat2);
        Double lonDistance = Math.toRadians(lon1 - lon2);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    private double getLatitude() {
        return this.lat;
    }

    private double getLongitude() {
        return this.lng;
    }
}
