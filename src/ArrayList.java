

public class ArrayList<V>{
    
    private V[] array;
    private int top;
    static final double INCREMENTCONSTANT = 1.5;
    
    public ArrayList(){
        this.array = (V[]) new Object[10];
        this.top = 0;
    }
    
    public void add(V value){
        if(this.top < this.array.length){
            this.array[this.top] = value;
            this.top++;
        }else{
            // Se lleno
            V[] tmp = (V[])new Object[(int) (this.array.length*this.INCREMENTCONSTANT)];
            for(int i = 0; i < this.top; i++){
                tmp[i] = this.array[i];
            }
            this.array = tmp;
            this.array[this.top] = value;
            this.top++;
        }
    }
    
    public V get(int index){
        return this.array[index];
    }
    
    public int size(){
        return this.top;
    }
    

}
