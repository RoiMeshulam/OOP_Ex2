public class FormalNode {
    private String pos;
    private int id;

    public FormalNode(Vertex v){
        this.pos=v.getLocation().x()+","+v.getLocation().y()+""+v.getLocation().z();
        this.id = v.getKey();
    }
}