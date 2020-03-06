package Bilhetes;

import MVC.MODELS.Percurso;
import java.io.Serializable;
import java.util.Date;

/**
 * Classe que representa um bilhete com a informação do percurso associado
 *
 * @author Joao e Jorge
 */
public class Bilhete implements Serializable {

    private Date datetime;
    private String descricao;
    private Percurso percurso;
    private Fatura fatura;
    private int idBilhete;
    private static int numInstances;

    /**
     * Constructor for the class Bilhete
     *
     * @param descricao descricao do bilhete
     * @param percurso percurso do bilhete
     * @param fatura fatura agregada ao bilhete
     */
    public Bilhete(String descricao, Percurso percurso, Fatura fatura) {
        this.idBilhete = ++numInstances;
        this.datetime = new Date();
        this.descricao = descricao;
        this.percurso = percurso;
        this.fatura = fatura;
    }

    /**
     * Getter method for the datetime
     *
     * @return datetime
     */
    public Date getDatetime() {
        return datetime;
    }

    /**
     * Getter method for descricao
     *
     * @return descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Getter method for idBilhete
     *
     * @return idBilhete
     */
    public int getIdBilhete() {
        return idBilhete;
    }

    /**
     * toString method for Bilhete
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "Bilhete{" + "datetime=" + datetime + ", descricao=" + descricao + ", percurso=" + percurso + ", fatura=" + fatura + ", idBilhete=" + idBilhete + '}';
    }

    /**
     * Setter method for descricao
     *
     * @param descricao descricao do bilhete
     * 
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Getter method for Percurso
     *
     * @return percurso
     */
    public Percurso getPercurso() {
        return percurso;
    }

    /**
     * Getter method for Fatura
     *
     * @return fatura
     */
    public Fatura getFatura() {
        return fatura;
    }
}
