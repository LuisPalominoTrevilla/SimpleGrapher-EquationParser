import java.util.EmptyStackException;

public class StackLinkedList <V>{
    private Node<V> top;
    
    public StackLinkedList(){
        this.top = null;
    }
    
    public void push(V value){
        Node<V> node = new Node<V>(value);
        node.setNext(this.top);
        this.top = node;
    }
    
    public V pop(){
        if(this.isEmpty()){
            throw new EmptyStackException();
        }else{
            Node<V> current = this.top;
            this.top = this.top.getNext();
            V value = current.getvalue();
            current = null;
            return value;
        }
        
    }
    
    public V peek(){
        if(this.isEmpty()){
            return null;
        }
        V val = this.top.getvalue();
        return val;
    }
    
    public boolean contains(V value){
        Node<V> current = this.top;
        while(current != null){
            if(value == current.getvalue()){
                return true;
            }else{
                current  = current.getNext();
            }
        }
        return false;
    }
    
    public boolean isEmpty(){
        return this.top == null;
    }
    
    
    
    public String toString(){
        String result = "__\n\n";
        Node<V> current = this.top;
        while(current != null){
            result += current.getvalue() + "\n";
            current = current.getNext();
        }
        return result+"__";
    }
}
