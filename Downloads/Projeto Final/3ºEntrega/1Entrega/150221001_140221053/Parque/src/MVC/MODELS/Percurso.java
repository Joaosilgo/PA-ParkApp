package MVC.MODELS;

import dijkstra.model.Conexao;
import dijkstra.model.ParquePlanner.TipoPercurso;
import dijkstra.model.Ponto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe model para o percurso 
 *
 * @author João e Jorge
 */
public class Percurso implements Iterable<Ponto> {

    /**
     * Colecao que guarda as palavras
     */
    private List<Ponto> pontosPercurso;//PONTOS INTERESSE (PONTOS ESCOLHIDOS NA LIST VIEW)//era final
    private List<Ponto> pontosCaminho;//percurso por ordem
    private boolean calculado;

    /**
     * Setter method for calculado
     *
     * @param calculado percurso is calculado
     */
    public void setCalculado(boolean calculado) {
        this.calculado = calculado;
    }

    /**
     * Getter method for calculado
     *
     * @return calculado
     */
    public boolean isCalculado() {
        return calculado;
    }

    private String name;
    private TipoPercurso tipoPercurso;
    private int custo;
    private int distancia;
    private List<Conexao> caminhoArestas;

    /**
     * Constructor method for Percurso
     *
     * @param name nome do percurso
     * @param tipoPercurso tipo de percurso
     * @param custo custo do percurso
     * @param distancia distancia do percurso
     * @param pontosPercurso pontos do percurso
     * @param pontosCaminho pontos do caminho
     * @param caminhoArestas arestas do caminho
     */
    public Percurso(String name, TipoPercurso tipoPercurso, int custo, int distancia, List<Ponto> pontosPercurso, List<Ponto> pontosCaminho, List<Conexao> caminhoArestas) {
        this.name = name;
        this.tipoPercurso = tipoPercurso;
        this.custo = custo;
        this.distancia = distancia;
        this.pontosPercurso = pontosPercurso;
        this.pontosCaminho = pontosCaminho;
        this.caminhoArestas = caminhoArestas;
        this.calculado = false;
    }

    public Percurso() {

    }

    /**
     * Getter method for caminhoArestas
     *
     * @return caminhoArestas
     */
    public List<Conexao> getCaminhoArestas() {
        return caminhoArestas;
    }

    /**
     * Second constructor method for Percurso
     *
     * @param name nome do percurso
     */
    public Percurso(String name) {
        pontosPercurso = new ArrayList<>();
        this.name = name;

        pontosCaminho = new ArrayList<>();
    }

    /**
     * Getter method for pontosCaminho
     *
     * @return pontosCaminho
     */
    public List<Ponto> getPontosCaminho() {
        return pontosCaminho;//está a vazio sao o meu caminho
    }

    /**
     * Method that adds a new pontoPercurso
     *
     * @param p ponto p
     * @throws PercursoException
     */
    public void addPonto(Ponto p) throws PercursoException {
        pontosPercurso.add(p);
    }

    /**
     * Method that clears pontosPercurso
     */
    public void clear() {
        pontosPercurso.clear();
    }

    /**
     * Getter method for pontosPercurso size
     *
     * @return pontosPercurso size
     */
    public int getPontosCount() {
        return pontosPercurso.size();
    }

    /**
     * Getter method for pontosPercurso
     *
     * @return pontosPercurso
     */
    public List<Ponto> getPontosPercurso() {
        return pontosPercurso;
    }

    /**
     * Getter method for name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * toString method for Percurso
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "Percurso{" + "pontosPercurso=" + pontosPercurso + ", pontosCaminho=" + pontosCaminho + ", calculado=" + calculado + ", name=" + name + ", tipoPercurso=" + tipoPercurso + ", custo=" + custo + ", distancia=" + distancia + ", caminhoArestas=" + caminhoArestas + '}';
    }

    /**
     * Cria uma memória dos atributos atuais.
     *
     * @return memória
     */
    public PercursoMemento createMemento() {
        return new PercursoMemento(name, pontosPercurso);
    }

    /**
     * Reverte o estado do objeto para os valores de uma memória anterior
     *
     * @param memento a memória anterior
     */
    public void setMemento(PercursoMemento memento) {
        this.name = memento.getNameMemento();

        this.pontosPercurso.clear();
        this.pontosPercurso.addAll(memento.getPercursoMemento());
    }

    /**
     * Iterator method for pontosPercurso
     *
     * @return iterator of pontosPercurso
     */
    @Override
    public Iterator<Ponto> iterator() {
        return pontosPercurso.iterator();
    }

    /**
     * Setter method for tipoPercurso
     *
     * @param tipoPercurso tipo de percurso
     */
    public void setTipoPercurso(TipoPercurso tipoPercurso) {
        this.tipoPercurso = tipoPercurso;
    }

    /**
     * Setter method for custo
     *
     * @param custo custo do percurso
     */
    public void setCusto(int custo) {
        this.custo = custo;
    }

    /**
     * Setter method for distancia
     *
     * @param distancia distancia do percurso
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    /**
     * Getter method for tipoPercurso
     *
     * @return tipoPercurso
     */
    public TipoPercurso getTipoPercurso() {
        return tipoPercurso;
    }

    /**
     * Getter method for custo
     *
     * @return custo
     */
    public int getCusto() {
        return custo;
    }

    /**
     * Getter method for distancia
     *
     * @return distancia
     */
    public int getDistancia() {
        return distancia;
    }
}
