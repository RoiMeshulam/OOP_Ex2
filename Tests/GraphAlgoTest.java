import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgoTest {

    @Test
    void graphCheck(){
        GraphAlgo graphAlgo = new GraphAlgo();
        Graph g = new Graph();
        Vertex v0 = new Vertex(0,new Location(1,1,0),0,0,"");
        Vertex v1 = new Vertex(1,new Location(2,2,0),0,0,"");
        Vertex v2 = new Vertex(2,new Location(3,3,0),0,0,"");
        Vertex v3 = new Vertex(3,new Location(4,4,0),0,0,"");
        Vertex v4 = new Vertex(4,new Location(5,5,0),0,0,"");
        g.addNode(v0);
        g.addNode(v1);
        g.addNode(v2);
        g.addNode(v3);
        g.addNode(v4);
        g.connect(0,1,9);
        g.connect(1,2,2);
        g.connect(1,3,14);
        g.connect(2,4,1);
        g.connect(3,0,1);
        g.connect(3,4,12);
        g.connect(4,1,3);
        g.connect(4,2,1);
        assertEquals(g.nodeSize(),5);
        assertEquals(g.edgeSize(),8);
        assertEquals(g.getMC(),13);
        assertEquals(g.getNode(3).getKey(),3);
        assertEquals(g.getEdge(4,2).getWeight(),1);
        graphAlgo.init(g);
        assertEquals(graphAlgo.shortestPathDist(0,0),-1);
        assertEquals(graphAlgo.shortestPathDist(0,1),9);
        assertEquals(graphAlgo.shortestPathDist(0,2),11);
        assertEquals(graphAlgo.shortestPathDist(0,3),23);
        assertEquals(graphAlgo.shortestPathDist(0,4),12);
        assertEquals(graphAlgo.shortestPathDist(1,0),15);
        assertEquals(graphAlgo.shortestPathDist(1,1),-1);
        assertEquals(graphAlgo.shortestPathDist(1,2),2);
        assertEquals(graphAlgo.shortestPathDist(1,3),14);
        assertEquals(graphAlgo.shortestPathDist(1,4),3);
        assertEquals(graphAlgo.shortestPathDist(2,0),19);
        assertEquals(graphAlgo.shortestPathDist(2,1),4);
        assertEquals(graphAlgo.shortestPathDist(2,2),-1);
        assertEquals(graphAlgo.shortestPathDist(2,3),18);
        assertEquals(graphAlgo.shortestPathDist(2,4),1);
        assertEquals(graphAlgo.shortestPathDist(3,0),1);
        assertEquals(graphAlgo.shortestPathDist(3,1),10);
        assertEquals(graphAlgo.shortestPathDist(3,2),12);
        assertEquals(graphAlgo.shortestPathDist(3,3),-1);
        assertEquals(graphAlgo.shortestPathDist(3,4),12);
        assertEquals(graphAlgo.shortestPathDist(4,0),18);
        assertEquals(graphAlgo.shortestPathDist(4,1),3);
        assertEquals(graphAlgo.shortestPathDist(4,2),1);
        assertEquals(graphAlgo.shortestPathDist(4,3),17);
        assertEquals(graphAlgo.shortestPathDist(4,4),-1);
        ArrayList<NodeData> true_path = new ArrayList<>();
        ArrayList<NodeData> check_path= (ArrayList<NodeData>) graphAlgo.shortestPath(3,0);
        true_path.add(v3);
        true_path.add(v0);
        assertEquals(true_path.size(),check_path.size());
        for (int i = 0; i <true_path.size() ; i++) {
            assertEquals(true_path.get(i).getKey(),check_path.get(i).getKey());
        }
        check_path.clear();
        true_path.add(v1);
        true_path.add(v2);
        check_path=(ArrayList<NodeData>) graphAlgo.shortestPath(3,2);
        assertEquals(true_path.size(),check_path.size());
        for (int i = 0; i <true_path.size() ; i++) {
            assertEquals(true_path.get(i).getKey(),check_path.get(i).getKey());
        }
        assertTrue(graphAlgo.isConnected());
        Vertex v5 = new Vertex(5,new Location(5,5,0),0,0,"");
        g.addNode(v5);
        assertFalse(graphAlgo.isConnected());
    }
    @Test
    void graphAlgoCheck(){
        GraphAlgo g1 = new GraphAlgo();
        Graph g =  new Graph();
        Vertex v0 = new Vertex(0,new Location(1,1,0),0,0,"");
        Vertex v1 = new Vertex(1,new Location(2,2,0),0,0,"");
        Vertex v2 = new Vertex(2,new Location(3,3,0),0,0,"");
        Vertex v3 = new Vertex(3,new Location(4,4,0),0,0,"");
        Vertex v4 = new Vertex(4,new Location(5,5,0),0,0,"");
        g.addNode(v0);
        g.addNode(v1);
        g.addNode(v2);
        g.addNode(v3);
        g.addNode(v4);
        g.connect(0,1,9);
        g.connect(1,2,2);
        g.connect(1,3,14);
        g.connect(2,4,1);
        g.connect(3,0,1);
        g.connect(3,4,12);
        g.connect(4,1,3);
        g.connect(4,2,1);
        g1.init(g);
        assertEquals(g1.isConnected(),true);
        assertEquals(g1.center().getKey(),3);
        assertEquals(g1.shortestPathDist(1,4),3);
        List<NodeData> x = g1.shortestPath(1,4);
        assertEquals(x.get(0).getKey(),1);
        assertEquals(x.get(1).getKey(),2);
        assertEquals(x.get(2).getKey(),4);
        g.connect(0,3,1);
        g.connect(1,0,1);
        g.connect(0,4,1);
        List<NodeData> cities = new ArrayList<>();
        cities.add(v0);
        cities.add(v1);
        cities.add(v4);
        cities.add(v3);
        List<NodeData> xt = g1.tsp(cities);
        assertEquals(xt.get(0).getKey(),0);
        assertEquals(xt.get(1).getKey(),3);
        assertEquals(xt.get(2).getKey(),0);
        assertEquals(xt.get(3).getKey(),4);
        assertEquals(xt.get(4).getKey(),1);

    }

}