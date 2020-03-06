package dijkstra.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ADT Graph implementation that stores a collection of edges (and vertices) and
 * where each edge contains the references for the vertices it connects.
 *
 * Does not allow duplicates of stored elements through <b>equals</b> criteria.
 *
 * @author João e Jorge
 * @param <E> Type of values stored in edges
 * @param <V> Type of values stored in vertices
 */
public class GraphEdgeList<V, E> implements Graph<V, E> {

    /* inner classes are defined at the end of the class, so are the auxiliary methods 
     */
    private Map<V, Vertex<V>> vertices;
    private Map<E, Edge<E, V>> edges;

    /**
     * Creates a empty graph.
     */
    public GraphEdgeList() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    /**
     * Returns the number of vertices
     *
     * @return vertices size
     */
    @Override
    public int numVertices() {
        return vertices.size();
    }

    /**
     * Returns the number of edges
     *
     * @return edges size
     */
    @Override
    public int numEdges() {
        return edges.size();
    }

    /**
     * Returns the edges of the graph as an iterable collection.
     *
     * @return set of edges
     */
    @Override
    public Iterable<Vertex<V>> vertices() {
        List<Vertex<V>> list = new ArrayList<>();
        for (Vertex<V> v : vertices.values()) {
            list.add(v);
        }
        return list;
    }

    /**
     * Returns a vertex's incident edges as an iterable collection.
     *
     * @return set of vertices
     */
    @Override
    public Iterable<Edge<E, V>> edges() {
        List<Edge<E, V>> list = new ArrayList<>();
        for (Edge<E, V> e : edges.values()) {
            list.add(e);
        }
        return list;
    }

    /**
     * Returns a vertex's incident edges as an iterable collection.
     *
     * @param v vertex
     * @return set of vertices
     */
    @Override
    public Iterable<Edge<E, V>> incidentEdges(Vertex<V> v) throws InvalidEdgeException {

        checkVertex(v);

        List<Edge<E, V>> incidentEdges = new ArrayList<>();
        for (Edge<E, V> edge : edges.values()) {
            if (((MyEdge) edge).contains(v)) {
                incidentEdges.add(edge);
            }
        }
        return incidentEdges;
    }

    /**
     * Method that returns an iterable of inbound edges from a vertex
     *
     * @param v vertex
     * @return inbound edges from a vertex
     * @throws InvalidEdgeException
     */
    @Override
    public Iterable<Edge<E, V>> inboundEdges(Vertex<V> v) throws InvalidEdgeException {

        checkVertex(v);

        List<Edge<E, V>> inboundEdges = new ArrayList<>();
        for (Edge<E, V> edge : edges.values()) {
            if (((MyEdge) edge).getVertexInbound() == v) {
                inboundEdges.add(edge);
            }
        }
        return inboundEdges;
    }

    /**
     * Method that returns an iterable of outbound edges from a vertex
     *
     * @param v vertex
     * @return outbound edges from a vertex
     * @throws InvalidEdgeException
     */
    @Override
    public Iterable<Edge<E, V>> outboundEdges(Vertex<V> v) throws InvalidEdgeException {

        checkVertex(v);

        List<Edge<E, V>> outboundEdges = new ArrayList<>();
        for (Edge<E, V> edge : edges.values()) {
            if (edge.getvertexOutbound().equals(v)) {
                outboundEdges.add(edge);
            }
        }
        return outboundEdges;
    }

    /**
     * Method that checks if a given vertex has an opposite vertex
     *
     * @param v vertex
     * @param e edge
     * @return opposite vertex if exists or the vertex if not exists
     * @throws InvalidVertexException
     * @throws InvalidEdgeException
     */
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(v);
        MyEdge edge = checkEdge(e);

        if (!edge.contains(v)) {
            return null;
        }
        if (edge.getVertexInbound() == v) {
            return edge.getvertexOutbound();
        } else {
            return edge.getVertexInbound();
        }
    }

    /**
     * Method that checks if two vertices are adjacent
     *
     * @param u vertex 1
     * @param v vertex 2
     * @return true if the vertices are adjacent and false if not
     * @throws InvalidVertexException
     */
    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidVertexException {
        checkVertex(v);
        checkVertex(u);

        for (Edge<E, V> edge : edges.values()) {
            if (edge.getVertexInbound().equals(u) && edge.getvertexOutbound().equals(v) || edge.getVertexInbound().equals(v) && edge.getvertexOutbound().equals(u)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that inserts a vertex with a certain element to the graph HashMap
     *
     * @param vElement vertex element
     * @return inserted vertex
     */
    @Override
    public Vertex<V> insertVertex(V vElement) {
        if (existsVertexWith(vElement)) {
            throw new IllegalArgumentException("There's already a vertex with this element.");
        }

        MyVertex newVertex = new MyVertex(vElement);

        vertices.put(vElement, newVertex);

        return newVertex;
    }

    /**
     * Method that inserts an edge with a certain element to the graph HashMap
     *
     * @param u vertex 1 
     * @param v vertex 2
     * @param edgeElement edge element
     * @return inserted edge
     * @throws InvalidVertexException
     */
    @Override
    public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement) throws InvalidVertexException {
        MyVertex outVertex = checkVertex(u);
        MyVertex inVertex = checkVertex(v);

        MyEdge newEdge = new MyEdge(edgeElement, outVertex, inVertex);

        edges.put(edgeElement, newEdge);

        return newEdge;
    }

    /**
     * Method that inserts two edges with the same element to the graph HashMap
     *
     * @param to vertex to
     * @param from vertex from
     * @param edgeElement edge element
     * @throws IllegalArgumentException
     */
    @Override
    public void insertBiEdge(Vertex<V> to, Vertex<V> from, E edgeElement) throws IllegalArgumentException {
        insertEdge(to, from, edgeElement);
        insertEdge(from, to, edgeElement);
    }

    /**
     * Method that inserts an edge with a certain element to the graph HashMap
     *
     * @param vElement1
     * @param vElement2
     * @param edgeElement
     * @return inserted edge
     * @throws InvalidVertexException
     */
    @Override
    public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) throws InvalidVertexException {
        if (!existsVertexWith(vElement1)) {
            throw new InvalidVertexException("No vertex contains " + vElement1);
        }
        if (!existsVertexWith(vElement2)) {
            throw new InvalidVertexException("No vertex contains " + vElement2);
        }

        MyVertex outVertex = vertexOf(vElement1);
        MyVertex inVertex = vertexOf(vElement2);

        MyEdge newEdge = new MyEdge(edgeElement, outVertex, inVertex);

        edges.put(edgeElement, newEdge);

        return newEdge;
    }

    /**
     * Method that removes a given vertex from the graph
     *
     * @param v
     * @return the element contained in the removed vertex
     * @throws InvalidVertexException
     */
    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        checkVertex(v);

        V element = v.element();

        Iterable<Edge<E, V>> incidentEdges = incidentEdges(v);
        for (Edge<E, V> edge : incidentEdges) {
            edges.remove(edge.element());
        }

        vertices.remove(v.element());

        return element;
    }

    /**
     * Method that removes a given edge from the graph
     *
     * @param e
     * @return the element contained in the removed edge
     * @throws InvalidEdgeException
     */
    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {
        checkEdge(e);

        E element = e.element();
        edges.remove(e.element());

        return element;
    }

    /**
     * Method that replaces an old element from a given vertex for a new given
     * element
     *
     * @param v
     * @param newElement
     * @return old element from the vertex
     * @throws InvalidVertexException
     */
    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {
        if (existsVertexWith(newElement)) {
            throw new IllegalArgumentException("There's already a vertex with this element.");
        }

        MyVertex vertex = checkVertex(v);

        V oldElement = vertex.element;
        vertex.element = newElement;

        return oldElement;
    }

    /**
     * Method that replaces an old element from a given edge for a new given
     * element
     *
     * @param e
     * @param newElement
     * @return old element from the edge
     * @throws InvalidEdgeException
     */
    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {
        if (existsEdgeWith(newElement)) {
            throw new IllegalArgumentException("There's already an edge with this element.");
        }

        MyEdge edge = checkEdge(e);

        E oldElement = edge.element;
        edge.element = newElement;

        return oldElement;
    }

    /**
     * Method that checks if the given element belongs to a vertex of the graph
     *
     * @param vElement
     * @return the vertex where the element exists if exists or null if not
     * exists
     */
    private MyVertex vertexOf(V vElement) {
        for (Vertex<V> v : vertices.values()) {
            if (v.element().equals(vElement)) {
                return (MyVertex) v;
            }
        }
        return null;
    }

    /**
     * Method that checks if the given element belongs to a vertex of the graph
     *
     * @param vElement
     * @return true if the element exists and false if not
     */
    private boolean existsVertexWith(V vElement) {
        return vertices.containsKey(vElement);
    }

    /**
     * Method that checks if the given element belongs to a edge of the graph
     *
     * @param edgeElement
     * @return true if the element exists and false if not
     */
    private boolean existsEdgeWith(E edgeElement) {
        return edges.containsKey(edgeElement);
    }

    /**
     * Method toString that prints the graph
     *
     * @return string with the vertices and edges from the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                String.format("Graph with %d vertices and %d edges:\n", numVertices(), numEdges())
        );

        sb.append("--- Vertices: \n");
        for (Vertex<V> v : vertices.values()) {
            sb.append("\t").append(v.toString()).append("\n");
        }
        sb.append("\n--- Edges: \n");
        for (Edge<E, V> e : edges.values()) {
            sb.append("\t").append(e.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Classe MyVertex that implements a vertex
     */
    class MyVertex implements Vertex<V> {

        V element;

        /**
         * Constructor for the class MyVertex
         *
         * @param element
         */
        public MyVertex(V element) {
            this.element = element;
        }

        /**
         * Method that returns the element from MyVertex
         *
         * @return element from vertex
         */
        @Override
        public V element() {
            return this.element;
        }

        /**
         * Method toString that prints a vertex
         *
         * @return string of the vertex
         */
        @Override
        public String toString() {
            return "Ponto: " + element;
        }
    }

    /**
     * Classe MyEdge that implements an edge
     */
    class MyEdge implements Edge<E, V> {

        E element;
        Vertex<V> vertexOutbound;
        Vertex<V> vertexInbound;

        /**
         * Constructor for the class MyEdge
         *
         * @param element
         * @param vertexOutbound
         * @param vertexInbound
         */
        public MyEdge(E element, Vertex<V> vertexOutbound, Vertex<V> vertexInbound) {
            this.element = element;
            this.vertexOutbound = vertexOutbound;
            this.vertexInbound = vertexInbound;
        }

        /**
         * Method that returns the element from MyEdge
         *
         * @return element from edge
         */
        @Override
        public E element() {
            return this.element;
        }

        /**
         * Method that checks if MyEdge contains a given vertex
         *
         * @param v
         * @return true if contains and false if not
         */
        public boolean contains(Vertex<V> v) {
            return (vertexOutbound == v || vertexInbound == v);
        }

        /**
         * Method that returns the vertices of MyEdge
         *
         * @return list with the vertices of MyEdge
         */
        @Override
        public Vertex<V>[] vertices() {

            Vertex[] vertices = new Vertex[2];
            vertices[0] = vertexOutbound;
            vertices[1] = vertexInbound;
            return vertices;
        }

        /**
         * Method toString that prints a Edge
         *
         * @return string with the edge and the belonged vertices
         */
        @Override
        public String toString() {
            return "Edge{{" + element + "}, vertexOutbound=" + vertexOutbound.toString()
                    + ", vertexInbound=" + vertexInbound.toString() + '}';
        }

        /**
         * Method that returns the inbound vertex of MyEdge
         *
         * @return inbound vertex
         */
        @Override
        public Vertex<V> getVertexInbound() {
            return vertexInbound;
        }

        /**
         * Method that returns the outbound vertex of MyEdge
         *
         * @return outbound vertex
         */
        @Override
        public Vertex<V> getvertexOutbound() {
            return vertexOutbound;
        }
    }

    /**
     * Checks whether a given vertex is valid and belongs to this graph
     *
     * @param v
     * @return vertex
     * @throws InvalidVertexException
     */
    private MyVertex checkVertex(Vertex<V> v) throws InvalidVertexException {

        MyVertex vertex;
        try {
            vertex = (MyVertex) v;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("Not a vertex.");
        }

        if (!vertices.containsKey(vertex.element)) {
            throw new InvalidVertexException("Vertex does not belong to this graph.");
        }
        return vertex;
    }

    /**
     * Checks whether a given edge is valid and belongs to this graph
     *
     * @param e
     * @return edge
     * @throws InvalidEdgeException
     */
    private MyEdge checkEdge(Edge<E, V> e) throws InvalidEdgeException {

        MyEdge edge;
        try {
            edge = (MyEdge) e;
        } catch (ClassCastException ex) {
            throw new InvalidVertexException("Not an adge.");
        }

        if (!edges.containsKey(edge.element)) {
            throw new InvalidEdgeException("Edge does not belong to this graph.");
        }
        return edge;
    }
}
