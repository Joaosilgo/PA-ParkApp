package DAO;

/**
 * Classe que cria instancias de bilhetes
 *
 * @author João e Jorge
 */
public class BilheteDAOFactory {
    
    public static BilheteDAO createConcreteDAO(String type, String basepath){
        
        switch(type){
            case "file": return new BilheteDAOFile(basepath);
            case "onejson": return new BilheteDAOJson(basepath);
            case "serialization": return new BilheteDAOSerialization(basepath);
            default: throw new IllegalArgumentException("type does not exist");
        }            
    }
}
