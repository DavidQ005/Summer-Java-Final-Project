/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.myproject.cop2800.project;

/**
 *
 * @author david
 */
import java.awt.Graphics;
import javax.swing.JLayeredPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
//import javax.swing.JLabel;
//import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.RenderingHints;

public class ProjectileSimulator implements ActionListener {
    
    Slider speedSlider;
    Slider angleSlider;
    Slider gravSlider;
    JButton runButton = new JButton();   
    JButton undoButton = new JButton();
    JButton resetButton = new JButton();
    //private JLabel jLabel1;
    JPanel pn;
    Graphics g;
    double time = 0.1; 
    ArrayList<Ball> balls = new ArrayList();
    Ball initialBall;
    JCheckBox slowMoCheckBox;
    JCheckBox showLabelsCheckBox;
    JCheckBox showTitleCheckBox;
    int updateInterval = 19;

    public static void main(String[] args) {
        ProjectileSimulator project = new ProjectileSimulator();
        project.run();
    }

    public void run() {
    JFrame fr = new JFrame();
    fr.setBounds(10, 10, 1600, 900);
    fr.setDefaultCloseOperation(3);

    speedSlider = new Slider("Initial Speed (m/s): ", 0, 150, 75, 10, 25, 1);
    int initialS = (int) speedSlider.getValue();
    
    angleSlider = new Slider("Launch Angle (degrees): ", 0, 90, 45, 5, 10, 1);
    
    gravSlider = new Slider("Gravity (m/s^2): ", 0, 20, 10, 1, 5, 1);
    
    //ImageIcon background = new ImageIcon("sky.png");
    //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("sky.png"))); 

    showLabelsCheckBox = new JCheckBox("Launch Stats");
    showLabelsCheckBox.setBounds(1150, 710, 100, 30);
    
    showTitleCheckBox = new JCheckBox("Hide Title");
    showTitleCheckBox.setBounds(1150, 740, 100, 30);
    showTitleCheckBox.addActionListener(this);
    
    double angleInRadians = Math.toRadians(0);
    double vx = initialS * Math.cos(angleInRadians);
    double vy = initialS * Math.sin(angleInRadians);
    initialBall = new Ball(10, 590, vx, vy, 0, 0, false);

    runButton.setBounds(1260, 685, 90, 140);
    runButton.addActionListener(this);
    runButton.setText("Run");
    
    undoButton.setBounds(1365, 685, 90, 140);
    undoButton.setText("Undo");
    undoButton.addActionListener(this);
    
    resetButton.setBounds(1470, 685, 90, 140);
    resetButton.setText("Reset");
    resetButton.addActionListener(this);
    
    slowMoCheckBox = new JCheckBox("Slow Motion");
    slowMoCheckBox.setBounds(1150, 680, 100, 30);
    slowMoCheckBox.addActionListener(this);
    
    pn = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //paint ground level line
            int thickness = 5;
            int y = 650;
            int x1 = 0;
            int x2 = 1600;
            for (int i = 0; i < thickness; i++) {
                g.drawLine(x1, y+i, x2, y+i);
            }
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //paint title text
            if (!showTitleCheckBox.isSelected()) {
               if (g instanceof Graphics2D) {
               Graphics2D g2d = (Graphics2D) g;
               g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
               }
               g.setFont(new Font("Plain", Font.BOLD, 30));
               g.drawString("Projectile Motion Simulator", 600, 40);
            }
            //paint ball
            g.fillOval((int) initialBall.x,(int) initialBall.y, 50, 50);
            for (Ball b : balls) { 
                g.fillOval ((int) b.x,(int) b.y, 50, 50); 
                g.setColor(Color.BLACK);
                Color originalColor = g.getColor();
                
                //paint launch stats on ball
                if(b.showStats){
                        g.setColor(Color.WHITE);
                        double initialS = b.vx / Math.cos(Math.toRadians(b.angle));
                        g.setFont(new Font("default", Font.PLAIN, 11).deriveFont(11.4f));

                        String label1 = String.format("S: %d", (int) initialS);
                        String label2 = String.format("A: %d", b.angle);
                        String label3 = String.format("G: %.1f", b.gravity);
                        
                        g.drawString(label1, (int)b.x+10, (int)b.y+18);
                        g.drawString(label2, (int)b.x+10, (int)b.y+28);
                        g.drawString(label3, (int)b.x+10, (int)b.y+38);
                }
                
                g.setColor(Color.BLACK);
                
                //paint dotted line that traces ball trajectory
                Graphics2D g2 = (Graphics2D) g;
                float[] dashPattern = {2f, 10f, 2f};
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dashPattern, 0f));
                for(int i=1; i<b.pastPositions.size()-3; i++){
                    Point p1 = b.pastPositions.get(i-1);
                    Point p2 = b.pastPositions.get(i);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }  
        }
    };       
    
    pn.setBounds(0, 0, fr.getWidth(), 900);

    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, fr.getWidth(), 900);
    layeredPane.add(pn, JLayeredPane.DEFAULT_LAYER); 
    speedSlider.panel.setBounds(0, 700, 350, 200);
    layeredPane.add(speedSlider.panel, Integer.valueOf(300)); 
    angleSlider.panel.setBounds(400, 700, 350, 200);
    layeredPane.add(angleSlider.panel, Integer.valueOf(300));
    gravSlider.panel.setBounds(800, 700, 350, 200);
    layeredPane.add(gravSlider.panel, Integer.valueOf(300));

    fr.setLayout(null);
    fr.add(runButton);
    //fr.add(new JLabel(background));
    fr.add(undoButton);
    fr.add(resetButton);
    fr.add(slowMoCheckBox);
    fr.add(showLabelsCheckBox);
    fr.add(showTitleCheckBox);
    fr.add(layeredPane);
    fr.setResizable(false);
    fr.setLocationRelativeTo(null);
    fr.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== runButton){
         
            int initialS = (int) speedSlider.getValue(); 
            double gravity = gravSlider.getValue();
            int angle = (int) angleSlider.getValue();
            
            double angleInRadians = Math.toRadians(angle); 
            double vx = initialS * Math.cos(angleInRadians); 
            double vy = initialS * Math.sin(angleInRadians); 
            
            Ball ball = new Ball(10, 590, vx, vy, gravity, angle, showLabelsCheckBox.isSelected()); 
            balls.add(ball); 
            
            Thread animationThread = new Thread(new Runnable() {
                public void run() {
                        Ball b = ball;
                        //run ball animation until ball reaches ground line
                        while (true) {
                            b.x += b.vx * time;
                            b.vy -= gravity * time;
                            b.y -= (b.vy + (b.vy - gravity * time) / 2) * time;
                           
                            b.pastPositions.add(new Point((int) b.x + 25, (int) b.y + 25));
                            
                            if (b.y + 50 > 650) {
                                b.y = 650 - 49;
                                pn.repaint((int) b.x - 40,(int) b.y - 39, 110, 122);
                                break;
                            }
                            
                            pn.repaint((int) b.x - 40,(int) b.y - 39, 110, 122);
                            try {
                                Thread.sleep(updateInterval);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                } 
            });
            animationThread.start();    
        } else if (e.getSource() == undoButton) {
            if (!balls.isEmpty()) {
                balls.remove(balls.size()-1);
                pn.repaint(0,0,1600,650);
            }
        } else if (e.getSource() == resetButton) {
            if (!balls.isEmpty()) {
                balls.clear();
                pn.repaint(0,0,1600,650);
            }
        } else if (e.getSource() == slowMoCheckBox) {
            if (slowMoCheckBox.isSelected()) {
                updateInterval = 35;
            } else {
                updateInterval = 19;
            }
        } else if (e.getSource() == showTitleCheckBox) {
            pn.repaint(600, 10, 400, 40);
        }
        else {
            balls.clear();
            pn.repaint();
        }
    } 
}
