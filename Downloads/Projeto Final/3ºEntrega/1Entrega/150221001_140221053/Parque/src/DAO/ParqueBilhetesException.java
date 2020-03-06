package DAO;

/**
 * 
 * @author João e Jorge
 */
public class ParqueBilhetesException extends RuntimeException {

    public ParqueBilhetesException() {
        super("O Bihete é invalido");
    }

    public ParqueBilhetesException(String string) {
        super(string);
    }
}