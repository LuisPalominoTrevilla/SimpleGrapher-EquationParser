import java.util.ArrayList;

public class Graph {
    
    private Equation function;
    private double inicio, fin, step;
    private ArrayList<Punto> puntos;
    
    public Graph(Equation equation, double inicio, double fin, double step){
        this.function = equation;
        this.inicio = inicio;
        this.fin = fin;
        this.step = step;
        this.puntos = new ArrayList<Punto>();
    }
    
    public void plotEquation(){
        double eval;
        for(double i = this.inicio; i <= this.fin; i += this.step){
            try{
                eval = this.function.evalEquation(i);
                Punto p = new Punto(i, eval);
                if(Double.isInfinite(eval)){
                    p.setLimitLeft((this.function.evalLeftLimit(i) > 0)?Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
                    p.setLimitRight((this.function.evalRightLimit(i) > 0)?Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
                }
                
                this.puntos.add(p);
            }catch(RuntimeException e){
                
            }
        }
    }
    
    public ArrayList<Punto> getPoints(){
        return this.puntos;
    }
}
