package dijkstra.graph;

/**
 * Data-independent representation of a vertex.
 * @author brunomnsilva
 * @param <V> Type of value stored in the vertex.
 */
public interface Vertex<V> {
    /**
     * Returns the element (object reference) stored in this vertex.
     * @return stored element
     */
    public V element();
}
