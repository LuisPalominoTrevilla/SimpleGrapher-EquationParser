
public class Node <V>{
    private V value;
    private Node<V> next;
    
    public Node(V value){
        this.value = value;
        this.next = null;
    }
    
    public void setNext(Node<V> next){
        this.next = next;
    }
    
    public Node<V> getNext(){
        return this.next;
    }
    
    public V getvalue(){
        return this.value;
    }
}
