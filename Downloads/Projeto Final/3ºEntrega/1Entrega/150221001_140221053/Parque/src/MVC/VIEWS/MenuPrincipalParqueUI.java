package MVC.VIEWS;

import MVC.CONTROLLERS.PercursoController;
import MVC.MODELS.PercursoException;
import dijkstra.model.ParquePlanner;
import dijkstra.model.ParquePlanner.Criteria;
import dijkstra.model.ParquePlanner.TipoPercurso;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import singleton.Logger;

/**
 * Classe view do menu principal do parque
 *
 * @author João e Jorge
 */
public class MenuPrincipalParqueUI extends VBox implements MenuPrincipalParqueInterface {

    private Label MenuName;
    private ToggleGroup groupLocomotion;
    private ToggleGroup groupCriteria;
    private ToggleButton costButton;
    private ToggleButton distanceButton;
    private ToggleButton bikeButton;
    private ToggleButton footButton;
    private ComboBox PontoComboBox;
    private Button btAdd;
    private Button btClear;
    private Button btUndo;
    private ListView<String> listPontos;
    private Button btCalcular;
    private Label lblError;
    private Label lblResultado;
    private Button btEmitirBilhete;
    private Label lblLocomotion;
    private Label lblCriterio;
    private final ParquePlanner parque;

    /**
     * Constructor method for MenuPrincipalParqueUI
     *
     * @param parque parque planner
     */
    public MenuPrincipalParqueUI(ParquePlanner parque) {
        this.parque = parque;
        initComponents();
        update(parque, null);
    }

    /**
     * Method that initiates the components
     */
    private void initComponents() {
        /* Inicializar controlos */
        MenuName = new Label();
        groupLocomotion = new ToggleGroup();
        groupCriteria = new ToggleGroup();
        costButton = new ToggleButton("Custo");
        distanceButton = new ToggleButton("Distancia");
        bikeButton = new ToggleButton("Bike");
        footButton = new ToggleButton("Foot");
        PontoComboBox = new ComboBox();
        btAdd = new Button("Add");
        btClear = new Button("Clear");
        btUndo = new Button("Undo");
        listPontos = new ListView<>();
        listPontos.setPrefWidth(100);
        listPontos.setPrefHeight(75);
        btCalcular = new Button("Calcular");
        lblError = new Label();
        lblResultado = new Label();
        costButton.setToggleGroup(groupCriteria);
        distanceButton.setToggleGroup(groupCriteria);
        bikeButton.setToggleGroup(groupLocomotion);
        footButton.setToggleGroup(groupLocomotion);
        /* Criar layout */
        lblLocomotion = new Label("Tipo Locomoção");
        lblLocomotion.setFont(new Font("Impact", 15));
        lblCriterio = new Label("Tipo Criteria");
        lblCriterio.setFont(new Font("Impact", 15));
        btEmitirBilhete = new Button("Emitir Bilhete");

        HBox hbox1 = new HBox(5, bikeButton, footButton);
        HBox hbox2 = new HBox(5, PontoComboBox, btAdd, btClear, btUndo);
        HBox hbox3 = new HBox(5, costButton, distanceButton);

        costButton.setUserData(Criteria.COST);
        distanceButton.setUserData(Criteria.DISTANCE);

        bikeButton.setUserData(TipoPercurso.BICICLETA);
        footButton.setUserData(TipoPercurso.PE);

        this.getChildren().addAll(MenuName, lblLocomotion, hbox1, lblCriterio, hbox3, hbox2, listPontos, btCalcular, lblError, lblResultado, btEmitirBilhete);
        this.setSpacing(10);
    }

    /**
     * Getter method for the UserInput
     *
     * @return UserInput @throws PercursoException
     */
    @Override
    public String getUserInput() throws PercursoException {
        if (PontoComboBox.getValue() == null) {
            throw new PercursoException("Selecione um Ponto");
        } else {
            String s = PontoComboBox.getValue().toString().trim();

            return s;
        }
    }

    /**
     * Getter method for the PontoComboBox
     *
     * @return PontoComboBox
     */
    @Override
    public ComboBox getPontoComboBox() {
        return PontoComboBox;
    }

    /**
     * Method that clears the user input
     */
    @Override
    public void clearUserInput() {
        PontoComboBox.setValue("");
    }

    /**
     * Method that shows a error message
     *
     * @param msg mensagem de erro
     */
    @Override
    public void showError(String msg) {
        lblError.setText(msg);
    }

    /**
     * Method that clears a error
     */
    @Override
    public void clearError() {
        lblError.setText("");
        lblResultado.setText("");
    }

    /**
     * Method that calculates a Percurso
     * 
     * @param custo custo do percurso
     * @param distancia distancia do percurso
     */
    @Override
    public void calcular(int custo, int distancia) {
        if (custo == 0) {
            throw new PercursoException("Impossivel Calcular trajeto - Custo 0");
        } else if (distancia == 0) {
            throw new PercursoException("Impossivel Calcular trajeto - Distância 0");
        } else {
            String mensagem;
            mensagem = "\nCusto: " + custo + "€"
                    + "\nDistância: " + distancia + "Miles";
            lblResultado.setText(mensagem);
            Logger.getInstance().writeToLog("User calculou percurso");

        }

    }

    /**
     * Method that shows the Percurso result
     *
     * @return lblResultado
     */
    public Label getLblResultado() {
        Logger.getInstance().writeToLog("User Viu resultado");
        return lblResultado;
    }

    /**
     * Getter method for the Group Lomotion
     *
     * @return groupLocomotion
     */
    @Override
    public ToggleGroup getToggleGroupLomotion() {
        if (this.groupLocomotion.getSelectedToggle() == null) {
            throw new PercursoException("Selecione um tipo de locomoção");
        } else {
            return groupLocomotion;
        }
    }

    /**
     * Getter method for the Group Criteria
     *
     * @return groupCriteria
     */
    @Override
    public ToggleGroup getToggleGroupCriteria() {
        if (this.groupCriteria.getSelectedToggle() == null) {
            throw new PercursoException("Selecione um tipo de Criterio de Calculo");
        } else {
            return groupCriteria;
        }
    }

    /**
     * Method that updates the Percurso
     *
     * @param o observable
     * @param arg argument
     */
    @Override
    public void update(java.util.Observable o, Object arg) {
        if (o instanceof ParquePlanner) {
            ParquePlanner models = (ParquePlanner) o;
            String text;
            text = lblError.getText();
            listPontos.getItems().clear();
            models.getPontosDeInteresse().forEach((item) -> {
                listPontos.getItems().add(item.getId());
            });
        }
    }

    /**
     * Getter method for the EmitirBilhete button
     *
     * @return btEmitirBilhete
     */
    @Override
    public Button getBtEmitirBilhete() {
        if (parque.getPercursoActual() == null || parque.getPercursoActual().isCalculado() == false) {
            throw new PercursoException("Impossível Emitir Bilhete, Percurso não foi calculado");
        } else {
            return btEmitirBilhete;
        }

    }

    /**
     * Setter method for the triggers
     *
     * @param controller percurso controller
     */
    @Override
    public void setTriggers(PercursoController controller) {
        btAdd.setOnAction((ActionEvent event) -> {
            controller.actionAddPonto();
        });
        btClear.setOnAction((ActionEvent event) -> {
            controller.actionClearWords();
        });
        btUndo.setOnAction((ActionEvent event) -> {
            controller.actionUndo();
        });
        btCalcular.setOnAction((ActionEvent event) -> {
            controller.actionCalcular();
        });
        btEmitirBilhete.setOnAction((ActionEvent event) -> {
            controller.actionEmitirBilhete();
        });
    }

}
