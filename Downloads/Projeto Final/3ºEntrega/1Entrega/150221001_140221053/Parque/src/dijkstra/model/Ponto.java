package dijkstra.model;

import java.util.Objects;

/**
 * Classe representativa de um ponto do grafo
 * 
 * @author João e Jorge
 */
public class Ponto {

    private final String id;
    private final String ponto;

    /**
     * Constructor method for the class Ponto
     *
     * @param id ID do ponto
     * @param ponto ponto
     */
    public Ponto(String id, String ponto) {
        this.id = id;
        this.ponto = ponto;
    }

    /**
     * Getter method for id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter method for ponto
     *
     * @return ponto
     */
    public String getPonto() {
        return ponto;
    }

    /**
     * Method toString that prints the ponto with his id
     *
     * @return string of the ponto
     */
    @Override
    public String toString() {
        return ponto + "(" + id + ")";
    }

    /**
     * Method that checks if the given object is from the type ponto
     *
     * @param obj object
     * @return true if the object if from the same type and false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ponto other = (Ponto) obj;
        return this.id.compareToIgnoreCase(other.id) == 0;
    }

    /**
     * Method that returns the hashCode from the object
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
