import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private Graph g;



    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = (Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Graph h = new Graph(this.g);
        return h;
    }

    @Override
    public boolean isConnected() {
        Iterator<NodeData> nodes = this.g.nodeIter();
        Vertex root = (Vertex) nodes.next();
        int relevantroot = root.getKey();
        BFS(root, this.g);
        while (nodes.hasNext()) {
            if (nodes.next().getTag() != 2) {
                return false;
            }
        }
        Graph revGraph = this.g.getReverseGraph();
        Vertex revRoot = (Vertex) revGraph.getNode(relevantroot);
        BFS(revRoot, revGraph);
        while (nodes.hasNext()) {
            if (nodes.next().getTag() != 2) {
                return false;
            }
        }
        return true;
    }

    private void relax(Edge e) {
        Vertex src = (Vertex) this.g.getNode(e.getSrc());
        Vertex dest = (Vertex) this.g.getNode(e.getDest());
        if (dest.get_wSoFar() > src.get_wSoFar() + e.getWeight()) {
            dest.set_wSoFar(src.get_wSoFar() + e.getWeight());
            dest.set_father(src);
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) {
            return -1;
        }
        Iterator itr = this.g.nodeIter();
        while (itr.hasNext()) {
            Vertex curr = (Vertex) itr.next();
            if (curr.getKey() == src) {
                curr.set_wSoFar(0);
                curr.set_father(null);
                continue;
            }
            curr.set_wSoFar(Integer.MAX_VALUE);
            curr.set_father(null);
        }
        PriorityQueue<Vertex> nodeList = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparing(Vertex::get_wSoFar));
        itr = this.g.nodeIter();
        while (itr.hasNext()) {
            nodeList.add((Vertex) itr.next());
        }
        while (!nodeList.isEmpty()) {
            Vertex curr = nodeList.poll();
            Iterator itr2 = this.g.edgeIter(curr.getKey());
            while (itr2.hasNext()) {
                relax((Edge) itr2.next());
            }
            PriorityQueue<Vertex> temp = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparing(Vertex::get_wSoFar));
            int cap = nodeList.size();
            for (int i = 0; i < cap; i++) {
                temp.add(nodeList.poll());
            }
            nodeList = temp;
        }
        Vertex v = (Vertex) this.g.getNode(dest);
        double ans = v.get_wSoFar();
        if (ans == Integer.MAX_VALUE) {
            return -1;
        }
        return ans;
    }

    private Vertex centerFuncV(int src) {
        Iterator itr = this.g.nodeIter();
        while (itr.hasNext()) {
            Vertex curr = (Vertex) itr.next();
            if (curr.getKey() == src) {
                curr.set_wSoFar(0);
                curr.set_father(null);
                continue;
            }
            curr.set_wSoFar(Integer.MAX_VALUE);
            curr.set_father(null);
        }
        PriorityQueue<Vertex> nodeList = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparing(Vertex::get_wSoFar));
        itr = this.g.nodeIter();
        while (itr.hasNext()) {
            nodeList.add((Vertex) itr.next());
        }
        while (!nodeList.isEmpty()) {
            Vertex curr = nodeList.poll();
            Iterator itr2 = this.g.edgeIter(curr.getKey());
            while (itr2.hasNext()) {
                relax((Edge) itr2.next());
            }
            PriorityQueue<Vertex> temp = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparing(Vertex::get_wSoFar));
            int cap = nodeList.size();
            for (int i = 0; i < cap; i++) {
                temp.add(nodeList.poll());
            }
            nodeList = temp;
        }
        itr = this.g.nodeIter();
        Vertex v = (Vertex) this.g.getNode(src);
        while (itr.hasNext()) {
            Vertex curr = (Vertex) itr.next();
            if (curr.get_wSoFar() > v.get_wSoFar()) {
                v = curr;
            }
        }
        return v;
    }

    private double centerFunc(int src) {
        Iterator itr = this.g.nodeIter();
        while (itr.hasNext()) {
            Vertex curr = (Vertex) itr.next();
            if (curr.getKey() == src) {
                curr.set_wSoFar(0);
                curr.set_father(null);
                continue;
            }
            curr.set_wSoFar(Integer.MAX_VALUE);
            curr.set_father(null);
        }
        PriorityQueue<Vertex> nodeList = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparing(Vertex::get_wSoFar));
        itr = this.g.nodeIter();
        while (itr.hasNext()) {
            nodeList.add((Vertex) itr.next());
        }
        while (!nodeList.isEmpty()) {
            Vertex curr = nodeList.poll();
            Iterator itr2 = this.g.edgeIter(curr.getKey());
            while (itr2.hasNext()) {
                relax((Edge) itr2.next());
            }
            PriorityQueue<Vertex> temp = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparing(Vertex::get_wSoFar));
            int cap = nodeList.size();
            for (int i = 0; i < cap; i++) {
                temp.add(nodeList.poll());
            }
            nodeList = temp;
        }
        itr = this.g.nodeIter();
        Vertex v = (Vertex) this.g.getNode(src);
        while (itr.hasNext()) {
            Vertex curr = (Vertex) itr.next();
            if (curr.get_wSoFar() > v.get_wSoFar()) {
                v = curr;
            }
        }
        return v.get_wSoFar();
    }


    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        shortestPathDist(src, dest);
        ArrayList<NodeData> ans = new ArrayList<>();
        Vertex curr = (Vertex) this.g.getNode(dest);
        while (curr.getKey() != src) {
            ans.add(0, curr);
            curr = curr.get_father();
        }
        ans.add(0, this.g.getNode(src));
        return ans;
    }

    @Override
    public NodeData center() {
        if (!this.isConnected()) {
            return null;
        }
        NodeData ans = null;
        double currMaxDist = Integer.MAX_VALUE;
        Iterator<NodeData> nodeList = this.g.nodeIter();
        while (nodeList.hasNext()) {
            NodeData curr = nodeList.next();
            double checked = this.centerFunc(curr.getKey());
            if (checked < currMaxDist) {
                currMaxDist = checked;
                ans = curr;
            }
        }
        return ans;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (!isConnected()) {
            return null;
        }
        List<NodeData> ans = new LinkedList<>();
        NodeData currNode = cities.remove(0);
        ans.add(currNode);
        NodeData bestNode = null;
        while (!cities.isEmpty()) {
            double shortestdist = Integer.MAX_VALUE;
            for (int i = 0; i < cities.size(); i++) {
                double temp = shortestPathDist(currNode.getKey(),cities.get(i).getKey());
                if(temp<shortestdist){
                    temp = shortestdist;
                    bestNode=cities.get(i);
                }
            }
            List<NodeData> put= shortestPath(currNode.getKey(),bestNode.getKey());
            int ansSize = ans.size();
            ans.addAll(put);
            ans.remove(ansSize-1);
            cities.remove(bestNode);
            currNode = bestNode;
        }
        return ans;
    }

    @Override
    public boolean save(String file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter write = new FileWriter(file);
            FormalGraph gh = new FormalGraph(this);
            gson.toJson(gh, write);
            write.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        try {
            Gson gson = new Gson();
            Reader read = new FileReader(file);
            HashMap map = gson.fromJson(read, HashMap.class);
            String nodejson = map.get("Nodes").toString();
            String edgejson = map.get("Edges").toString();
            nodejson = nodejson.replace("{", "");
            nodejson = nodejson.substring(1, nodejson.length() - 2);
            edgejson = edgejson.replace("{", "");
            edgejson = edgejson.substring(1, edgejson.length() - 2);
            String[] nodeList = nodejson.split("},");
            String[] edgeList = edgejson.split("},");
            Graph draftGraph = new Graph();
            for (int i = 0; i < nodeList.length; i++) {
                if (i > 0) {
                    nodeList[i] = nodeList[i].substring(1);
                }
                String[] data = nodeList[i].split("id");
                String pos = data[0];
                String id = data[1];
                pos = pos.substring(4, pos.length() - 3);
                Location loc = new Location(Double.parseDouble(pos.split(",")[0]), Double.parseDouble(pos.split(",")[1]), Double.parseDouble(pos.split(",")[2]));
                id = id.substring(1, id.length() - 2);
                Vertex v = new Vertex(Integer.parseInt(id), loc, 0, 0, "");
                draftGraph.addNode(v);
            }
            for (int i = 0; i < edgeList.length; i++) {
                if (i > 0) {
                    edgeList[i] = edgeList[i].substring(1);
                }
                String src = edgeList[i].split(",")[0];
                String weight = edgeList[i].split(",")[1];
                String dest = edgeList[i].split(",")[2];
                src = src.substring(4, src.length() - 2);
                weight = weight.substring(3);
                dest = dest.substring(6, dest.length() - 2);
                draftGraph.connect(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
            }
            this.init(draftGraph);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void BFS(Vertex root, Graph graph) {
        //0-WHITE,1-GRAY,2-BLACK
        Queue<NodeData> q = new LinkedList<NodeData>();
        Iterator<NodeData> nodes = graph.nodeIter();
        while (nodes.hasNext()) {
            nodes.next().setTag(0);
        }
        q.add(root);
        while (!q.isEmpty()) {
            Vertex curr = (Vertex) q.poll();
            Iterator<EdgeData> curr_edge = graph.edgeIter(curr.getKey());
            while (curr_edge.hasNext()) {
                NodeData dest = graph.getNode(curr_edge.next().getDest());
                if (dest.getTag() == 0) {
                    dest.setTag(1);
                    q.add(dest);
                }
            }
            curr.setTag(2);
        }
    }

//    private void reverseBFS(Vertex root, Graph graph) {
//        //0-WHITE,1-GRAY,2-BLACK
//        Queue<NodeData> q = new LinkedList<NodeData>();
//        Iterator<NodeData> nodes = graph.nodeIter();
//        while (nodes.hasNext()) {
//            nodes.next().setTag(0);
//        }
//        q.add(root);
//        while (!q.isEmpty()) {
//            Vertex curr = (Vertex) q.poll();
//            Iterator<EdgeData> curr_edge = graph.reverseedgeIter(curr.getKey());
//            while (curr_edge.hasNext()) {
//                NodeData dest = graph.getNode(curr_edge.next().getDest());
//                if (dest.getTag() == 0) {
//                    dest.setTag(1);
//                    q.add(dest);
//                }
//            }
//            curr.setTag(2);
//        }
//    }
}