package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    String alfa2;
    String cidade;
    String regiao;
    int populacao;
    double latitude;
    double longitude;

    Pais pais;
    int linhaCSV;

    public Cidade(){
    }

    @Override
    public String toString() {
        return cidade + " | " + alfa2.toUpperCase() + " | " + regiao + " | " + populacao +
                " | " + "(" + latitude + ","+longitude + ")";
    }
}

