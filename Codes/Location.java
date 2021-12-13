import api.GeoLocation;

import java.text.DecimalFormat;

public class Location implements GeoLocation {
    private double _x;
    private double _y;
    private double _z;

    public Location(double x, double y, double z){
        _x=x;
        _y=y;
        _z=z;
    }
    public Location(Location l){
        _x=l.x();
        _y=l.y();
        _z=l.z();
    }


    public void setLocation (GeoLocation location){
        _x= location.x();
        _y= location.y();
        _z= location.z();
    }

    @Override
    public double x() {
        return _x;
    }

    @Override
    public double y() {
        return _y;
    }

    @Override
    public double z() {
        return _z;
    }

    @Override
    public double distance(GeoLocation g) {
        double dx= _x - g.x();
        double dy= _y - g.y();
        double dz= _z - g.z();
        double dist = (dx*dx)+(dy*dy)+(dz*dz);
        dist= Math.sqrt(dist);
        return dist;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#####");
        String x= df.format(this._x);
        String y= df.format(this._y);
        return "("+x+","+y+")";
    }
}
