package DAO;

import Bilhetes.Bilhete;
import java.util.List;

/**
 * Interface for the BilheteDAO
 *
 * @author João e Jorge
 */
public interface BilheteDAO {

    /**
     * Method that selects all the Bilhetes into a List
     *
     * @return new ArrayList
     */
    public List<Bilhete> selectAll();

    /**
     * Method that returns a Bilhete given his ID
     *
     * @param idBilhete
     * @return Bilhete
     */
    public Bilhete select(int idBilhete);

    /**
     * Method that inserts a new Bilhete given an entry
     *
     * @param entry
     * @return true if the Bilhete was inserted
     */
    public boolean insert(Bilhete entry);

    /**
     * Method that removes a Bilhete given a studentCode
     *
     * @param idBilhete
     * @return true if the Bilhete was removed
     */
    public boolean remove(int idBilhete);

    /**
     * Method that updates the ticket description
     *
     * @param idBilhete
     * @param descricao
     * @return UnsupportedOperationException
     */
    public boolean updateBilheteDescricao(int idBilhete, String descricao);

    /**
     * Method that calculates the average price of all tickets
     *
     * @return media
     */
    public double sellectAllPrecoMedio();
    
    
    
}
