package com.elvis.graph.visualizer;

import com.elvis.model.SimpleWeightGraph;
import traer.animation.Smoother3D;
import traer.physics.Particle;
import traer.physics.ParticleSystem;
import traer.physics.Vector3D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 19:17
 * http://www.aharef.info/static/htmlgraph/sourcecode.html
 */
public class GraphVisualizer {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final Dimension size = new Dimension(WIDTH, HEIGHT);


    SimpleWeightGraph graph;

    public GraphVisualizer(SimpleWeightGraph graph) {
        this.graph = graph;
    }

    boolean[] maxCutMask;

    public void setMaxCutMask(boolean[] maxCutMask) {
        this.maxCutMask = maxCutMask;
    }
    //    public static void main(String[] args) {
//        new GraphVisualizer().start();
//    }

    ParticleSystem physics;
    Smoother3D centroid;
    JFrame frame;

    Map<Particle, Integer> particleIntegerMap = new HashMap<Particle, Integer>();

    public void start() {
        physics = new ParticleSystem(0, 0.25f);
        centroid = new Smoother3D(0.0f, 0.0f, 1.0f, 0.8f);
        physics.clear();

        frame = new JFrame("Graph Visualizer");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(size);
        VisualizerView v = new VisualizerView(physics, centroid);
        v.setMaxCutMask(maxCutMask);
        v.setParticleIntegerMap(particleIntegerMap);
        frame.setContentPane(v);
        frame.pack();
        frame.setVisible(true);


        new Thread(new Runnable() {

            float EDGE_LENGTH = 5;
            float EDGE_STRENGTH = 0.2f;

            java.util.List<Particle> listParticle = new ArrayList<Particle>();
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
                        if (step < graph.size()) {
                            Particle a = physics.makeParticle(10, rand.nextInt(100) - 50, rand.nextInt(100) - 50, 0);
                            particleIntegerMap.put(a, step);
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

        }).start();
    }


}
