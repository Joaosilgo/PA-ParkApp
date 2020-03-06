package MVC.CONTROLLERS;

import MVC.MODELS.Percurso;
import MVC.MODELS.PercursoException;
import MVC.VIEWS.MenuEmissaoBilheteUI;
import MVC.VIEWS.MenuPrincipalParqueInterface;
import dijkstra.graph.Edge;
import dijkstra.model.Conexao;
import dijkstra.model.ParquePlanner;
import dijkstra.model.ParquePlanner.Criteria;
import dijkstra.model.ParquePlanner.TipoPercurso;
import dijkstra.model.Ponto;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafxgraph_v2.graphview.GraphPanel;

/**
 * Classe controller do percurso
 *
 * @author João e Jorge
 */
public class PercursoController {

    private final MenuPrincipalParqueInterface view;
    private final ParquePlanner parque;
    private final GraphPanel<Ponto, Conexao> graphView;
    private final TabPane tabpane;

    /**
     * Constructor method for the class PercursoController
     *
     * @param view interface da view do menu
     * @param parque modelo
     * @param graphView visualizacao do grafo
     * @param tabpane tabpane a ser usada
     */
    public PercursoController(MenuPrincipalParqueInterface view, ParquePlanner parque, GraphPanel<Ponto, Conexao> graphView, TabPane tabpane) {
        this.view = view;
        this.parque = parque;
        this.graphView = graphView;
        this.tabpane = tabpane;
        initiateView(view);
        this.parque.addObserver(view);
    }

    private void initiateView(MenuPrincipalParqueInterface view) {
        parque.getListPontos().forEach((item) -> {
            view.getPontoComboBox().getItems().add(item.getId());
        });
        view.setTriggers(this);
    }

    /**
     * Method that adds new Ponto
     */
    public void actionAddPonto() {
        try {
            view.getToggleGroupLomotion();
            view.getToggleGroupCriteria();
            String userInput = view.getUserInput().trim();
            Ponto ponto = parque.getPontoById(userInput);
            parque.addPontoInteresse(ponto);
            view.clearUserInput();
            view.clearError();
        } catch (PercursoException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Method that clears all Pontos
     */
    public void actionClearWords() {
        parque.clearPontosInteresse();
        view.clearError();
    }

    /**
     * Method undo for the last action
     */
    public void actionUndo() {
        try {
            view.clearError();
            parque.undo();
        } catch (PercursoException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Method that calculates the percurso
     * 
     * @throws PercursoException 
     */
    public void actionCalcular() throws PercursoException {

        try {
            List<Conexao> mapa = new ArrayList<>();
            Percurso percurso = parque.gerarPercurso(
                    (TipoPercurso) view.getToggleGroupLomotion().getSelectedToggle().getUserData(),
                    (Criteria) view.getToggleGroupCriteria().getSelectedToggle().getUserData(),
                    parque.getPontosDeInteresse(),
                    mapa);

            view.calcular(percurso.getCusto(), percurso.getDistancia());
            percurso.setCalculado(true);
            desenharPath();

        } catch (PercursoException e) {

            view.showError(e.getMessage());
        }
    }

    /**
     * Method that prints a Bilhete
     */
    public void actionEmitirBilhete() throws PercursoException {
        try {
            view.getBtEmitirBilhete();
            Tab tabActual = this.tabpane.getTabs().get(0);
            tabActual.setContent(new MenuEmissaoBilheteUI(parque, graphView, tabpane));
        } catch (PercursoException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Method that draws a path
     */
    public void desenharPath() {
        List<Conexao> caminho = this.parque.getPercursoActual().getCaminhoArestas();
        for (Conexao i : caminho) {
            for (Edge<Conexao, Ponto> item : this.parque.getGraph().edges()) {
                if (item.element().getId_c() == i.getId_c()) {
                    this.graphView.setEdgeColor(item, Color.RED, 0.5);
                }
            }
        }
    }
}
