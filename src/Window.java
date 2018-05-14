import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame{
    
    private Grapher graph;
    private ControlPanel cp;
    
    public Window(){
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.graph = new Grapher();
        this.cp = new ControlPanel(this.graph);
        this.add(this.graph);
        this.add(this.cp, BorderLayout.EAST);
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Window();
    }

}
