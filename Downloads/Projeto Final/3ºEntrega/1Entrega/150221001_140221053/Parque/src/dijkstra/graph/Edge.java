package dijkstra.graph;

/**
 * Data-independent representation of an edge.
 *
 * @author João e Jorge
 * @param <E> Type of value stored in the edge
 * @param <V> Type of value stored in the vertices that this edge connects.
 */
public interface Edge<E, V> {

    /**
     * Returns the element (object reference) stored in this edge.
     *
     * @return stored element
     */
    public E element();

    /**
     * Returns references of both vertices that this edge connects in the form
     * of an array.
     *
     * @return an array of length 2, i.e., vertices()[0] and vertices()[1]
     */
    public Vertex<V>[] vertices();

    /**
     * Method that returns the inbound vertex of MyEdge
     *
     * @return inbound vertex
     */
    public Vertex<V> getVertexInbound();

    /**
     * Method that returns the outbound vertex of MyEdge
     *
     * @return outbound vertex
     */
    public Vertex<V> getvertexOutbound();
}
