package DAO;

import Bilhetes.Bilhete;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que guarda o bilhete como objecto atraves da sua colecao de pares de valores
 *
 * @author João e Jorge
 */
public class BilheteDAOJson implements BilheteDAO {

    private String basePath;
    private static final String fileName = "Bilhetes.json";

    public BilheteDAOJson(String basePath) {
        this.basePath = basePath;
    }

    /**
     * Method that selects all the Bilhetes into a List
     *
     * @return new ArrayList
     */
    @Override
    public List<Bilhete> selectAll() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(basePath + fileName));
            Gson gson = new GsonBuilder().create();

            ArrayList<Bilhete> list = gson.fromJson(br, new TypeToken<ArrayList<Bilhete>>() {
            }.getType());

            if (list == null) {
                return new ArrayList<>();
            }
            return list;

        } catch (IOException ex) {
            Logger.getLogger(BilheteDAOJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    /**
     * Method that returns a Bilhete given his ID
     *
     * @param idBilhete ID do bilhete
     * @return Bilhete
     */
    @Override
    public Bilhete select(int idBilhete) {
        ArrayList<Bilhete> list = (ArrayList<Bilhete>) selectAll();
        for (Bilhete bilhete : list) {
            if (bilhete.getIdBilhete() == idBilhete) {
                return bilhete;
            }
        }
        return null;
    }

    /**
     * Method that inserts a new Bilhete given an entry
     *
     * @param entry Bilhete entry
     * @return true if the Bilhete was inserted
     */
    @Override
    public boolean insert(Bilhete entry) {
        List<Bilhete> all = selectAll();
        if (all.contains(entry)) {
            return false;
        }
        all.add(entry);

        save(all);

        return true;
    }

    /**
     * Method that saves the tickets list
     *
     * @param list lista de bilhetes
     */
    private void save(List<Bilhete> list) {
        try {
            FileWriter writer = null;
            Gson gson = new GsonBuilder().create();

            writer = new FileWriter(basePath + fileName);
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(BilheteDAOJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that selects all tickets where the TipoPercurso is PE
     *
     * @return ArrayList
     */
    public List<Bilhete> selectAllTipoPercursoPe() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(basePath + fileName));
            Gson gson = new GsonBuilder().create();

            ArrayList<Bilhete> list = gson.fromJson(br,
                    new TypeToken<ArrayList<Bilhete>>() {
                    }.getType());
            if (list == null) {
                return new ArrayList<>();
            }
            for (Bilhete item : list) {
                //  item.getPercurso().getTipoPercurso()
            }
            return list;

        } catch (IOException ex) {
            Logger.getLogger(BilheteDAOJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    /**
     * Method that removes a Bilhete given a studentCode
     *
     * @param idBilhete ID do bilhete
     * @return true if the Bilhete was removed
     */
    public boolean remove(int idBilhete) {
        Bilhete bilhete = getGradeWithCode(idBilhete);

        List<Bilhete> all = selectAll();
        if (all.contains(bilhete)) {
            all.remove(bilhete);
            save(all);
            return true;
        }
        return false;
    }

    /**
     * Method that updates the description of a given Bilhete
     *
     * @param idBilhete ID do bilhete
     * @param descricao descrição do bilhete
     * @return true if it was updated
     */
    public boolean updateGrade(int idBilhete, String descricao) {
        Bilhete bilhete = getGradeWithCode(idBilhete);
        List<Bilhete> all = selectAll();
        if (all.contains(bilhete)) {
            bilhete.setDescricao(descricao);
            save(all);
            return true;
        }
        return false;
    }

    /**
     * Method that gets the grade with the given studentCode
     *
     * @param idBilhete ID do bilhete
     * @return Bilhete from the given studentCode
     */
    public Bilhete getGradeWithCode(int idBilhete) {
        for (Bilhete gradeEntry : selectAll()) {
            if (gradeEntry.getIdBilhete() == idBilhete) {
                return gradeEntry;
            }
        }
        return null;
    }

    /**
     * Method that updates the ticket description
     *
     * @param idBilhete ID do bilhete
     * @param descricao descrição do bilhete
     * @return UnsupportedOperationException
     */
    @Override
    public boolean updateBilheteDescricao(int idBilhete, String descricao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Method that calculates the average price of all tickets
     *
     * @return media
     */
    @Override
    public double sellectAllPrecoMedio() {
        List<Bilhete> b = selectAll();

        double custoBilhetes = 0.0;
        int totalBilhetes = b.size();

        for (Bilhete item : b) {
            custoBilhetes += item.getPercurso().getCusto();
        }

        double media = custoBilhetes / totalBilhetes;

        return media;
    }
}
