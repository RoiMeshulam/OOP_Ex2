import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        GraphAlgo g = new GraphAlgo();
        g.load(json_file);
        return g.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        GraphAlgo g = new GraphAlgo();
        g.load(json_file);
        return g;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        GraphAlgo g = new GraphAlgo();
        boolean flag = g.load(json_file);
        if(flag==true){
            new GUI(g);
        }
        else{
            GraphAlgo g2 = new GraphAlgo();
            Graph gh = new Graph();
            g2.init(gh);
            new GUI(g2);
        }
    }

    public static void main(String[] args) {

        runGUI(args[0]);
    }


}