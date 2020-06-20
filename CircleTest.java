package com.codebin;

import javafx.util.Pair;
import java.util.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.LinkedList;
import java.awt.geom.AffineTransform;


public class CircleTest extends JPanel {

    private static final int SIZE = 900;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;
    private Ellipse2D[] intArray;
    private Graph g;


    public CircleTest(Graph g) {
        super(true);
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        this.n = g.getSize();
        intArray = new Ellipse2D[n];
        this.g = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        LinkedList<Pair<Integer, Integer>> list[] = this.g.getAdjList();

        super.paintComponent(g);
        Font  f1  = new Font(Font.SERIF, Font.PLAIN,  20);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        a = getWidth() / 2;
        b = getHeight() / 2;
        int m = Math.min(a, b);
        r = 4 * m / 5;
        int r2 = Math.abs(m - r) / 2;
        g2d.setColor(Color.blue);
        for (int i = 0; i < n; i++) {
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(a + r * Math.cos(t));
            int y = (int) Math.round(b + r * Math.sin(t));
            String s = String.valueOf(i);
            g2d.setFont(f1);
            intArray[i] = new Ellipse2D.Double(x - r2, y - r2, 0.7 * r2, 0.7 * r2);
            int stringX = (int)Math.round(x -  0.83*r2 );
            int stringY = (int)Math.round(y - 0.5*r2 );
            g2d.drawString(s, stringX, stringY);
            g2d.draw(intArray[i]);
        }
       for (int i = 0; i < n; i++){
            if (list[i].size() != 0){
                for (int j = 0 ; j < list[i].size(); j++){
                    double from = angleBetween(intArray[i], intArray[list[i].get(j).getKey()
                            ]);
                    double to = angleBetween(intArray[list[i].get(j).getKey()], intArray[i]);

                    Point2D pointFrom = getPointOnCircle(intArray[i], from);
                    Point2D pointTo = getPointOnCircle(intArray[list[i].get(j).getKey()], to);

                    g2d.setColor(Color.RED);
                    Line2D line = new Line2D.Double(pointFrom, pointTo);
                    g2d.draw(line);
                    EndingWithArrow arrow = new EndingWithArrow();
                    
                    if(this.g.isDirected()){
                        AffineTransform at = AffineTransform.getTranslateInstance(
                                pointTo.getX() - (arrow.getBounds2D().getWidth() / 2d), pointTo.getY());
                        at.rotate(from, arrow.getBounds2D().getCenterX(), 0);
                        arrow.transform(at);
                        g2d.draw(arrow);
                    }
                    //int x1 = (int)Math.round(intArray[i].getX());
                    //int y1 = (int)Math.round(intArray[i].getY());
                    //int x2 = (int)Math.round(intArray[list[i].get(j).getKey()].getX());
                    //int y2 = (int)Math.round(intArray[list[i].get(j).getKey()].getY());
                    //int height = (int)Math.round(intArray[i].getHeight());
                    //int width = (int)Math.round(intArray[i].getWidth());
                    //g2d.drawLine(x1, y1 + (height/2), x2 + (width/2), y2);

                    g2d.setColor(Color.BLACK);
                    String tag = String.valueOf(list[i].get(j).getValue());
                    int helpX = (int)Math.round((Math.abs(pointFrom.getX() + pointTo.getX()))/2);
                    int helpY = (int)Math.round((Math.abs(pointFrom.getY() + pointTo.getY()))/2);
                    int tagX = (int)Math.round((Math.abs(pointFrom.getX() + helpX))/2);
                    int tagY = (int)Math.round((Math.abs(pointFrom.getY() + helpY))/2);
                    g2d.drawString(tag, tagX, tagY);

              /*      int helpX1 = (int)Math.round((Math.abs(x1 + x2))/2);
                    int helpY1 = (int)Math.round((Math.abs(y1 + y2))/2);
                    int tagX1 = (int)Math.round((Math.abs(x1 + helpX))/2);
                    int tagY1 = (int)Math.round((Math.abs(y1 + helpY))/2);
                    g2d.drawString(tag, tagX1, tagY1);
                     */
                }
            }
        }
        g2d.dispose();
    }

    public static int enterInt(Scanner sc){
        int userVal = -1;

        while (userVal < 0){
            try {
                System.out.println("Enter number:" );
                userVal = sc.nextInt();
                if(userVal < 0){
                    System.out.println("Number has to be greater than 0");
                    continue;
                }

            }catch ( InputMismatchException e){
                System.out.println("Error - Entered number is not an Integer");
                sc.next();
            }
        }
        return userVal;
    }

    public static int enterInt(Scanner sc, int first, int second){
        int userVal = -1;


        while (userVal != first || userVal != second){
        try {
            System.out.println("Enter 1-yes or 0-no" );
            userVal = sc.nextInt();
            if(userVal == first)return first;
            else if (userVal == second)return second;
            else continue;

        }catch ( InputMismatchException e){
            System.out.println("Error - Entered number is not an Integer");
            sc.next();
        }
    }
        return userVal;
    }

    private static void create() {

        Scanner sc= new Scanner(System.in);
        System.out.println("Enter vertices number of a Graph");
        int vertices = enterInt(sc);

        System.out.println("Enter edges number of a Graph");
        int edges = enterInt(sc);

        System.out.println("Is the graph directed? For yes enter 1, for no enter 0");
        int directed = enterInt(sc, 0,1);
        boolean isDirected;

        if(directed == 1) {
            isDirected = true;
        }else isDirected = false;

        Graph g = new Graph(vertices, isDirected);
        for (int i = 0; i < edges; i++){
            System.out.println("Enter edge information fist - source index, second destination index, third - weight of the edge");
            int source, destination, weight;
                source = enterInt(sc);
                destination = enterInt(sc);
                weight = enterInt(sc);
                g.addEdge(source, destination, weight);
        }
        sc.close();/*
        Graph g = new Graph(6, true);
        g.addEdge(0,1, 13);
        g.addEdge(0,2, 11);
        g.addEdge(0,3, 1);
        g.addEdge(0, 4, 3);
        g.addEdge(1, 4, 4);
        g.addEdge(2, 5, 2);  */

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new CircleTest(g));
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                create();
            }
        });
    }

    protected Point2D center(Rectangle2D bounds) {
        return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
    }

    protected double angleBetween(Shape from, Shape to) {
        return angleBetween(center(from.getBounds2D()), center(to.getBounds2D()));
    }

    protected double angleBetween(Point2D from, Point2D to) {
        double x = from.getX();
        double y = from.getY();

        double deltaX = to.getX() - x;
        double deltaY = to.getY() - y;
        double rotation = -Math.atan2(deltaX, deltaY);
        rotation = Math.toRadians(Math.toDegrees(rotation) + 180);
        return rotation;
    }

    protected Point2D getPointOnCircle(Shape shape, double radians) {
        Rectangle2D bounds = shape.getBounds();
        Point2D point = center(bounds);
        return getPointOnCircle(point, radians, Math.max(bounds.getWidth(), bounds.getHeight()) / 2d);
    }

    protected Point2D getPointOnCircle(Point2D center, double radians, double radius) {

        double x = center.getX();
        double y = center.getY();

        radians = radians - Math.toRadians(90.0);

        double xPosy = Math.round((float) (x + Math.cos(radians) * radius));
        double yPosy = Math.round((float) (y + Math.sin(radians) * radius));

        return new Point2D.Double(xPosy, yPosy);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 900);
    }

}