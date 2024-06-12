package pt.ulusofona.aed.deisiworldmeter;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class TestMain {

    @Test
    public void Test_output_cities_arraylist() {

        boolean loadFiles = Main.parseFiles(new File("test-files"));

        ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);

        if (!cidades.isEmpty()) {
            for (Object cidade : cidades) {
                System.out.println(cidade);
            }
        }

        Assertions.assertFalse(cidades.isEmpty());
    }

    @Test
    public void Test_output_coutries_arraylist() {

        boolean loadFiles = Main.parseFiles(new File("test-files"));

        ArrayList paises = Main.getObjects(TipoEntidade.PAIS);

        if (!paises.isEmpty()) {
            for (Object pais : paises) {
                System.out.println(pais);
            }
        }

        Assertions.assertFalse(paises.isEmpty());

    }

    @Test
    public void Teste_leitura_ficheiros_parseFiles() {

        boolean actual = Main.parseFiles(new File("test-files"));

        Assertions.assertTrue(actual);

    }

    @Test
    public void Test_time_menor_900ms_parseFiles() {

        long start = System.currentTimeMillis();
        boolean actual = Main.parseFiles(new File("test-files"));
        long end = System.currentTimeMillis();

        Assertions.assertTrue((end-start) < 900);

        System.out.println("O tempo de leitura foi de " + (end - start) + " ms");

    }

    @Test
    public void Test_command_insert_country_and_remove_city() {

        Boolean loadFiles = Main.parseFiles(new File("test-files"));

        // Adding a city

        int nrCities = Main.csvCitiesWithCountry.size();

        Main.execute("INSERT_CITY ao test 10 1000");

        int addedCity = Main.csvCitiesWithCountry.size();

        Assertions.assertEquals(nrCities + 1, addedCity);

        //-------------------------------//-----------------------

        // Removing a country

        int nrCountries = Main.csvCountries.size();

        Main.execute("REMOVE_COUNTRY Angola");

        int removedCountry = Main.csvCountries.size();

        Assertions.assertEquals(nrCountries - 1, removedCountry);

    }

}

