package parque;

import MVC.CONTROLLERS.PercursoController;
import MVC.VIEWS.EstatisticaParque;
import MVC.VIEWS.MenuPrincipalParqueUI;
import dijkstra.graph.Edge;
import dijkstra.model.Conexao;
import dijkstra.model.Conexao.TYPE_CONECT;
import dijkstra.model.ParquePlanner;
import dijkstra.model.Ponto;
import java.io.FileNotFoundException;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafxgraph_v2.graphview.CircularSortedPlacementStrategy;
import javafxgraph_v2.graphview.GraphPanel;
import javafxgraph_v2.graphview.VertexPlacementStrategy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafxgraph_v2.graphview.Arrow;
import javafxgraph_v2.graphview.GraphEdge;
import static javafxgraph_v2.graphview.GraphPanel.ARROW_COLOR;
import static javafxgraph_v2.graphview.GraphPanel.GRAPH_EDGE_WIDTH;

/**
 * Main
 *
 * @author João e Jorge
 */
public class Parque extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

    /**
     * JavaFX for the ParquePlanner
     *
     * @param primaryStage
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    private TabPane configTabPane(MenuPrincipalParqueUI view, GraphPanel graphView) {
        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("MENU", view);
        Tab tab2 = new Tab("MAPA", graphView);
        Tab tab3 = new Tab("ESTATISTICAS", new EstatisticaParque());
        tabpane.getTabs().addAll(tab1, tab2, tab3);
        tabpane.getStylesheets().addAll(this.getClass().getResource("/javafxgraphs/ui/resources/TabPaneStyle.css").toExternalForm());
        tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        return tabpane;
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, InterruptedException {
        //MODEL
        ParquePlanner planner = new ParquePlanner();
        //GRAPH
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        GraphPanel<Ponto, Conexao> graphView = new GraphPanel<>(planner.getGraph(), strategy);
        //VIEW
        MenuPrincipalParqueUI view = new MenuPrincipalParqueUI(planner);
        //TABPANE  
        TabPane tabpane = configTabPane(view, graphView);
        //CONTROLLER
        PercursoController controller = new PercursoController(view, planner, graphView, tabpane);

        Scene sceneTeste = new Scene(tabpane, 1259, 606);
        primaryStage.setTitle("Parque Bio");
        primaryStage.setScene(sceneTeste);
        primaryStage.setOpacity(0.9);
        primaryStage.show();
        graphView.plotGraph();

        setPathsColor(planner, graphView);
        for (Map.Entry<Edge<Conexao, Ponto>, GraphEdge> item : graphView.getGraphEdgeMap().entrySet()) {
            if (item.getKey().element().getTipo().equals(TYPE_CONECT.CAMINHO)) {
                double[] arrowShape = new double[]{0, 0, GRAPH_EDGE_WIDTH, 2 * GRAPH_EDGE_WIDTH, -GRAPH_EDGE_WIDTH, 2 * GRAPH_EDGE_WIDTH};

                Arrow arrow1 = new Arrow(item.getValue(), ARROW_COLOR, 0.2f, arrowShape);
                item.getValue().addArrow(arrow1);
                graphView.getChildren().add(arrow1);

            }
        }

    }

    private void setPathsColor(ParquePlanner planner, GraphPanel graphView) {
        for (Edge<Conexao, Ponto> item : planner.getGraph().edges()) {
            if (item.element().getTipo().equals(TYPE_CONECT.PONTE)) {
                graphView.setEdgeColor(item, Color.DODGERBLUE, 0.8);
            } else {
                graphView.setEdgeColor(item, Color.CYAN, 0.5);
            }
        }
    }

}
