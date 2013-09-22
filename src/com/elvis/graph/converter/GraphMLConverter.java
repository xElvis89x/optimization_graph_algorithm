package com.elvis.graph.converter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: el
 * Date: 17.07.13
 * Time: 0:15
 * To change this template use File | Settings | File Templates.
 */
public class GraphMLConverter {

    class XMLPoint extends Point2D.Float {
        int index;
    }

    protected FileNameExtensionFilter graphFilter = new FileNameExtensionFilter("yEd graph file", "graphml");

    public void start() {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(true);
        chooser.resetChoosableFileFilters();
        chooser.addChoosableFileFilter(graphFilter);
        chooser.showOpenDialog(null);
        for (File file : chooser.getSelectedFiles()) {
            convert(file);
        }
        JOptionPane.showMessageDialog(null, "Graphs already converted");
    }

    public void convert(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("node");
            Map<String, XMLPoint> points = new HashMap<String, XMLPoint>();
            int ind = 0;
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    XMLPoint point = new XMLPoint();
                    point.index = ind++;
                    point.x = new Float(eElement.getElementsByTagName("y:Geometry").item(0).getAttributes().getNamedItem("x").getNodeValue());
                    point.y = new Float(eElement.getElementsByTagName("y:Geometry").item(0).getAttributes().getNamedItem("y").getNodeValue());
                    points.put(((Element) nNode).getAttribute("id"), point);
                }
            }

            float[][] matrix = new float[points.size()][points.size()];

            NodeList edgeList = doc.getElementsByTagName("edge");
            for (int i = 0; i < edgeList.getLength(); i++) {
                Node nNode = edgeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    XMLPoint source = points.get(eElement.getAttribute("source"));
                    XMLPoint target = points.get(eElement.getAttribute("target"));
                    matrix[target.index][source.index] = matrix[source.index][target.index] = (float) source.distance(target);
                }
            }
            FileOutputStream outputStream = null;
            String path = file.getPath().replace(".graphml", ".graph");
            writeToStream(points.size(), "mw", matrix, outputStream = new FileOutputStream(path));
            outputStream.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToStream(int size, String type, float[][] matrix, FileOutputStream outputStream) throws IOException {
        outputStream.write((type + "\n").getBytes());
        outputStream.write((size + "\n").getBytes());
        for (int k = 0; k < size; k++) {
            for (int j = 0; j < size; j++) {
                outputStream.write((matrix[k][j] + " ").getBytes());
            }
            outputStream.write("\n".getBytes());
        }
    }
}
