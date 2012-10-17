package com.elvis.graph;

import com.elvis.model.SimpleWeightGraph;
import traer.animation.Smoother3D;
import traer.physics.Particle;
import traer.physics.ParticleSystem;
import traer.physics.Vector3D;

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

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final Dimension size = new Dimension(WIDTH, HEIGHT);
    int NODE_SIZE = 7;
    float EDGE_LENGTH = 5;
    float EDGE_STRENGTH = 0.2f;

    SimpleWeightGraph graph;

    public GraphVisualizator(SimpleWeightGraph graph) {
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
        frame.setPreferredSize(size);
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
            Graphics2D g2 = (Graphics2D) g;
            physics.tick(0.8f);
            if (physics.numberOfParticles() > 1) {
                updateCentroid();
            }
            centroid.tick();
            g2.translate(frame.getSize().width / 2, frame.getSize().height / 2);
            g2.scale(centroid.z() * 0.7, centroid.z() * 0.7);
            g2.translate(-centroid.x(), -centroid.y());

            for (int i = 0; i < physics.numberOfSprings(); ++i) {
                traer.physics.Spring e = physics.getSpring(i);
                Particle a = e.getOneEnd();
                Particle b = e.getTheOtherEnd();
                g2.drawLine((int) a.position().x(), (int) a.position().y(), (int) b.position().x(), (int) b.position().y());
            }
            for (int i = 0; i < physics.numberOfParticles(); ++i) {
                Particle v = physics.getParticle(i);
                g2.setColor(v.mass() > 20 ? Color.RED : Color.BLACK);
                g2.fillOval((int) v.position().x() - NODE_SIZE / 2, (int) v.position().y() - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            }
        }

        List<Particle> listParticle = new ArrayList<Particle>();
        Random rand = new Random();

        @Override
        public void run() {
            int step = 0;
            long time = 0;
            Particle center = physics.makeParticle(100, 0, 0, 0);
            center.makeFixed();
            while (true) {
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis();
                    if (step < graph.getSize()) {
                        Particle a = physics.makeParticle(10, rand.nextInt(100) - 50, rand.nextInt(100) - 50, 0);
                        for (int i = 0; i < step; i++) {
                            if (graph.getCell(i, step) != 0) {
                                physics.makeSpring(a, listParticle.get(i), EDGE_STRENGTH, EDGE_STRENGTH, EDGE_LENGTH * graph.getCell(i, step));
                            }
                            //physics.makeAttraction(a, listParticle.get(i), -50, -1);
                            physics.makeAttraction(center, listParticle.get(i), -50, -1);

                        }
                        listParticle.add(a);
                        step++;
                    }
                }

                Vector3D c = new Vector3D(0, 0, 0);
                int partCount = physics.numberOfParticles();
                for (int i = 1; i < physics.numberOfParticles(); i++) {
                    c.set(
                            physics.getParticle(i).position().x() + c.x(),
                            physics.getParticle(i).position().y() + c.y(),
                            physics.getParticle(i).position().z() + c.z()
                    );
                }
                partCount = partCount - 1;
                center.position().set(c.x() / partCount, c.y() / partCount, c.z() / partCount);

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
            centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, frame.getSize().height / (deltaY + 50));
        } else {
            centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, frame.getSize().width / (deltaX + 50));
        }
    }
}
