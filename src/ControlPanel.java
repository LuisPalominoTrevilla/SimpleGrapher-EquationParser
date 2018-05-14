import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ControlPanel extends JPanel implements ActionListener{
    
    private Grapher graph;
    private JTextArea equation;
    private JButton graficar, zoomIn, zoomOut;
    private JPanel graphColor;
    
    public ControlPanel(Grapher graph){
        super();
        this.setPreferredSize(new Dimension(400, 800));
        this.graph = graph;
        this.setBackground(Color.decode("#e5e4de"));
        
        JLabel y = new JLabel("f(x) = ");
        y.setFont(new Font("normal", 0, 20));
        this.equation = new JTextArea(2, 20);
        this.equation.setFont(new Font("normal", 0, 20));
        JScrollPane scrollPane = new JScrollPane(this.equation);
        
        this.graficar = new JButton("Graficar funcion");
        this.graficar.addActionListener(this);
        
        this.graphColor = new JPanel();
        this.graphColor.setPreferredSize(new Dimension(50, 50));
        this.graphColor.setBackground(Color.red);
        this.graphColor.addMouseListener(new MouseListener() {
            
            public void mouseReleased(MouseEvent e) {
                
            }
            public void mousePressed(MouseEvent e) {
                
            }
            public void mouseExited(MouseEvent e) {
                
            }
            public void mouseEntered(MouseEvent e) {
                
            }
            public void mouseClicked(MouseEvent e) {
                Color seleccionado = JColorChooser.showDialog(ControlPanel.this, "Selecciona el color de la base", Color.BLUE);
                ControlPanel.this.graphColor.setBackground(seleccionado);
                ControlPanel.this.graph.setGraphColor(seleccionado);
            }
        });
        
        this.zoomIn = new JButton("+");
        this.zoomOut = new JButton("-");
        this.zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ControlPanel.this.graph.zoomIn();
            }
        });
        this.zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ControlPanel.this.graph.zoomOut();
            }
        });
        
        this.add(y);
        this.add(scrollPane);
        this.add(this.graficar);
        this.add(this.graphColor);
        this.add(this.zoomIn);
        this.add(this.zoomOut);
    }

    public void actionPerformed(ActionEvent e) {
        try{
            Equation eq = new Equation(this.equation.getText());
            this.graph.graphEquation(eq);
        }catch(EquationNotWellFormatedException ex){
            this.graph.stopGraphing();
        }
    }
}
