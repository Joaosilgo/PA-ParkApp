package MVC.MODELS;

import dijkstra.model.Ponto;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Memento do MVC
 *
 * @author João e Jorge
 */
public class PercursoMemento {

    private final String nameMemento;
    private final List<Ponto> PercursoMemento;

    /**
     * Constructor method for PercursoMemento
     *
     * @param nameMemento nome do memento
     * @param PercursoMemento percurso do memento
     */
    public PercursoMemento(String nameMemento, List<Ponto> PercursoMemento) {
        this.nameMemento = nameMemento;
        this.PercursoMemento = new ArrayList<>(PercursoMemento);
    }

    /**
     * Getter method for NameMemento
     *
     * @return nameMemento
     */
    public String getNameMemento() {
        return nameMemento;
    }

    /**
     * Getter method for PercursoMemento
     *
     * @return PercursoMemento
     */
    public List<Ponto> getPercursoMemento() {
        return PercursoMemento;
    }
}
