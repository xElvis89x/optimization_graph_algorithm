package com.elvis.graph;

import com.elvis.model.SimpleBooleanGraph;
import traer.animation.Smoother3D;
import traer.physics.Particle;
import traer.physics.ParticleSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 19:17
 * http://www.aharef.info/static/htmlgraph/sourcecode.html
 */
public class GraphVisualizator {

    int NODE_SIZE = 7;
    float EDGE_LENGTH = 100;
    float EDGE_STRENGTH = 0.2f;

    SimpleBooleanGraph graph;

    public GraphVisualizator(SimpleBooleanGraph graph) {
        this.graph = graph;
    }

    //    public static void main(String[] args) {
//        new GraphVisualizator().start();
//    }

    ParticleSystem physics;
    Smoother3D centroid;
    JFrame frame;

    public void start() {
        physics = new ParticleSystem(0, 0.25f);
        centroid = new Smoother3D(0.0f, 0.0f, 1.0f, 0.8f);
        physics.clear();


        frame = new JFrame("Graph Visualisation");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        Vis v = new Vis();
        frame.setContentPane(v);
        frame.pack();
        frame.setVisible(true);

        new Thread(v).start();
    }


    class Vis extends JPanel implements Runnable {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            physics.tick(0.8f);
            if (physics.numberOfParticles() > 1) {
                updateCentroid();
            }
            centroid.tick();
            g.translate((int) g.getClipBounds().getWidth() / 2, (int) g.getClipBounds().getHeight() / 2);
            g.translate((int) -centroid.x(), (int) -centroid.y());

            for (int i = 0; i < physics.numberOfSprings(); ++i) {
                traer.physics.Spring e = physics.getSpring(i);
                Particle a = e.getOneEnd();
                Particle b = e.getTheOtherEnd();
                g.drawLine((int) a.position().x(), (int) a.position().y(), (int) b.position().x(), (int) b.position().y());
            }
            for (int i = 0; i < physics.numberOfParticles(); ++i) {
                Particle v = physics.getParticle(i);
                g.fillOval((int) v.position().x() - NODE_SIZE / 2, (int) v.position().y() - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            }
        }

        List<Particle> listParticle = new ArrayList<Particle>();
        Random rand = new Random();

        @Override
        public void run() {
            int step = 0;
            long time = 0;
            while (true) {
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis();
                    if (step < graph.getSize()) {
                        Particle a = physics.makeParticle(10, rand.nextInt(100) - 50, rand.nextInt(100) - 50, 0);
                        for (int i = 0; i < step; i++) {
                            if (graph.getCell(i, step)) {
                                physics.makeSpring(a, listParticle.get(i), EDGE_STRENGTH, EDGE_STRENGTH, EDGE_LENGTH);
                            }
                        }
                        listParticle.add(a);
                        step++;
                    }
                }

                frame.getContentPane().repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void updateCentroid() {
        float
                xMax = Float.NEGATIVE_INFINITY,
                xMin = Float.POSITIVE_INFINITY,
                yMin = Float.POSITIVE_INFINITY,
                yMax = Float.NEGATIVE_INFINITY;

        for (int i = 0; i < physics.numberOfParticles(); ++i) {
            Particle p = physics.getParticle(i);
            xMax = Math.max(xMax, p.position().x());
            xMin = Math.min(xMin, p.position().x());
            yMin = Math.min(yMin, p.position().y());
            yMax = Math.max(yMax, p.position().y());
        }
        float deltaX = xMax - xMin;
        float deltaY = yMax - yMin;
        if (deltaY > deltaX) {
            centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, 600 / (deltaY + 50));
        } else {
            centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, 800 / (deltaX + 50));
        }
    }
}
