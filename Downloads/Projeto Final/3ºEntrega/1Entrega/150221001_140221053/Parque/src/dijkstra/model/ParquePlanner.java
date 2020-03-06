package dijkstra.model;

import MVC.MODELS.Percurso;
import MVC.MODELS.PercursoCareTaker;
import MVC.MODELS.PercursoException;
import dijkstra.graph.Edge;
import dijkstra.graph.Graph;
import dijkstra.graph.GraphEdgeList;
import dijkstra.graph.InvalidVertexException;
import dijkstra.graph.Vertex;
import dijkstra.model.Conexao.TYPE_CONECT;
import dijkstra.model.ParquePlanner.Criteria;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Observable;
import javafxgraph_v2.graphview.GraphPanel;

/**
 * Classe modelo que gere o parque
 *
 * @author João e Jorge
 */
public class ParquePlanner extends Observable {

    /**
     * Class Enum Criteria Distance and Cost
     */
    public enum Criteria {

        DISTANCE,
        COST;

        /**
         *
         * @return String : return the unit that we are working for the Criteria
         */
        public String getUnit() {
            switch (this) {
                case COST:
                    return "€";
                case DISTANCE:
                    return "Miles";
            }
            return "Unknown";
        }
    };

    /**
     * Class Enum TipoPercurso Pe and Bicicleta
     */
    public enum TipoPercurso {

        PE,
        BICICLETA;

        /**
         *
         * @return String : return the unit that we are working for the
         * TipoPercurso
         */
        public String toString() {
            switch (this) {
                case PE:
                    return "Pe";
                case BICICLETA:
                    return "Bicicleta";
            }
            return "Unknown";
        }
    };

    private final Graph<Ponto, Conexao> graph;
    private GraphPanel graphView;
    private List<Ponto> listPontos;
    private List<Conexao> listConexoes;
    private static String line;
    private List<Ponto> pontosDeInteresse;
    private Percurso PercursoActual;
    private final PercursoCareTaker caretaker;

    /**
     * Getter method for PercursoActual
     *
     * @return PercursoActual
     */
    public Percurso getPercursoActual() {
        return PercursoActual;
    }

    /**
     * Getter method for PontosDeInteresse
     *
     * @return pontosDeInteresse
     */
    public List<Ponto> getPontosDeInteresse() {
        return pontosDeInteresse;
    }

    /**
     * Method that adds a new PontoInteresse to the current Percurso
     *
     * @param p ponto p
     * @throws PercursoException
     */
    public void addPontoInteresse(Ponto p) throws PercursoException {
        caretaker.saveState(PercursoActual);
        pontosDeInteresse.add(p);
        setChanged();
        notifyObservers();
    }

    /**
     * Method that clears all PontosInteresse from the current Percurso
     *
     * @throws PercursoException
     */
    public void clearPontosInteresse() throws PercursoException {
        pontosDeInteresse.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Undo method for Percurso
     *
     * @throws PercursoException
     */
    public void undo() throws PercursoException {
        caretaker.restoreState(PercursoActual); //lança excecao se nao puder fazer!
        setChanged();
        notifyObservers();
    }

    /**
     * Method that generates a new Percurso
     *
     * @param p tipo de percurso
     * @param criteria criteria 
     * @param pontosEscolha pontos escolhidos
     * @param teste lista de teste
     * @return PercursoActual
     * @throws PercursoException
     */
    public Percurso gerarPercurso(TipoPercurso p, Criteria criteria, List<Ponto> pontosEscolha, List<Conexao> teste) throws PercursoException {
        int sumCost = 0;
        int sumDistancia = 0;
        List<Ponto> pontosCaminho;
        pontosCaminho = new ArrayList<>();
        for (int i = 0; i < pontosEscolha.size(); i++) {
            Ponto first = pontosEscolha.get(i);
            if (pontosEscolha.size() > i + 1) {
                Ponto second = pontosEscolha.get(i + 1);
                if (criteria.equals(Criteria.DISTANCE)) {
                    sumDistancia += minimumCostPath(p, criteria, first, second, pontosEscolha, pontosCaminho, teste);//esuerda pontos interess --direita lista vazia
                } else {
                    sumCost += minimumCostPath(p, criteria, first, second, pontosEscolha, pontosCaminho, teste);
                }
            }
        }

        if (criteria.equals(Criteria.DISTANCE)) {
            sumCost += minimumCostPathPreco(pontosCaminho);

        } else {

            sumDistancia += minimumCostPathDistancia(pontosCaminho);
        }

        this.PercursoActual = new Percurso("teste", p, sumCost, sumDistancia, pontosEscolha, pontosCaminho, teste);
        PercursoActual.setCusto(sumCost);
        PercursoActual.setDistancia(sumDistancia);
        return PercursoActual;
    }

    /**
     * Constructor method for ParquePlanner
     */
    public ParquePlanner() {
        this.graph = new GraphEdgeList<>();
        this.listPontos = new ArrayList<>();
        this.listConexoes = new ArrayList<>();

        caretaker = new PercursoCareTaker();
        this.pontosDeInteresse = new ArrayList<>();

        readFile();
    }

    /**
     * Method that checks if a given Ponto already exists in the graph
     *
     * @param ponto ponto
     * @return Vertex<Ponto>
     * @throws ParquePlannerException
     */
    private Vertex<Ponto> checkPonto(Ponto ponto) throws ParquePlannerException {
        if (ponto == null) {
            throw new ParquePlannerException("Ponto cannot be null");
        }
        Vertex<Ponto> find = null;
        for (Vertex<Ponto> v : graph.vertices()) {
            if (v.element().equals(ponto)) { //equals was overriden in Ponto!!
                find = v;
            }
        }
        if (find == null) {
            throw new ParquePlannerException("Ponto with code (" + ponto.getId() + ") does not exist");
        }
        return find;
    }

    /**
     * Method that adds a new Ponto to the graph
     *
     * @param ponto ponto
     * @throws ParquePlannerException
     */
    public void addPonto(Ponto ponto) throws ParquePlannerException {
        if (ponto == null) {
            throw new ParquePlannerException("Ponto cannot be null");
        }
        try {
            graph.insertVertex(ponto);
            listPontos.add(ponto);
        } catch (InvalidVertexException e) {
            throw new ParquePlannerException("Airport with code (" + ponto.getId() + ") already exists");
        }
    }

    /**
     * Method that adds a new Conexao to the graph
     *
     * @param ponto1 ponto 1
     * @param ponto2 ponto 2
     * @param conexao conexao
     * @throws ParquePlannerException
     */
    public void addConexao(Ponto ponto1, Ponto ponto2, Conexao conexao)
            throws ParquePlannerException {

        if (conexao == null) {
            throw new ParquePlannerException("Conexao is null");
        }
        Vertex<Ponto> a1 = checkPonto(ponto1);
        Vertex<Ponto> a2 = checkPonto(ponto2);
        Conexao c = new Conexao(conexao.getId_c(), conexao.getTipo(), conexao.getConexao(), conexao.isNavegabilidade(), conexao.getCusto(), conexao.getDistancia());
        try {
            if (conexao.getTipo().equals(TYPE_CONECT.PONTE)) {
                graph.insertEdge(ponto1, ponto2, conexao);
                graph.insertEdge(a1, a2, conexao);
                listConexoes.add(conexao);
            } else {
                graph.insertEdge(ponto1, ponto2, conexao);
                graph.insertEdge(ponto2, ponto1, c);
            }
        } catch (InvalidVertexException e) {
            System.out.println("Exception thrown  :" + e);
        }
    }

    /**
     * Method that returns all the Pontos from the list
     *
     * @return listPontos
     */
    public List<Ponto> getListPontos() {
        return listPontos;
    }

    /**
     * Method that returns all the Conexoes from the list
     *
     * @return listConexoes
     */
    public List<Conexao> getListConexoes() {
        return listConexoes;
    }

    /**
     * Getter method for the graph
     *
     * @return graph
     */
    public Graph<Ponto, Conexao> getGraph() {
        return graph;
    }

    /**
     * Getter method for the PontoById
     *
     * @param id ID do ponto
     * @return Ponto if the Id exists
     */
    public Ponto getPontoById(String id) {
        for (Vertex<Ponto> item : graph.vertices()) {
            if (item.element().getId().equalsIgnoreCase(id)) {
                return item.element();
            }
        }
        return null;
    }

    /**
     * Getter method for Ponto
     *
     * @param id ID do ponto
     * @return Ponto
     */
    public Vertex<Ponto> getPonto(String id) {
        for (Vertex<Ponto> item : graph.vertices()) {
            if (item.element().getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Getter method for the Edge
     *
     * @param id ID do ponto
     * @return Edge if exists
     */
    public Edge<Conexao, Ponto> getEdge(int id) {
        for (Edge<Conexao, Ponto> item : graph.edges()) {
            if (item.element().getId_c() == id) {
                return item;
            }
        }
        return null;
    }

    /**
     * Method that returns all the paths between ponto1 and ponto2
     *
     * @param ponto1 ponto 1
     * @param ponto2 ponto 2
     * @return ArrayList of the paths between ponto1 and ponto2
     * @throws ParquePlannerException
     */
    public List<Conexao> getPathsBetween(Ponto ponto1, Ponto ponto2)
            throws ParquePlannerException {
        if (ponto1 == null || ponto2 == null) {
            System.out.println("Nao existe Ponto");
        }

        ArrayList<Conexao> paths = new ArrayList();
        for (Vertex<Ponto> item : graph.vertices()) {
            if (item.element().equals(ponto1)) {
                for (Edge<Conexao, Ponto> elem : graph.outboundEdges(item)) {
                    Vertex<Ponto> opposite = graph.opposite(item, elem);
                    if (opposite.element() == ponto2) {
                        paths.add(elem.element());
                    }
                }
            }
        }
        return paths;
    }

    /**
     * toString method for the Parque Planner
     *
     * @return toString
     */
    @Override
    public String toString() {
        String inf = "\n";
        inf += "\n ParquePlanner( " + graph.numEdges() + "Paths | " + graph.numVertices() + " Pontos)\n";

        for (Vertex<Ponto> v1 : graph.vertices()) {
            for (Vertex<Ponto> v2 : graph.vertices()) {
                if (v1.equals(v2)) {
                    break;
                }
                inf += "\n|" + v1.element().toString() + "- TO -" + v2.element().toString() + "|";
                List<Conexao> b = getPathsBetween(v1.element(), v2.element());
                if (b.isEmpty()) {
                    inf += "\n  (No Paths)  \n";
                } else {
                    for (Conexao item : b) {
                        inf += "\n" + item.toString() + "";
                    }
                }
            }
        }
        return inf;
    }

    /**
     * Method that calculates the minimum cost of a path
     *
     * @param p tipo de percurso 
     * @param criteria criteria 
     * @param orig ponto de origem 
     * @param dst ponto de destino
     * @param pontosInteresse pontos de interesse
     * @param pontos lista de pontos  
     * @param teste lista de conexoes teste
     * @return minimum cost path
     * @throws ParquePlannerException
     */
    public int minimumCostPath(TipoPercurso p, Criteria criteria, Ponto orig, Ponto dst, List<Ponto> pontosInteresse, List<Ponto> pontos, List<Conexao> teste) throws ParquePlannerException {
        Map<Vertex<Ponto>, Integer> costs = new HashMap<>();
        Map<Vertex<Ponto>, Vertex<Ponto>> predecessors = new HashMap<>();
        HashMap<Vertex<Ponto>, Edge<Conexao, Ponto>> flown = new HashMap<>();//

        Vertex<Ponto> pontoDestino = checkPonto(dst);
        Vertex<Ponto> pontoOrig = checkPonto(orig);

        dijkstra(p, criteria, checkPonto(orig), costs, predecessors, flown);
        int cost = costs.get(pontoDestino);
        List<Ponto> pontosAux = new ArrayList<>();
        List<Conexao> caminhoAux = new ArrayList<>();
        //System.out.println("->" + dst);
        do {
            pontosAux.add(pontoDestino.element());
            caminhoAux.add(flown.get(pontoDestino).element());
            pontoDestino = predecessors.get(pontoDestino);
            //System.out.println("->" + pontoDestino);
        } while (pontoDestino != pontoOrig);
        for (int i = pontosAux.size() - 1; i >= 0; i--) {
            pontos.add(pontosAux.get(i));
        }
        for (int i = caminhoAux.size() - 1; i >= 0; i--) {
            teste.add(caminhoAux.get(i));
        }
        return cost;
    }

    /**
     * Dijkstra method for Parque Planner
     *
     * @param t tipo de percurso
     * @param criteria criteria 
     * @param orig ponto de origem  
     * @param costs custo entre mapas
     * @param predecessors predecessores
     * @param flown flown
     */
    private void dijkstra(TipoPercurso t, Criteria criteria, Vertex<Ponto> orig,
            Map<Vertex<Ponto>, Integer> costs,
            Map<Vertex<Ponto>, Vertex<Ponto>> predecessors,
            Map<Vertex<Ponto>, Edge<Conexao, Ponto>> flown) {

        List<Vertex<Ponto>> unvisited = new ArrayList<>();

        for (Vertex<Ponto> vertex : graph.vertices()) {
            unvisited.add(vertex);
            costs.put(vertex, Integer.MAX_VALUE);
            predecessors.put(vertex, null);
            flown.put(vertex, null);
        }
        costs.put(orig, 0);
        while (!unvisited.isEmpty()) {
            Vertex<Ponto> lowCostVert = findLowerCostVertex(unvisited, costs);
            unvisited.remove(lowCostVert);
            ArrayList<Edge<Conexao, Ponto>> incidentes = new ArrayList<>();
            for (Edge<Conexao, Ponto> edge : graph.outboundEdges(lowCostVert)) {
                switch (t) {
                    case PE:
                        incidentes.add(edge);
                    case BICICLETA:
                        incidentes.add(edge);
                }
            }
            for (Edge<Conexao, Ponto> path : incidentes) {
                Vertex<Ponto> oposto = graph.opposite(lowCostVert, path);
                if (unvisited.contains(oposto)) {
                    int custo = 0;
                    switch (criteria) {
                        case COST:
                            custo = path.element().getCusto() + costs.get(lowCostVert);
                            break;
                        case DISTANCE:
                            custo = path.element().getDistancia() + costs.get(lowCostVert);
                    }
                    if (costs.get(oposto) > custo) {
                        costs.put(oposto, custo);
                        predecessors.put(oposto, lowCostVert);
                        flown.put(oposto, path);
                    }
                }
            }
        }
    }

    /**
     * Method that calculates the minimum cost Vertex
     *
     * @param unvisited pontos por visitar
     * @param costs custo entre mapas
     * @return minimim cost Vertex
     */
    private Vertex<Ponto> findLowerCostVertex(List<Vertex<Ponto>> unvisited,
            Map<Vertex<Ponto>, Integer> costs) {

        double min = Integer.MAX_VALUE;

        Vertex<Ponto> minCostVertex = null;

        for (Vertex<Ponto> aeroporto : unvisited) {
            if (costs.get(aeroporto) <= min) {
                minCostVertex = aeroporto;
                min = costs.get(aeroporto);
            }
        }
        return minCostVertex;
    }

    /**
     * Method that reades the .dat file
     *
     *
     */
    private void readFile() {
        File f;
        String nameFile;
        try (Scanner scan = new Scanner(System.in)) {
            f = new File("src\\parque\\mapa0.dat");
            System.out.println("->Enter name of file here : ");
            nameFile = scan.nextLine();
        }
        System.out.println(nameFile);
        if (nameFile.equals("mapa0.dat")) {
            try {

                FileReader filereader = new FileReader(f);
                BufferedReader bufferReader = new BufferedReader(filereader);
                while ((line = bufferReader.readLine()) != null) {
                    String[] token = line.split(", ");
                    if (token.length == 2) {
                        Ponto ponto = new Ponto(token[0], token[1]);
                        this.addPonto(ponto);
                        System.out.println("Ponto Lido " + ponto.getId());
                    } else if (token.length > 2) {
                        Conexao conexao = new Conexao(Integer.parseInt(token[0]), Enum.valueOf(TYPE_CONECT.class, token[1].toUpperCase()), token[2], Boolean.parseBoolean(token[2]), Integer.parseInt(token[6]), Integer.parseInt(token[7]));
                        this.addConexao(this.getPontoById(token[3]), this.getPontoById(token[4]), conexao);
                        System.out.println("Conexao Lida " + conexao.getId_c());
                    }

                }
            } catch (FileNotFoundException el) {

                System.out.println("Exception thrown  :" + el);
                System.out.println("FileNotFound");
            } catch (IOException e) {
                System.out.println("Exception thrown  :" + e);

            }
        } else {
            System.out.println("->File incorrect");
            System.out.println("->Impossivel carregar Mapa");
        }
    }

    /**
     * Method that calculates the minimum preco cost path
     *
     * @param pontos pontos
     * @return minimum preco cost path
     */
    public int minimumCostPathPreco(List<Ponto> pontos) {
        int sum = 0;
        for (int i = 0; i < pontos.size(); i++) {
            Ponto first = pontos.get(i);
            if (pontos.size() > i + 1) {
                Ponto second = pontos.get(i + 1);
                List<Conexao> l = this.getPathsBetween(first, second);
                if (l.get(0) != null) {
                    sum += l.get(0).getCusto();
                } else {
                }
            }
        }
        return sum;
    }

    /**
     * Method that calculates the minimum distancia cost path
     *
     * @param pontos pontos
     * @return minimum distancia cost path
     */
    public int minimumCostPathDistancia(List<Ponto> pontos) {
        int sum = 0;
        for (int i = 0; i < pontos.size(); i++) {
            Ponto first = pontos.get(i);
            if (pontos.size() > i + 1) {
                Ponto second = pontos.get(i + 1);
                List<Conexao> l = this.getPathsBetween(first, second);
                sum += l.get(0).getDistancia();
            }
        }
        return sum;
    }
}
