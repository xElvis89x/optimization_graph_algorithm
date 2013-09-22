package com.elvis.graph.visualizer.jgrapht;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: el
 * Date: 23.08.13
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class GraphVisualizer {

    private static final Color DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( 530, 320 );

    //
    private JGraphModelAdapter m_jgAdapter;

    private void init() {
        ListenableGraph g = new ListenableDirectedGraph( DefaultEdge.class );

        // create a visualization using JGraph, via an adapter
        m_jgAdapter = new JGraphModelAdapter( g );

        new SimpleGraph<>()
        getContentPane().add(jgraph);


        // add some sample data (graph manipulated via JGraphT)
        g.addVertex( "v1" );
        g.addVertex( "v2" );
        g.addVertex( "v3" );
        g.addVertex( "v4" );

        g.addEdge( "v1", "v2" );
        g.addEdge( "v2", "v3" );
        g.addEdge( "v3", "v1" );
        g.addEdge( "v4", "v3" );

    }

    private Panel getContentPane() {
        return null;
    }

}
