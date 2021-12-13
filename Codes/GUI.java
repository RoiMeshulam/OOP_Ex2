import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GUI extends JFrame {
    private GraphAlgo g;
    private Graph graph;
    private JMenuBar menubar;
    private JMenu fileMenu;
    private JMenu change;
    private JMenu algo;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem addnode;
    private JMenuItem remove;
    private JMenuItem connect;
    private JMenuItem disconnect;
    private JMenuItem isConnected;
    private JMenuItem shortestPath;
    private JMenuItem isCenter;
    private JMenuItem tsp;
    private int width=1600;
    private int height=900;
    private boolean firstpaint;




    public GUI(GraphAlgo gr) throws HeadlessException {
        firstpaint=true;
        this.g = new GraphAlgo();
        this.g=gr;

//        g.load(json_file);
//        graph= new Graph();
//        g.init(graph);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);

        //buttons menu
        Container contentpane = this.getContentPane();
        contentpane.setLayout(null);
        fileMenu = new JMenu("Menu");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        change = new JMenu("Change");
        algo = new JMenu("Algorithm");
        fileMenu.add(load);
        fileMenu.add(save);
        fileMenu.add(change);
        fileMenu.add(algo);
        addnode = new JMenuItem("Add a node");
        remove = new JMenuItem("Remove a node");
        connect = new JMenuItem("Connect an edge");
        disconnect = new JMenuItem("Disconnect an edge");
        isConnected = new JMenuItem("Check if the graph is connected");
        shortestPath = new JMenuItem("Check what is the shortest path between two nodes");
        isCenter = new JMenuItem("Check which node is center");
        tsp = new JMenuItem("tsp");
        change.add(addnode);
        change.add(remove);
        change.add(connect);
        change.add(disconnect);
        algo.add(isConnected);
        algo.add(shortestPath);
        algo.add(isCenter);
        algo.add(tsp);
        menubar = new JMenuBar();
        menubar.add(fileMenu);
        this.setJMenuBar(menubar);
        this.setVisible(true);





//action listeners
        load.addActionListener(new AddGraphListener(this));
        save.addActionListener(new AddGraphListener(this));
        addnode.addActionListener(new AddGraphListener(this));
        remove.addActionListener(new AddGraphListener(this));
        connect.addActionListener(new AddGraphListener(this));
        disconnect.addActionListener(new AddGraphListener(this));
        isConnected.addActionListener(new AddGraphListener(this));
        shortestPath.addActionListener(new AddGraphListener(this));
        isCenter.addActionListener(new AddGraphListener(this));
        tsp.addActionListener(new AddGraphListener(this));
//        new ActionListener(new AddGraphListener(this));
        AddGraphListener temp = new AddGraphListener(this);
        firstpaint=temp.firstPaint(firstpaint);


    }

    public class AddGraphListener implements ActionListener {
        GUI _agl;
        GraphP _panel;

        public AddGraphListener(GUI agl) {
            _agl=agl;
            _panel=new GraphP();
        }

        public boolean firstPaint(boolean firstpaint){
            if (firstpaint){
                repaint();
                _agl.add(_panel);
                if (_agl.g.getGraph().nodeSize()==0){
                    JOptionPane.showMessageDialog(null, "Invalid file/ Empty graph was inserted");
                }
            }
            return false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _agl.load) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    int option = fileChooser.showOpenDialog(_agl);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        g.load(file.getAbsolutePath());
                    }
                    repaint();
                    _agl.add(_panel);


                }
                catch (Exception exp){
                    JOptionPane.showMessageDialog(null, "Invalid file");
                }
            }
            else if (e.getSource() == _agl.save) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(_agl);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    g.save(file.getAbsolutePath() + ".json");
                }
            }
            else if (e.getSource() == _agl.addnode) {
                String inputString = JOptionPane.showInputDialog(null, "Enter ID,x,y,z (for example 0,2.1,5.0,1)");
                String[] temp;
                temp = inputString.split(",");
                int idCheck = Integer.parseInt(temp[0]);
                if(g.getGraph().getNode(idCheck)!=null){
                    JOptionPane.showMessageDialog(null, "ID exists");
                    return;}
                try {
                    Vertex v = new Vertex(Integer.parseInt(temp[0]), new Location(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]), Double.parseDouble(temp[3])), 0, 0, "");
                    JOptionPane.showMessageDialog(null, "New Node was added ID:" +v.getKey());
                    g.getGraph().addNode(v);
                    repaint();
                    _agl.add(_panel);
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Invalid values");
                }
            }
            else if(e.getSource() == _agl.remove){
                String inputString = JOptionPane.showInputDialog(null, "Enter ID");
                try {
                    g.getGraph().removeNode(Integer.parseInt(inputString));
                    repaint();
                    _agl.add(_panel);
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Invalid values OR There is no Node with this ID");
                }
            }
            else if(e.getSource() == _agl.connect){
                String inputString = JOptionPane.showInputDialog(null, "Enter ID of source,ID of destination and a weight(example: 0,7,5.12343)");
                try {
                    String[] temp;
                    temp = inputString.split(",");
                    g.getGraph().connect(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Double.parseDouble(temp[2]));
                    repaint();
                    _agl.add(_panel);
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Invalid values OR Missing a node with this ID");
                }
            }
            else if(e.getSource() == _agl.disconnect){
                String inputString = JOptionPane.showInputDialog(null, "Enter ID of source,ID of destination(example: 0,7)");
                try {
                    String[] temp;
                    temp = inputString.split(",");
                    g.getGraph().removeEdge(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
                    repaint();
                    _agl.add(_panel);
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Invalid values OR Missing a node with this ID");
                }
            }
            else if(e.getSource() == _agl.isConnected){
                try{
                    boolean ans = g.isConnected();
                    if(ans == true){
                        JOptionPane.showMessageDialog(null, "The graph is connected.");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "The graph is not connected.");
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "There is no graph yet");
                }
            }
            else if(e.getSource() == _agl.shortestPath){
                String inputString = JOptionPane.showInputDialog(null, "Enter ID of source,ID of destination(example: 0,7)");
                try {
                    String[] temp = new String[2];
                    temp = inputString.split(",");
                    List<NodeData> ans = g.shortestPath(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
                    String formal = "The shortest path is: ";
                    for (int i = 0; i < ans.size(); i++) {
                        formal=formal+ans.get(i).getKey()+",";
                    }
                    formal = formal + "overall weight: "+g.shortestPathDist(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
                    JOptionPane.showMessageDialog(null, formal);
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Invalid values OR Missing a node with this ID");
                }
            }
            else if(e.getSource() == _agl.isCenter){
                try {
                    Vertex ans = (Vertex) g.center();
                    JOptionPane.showMessageDialog(null, "" + ans.getKey());
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "There is no graph or the graph is not connected!");
                }
            }
            else if(e.getSource() == _agl.tsp){
                String inputString = JOptionPane.showInputDialog(null, "Enter cities ID(example: 0,7,3,19)");
                try {
                    String[] temp;
                    temp = inputString.split(",");
                    List<NodeData> cities = new ArrayList<>();
                    for (int i = 0; i < temp.length; i++) {
                        int key = Integer.parseInt(temp[i]);
                        cities.add(g.getGraph().getNode(key));}
                    List<NodeData> ans = g.tsp(cities);
                    String formal = "The shortest path between the cities: ";
                    for (int i = 0; i < ans.size(); i++) {
                        formal=formal+(""+ans.get(i).getKey()+",");
                    }
                    JOptionPane.showMessageDialog(null, formal);
                }
                catch(Exception exp){
                    JOptionPane.showMessageDialog(null, "Invalid values OR Missing a node with this ID");
                }
            }
        }
    }

    @Override
    public void paint(Graphics l) {
        super.paint(l);

    }

    public class GraphP extends JPanel   {
        Iterator<NodeData> vers;
        Iterator<EdgeData> edges;
        double scale_x;
        double scale_y;
        double min_x=Double.MAX_VALUE;
        double min_y=Double.MAX_VALUE;
        double max_x=Double.MIN_VALUE;
        double max_y=Double.MIN_VALUE;


        public GraphP() {
            Color c = new Color(150, 134, 57);
            super.setBackground(c);
            super.setBounds(0,0,width,height);
            super.setSize(width,height);
        }



        @Override
        protected void paintComponent(Graphics l) {
            super.paintComponent(l);
            scale_x=0;
            scale_y=0;
            min_x=Double.MAX_VALUE;
            min_y=Double.MAX_VALUE;
            max_x=Double.MIN_VALUE;
            max_y=Double.MIN_VALUE;
            Iterator<NodeData> vers = g.getGraph().nodeIter();
            while (vers.hasNext()) {
                Vertex counterhelper = (Vertex) vers.next();
                if (counterhelper.getLocation().x()<min_x){
                    min_x=counterhelper.getLocation().x();
                }
                else if (counterhelper.getLocation().x()>max_x){
                    max_x= counterhelper.getLocation().x();
                }
                if (counterhelper.getLocation().y()<min_y){
                    min_y= counterhelper.getLocation().y();
                }
                else if (counterhelper.getLocation().y()>max_y){
                    max_y= counterhelper.getLocation().y();
                }

            }
            scale_x=max_x-min_x;
            scale_y=max_y-min_y;
            scale_x=width/scale_x;
            scale_x= scale_x *0.9;
            scale_y=height/scale_y;
            scale_y=scale_y*0.8;

            Iterator<NodeData> nodeItr = g.getGraph().nodeIter();
            while(nodeItr.hasNext()){
                Vertex n = (Vertex) nodeItr.next();
                Iterator<EdgeData> itr = g.getGraph().edgeIter(n.getKey());
                while (itr.hasNext()){
                    Edge e = (Edge) itr.next();
                    Vertex vs =(Vertex) g.getGraph().getNode(e.getSrc());
                    Vertex vd = (Vertex) g.getGraph().getNode(e.getDest());
                    double destx=vd.getLocation().x();
                    destx= ((destx-min_x)*scale_x)+10;
                    double desty=vd.getLocation().y();
                    desty= ((desty-min_y)*scale_y)+10;
                    double srcx=vs.getLocation().x();
                    srcx = ((srcx-min_x)*scale_x)+10;
                    double srcy=vs.getLocation().y();
                    srcy = ((srcy-min_y)*scale_y)+10;

                    l.setColor(Color.BLACK);
                    Arrow arrow = new Arrow((int)srcx,(int) srcy,(int) destx,(int)desty,Color.black,1);
                    arrow.draw(l);
                }
            }

            vers=g.getGraph().nodeIter();
            while (vers.hasNext()) {
                Vertex var = (Vertex) vers.next();
                double sx=((var.getLocation().x()-min_x)*scale_x) + 5 ;
                double sy= ((var.getLocation().y()-min_y)*scale_y) + 5;
                l.setColor(Color.WHITE);
                l.fillOval((int)sx,(int)sy,10,10);
                l.setColor(Color.black);
                l.drawString("Key= "+var.getKey(), (int) sx,(int) sy+40);
                l.drawString(var.getLocation().toString(),(int) sx-5,(int) sy+60);

            }
        }

    }



}