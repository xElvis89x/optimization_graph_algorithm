package com.elvis.graph.visualizer.phisics;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import traer.animation.Smoother3D;
import traer.physics.Particle;
import traer.physics.ParticleSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User: el
 * Date: 22.10.12
 * Time: 20:52
 */
public class VisualizerView extends JPanel {
    final float zoomAddConst = 0.1f;

    int NODE_SIZE = 7;

    private boolean[] MaxCutMask;

    private JPanel contentPane;
    private JButton zoomOutButton;
    private JButton zoomInButton;

    public JButton getAddPoint() {
        return addPoint;
    }

    private JButton addPoint;

    ParticleSystem physics;
    Smoother3D centroid;

    public VisualizerView(ParticleSystem particleSystem, Smoother3D centroid) {
        this.physics = particleSystem;
        this.centroid = centroid;
        $$$setupUI$$$();

        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoom += zoomAddConst;
                updateUI();
            }
        });
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoom - zoomAddConst > 0) {
                    zoom -= zoomAddConst;
                }
                updateUI();
            }
        });

        VisualizationMoveHandler visualizationMoveHandler = new VisualizationMoveHandler();
        addMouseMotionListener(visualizationMoveHandler);
        addMouseListener(visualizationMoveHandler);
    }

    public void setMaxCutMask(boolean[] maxCutMask) {
        MaxCutMask = maxCutMask;
    }

    Point center = new Point();
    float zoom = 0.7f;

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        zoomOutButton = new JButton();
        zoomOutButton.setBorderPainted(false);
        zoomOutButton.setContentAreaFilled(false);
        zoomOutButton.setIcon(new ImageIcon(getClass().getResource("/com/elvis/graph/visualizer/phisics/zoomOut.png")));
        zoomOutButton.setText("");
        contentPane.add(zoomOutButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPane.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        zoomInButton = new JButton();
        zoomInButton.setBorderPainted(true);
        zoomInButton.setContentAreaFilled(false);
        zoomInButton.setIcon(new ImageIcon(getClass().getResource("/com/elvis/graph/visualizer/phisics/zoomIn.png")));
        zoomInButton.setText("");
        contentPane.add(zoomInButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addPoint = new JButton();
        addPoint.setText("+");
        contentPane.add(addPoint, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    class VisualizationMoveHandler extends MouseAdapter {
        Point lastLocation;

        @Override
        public void mousePressed(MouseEvent e) {
            lastLocation = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            center.x += e.getPoint().x - lastLocation.x;
            center.y += e.getPoint().y - lastLocation.y;
            lastLocation = e.getPoint();
            updateUI();
        }
    }

    Map<Particle, Integer> particleIntegerMap = new HashMap<Particle, Integer>();

    public void setParticleIntegerMap(Map<Particle, Integer> particleIntegerMap) {
        this.particleIntegerMap = particleIntegerMap;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        physics.tick(0.8f);
        if (physics.numberOfParticles() > 1) {
            updateCentroid();
        }
        centroid.tick();
        g2.translate(getSize().width / 2, getSize().height / 2);
        g2.scale(centroid.z() * zoom, centroid.z() * zoom);
        g2.translate(-centroid.x() + center.x, -centroid.y() + center.y);

        for (int i = 0; i < physics.numberOfSprings(); ++i) {
            traer.physics.Spring e = physics.getSpring(i);
            Particle a = e.getOneEnd();
            Particle b = e.getTheOtherEnd();
            int aInd = particleIntegerMap.get(a);
            int bInd = particleIntegerMap.get(b);
            if (MaxCutMask != null && MaxCutMask[aInd] != MaxCutMask[bInd]) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.BLACK);
            }
            g2.drawLine((int) a.position().x(), (int) a.position().y(), (int) b.position().x(), (int) b.position().y());
        }
        for (int i = 0; i < physics.numberOfParticles(); ++i) {
            Particle v = physics.getParticle(i);

            if (i != 0 && MaxCutMask != null) {
                g2.setColor(MaxCutMask[i - 1] ? Color.GREEN : Color.BLUE);
            } else {
                g2.setColor(Color.RED);
            }

            g2.fillOval((int) v.position().x() - NODE_SIZE / 2, (int) v.position().y() - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
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
            centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, getSize().height / (deltaY + 50));
        } else {
            centroid.setTarget(xMin + 0.5f * deltaX, yMin + 0.5f * deltaY, getSize().width / (deltaX + 50));
        }
    }


    private void createUIComponents() {
        contentPane = this;
    }
}
