import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class Grapher extends JPanel{
    
    private boolean shouldGraph;
    private Equation equation;
    private Graph graph;
    private Color graphColor;
    private int step;
    private final int[] zooms = {10, 20, 30, 40};
    private int currentZoom;
    
    public Grapher(){
        super();
        this.shouldGraph = false;
        this.graphColor = Color.red;
        this.currentZoom = 1;
        this.step = this.zooms[this.currentZoom];
        this.setPreferredSize(new Dimension(800, 800));
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int offsety = this.getHeight()/2;
        int offsetx = this.getWidth()/2;
        
        
        
        // Pintar ticks
        Graphics2D g2 = (Graphics2D)g;
        
        Shape l, l2;
        for(double i = -this.getWidth()/2/step; i <= this.getWidth()/2/step; i+=1){
            l = new Line2D.Double(i*step+offsetx, 0,i*step+offsetx, this.getHeight());
            l2 = new Line2D.Double(0, i*step+offsety,this.getWidth(), i*step+offsety);
            if(i % 5 == 0){
                g.setColor(Color.decode("#a5a5a5"));
                
            }else{
                g.setColor(Color.decode("#e0e0e0"));
            }
            g2.draw(l);
            g2.draw(l2);
        }
        
        // Pintar ejes
        g.setColor(Color.black);
        g.drawLine(offsetx, 0, offsety, this.getHeight());
        Font font = new Font("LucidaSans", Font.BOLD, 18);
        String a = "Y";
        String b = "X";
        g.setFont(font);
        g.drawString(a, offsetx+20, 20);
        g.drawLine(0, offsety, this.getWidth(), offsety);
        g.drawString(b, this.getWidth()-20, offsety-20);
        
        if(this.shouldGraph){
            this.graphExample(g);
        }
        
    }
    
    public void graphEquation(Equation e){
        this.equation = e;
        this.shouldGraph = true;
        this.repaint();
    }
    
    public void stopGraphing(){
        this.shouldGraph = false;
        this.repaint();
    }
    
    public void setGraphColor(Color color){
        this.graphColor = color;
    }
    
    public void zoomIn(){
        if(this.currentZoom < this.zooms.length-1){
            this.step = this.zooms[++this.currentZoom];
            this.repaint();
        }
    }
    
    public void zoomOut(){
        if(this.currentZoom > 0){
            this.step = this.zooms[--this.currentZoom];
            this.repaint();
        }
    }
    
    private void graphExample(Graphics g){
        
        int offsety = this.getHeight()/2;
        int offsetx = this.getWidth()/2;
        Graphics2D g2 = (Graphics2D)g;

        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.graph = new Graph(equation, -this.getWidth()/2/step, this.getWidth()/2/step, .5);
        this.graph.plotEquation();
        g.setColor(this.graphColor);
        
        for(int i = 0; i < this.graph.getPoints().size()-1; i++){
            if(!Double.isNaN(this.graph.getPoints().get(i).getY()) && !Double.isNaN(this.graph.getPoints().get(i+1).getY()) && Double.isFinite(this.graph.getPoints().get(i).getY()) && Double.isFinite(this.graph.getPoints().get(i+1).getY())){
                Shape l = new Line2D.Double(this.graph.getPoints().get(i).getX()*step+offsetx,-this.graph.getPoints().get(i).getY()*step+offsety,this.graph.getPoints().get(i+1).getX()*step+offsetx,-this.graph.getPoints().get(i+1).getY()*step+offsety);
                g2.draw(l);
            }else if(Double.isInfinite(this.graph.getPoints().get(i+1).getY())){ // Si el izquierdo es infinito
                Shape l = new Line2D.Double(this.graph.getPoints().get(i).getX()*step+offsetx,-this.graph.getPoints().get(i).getY()*step+offsety,this.graph.getPoints().get(i+1).getX()*step+offsetx,(this.graph.getPoints().get(i+1).getLimitLeft()==Double.POSITIVE_INFINITY)? 0:this.getHeight());
                g2.draw(l);
            }else if(Double.isInfinite(this.graph.getPoints().get(i).getY())){
                Shape l = new Line2D.Double(this.graph.getPoints().get(i).getX()*step+offsetx,(this.graph.getPoints().get(i).getLimitRight() == Double.POSITIVE_INFINITY)? 0:this.getHeight(),this.graph.getPoints().get(i+1).getX()*step+offsetx,-this.graph.getPoints().get(i+1).getY()*step+offsety);
                g2.draw(l);
            }
        }
    }
}
