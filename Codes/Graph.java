import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Graph implements DirectedWeightedGraph {
    private HashMap<Integer,NodeData> _nodes;
    private HashMap<NodeData, ArrayList<EdgeData>> _edges;
    private final HashMap<NodeData,ArrayList<EdgeData>> _degree;
    private int _counterMC;
    private int _counterEdge;
    private int _counterNodes;

    public Graph(){
        _nodes = new HashMap<>();
        _edges= new HashMap<>();
        _degree= new HashMap<>();
        _counterEdge=0;
        _counterNodes=0;
        _counterMC=0;
    }
    public Graph(Graph gr){
        _counterEdge=gr.edgeSize();
        _counterNodes=gr.nodeSize();
        _counterMC=gr.getMC();
        this._nodes = new HashMap<>();
        this._edges = new HashMap<>();
        this._degree = new HashMap<>();
        Iterator<NodeData> iterN = gr.nodeIter();
        while(iterN.hasNext()){
            this.addNode(iterN.next());
        }
        Iterator<EdgeData> iterE = gr.edgeIter();
        while (iterE.hasNext()){
            this.connect(iterE.next().getSrc(),iterE.next().getDest(),iterE.next().getWeight());
        }
    }

    @Override
    public NodeData getNode(int key) {
        return _nodes.get(key) ;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        ArrayList<EdgeData> srcEdges = _edges.get(_nodes.get(src));
        for (int i=0;i<srcEdges.size();i++){
            if(srcEdges.get(i).getDest() == dest){
                return srcEdges.get(i);
            }
        }
        return null;
    }

    @Override
    public void addNode(NodeData n) {
        _nodes.put(n.getKey(), (Vertex) n);
        _edges.put((Vertex) _nodes.get(n.getKey()),new ArrayList<>());
        _degree.put((Vertex) _nodes.get(n.getKey()),new ArrayList<>());
        _counterMC++;
        _counterNodes++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge e = new Edge(src,dest,w,"",0);
        Edge re = new Edge(dest,src,w,"",0);
        _edges.get(_nodes.get(src)).add(e);
        _degree.get(_nodes.get(src)).add(e);
        _degree.get(_nodes.get(dest)).add(e);
        _counterEdge++;
        _counterMC++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return _nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        ArrayList<EdgeData> allEdges = new ArrayList<>();
        for (int i = 0; i < _counterNodes; i++) {
            Vertex v = (Vertex) _nodes.get(i);
            if(v==null){continue;}
            for (int j=0;j<_edges.get(v).size();j++){
                allEdges.add((Edge) _edges.get(v).get(j));
            }
        }
        return allEdges.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return _edges.get(_nodes.get(node_id)).iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        Vertex v = (Vertex) this.getNode(key);
        ArrayList<EdgeData> removeList = _degree.get(v);
        for (int i = 0; i < removeList.size(); i++) {
            if(removeList.get(i).getDest()==key){
                removeEdge(removeList.get(i).getSrc(),key);
            }
        }
        _nodes.remove(key);
        _degree.remove(v);
        _edges.remove(v);
        _counterMC++;
        _counterNodes--;
        return v;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData t = null;
        if(this.getNode(src)==null){
            return null;
        }
        if(this.getNode(dest)==null){
            return null;
        }
        int loc=0;
        ArrayList<EdgeData> temp=_edges.get(this.getNode(src));
        for (int i=0; i< temp.size();i++){
            if (temp.get(i).getDest()==dest){
                loc=i;
                break;
            }
        }
        t=temp.get(loc);
        _edges.get(this.getNode(src)).remove(loc);
        _degree.remove(_nodes.get(src),t);
        _degree.remove(_nodes.get(dest),t);
        _counterMC++;
        _counterEdge--;

        return t;
    }

    @Override
    public int nodeSize() {
        return _counterNodes;
    }

    @Override
    public int edgeSize() {
        return _counterEdge;
    }

    @Override
    public int getMC() {
        return _counterMC;
    }
    public Graph getReverseGraph(){
        Graph rg = new Graph();
        Iterator nodeItr = this.nodeIter();
        while(nodeItr.hasNext()){
            rg.addNode((NodeData) nodeItr.next());
        }
        nodeItr=this.nodeIter();
        while (nodeItr.hasNext()){
            Vertex curr = (Vertex) nodeItr.next();
            Iterator currEdges = this.edgeIter(curr.getKey());
            while (currEdges.hasNext()){
                Edge e = (Edge) currEdges.next();
                rg.connect(e.getDest(),e.getSrc(),e.getWeight());
            }
        }
        return rg;
    }
}