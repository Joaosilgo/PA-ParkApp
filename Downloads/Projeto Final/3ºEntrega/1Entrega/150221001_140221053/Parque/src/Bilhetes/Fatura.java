package Bilhetes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Classe que representa uma fatura associada a um bilhete
 *
 * @author João e Jorge
 */
public class Fatura implements Serializable {

    private Date data;
    private String NIF;

    /**
     * Constructor for the class Fatura
     *
     * @param NIF NIF agregado a fatura
     */
    public Fatura(String NIF) {
        this.data = new Date();
        this.NIF = NIF;
    }

    /**
     * Secondary constructor for the class Fatura
     */
    public Fatura() {
    }

    /**
     * toString method for Fatura
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "Fatura{" + "data=" + data + ", NIF=" + NIF + '}';
    }

    /**
     * Setter method for Data
     *
     * @param data data da fatura
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Setter method for NIF
     *
     * @param NIF NIF agregado a fatura
     */
    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    /**
     * Getter method for Data
     *
     * @return Data
     */
    public Date getData() {
        return data;
    }

    /**
     * Getter method for NIF
     *
     * @return NIF
     */
    public String getNIF() {
        return NIF;
    }
}
