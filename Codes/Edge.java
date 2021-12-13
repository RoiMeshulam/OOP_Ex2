import api.EdgeData;

public class Edge implements EdgeData {
    private int _src;
    private int _dest;
    private double _weight;
    private String _info;
    private int _tag;

    public Edge(int src,int dest , double weight, String info, int tag){
        _dest=dest;
        _src=src;
        _weight=weight;
        _info=info;
        _tag=tag;
    }
    public Edge (Edge e){
        _dest=e.getDest();
        _src=e.getSrc();
        _weight=e.getWeight();
        _info=getInfo();
        _tag=e.getTag();
    }

    @Override
    public int getSrc() {
        return _src;
    }

    @Override
    public int getDest() {
        return _dest;
    }

    @Override
    public double getWeight() {
        return _weight;
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
