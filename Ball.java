/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.cop2800.project;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Ball {
    double x;
    double y;
    double vx;
    double vy;
    double gravity;
    int angle;
    boolean showStats;
    ArrayList<Point> pastPositions;

    Ball(int x, int y, double vx, double vy, double gravity, int angle, boolean showStats) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.gravity = gravity;
        this.angle = angle;
        this.showStats = showStats;
        this.pastPositions = new ArrayList<>();
        this.pastPositions.add(new Point(x+25, y+25));
    }
}
