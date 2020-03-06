package MVC.VIEWS;

import MVC.CONTROLLERS.PercursoController;
import java.util.Observer;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;

/**
 * Interface for the MenuPrincipalParqueInterface
 *
 * @author João e Jorge
 */
public interface MenuPrincipalParqueInterface extends Observer {

    /**
     * Getter method for the EmitirBilhete button
     *
     * @return btEmitirBilhete
     */
    Button getBtEmitirBilhete();

    /**
     * Getter method for the PontoComboBox
     *
     * @return PontoComboBox
     */
    ComboBox getPontoComboBox();

    /**
     * Getter method for the UserInput
     *
     * @return UserInput @throws PercursoException
     */
    String getUserInput();

    /**
     * Method that clears the user input
     */
    void clearUserInput();

    /**
     * Method that shows a error message
     *
     * @param msg mensagem de erro
     */
    void showError(String msg);

    /**
     * Method that clears a error
     */
    void clearError();

    /**
     * Method that calculates a Percurso
     *
     * @param custo custo do percurso
     * @param distancia distancia do percurso
     */
    void calcular(int custo, int distancia);

    /**
     * Getter method for the Group Lomotion
     *
     * @return groupLocomotion
     */
    public ToggleGroup getToggleGroupLomotion();

    /**
     * Getter method for the Group Criteria
     *
     * @return groupCriteria
     */
    public ToggleGroup getToggleGroupCriteria();

    /**
     * Setter method for the triggers
     *
     * @param controller controller do percurso
     */
    void setTriggers(PercursoController controller);

}
