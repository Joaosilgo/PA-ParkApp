package DAO;

import Bilhetes.Bilhete;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serializacao de uma classe e ativada pela classe que implementa a interface do bilhete
 *
 * @author João e Jorge
 */
public class BilheteDAOSerialization implements BilheteDAO {

    private String basePath;
    private ArrayList<Bilhete> list;
    private final static String filename = "Bilhetes.dat";

    /**
     * Constructor method for BilheteDAOSerialization
     *
     * @param basepath local onde está o ficheiro guardado
     */
    public BilheteDAOSerialization(String basepath) {

        this.basePath = basepath;
        this.list = new ArrayList<>();
        loadAll();
    }

    /**
     * Method that loads BilheteDAOSerialization
     */
    public void loadAll() {
        try {
            FileInputStream fileIn = new FileInputStream(this.basePath + filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.list = (ArrayList<Bilhete>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            return;
        } catch (ClassNotFoundException c) {
            return;
        }
    }

    /**
     * Mehtod that saves all BilheteDAOSerialization
     */
    private void saveall() {
        FileOutputStream fileOut = null;
        ArrayList<Bilhete> list = (ArrayList<Bilhete>) selectAll();

        try {
            fileOut = new FileOutputStream(basePath + filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BilheteDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BilheteDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that selects all gradeEntrys
     *
     * @return ArrayList of the gradeEntrys
     */
    @Override
    public List<Bilhete> selectAll() {

        List<Bilhete> listAux = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(basePath + filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (ArrayList<Bilhete>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {

            return new ArrayList<>();
        } catch (ClassNotFoundException c) {
            System.out.println("HashSet of Books- class not found");

            return new ArrayList<>();
        }
        return list;
    }

    /**
     * Method that selects a Bilhete given his ID
     *
     * @param IdBilhete ID do bilhete
     * @return Bilhete gradeEntry
     */
    @Override
    public Bilhete select(int IdBilhete) {
        for (Bilhete gradeEntry : list) {
            if (gradeEntry.getIdBilhete() == IdBilhete) {
                return gradeEntry;
            }
        }
        return null;
    }

    /**
     * Method that inserts a new Bilhete into the List
     *
     * @param entry entry do bilhete
     * @return true if the Bilhete was inserted
     */
    @Override
    public boolean insert(Bilhete entry) {
        if (list.contains(entry)) {
            return false;
        }
        list.add(entry);
        saveall();
        return true;
    }

    /**
     * Method that gets the grade with code give a Bilhete ID
     *
     * @param IdBilhete ID do bilhete
     * @return Bilhete gradeEntry
     */
    public Bilhete getGradeWithCode(int IdBilhete) {
        for (Bilhete gradeEntry : list) {
            if (gradeEntry.getIdBilhete() == IdBilhete) {
                return gradeEntry;
            }
        }
        return null;
    }

    /**
     * Method that removes a Bilhete given a IdBilhete
     *
     * @param IdBilhete ID do bilhete
     * @return true if the Bilhete was removed
     */
    @Override
    public boolean remove(int IdBilhete) {
        if (list.contains(getGradeWithCode(IdBilhete))) {
            list.remove(getGradeWithCode(IdBilhete));
            saveall();
            return true;
        }
        return false;
    }

    /**
     * Method that updates the Bilhete description
     *
     * @param IdBilhete ID do bilhete
     * @param descricao descrição do bilhete
     * @return true if the description was updated
     */
    @Override
    public boolean updateBilheteDescricao(int IdBilhete, String descricao) {
        if (list.contains(getGradeWithCode(IdBilhete))) {
            getGradeWithCode(IdBilhete).setDescricao(descricao);
            saveall();
            return true;
        }
        return false;
    }

    /**
     * Method that selects the average price of tickets
     *
     * @return average ticket price
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
