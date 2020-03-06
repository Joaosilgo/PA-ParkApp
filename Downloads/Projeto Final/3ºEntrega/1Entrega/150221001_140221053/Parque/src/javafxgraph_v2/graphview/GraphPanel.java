/* 
 * The MIT License
 *
 * Copyright 2018 brunomnsilva@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package javafxgraph_v2.graphview;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
//import javafxgraph_v2.graph.Edge;
//import javafxgraph_v2.graph.Graph;
//import javafxgraph_v2.graph.Vertex;
import dijkstra.graph.Edge;
import dijkstra.graph.Vertex;
import dijkstra.graph.Graph;
import dijkstra.model.Ponto;
import dijkstra.model.Conexao;
import dijkstra.model.ParquePlannerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



/**
 * JavaFX pane that is capable of plotting a Graph ADT.
 *
 * Vertices and edges can be dragged by the user. Tooltips are added to vertices
 * and edges so one can inspect the stored value.
 *
 * @author brunomnsilva
 * @param <Ponto>
 * @param <Conexao>
 * @param <V> Type of element for the vertices
 * @param <E> Type of element for the edges.
 */
public class GraphPanel<Ponto, Conexao> extends Pane {

    public Map<Edge<Conexao, Ponto>, GraphEdge> getGraphEdgeMap() {
        return graphEdgeMap;
    }

    /*
        BEGIN CONFIGURATION:
    
        You should change the following configurations accordingly to your preferences.
    */
    public static final double GRAPH_VERTEX_DIAMETER = 5;
    public static final Color GRAPH_VERTEX_STROKE = Color.GAINSBORO;
    public static final Color GRAPH_VERTEX_FILL = Color.GAINSBORO;
    public static final Font GRAPH_VERTEX_LABEL_FONT = Font.font("Verdana", FontWeight.BOLD, 10);

    public static final Color GRAPH_EDGE_COLOR = Color.LIGHTCORAL.deriveColor(1, 1, 1, 0.4);
    public static final int GRAPH_EDGE_WIDTH = 4;
    public static final Font GRAPH_EDGE_LABEL_FONT = Font.font("Verdana", FontWeight.LIGHT, 10);
    
    public static final Color ARROW_COLOR = Color.DARKBLUE;

    public static final boolean GRAPH_VERTEX_USE_TOOLTIP = false;
    public static final boolean GRAPH_EDGE_USE_TOOLTIP = false;
    
    private static final double MAX_ANGLE_PLACEMENT = 20;
    
    /*
        END CONFIGURATION.
    */
    
    
    private final Graph<Ponto, Conexao> theGraph;
    private final VertexPlacementStrategy placementStrategy;
    private Map<Vertex<Ponto>, GraphVertex> graphVertexMap;
    private Map<Edge<Conexao, Ponto>, GraphEdge> graphEdgeMap;

   public GraphPanel(Graph<Ponto, Conexao> theGraph, VertexPlacementStrategy placementStrategy) throws FileNotFoundException  {
        if (theGraph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (placementStrategy == null) {
            throw new IllegalArgumentException("Placement strategy cannot be null");
        }

        this.theGraph = theGraph;
        this.placementStrategy = placementStrategy;

        graphVertexMap = new HashMap<>();
        graphEdgeMap = new HashMap<>();
         Image image = new Image(new FileInputStream("src\\images\\xx.PNG")); 
         //Image image = new Image(getClass().getResourceAsStream("map1.PNG"));
         
        ImageView background = new ImageView(image);
        background.setPreserveRatio(true); 
        background.setFitWidth(1259);
        background.setFitHeight(606);
         ToolBar toolBar = new ToolBar(
            new Button("New"),
            new Button("Open"),
            new Button("Save"),
            new Separator(),
            new Button("Clean"),
            new Button("Compile"),
            new Button("Run"),
            new Separator(),
            new Button("Debug"),
            new Button("Profile")
        );
this.getChildren().addAll(background);
   }

    /**
     * Resets vertices and edges to default colors. Default colors are defined
     * in constants in this class.
     */
    public void resetColorsToDefault() {

        for (Vertex<Ponto> vertex : graphVertexMap.keySet()) {
            GraphVertex graphVertex = graphVertexMap.get(vertex);
            graphVertex.setFill(GRAPH_VERTEX_FILL);
            graphVertex.setStroke(GRAPH_VERTEX_STROKE);
        }

        for (Edge<Conexao, Ponto> edge : graphEdgeMap.keySet()) {
            GraphEdge graphEdge = graphEdgeMap.get(edge);
            graphEdge.setFill(GRAPH_EDGE_COLOR);
        }

    }

    /**
     * Change a vertex's colors.
     *
     * @param v the graph vertex
     * @param fill color to fill the vertex shape
     * @param stroke color for the outline of the vertex shape
     * @return true if vertex was found and color changed; false otherwise.
     */
    public boolean setVertexColor(Vertex<Ponto> v, Color fill, Color stroke) {
        GraphVertex graphVertex = graphVertexMap.get(v);
        if (graphVertex == null) {
            return false;
        }

        graphVertex.setFill(fill);
        graphVertex.setStroke(stroke);
        return true;
    }

    /**
     * Change an edge's color
     *
     * @param e the graph edge
     * @param c the color for the edge
     * @param opacity the opacity to be applied to the color <i>c</i> [0,1];
     * @return true if edge was found and color changed; false otherwise.
     */
    public boolean setEdgeColor(Edge<Conexao, Ponto> e, Color c, double opacity) {
        GraphEdge graphEdge = graphEdgeMap.get(e);
        if (graphEdge == null) {
            return false;
        }

        graphEdge.setStroke(c.deriveColor(1, 1, 1, opacity));
        return true;
    }

    /**
     * Method that effectively plots the graph.
     *
     * This method must be called AFTER an instance of this class was added to a
     * JavaFX scene. It it is called before, then the initial values for width
     * and height of this pane are zero, effectively causing all vertices to be
     * placed in the top left corner. *
     */
    public void plotGraph() {
        if (this.getScene() == null) {
            throw new IllegalStateException("You must call this method after the instance was added to a scene.");
        }

        /* create vertex graphical representations */
        for (Vertex<Ponto> vertex : listOfVertices()) {
            GraphVertex vertexAnchor = new GraphVertex(0, 0, GRAPH_VERTEX_DIAMETER);
            graphVertexMap.put(vertex, vertexAnchor);
        }

        /* call strategy to place the vertices in their initial locations */
        placementStrategy.placeVertices(this.widthProperty().doubleValue(),
                this.heightProperty().floatValue(), graphVertexMap);

        /* create edges graphical representations between existing vertices */
        //this is used to guarantee that no duplicate edges are ever inserted
        List<Edge<Conexao, Ponto>> edgesToPlace = listOfEdges();

        for (Vertex<Ponto> vertex : graphVertexMap.keySet()) {

            //o do prof
            //  Iterable<Edge<Conexao, Ponto>> incidentEdges = theGraph.incidentEdges(vertex);
             //o meu
            Iterable<Edge<Conexao, Ponto>> incidentEdges =theGraph.outboundEdges(vertex);
            for (Edge<Conexao, Ponto> edge : incidentEdges) {

                //if already plotted, ignore edge.
                if (!edgesToPlace.contains(edge)) {
                    continue;
                }

                /* (begin) TODO: You may have to adapt this code for other Graph variants. */
                Vertex<Ponto> oppositeVertex = theGraph.opposite(vertex, edge);
                System.out.println("oposir: " + oppositeVertex);
                GraphVertex graphVertex = graphVertexMap.get(vertex);
                GraphVertex graphVertexOpposite = graphVertexMap.get(oppositeVertex);
                /* (end) TODO: */

                /**
                 * The following code is generic enough for all situations, so
                 * you can leave it the way it is.
                 */
                double midpointX = (graphVertex.getCenterX() + graphVertexOpposite.getCenterX()) / 2;
                double midpointY = (graphVertex.getCenterY() + graphVertexOpposite.getCenterY()) / 2;
                Point2D midpoint = new Point2D(midpointX, midpointY);

                Point2D startpoint = new Point2D(graphVertex.getCenterX(), graphVertex.getCenterY());

              if (getTotalEdgesBetween(vertex, oppositeVertex) > 1) {
                 //   if(getPathsBetween(vertex.element(), oppositeVertex.element()).size()>1)
                  //  {
                    //random rotation between [-45,45] degrees
                    double angle = MAX_ANGLE_PLACEMENT;
                    midpoint = UtilitiesPoint2D.rotate(midpoint, 
                            startpoint, 
                            (-angle) + Math.random() * (angle - (-angle)));
                }

                GraphEdge graphEdge = new GraphEdge();
                graphEdge.startXProperty().bind(graphVertex.centerXProperty());
                graphEdge.startYProperty().bind(graphVertex.centerYProperty());

                graphEdge.setControlX1(midpoint.getX());
                graphEdge.setControlY1(midpoint.getY());
                graphEdge.setControlX2(midpoint.getX());
                graphEdge.setControlY2(midpoint.getY());

                graphEdge.endXProperty().bind(graphVertexOpposite.centerXProperty());
                graphEdge.endYProperty().bind(graphVertexOpposite.centerYProperty());

                graphEdge.setStroke(GRAPH_EDGE_COLOR);
                graphEdge.setStrokeWidth(GRAPH_EDGE_WIDTH);

                graphEdge.setFill(Color.TRANSPARENT);

                this.getChildren().add(graphEdge);
                graphEdgeMap.put(edge, graphEdge);

                if(GRAPH_VERTEX_USE_TOOLTIP) {
                    Tooltip t = new Tooltip(edge.element().toString());
                    Tooltip.install(graphEdge, t);
                } else {
                    Text label = new Text(edge.element().toString());
                    label.setFont(GRAPH_EDGE_LABEL_FONT);

                    label.xProperty().bind(graphEdge.controlX1Property().add(graphEdge.controlX1Property()).divide(2).subtract(label.getLayoutBounds().getWidth() / 2));
                    label.yProperty().bind(graphEdge.controlY1Property().add(graphEdge.controlY1Property()).divide(2));

                    this.getChildren().add(label);
                }
 /* 
                (begin) TODO:
                Place arrow on edge. Remove or adapt according to your requirements.
                
                if param t < 0.5 : Direction is from graphVertex -> graphVertexOpposite 
                else in the other direction
                 */
                //double[] arrowShape = new double[]{0, 0, 10, 20, -10, 20};
                double[] arrowShape = new double[]{0, 0, GRAPH_EDGE_WIDTH, 2*GRAPH_EDGE_WIDTH, -GRAPH_EDGE_WIDTH, 2*GRAPH_EDGE_WIDTH};
                
                
                Arrow arrow1 = new Arrow(graphEdge, ARROW_COLOR, 0.2f, arrowShape);
                graphEdge.addArrow(arrow1);
              //  this.getChildren().add(arrow1);

                Arrow arrow2 = new Arrow(graphEdge, ARROW_COLOR, 0.8f, arrowShape);
                graphEdge.addArrow(arrow2);
               // this.getChildren().add(arrow2);
                /* (end) TODO: */

                /* Edge already placed. */
                edgesToPlace.remove(edge);
            }

        }

        /* place anchors above lines */
        for (Vertex<Ponto> vertex : graphVertexMap.keySet()) {
            GraphVertex anchor = graphVertexMap.get(vertex);

            /* Style for the vertex representation */
            anchor.setFill(GRAPH_VERTEX_FILL);
            anchor.setStroke(GRAPH_VERTEX_STROKE);
            anchor.setStrokeWidth(4);
            anchor.setStrokeType(StrokeType.OUTSIDE);

            this.getChildren().add(anchor);
            
            if(GRAPH_VERTEX_USE_TOOLTIP) {
                Tooltip t = new Tooltip(vertex.element().toString());
                Tooltip.install(anchor, t);
            } else {
                Text label = new Text(vertex.element().toString());
                label.setFont(GRAPH_VERTEX_LABEL_FONT);

                label.xProperty().bind(anchor.centerXProperty().add(anchor.centerXProperty()).divide(2).subtract(label.getLayoutBounds().getWidth() / 2));
                label.yProperty().bind(anchor.centerYProperty().add(anchor.centerYProperty()).divide(2).add(GRAPH_VERTEX_DIAMETER * 2));

                this.getChildren().add(label);
            }
            
            
        }

        enableReziseListener();
    }

    /**
     * TODO: It may be necessary to adjust this method based, if you use another
     * Graph variant
     */
    private int getTotalEdgesBetween(Vertex<Ponto> v, Vertex<Ponto> u) {

        int count = 0;
        for (Edge<Conexao, Ponto> edge : theGraph.edges()) {
            if (edge.vertices()[0] == v && edge.vertices()[1] == u
                    || edge.vertices()[0] == u && edge.vertices()[1] == v) {
                count++;
            }
        }
        return count;
    }
     public List<Conexao> getPathsBetween(Ponto ponto1, Ponto ponto2)
            throws ParquePlannerException {
        if (ponto1 == null || ponto2 == null) {
            System.out.println("Nao existe Ponto");
        }

        ArrayList<Conexao> paths = new ArrayList();
        for (Vertex<Ponto> item : theGraph.vertices()) {
            if (item.element().equals(ponto1)) {
                for (Edge<Conexao, Ponto> elem : theGraph.outboundEdges(item)) {
                    Vertex<Ponto> opposite = theGraph.opposite(item, elem);
                    if (opposite.element() == ponto2) {
                        paths.add(elem.element());
                    }
                }
            }
        }
        return paths;
    }

    private List<Edge<Conexao, Ponto>> listOfEdges() {
        List<Edge<Conexao, Ponto>> list = new LinkedList<>();
        for (Edge<Conexao, Ponto> edge : theGraph.edges()) {
            list.add(edge);
        }
        return list;
    }

    private List<Vertex<Ponto>> listOfVertices() {
        List<Vertex<Ponto>> list = new LinkedList<>();
        for (Vertex<Ponto> vertex : theGraph.vertices()) {
            list.add(vertex);
        }
        return list;
    }

    
    
    /**
     * Change the following code only if you know what you are doing.
     */
    
    private void enableReziseListener() {

        Scene scene = this.getScene();
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(this, ratio, initHeight, initWidth, this);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {

        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        public SceneSizeChangeListener(Node rootNode, double ratio, double initHeight, double initWidth, Pane contentPane) {
            this.scene = rootNode.getScene();
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor
                    = newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }

}
