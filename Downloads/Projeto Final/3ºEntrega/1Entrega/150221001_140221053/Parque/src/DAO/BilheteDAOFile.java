package DAO;

import Bilhetes.Bilhete;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe File onde se cria as tabelas manualmente para inserir o bilhete
 * 
 * @author João e Jorge
 */
public class BilheteDAOFile implements BilheteDAO {

    private String basePath;

    public BilheteDAOFile(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public List<Bilhete> selectAll() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public Bilhete select(int idBilhete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Bilhete entry) {
       FileWriter fw = null;
        try {
            fw = new FileWriter(basePath + entry.getIdBilhete() + ".Bilhete");
            fw.write("Id: " + entry.getIdBilhete());
            fw.write("\nData: " + entry.getDatetime());
            fw.write("\nPercurso: " + entry.getPercurso().getName());
            fw.write("\nPercurso Custo: " + entry.getPercurso().getCusto());
              fw.write("\nPercurso Distancia: " + entry.getPercurso().getDistancia());
                fw.write("\nPercurso Tipo Percurso: " + entry.getPercurso().getTipoPercurso());
                fw.write("\nPercurso Nº de Pontos: " + entry.getPercurso().getPontosCount());
                fw.write("\nPercurso Pontos: " + entry.getPercurso().getPontosCaminho());
                 fw.write("\nPercurso Caminho: " + entry.getPercurso().getCaminhoArestas());
            fw.write("\nFatura: " + entry.getFatura());
            fw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(BilheteDAOFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(BilheteDAOFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean remove(int idBilhete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateBilheteDescricao(int idBilhete, String descricao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double sellectAllPrecoMedio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

