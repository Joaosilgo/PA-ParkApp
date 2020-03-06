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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
//import javafxgraph_v2.graph.Vertex;
import dijkstra.graph.Vertex;

/**
 * Strategy that places the vertices within a circle, guaranteeing that the vertices
 * are placed alphabetically ordered by their element().toString().
 * 
 * @author brunomnsilva
 */
public class CircularSortedPlacementStrategy extends CircularPlacementStrategy {

    @Override
    protected <T> Collection<Vertex<T>> sort(Map<Vertex<T>, GraphVertex> map) {
        List<Vertex<T>> list = new ArrayList<>();
        list.addAll(map.keySet());
        
        Collections.sort(list, new Comparator<Vertex<T>>() {
            @Override
            public int compare(Vertex<T> t, Vertex<T> t1) {
                return t.element().toString().compareToIgnoreCase(t1.element().toString());
            }
        });
        
        return list;
    }
    
}
