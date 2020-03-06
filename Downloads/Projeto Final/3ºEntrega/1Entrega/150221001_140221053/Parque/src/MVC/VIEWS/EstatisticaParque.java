package MVC.VIEWS;

import Bilhetes.Bilhete;
import DAO.BilheteDAO;
import DAO.BilheteDAOJson;
import DAO.ParqueBilhetes;
import dijkstra.model.ParquePlanner.TipoPercurso;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Class View das estatisticas do parque
 *
 * @author João e Jorge
 */
public class EstatisticaParque extends HBox{

    private BilheteDAO b;
    private ParqueBilhetes database;
    private int totalPe = 0;
    private int totalBike = 0;
    private int todos = 0;
    private double percentagemPe = 0.0;
    private double percentagemBike = 0.0;
    private PieChart chartPercentagem;
    private ObservableList<PieChart.Data> pieChartData;
    private VBox vBoxPercentagem;
    private VBox vBoxprecoMedia;  
    
    /**
     * Constructor method for EstatisticaParque
     */
    public EstatisticaParque() {
        b = new BilheteDAOJson("");
        
        this.getChildren().addAll(criterio(), precoMedioBilhetes());
        this.setSpacing(20);
    }

    /**
     * Method that gets the average tickets price
     */
    private VBox precoMedioBilhetes() {
       vBoxprecoMedia = new VBox();
        vBoxprecoMedia.setSpacing(10);
        Label label = new Label("Preço Medio Bilhetes:");
        label.setFont(new Font("Impact",20));
        Label labelvalor = new Label(String.valueOf(b.sellectAllPrecoMedio()));
       vBoxprecoMedia.getChildren().addAll(label, labelvalor);
        return vBoxprecoMedia;
    }

    /**
     * Method criterio for the VBox EstatisticaParque
     *
     * @return vbox
     */
    private VBox criterio() {
        List<Bilhete> lista = b.selectAll();
        List<Bilhete> listaPe = new ArrayList<>();
        List<Bilhete> listaBike = new ArrayList<>();
        for (Bilhete item : lista) {
            if (item.getPercurso().getTipoPercurso().equals(TipoPercurso.PE)) {
                listaPe.add(item);
            } else {
                listaBike.add(item);
            }
        }
        totalPe = listaPe.size();
        totalBike = listaBike.size();
        todos = lista.size();

        percentagemPe = (totalPe * 100) / todos;
        percentagemBike = (totalBike * 100) / todos;
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("A pé: " + String.valueOf(totalPe) + " | " + percentagemPe + "%", percentagemPe),
                new PieChart.Data("Bicicleta: " + String.valueOf(totalBike) + " | " + percentagemBike + "%", percentagemBike));
        chartPercentagem = new PieChart(pieChartData);
        defineEffect(pieChartData);
        vBoxPercentagem = new VBox();
         Label label = new Label("Percentagem:");
        label.setFont(new Font("Impact",20));
        vBoxPercentagem.getChildren().addAll(label,chartPercentagem);
        return vBoxPercentagem;
    }

    /**
     * Method that defines an effect to the pie chart
     * 
     * @param pieChartData informacao da pie chart
     */
    private void defineEffect(ObservableList<PieChart.Data> pieChartData) {
        pieChartData.stream().forEach(pieData -> {
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Bounds b1 = pieData.getNode().getBoundsInLocal();
                double newX = (b1.getWidth()) / 2 + b1.getMinX();
                double newY = (b1.getHeight()) / 2 + b1.getMinY();
                // Make sure pie wedge location is reset
                pieData.getNode().setTranslateX(0);
                pieData.getNode().setTranslateY(0);
                TranslateTransition tt = new TranslateTransition(
                        Duration.millis(1500), pieData.getNode());
                tt.setByX(newX);
                tt.setByY(newY);
                tt.setAutoReverse(true);
                tt.setCycleCount(2);
                tt.play();
            });
        });
    }

}
