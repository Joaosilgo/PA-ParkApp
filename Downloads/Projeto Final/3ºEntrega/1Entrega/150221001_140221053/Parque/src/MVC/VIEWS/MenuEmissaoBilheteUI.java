package MVC.VIEWS;

import MVC.MODELS.EmissaoBilhetesException;
import Bilhetes.Bilhete;
import Bilhetes.Fatura;
import DAO.ParqueBilhetes;
import PDFGenerator.GenerateTicket;
import dijkstra.model.Conexao;
import dijkstra.model.ParquePlanner;
import dijkstra.model.Ponto;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafxgraph_v2.graphview.GraphPanel;

/**
 * Classe view do menu de emissao de bilhete
 *
 * @author João e Jorge
 */
public class MenuEmissaoBilheteUI extends VBox {

    private Label lblNif;
    private TextField nifInput;
    private Button GenerateBilhete;
    private Button retroceder;
    private Label lblError;
    private Label lblResult;
    private ParqueBilhetes parqueBilhetes;
    private ParquePlanner parque;
    private final GraphPanel<Ponto, Conexao> graphView;
    private final TabPane tabpane;
    private GenerateTicket d;

    /**
     * Constructor method for MenuEmissaoBilheteUI
     *
     * @param parque modelo
     * @param graphView visualizacao do grafo
     * @param tabpane tabpane a ser usada
     */
    public MenuEmissaoBilheteUI(ParquePlanner parque, GraphPanel<Ponto, Conexao> graphView, TabPane tabpane) {
        initComponents();
        d = new GenerateTicket();
        this.parqueBilhetes = new ParqueBilhetes();
        this.parque = parque;
        this.graphView = graphView;
        this.tabpane = tabpane;
        setTriggers();
    }

    /**
     * Method that initiates the components
     */
    private void initComponents() {
        lblNif = new Label("Insira Nif para Fatura");
        nifInput = new TextField();
        nifInput.setPrefSize(5, 5);
        nifInput.setPromptText("Nif");
        retroceder = new Button("Retroceder");
        GenerateBilhete = new Button("Gerar Bilhete");
        lblError = new Label();
        lblResult = new Label();
        HBox hbox1 = new HBox(5, retroceder, GenerateBilhete);
        this.getChildren().addAll(lblNif, nifInput, hbox1, lblError, lblResult);
        this.setSpacing(20);
    }

    /**
     * Getter method for NifInput
     *
     * @return nifInput
     */
    public TextField getNifInput() {
        if (nifInput.getText().length() < 9 && nifInput.getText().length() >= 1) {
            throw new EmissaoBilhetesException("Insira um Valor Válido");
        } else if (nifInput.getText().equals("")) {
            this.nifInput.setText("999999999");
            return nifInput;
        } else {
            return nifInput;
        }
    }

    public void clear() {
        lblError.setText("");
        lblResult.setText("");
    }

    public void setTriggers() {
        GenerateBilhete.setOnAction((ActionEvent event) -> {
            try {
                clear();
                Fatura fatura = new Fatura(getNifInput().getText());
                Bilhete bilhete = new Bilhete("ParqueBioTicket", parque.getPercursoActual(), fatura);
                d.generateBilhetePdf(bilhete);
                this.parqueBilhetes.insert(bilhete);
                this.lblResult.setText("Bilhete Impresso");
            } catch (EmissaoBilhetesException e) {
                this.lblError.setText(e.getMessage());
            }
        });
        retroceder.setOnAction((ActionEvent event) -> {
            retroceder.getScene().setRoot(new MenuPrincipalParqueUI(parque));

        });
    }
}
