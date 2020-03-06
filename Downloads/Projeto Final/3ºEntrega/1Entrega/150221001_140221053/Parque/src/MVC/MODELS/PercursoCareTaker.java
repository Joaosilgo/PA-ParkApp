package MVC.MODELS;

import java.util.Stack;

/**
 * Classe Care Taker para guardar e recuperar os estados do percurso
 *
 * @author João e Jorge
 */
public class PercursoCareTaker {

    private final Stack<PercursoMemento> allMementos = new Stack<>();

    /**
     * Method that saves the state of a Percurso
     *
     * @param originator percurso originator
     */
    public void saveState(Percurso originator) {
        if (originator == null) {
            return;
        }
        allMementos.push(originator.createMemento());
    }

    /**
     * Method that restores the state of a Percurso
     *
     * @param originator percurso originator
     * @throws PercursoException
     */
    public void restoreState(Percurso originator) throws PercursoException {
        if (originator == null) {
            return;
        }
        if (allMementos.isEmpty()) {
            throw new PercursoException("Nao existem mais estados guardados.");
        }
        originator.setMemento(allMementos.pop());
    }
}
