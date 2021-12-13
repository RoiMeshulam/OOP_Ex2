import api.GeoLocation;
import api.NodeData;

public class Vertex implements NodeData {
    private int _key;
    private Location _location;
    private double _weight;
    private int _tag;
    private String _info;
    private double _wSoFar;
    private Vertex _father;

    public Vertex (int key, Location location, double weight, int tag, String info ){
        _key=key;
        _location = new Location(location);
        _weight= weight;
        _tag= tag;
        _info=info;
        _wSoFar = Integer.MAX_VALUE;
        _father = null;

    }
    public Vertex(Vertex n){
        _key= n.getKey();
        _weight=n.getWeight();
        _tag=n.getTag();
        _info=n.getInfo();
        _location= new Location((Location) n.getLocation());
        _wSoFar = n.get_wSoFar();
        _father = n.get_father();
    }

    public double get_wSoFar(){return _wSoFar;}
    public double get_wSoFarOp(){return (-1)*_wSoFar;}
    public Vertex get_father(){return _father;}
    public void set_wSoFar(double x){this._wSoFar=x;}
    public void set_father(Vertex e){this._father = e;}

    @Override
    public int getKey() {
        return _key;
    }

    @Override
    public GeoLocation getLocation() {
        return _location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        _location.setLocation(p);
    }

    @Override
    public double getWeight() {
        return _weight;
    }

    @Override
    public void setWeight(double w) {
        _weight= w;
    }

    @Override
    public String getInfo() {
        return _info;
    }

    @Override
    public void setInfo(String s) {
        _info=s;
    }

    @Override
    public int getTag() {
        return _tag;
    }

    @Override
    public void setTag(int t) {
        _tag=t;
    }
}