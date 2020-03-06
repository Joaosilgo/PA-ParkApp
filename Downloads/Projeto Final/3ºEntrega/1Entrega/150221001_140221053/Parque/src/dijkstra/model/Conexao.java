package dijkstra.model;

import java.util.Objects;

/**
 * Classe modelo do algoritmo Dijkstra que gere o percurso atual com todos os pontos de interesse 
 * 
 * @author João e Jorge
 */
public class Conexao {

    /**
     * Enum for TYPE_CONECT
     */
    public enum TYPE_CONECT {

        PONTE, CAMINHO;

        /**
         * Method that returns ponte if TYPE_CONECT = PONTE and caminho if
         * TYPE_CONECT = CAMINHO
         *
         * @return ponte or caminho
         */
        @Override
        public String toString() {
            String s = "";
            switch (this) {
                case PONTE:
                    s += "ponte";
                case CAMINHO:
                    s += "caminho";
                default:
                    s += "";
            }
            return s;
        }
    };

    private int id_c;
    private TYPE_CONECT tipo;
    private final String conexao;
    private final boolean navegabilidade;
    private final int custo;
    private final int distancia;

    /**
     * Constructor for the class Conexao
     *
     * @param id_c id da conexao
     * @param tipo tipo de conexao
     * @param conexao conexao
     * @param navegabilidade navegabilidade
     * @param custo custo do percurso
     * @param distancia distancia do percurso
     */
    public Conexao(int id_c, TYPE_CONECT tipo, String conexao, boolean navegabilidade, int custo, int distancia) {
        if (custo < 0) {
            throw new IllegalArgumentException("Price must be > 0");
        }
        if (distancia < 0) {
            throw new IllegalArgumentException("Distance must be > 0");
        }
        this.tipo = tipo;
        this.id_c = id_c;
        this.conexao = conexao;
        this.navegabilidade = navegabilidade;
        this.custo = custo;
        this.distancia = distancia;
    }

    /**
     * Getter method for id
     *
     * @return id
     */
    public int getId_c() {
        return id_c;
    }

    /**
     * Getter method for tipo
     *
     * @return tipo
     */
    public TYPE_CONECT getTipo() {
        return tipo;
    }

    /**
     * Getter method for conexao
     *
     * @return conexao
     */
    public String getConexao() {
        return conexao;
    }

    /**
     * Getter method for navegabilidade
     *
     * @return navegabilidade
     */
    public boolean isNavegabilidade() {
        return navegabilidade;
    }

    /**
     * Getter method for custo
     *
     * @return custo
     */
    public int getCusto() {
        return custo;
    }

    /**
     * Getter method for distancia
     *
     * @return distancia
     */
    public int getDistancia() {
        return distancia;
    }

    /**
     * Getter method for description
     *
     * @return description
     */
    public String getDescription() {
        return conexao + " " + id_c;
    }

    /**
     * Method toString that prints the conexao description, distancia and custo
     *
     * @return string of the conexao
     */
    @Override
    public String toString() {
        return String.format("\n->(%s [%d miles,  %d €])\n",
                getDescription(), distancia, custo);
    }

    /**
     * Method that checks if the given object is from the type conexao
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
        final Conexao other = (Conexao) obj;
        if (this.id_c != other.id_c) {
            return true;
        }
        return !Objects.equals(this.conexao, other.conexao);
    }

    /**
     * Method that returns the hashCode from the object
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.conexao);
        hash = 41 * hash + this.id_c;
        return hash;
    }
}