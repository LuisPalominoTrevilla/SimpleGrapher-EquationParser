/*
 * Point Class
 * Author: Luis Palomino Trevilla
 * Date: 11-08-2017
 * 
 */
public class Punto{
    private double x, y, limitLeft, limitRight;
    
    public Punto(){
        this.x = 0;
        this.y = 0;
    }
    
    public Punto(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public double getDistanceTo(Punto p2){
        return Math.sqrt((p2.y - this.y)*(p2.y - this.y)+(p2.x - this.x)*(p2.x - this.x));
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public void setLimitLeft(double l){
        this.limitLeft = l;
    }
    
    public void setLimitRight(double r){
        this.limitRight = r;
    }
    
    public double getLimitLeft(){
        return this.limitLeft;
    }
    
    public double getLimitRight(){
        return this.limitRight;
    }
}
