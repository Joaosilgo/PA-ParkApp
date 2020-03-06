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

import java.util.Map;
import java.util.Random;
//import javafxgraph_v2.graph.Vertex;
import dijkstra.graph.Vertex;

/**
 * Strategy that randomly places the vertices within the allowed bounds.
 * @author brunomnsilva
 */
public class RandomPlacementStrategy implements VertexPlacementStrategy {

    @Override
    public <T> void placeVertices(double width, double height, Map<Vertex<T>, GraphVertex> map) {
        
        Random rand = new Random();

        for (Vertex<T> vertex : map.keySet()) {
            
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            
            GraphVertex get = map.get(vertex);
            get.centerXProperty().set(x);
            get.centerYProperty().set(y);
          
        }
    }
    
}
