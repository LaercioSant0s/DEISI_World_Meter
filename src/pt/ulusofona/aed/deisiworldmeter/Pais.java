package pt.ulusofona.aed.deisiworldmeter;

import java.util.ArrayList;
import java.util.Objects;

public class Pais {
    int id;
    String alfa2;
    String alfa3;
    String nome;

    int linhaCSV;

    ArrayList<Populacao> populacao = new ArrayList<>();

    ArrayList<Cidade> cidades = new ArrayList<>();

    public Pais() {
    }

    @Override
    public String toString() {

        if (id > 700) {
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase() + " | " + populacao.size();
        } else {
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase();
        }
    }
}
