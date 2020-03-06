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

import java.util.Collection;
import java.util.Map;
import javafx.geometry.Point2D;
//import javafxgraph_v2.graph.Vertex;
import dijkstra.graph.Vertex;

/**
 * Strategy that places the vertices within a circle.
 * 
 * @author brunomnsilva
 */
public class CircularPlacementStrategy implements VertexPlacementStrategy {

    @Override
    public <T> void placeVertices(double width, double height,Map<Vertex<T>, GraphVertex> map) {
       
        Point2D center = new Point2D(width / 2, height / 2);
        int N = map.size();
        double angleIncrement = -360f / N;
        
        //place first vertice north position, others in clockwise manner
        boolean first = true;
        Point2D p = null;
        for (Vertex<T> vertex : sort(map)) {

            GraphVertex get = map.get(vertex);
            
            if (first) {
                //verifiy smaller width and height.
                if(width > height)
                    p = new Point2D(center.getX(),
                            center.getY() - height / 2 + get.radiusProperty().doubleValue() * 2);
                else
                    p = new Point2D(center.getX(),
                            center.getY() - width / 2 + get.radiusProperty().doubleValue() * 2);
                
        
                first = false;
            } else {
                p = UtilitiesPoint2D.rotate(p, center, angleIncrement);
            }

            //GraphVertex get = map.get(vertex);
            get.centerXProperty().set(p.getX());
            get.centerYProperty().set(p.getY());
            
        }
    }
    
    protected <T> Collection<Vertex<T>> sort(Map<Vertex<T>, GraphVertex> map) {
        return map.keySet();
    }
}
