/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dijkstra.graph.Edge;
import dijkstra.graph.Graph;
import dijkstra.graph.GraphEdgeList;
import dijkstra.graph.InvalidEdgeException;
import dijkstra.graph.InvalidVertexException;
import dijkstra.graph.Vertex;
import dijkstra.model.Conexao;
import dijkstra.model.Conexao.TYPE_CONECT;
import dijkstra.model.ParquePlanner;
import dijkstra.model.ParquePlannerException;
import dijkstra.model.Ponto;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author joaos
 */
public class DigraphJUnitTest {

    private GraphEdgeList<Ponto, Conexao> graph;
    private Ponto ponto;
    private Ponto ponto1;
    private Ponto ponto2;
    private Ponto ponto3;
    private Vertex<Ponto> p;
    private Vertex<Ponto> p1;
    private Vertex<Ponto> p2;
    private Vertex<Ponto> p3;
    private Conexao c;
    private Conexao c1;
    private ParquePlanner planner;

    public DigraphJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.graph = new GraphEdgeList<>();
        ponto = new Ponto("1", "teste1");
        ponto1 = new Ponto("2", "teste2");
        ponto2 = new Ponto("3", "teste3");
        ponto3 = new Ponto("4", "teste4");
        p = this.graph.insertVertex(ponto);
        p1 = this.graph.insertVertex(ponto1);
        p2 = this.graph.insertVertex(ponto2);
        p3 = this.graph.insertVertex(ponto3);
        c = new Conexao(1, TYPE_CONECT.CAMINHO, "conexao1-2", true, 10, 10);
        c1 = new Conexao(2, TYPE_CONECT.CAMINHO, "conexao2-3", true, 10, 10);
        //  this.graph.insertEdge(p, p1, c);
        //  this.graph.insertEdge(p2, p3, c1);
        planner = new ParquePlanner();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void numVertices() {

        assertEquals("numVertices=1", true, graph.numVertices() == 4);

    }

    @Test
    public void numEdges() {

        this.graph.insertEdge(ponto, ponto1, c);
        assertEquals("numEdges=1", true, graph.numEdges() == 1);
    }

    @Test
    public void inboundEdges() {

        List<Edge<Conexao, Ponto>> inboundEdges = new ArrayList<>();
        inboundEdges.add(graph.insertEdge(ponto, ponto2, c));
        assertEquals(graph.inboundEdges(p1), inboundEdges);

    }

    @Test
    public void outboundEdges() {

        List<Edge<Conexao, Ponto>> outboundEdges = new ArrayList<>();
        outboundEdges.add(graph.insertEdge(ponto, ponto2, c));
        assertEquals(graph.outboundEdges(p), outboundEdges);
    }

    /**
     * Returns the vertices of the graph as an iterable collection
     *
     * @return set of vertices
     */

    /**
     * Returns the edges of the graph as an iterable collection.
     *
     * @return set of edges
     */
    @Test
    public void edges() {
        List<Edge<Conexao, Ponto>> edges = new ArrayList<>();
        edges.add(this.graph.insertEdge(ponto, ponto1, c));
        edges.add(this.graph.insertEdge(ponto2, ponto3, c1));
        assertEquals(this.graph.edges(), edges);
    }

    /**
     * Given a vertex and an edge, returns the opposite vertex.
     *
     * @param v a vertex Vertex<V> v, Edge<E, V> e
     * @param e an edge
     * @return the opposite vertex Vertex<V>
     * @exception InvalidVertexException if the vertex is invalid for the graph.
     * @exception InvalidEdgeException if the edge is invalid for the graph.
     */
    @Test
    public void opposite() {
        Edge<Conexao, Ponto> e = this.graph.insertEdge(ponto, ponto1, c);
        Vertex<Ponto> v = this.graph.opposite(p, e);
        assertEquals(true, p1.element().getId().equalsIgnoreCase(v.element().getId()));
    }

    /**
     * Tests whether two vertices are adjacent.
     *
     * @param u a vertex (outbound, if digraph)
     * @param v another vertex (inbound, if digraph)
     * @return true if they are adjacent, false otherwise.
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    @Test
    public void areAdjacent() {
        Edge<Conexao, Ponto> e = this.graph.insertEdge(ponto, ponto1, c);
        assertEquals(true, this.graph.areAdjacent(p, p1));
    }

    /**
     * Inserts a new vertex with a given element, returning its reference.
     *
     * @param vElement the element to store at the vertex. Cannot be null.V
     * vElement
     * @return the reference of the newly created vertex Vertex<V>
     */
    @Test
    public void insertVertex() {
        Ponto p4 = new Ponto("5", "Teste4");
        Vertex<Ponto> v = this.graph.insertVertex(p4);

        assertEquals(true, v.element().getId() == p4.getId());
    }

    /**
     * Inserts a new edge with a given element between two vertices, returning
     * its reference.
     *
     * @param u a vertex (outbound, if digraph)
     * @param v another vertex (inbound, if digraph)
     * @param edgeElement the element to store in the new edge
     * @return the reference for the newly created edge
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    @Test
    public void insertEdge() {
        //c1 = new Conexao(2, TYPE_CONECT.CAMINHO, "conexao2-3", true, 10, 10);
        Edge<Conexao, Ponto> edge3 = graph.insertEdge(p, p1, c1);

        for (Edge<Conexao, Ponto> item : graph.edges()) {
            if (item.equals(edge3)) {
                assertEquals(true, item.equals(edge3));
            }
        }

    }

    @Test
    public void getId() {
        assertEquals(true, ponto.getId().equals("1"));
    }

    @Test
    public void getPonto() {
        assertEquals(true, ponto.getPonto().equals("teste1"));
    }

    @Test

    public void TestPontotoString() {

        assertEquals(true, ponto.toString().equals(ponto.getPonto() + "(" + ponto.getId() + ")"));
    }

    @Test
    public void addPonto() {
        planner.addPonto(ponto);
        assertEquals(true, planner.getListPontos().contains(ponto));
    }
}
