package DAO;

import Bilhetes.Bilhete;
import Bilhetes.Fatura;
import DAO.BilheteDAO;
import DAO.BilheteDAOJson;
import MVC.MODELS.Percurso;
import java.util.List;

/**
 * Class ParqueBilhetes
 *
 * @author João e Jorge
 */
public class ParqueBilhetes {

    private BilheteDAO dao;

    private BilheteDAOFactory factory;

    /**
     * Constructor method for ParqueBilhetes
     */
    public ParqueBilhetes() {
        factory = new BilheteDAOFactory();
        this.dao = factory.createConcreteDAO("onejson", "C:\\Users\\joaos\\Desktop\\I\\EST\\4ºAno\\PA\\PA\\3ºEntrega\\1Entrega\\150221001_140221053\\Parque\\");
    }

    /**
     * Method that checks if a given ticket ID exists
     *
     * @param IdBilhete ID do bilhete
     * @return true if exists
     */
    public boolean exists(int IdBilhete) {
        System.out.println(dao);
        System.out.println(dao.selectAll());

        for (Bilhete gradeEntry : dao.selectAll()) {
            if (gradeEntry.getIdBilhete() == IdBilhete) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method that inserts a new Bilhete
     *
     * @param bilhete Bilhete a inserir
     * @throws ParqueBilhetesException
     */
    public void insert(Bilhete bilhete) throws ParqueBilhetesException {
        dao.insert(bilhete);

    }

    /**
     * Method that removes a Bilhete
     *
     * @param idBilhete ID do bilhete
     * @throws ParqueBilhetesException
     */
    public void remove(int idBilhete) throws ParqueBilhetesException {
        if (!exists(idBilhete)) {
            throw new ParqueBilhetesException("Bilhete não existe");
        }
        dao.remove(idBilhete);

    }

    /**
     * Method that gets Bilhete from a given Bilhete ID
     *
     * @param idBilhete ID do bilhete
     * @return Bilehte
     * @throws ParqueBilhetesException
     */
    public Bilhete get(int idBilhete) throws ParqueBilhetesException {
        if (!exists(idBilhete)) {
            throw new ParqueBilhetesException("Bilhete não existe");
        }
        return dao.select(idBilhete);
    }

    /**
     * Method that updates the grade of a given Bilhete
     *
     * @param idBilhete ID do bilhete
     * @param descricao descricao do bilhete 
     * @throws ParqueBilhetesException
     */
    public void updateGrade(int idBilhete, String descricao) throws ParqueBilhetesException {
        if (!exists(idBilhete)) {
            throw new ParqueBilhetesException("Erro na operação");
        }
        dao.updateBilheteDescricao(idBilhete, descricao);
    }

    /**
     * Method that returns all iterable Bilhetes
     *
     * @return iterable Bilhetes
     */
    public List<Bilhete> getAll() {

        return dao.selectAll();
    }
}
