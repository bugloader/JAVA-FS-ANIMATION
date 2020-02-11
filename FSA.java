package fsa;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class FSA extends JFrame {
    Vector constant,oragin;
    ArrayList<Vector> timeDomainSignal;
    Vector[] positiveVectors, negativeVectors;
    final int Time = 1000;
    int bounds;
    ArrayList<Vector> paintedPoints;
    JPanel graphPanel = new JPanel();
    public void initial() {
        timeDomainSignal = new ArrayList<>();
        for (int i = 50; i < 800; i++) {
            timeDomainSignal.add(new Vector(i, 50));
        }
        for (int i = 50; i < 400; i++) {
            timeDomainSignal.add(new Vector(800, i));
        }
        for (int i = 800; i > 50; i--) {
            timeDomainSignal.add(new Vector(i, 400));
        }
        for (int i = 400; i > 50; i--) {
            timeDomainSignal.add(new Vector(50, i));
        }
        System.out.print(timeDomainSignal.size());
        oragin = new Vector(600, 700);
        paintedPoints = new ArrayList<>();
        this.setBounds(0, 0, 950, 950);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(graphPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    public void FT() {
        Fouriers f = new Fouriers(oragin, timeDomainSignal, 1024);
        f.generateConstantVector();
        f.generateAllVectors();
        bounds = f.bounds;
        constant = f.constant;
        positiveVectors = f.positiveVectors;
        negativeVectors = f.negativeVectors;
    }
    public void drawVectors()
    {
        Graphics g = graphPanel.getGraphics();
        Vector last,next;
        for (int i = 0; i < Time; i++) {
            g.clearRect(0, 0, 900, 900);
            last = oragin;
            next = Vector.add(last, constant);
            g.drawLine(last.getX(), last.getY(),
                        next.getX(), next.getY());
            for(int j=0;j<paintedPoints.size()-1;j++)
            {
                g.drawLine(paintedPoints.get(j).getX(), paintedPoints.get(j).getY(),
                        paintedPoints.get(j+1).getX(), paintedPoints.get(j+1).getY());
            }
            for(int j=0;j<bounds;j++)
            {
                last = next;
                next = Vector.add(last, 
                        positiveVectors[j].rotate((double)(j+1)*i/Time));
                g.drawLine(last.getX(), last.getY(),
                        next.getX(), next.getY());
                last = next;
                next = Vector.add(last, 
                        negativeVectors[j].rotate((double)-(j+1)*i/Time));
                g.drawLine(last.getX(), last.getY(),
                        next.getX(), next.getY());
            }
            paintedPoints.add(next);
            try {
                Thread.sleep((long) 30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public FSA() {
        super("GUI");
        initial();
        FT();
        drawVectors();
    }
    public static void main(String[] args) {
        new FSA();
    }
}