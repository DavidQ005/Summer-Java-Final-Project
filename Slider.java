/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.cop2800.project;

/**
 *
 * @author david
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class Slider implements ChangeListener{

    //JFrame frame;
    JPanel panel;
    JLabel label;
    JSlider slider;
    
    String sliderText;
    double min;
    double max;
    double defaultV;
    double minTick;
    double majTick;
    int scale;
    
    Slider (String sliderText, double min, double max, double defaultV, double minTick, double majTick, int scale) {
        this.sliderText = sliderText;
        this.min = min;
        this.max = max;
        this.minTick = minTick;
        this.majTick = majTick;
        this.defaultV = defaultV;
        this.scale = scale;
        Slider();
    }
    
    
   private void Slider() {
        //frame = new JFrame();
        panel = new JPanel(); 
        label = new JLabel();
        slider = new JSlider((int) (min*scale),(int) (max*scale),(int) (defaultV*scale));
        
        slider.setPreferredSize(new Dimension(50, 50));
        
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing((int) minTick * scale);
        
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing((int) majTick * scale);
        
        slider.setPaintLabels(true);
        
        label.setText(sliderText + slider.getValue());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Setif", Font.BOLD, 15));
        
        slider.addChangeListener(this);
        
        panel.setLayout(new BorderLayout());
        panel.setBounds(0,0,1600, 200);
        slider.setBounds(0, 0, 1600, 200);
        label.setBounds(150, 80, 200, 80);
        panel.add(slider, BorderLayout.CENTER);
        panel.add(label, BorderLayout.NORTH);
        //frame.add(panel);
        //frame.setSize(420,420);
        //frame.setVisible(true);
    }
    
    public double getValue() {
        return (double) slider.getValue() / scale;
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        label.setText(sliderText + String.format("%.1f", getValue())); 
    }
}

