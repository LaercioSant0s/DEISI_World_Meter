package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class TestMain {


    @Test
    public void Teste_output_arraylist_cidades() {

        boolean loadFiles = Main.parseFiles(new File("test-files"));

        ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);

        int i = 0;
        while (i < 5) {
            System.out.println(cidades.get(i));
            i++;
        }

    }


    @Test
    public void Teste_output_arraylist_paises() {

        try {
            boolean loadFiles = Main.parseFiles(new File("test-files"));

            ArrayList paises = Main.getObjects(TipoEntidade.PAIS);

            int i = 0;
            while (i < 10) {
                System.out.println(paises.get(i));
                i++;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro no ficheiro");
        }

    }

    @Test
    public void Teste_leitura_ficheiros_parseFiles() {

        boolean actual = Main.parseFiles(new File("test-files"));

        Assertions.assertTrue(actual);

    }

    @Test
    public void Teste_tempo_parseFiles() {

        long start = System.currentTimeMillis();
        boolean actual = Main.parseFiles(new File("test-files"));
        long end = System.currentTimeMillis();

        System.out.println("O tempo de leitura foi de " + (end-start) + " ms");

    }

    @Test
    public void Teste_Tempo_ParseFilesEGetObjects() {

        long start = System.currentTimeMillis();
        boolean loadFiles = Main.parseFiles(new File("test-files"));
        ArrayList paises = Main.getObjects(TipoEntidade.PAIS);
        ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);
        long end = System.currentTimeMillis();

        System.out.println("O tempo de leitura e devolução da informação foi de " + (end-start) + " ms");
    }

    @Test
    public void testToStringIDMenor700() {
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        ArrayList abuga = Main.getObjects(TipoEntidade.PAIS);
        String resultadoAtual = abuga.get(3).toString() ;
        String resultadoEsperado = "Alemanha | 276 | DE | DEU";
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste coverter para String (País, ID < 700): Incorreto");
    }

    @Test
    public void testToStringIDMaior700() {
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        ArrayList abuga = Main.getObjects(TipoEntidade.PAIS);
        String resultadoAtual = abuga.get(1).toString() ;
        String resultadoEsperado = "África do Sul | 710 | ZA | ZAF | 151";
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste coverter para String (País, ID > 700): Incorreto");
    }

    @Test
    public void testToStringCidades() {
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        ArrayList abuga = Main.getObjects(TipoEntidade.CIDADE);
        String resultadoAtual = abuga.get(7).toString() ;
        String resultadoEsperado = "acin | AF | 18 | 15098 | (34.082481,70.668152)";
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste coverter para String de Cidade: Incorreto");
    }
// af,acin,18,15098.0,34.082481,70.668152

    @Test
    public void testInputInbalido() {
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        ArrayList abuga = Main.getObjects(TipoEntidade.INPUT_INVALIDO);
        System.out.println(abuga.get(0));
        System.out.println(abuga.get(1));
        System.out.println(abuga.get(2));
    }

    @Test
    public void testParseFilesComErros(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        ArrayList abuga = Main.getObjects(TipoEntidade.INPUT_INVALIDO);
        String resultadoEsperado = "populacao.csv | 151 | 349 | 2 / cidades.csv | 12 | 7 | 9 / paises.csv | 18 | 1 | 7";
        String resultadoAtual = abuga.get(2).toString()+" / "+abuga.get(1).toString()+" / "+abuga.get(0).toString();
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste parse file com erros: Incorreto");
    }

    @Test
    public void testParseFilesSemErros(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        ArrayList abuga = Main.getObjects(TipoEntidade.PAIS);
        ArrayList abugaCidades = Main.getObjects(TipoEntidade.CIDADE);
        String resultadoEsperado = "Pais1: Alemanha | 276 | DE | DEU\nCidades pais1:\n" +
                "achim | DE | 06 | 30117 | (53.033333,9.016667)\n" +
                "adendorf | DE | 06 | 9917 | (53.283333,10.45)\n" +
                "Pais2: Austrália | 36 | AU | AUS\nCidades pais2:\n" +
                "orange | AU | 02 | 30402 | (-33.283089000000004,149.100006)\n" +
                "pakenham | AU | 07 | 10602 | (-38.070178999999996,145.474106)\n";
        String resultadoAtual = "Pais1: "+abuga.get(3)+"\nCidades pais1:\n" +
                abugaCidades.get(10)+"\n" +
                abugaCidades.get(11)+"\n" +
                "Pais2: "+abuga.get(10)+"\nCidades pais2:\n" +
                abugaCidades.get(8)+"\n" +
                abugaCidades.get(9)+"\n";
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste parse file sem erros: Incorreto");
    }

    @Test
    public void testCommandHelp(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("HELP");
        String resultadoEsperado = """
                
                -------------------------
                Commands available:
                COUNT_CITIES ‹min_population>
                GET_CITIES_BY_COUNTRY ‹num-results> <country-name>
                SUM_POPULATIONS <countries-list>
                GET_HISTORY <year-start> <year-end> ‹country_name>
                GET_MISSING_HISTORY <year-start> <year-end›
                GET_MOST_POPULOUS <num-results>
                GET_TOP_CITIES_BY_COUNTRY <num-results> ‹country-name>
                GET_DUPLICATE_CITIES ‹min_population>
                GET_COUNTRIES_GENDER_GAP <min-gender-gap>
                GET_TOP_POPULATION_INCREASE <year-start> <year_end>
                GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES ‹min-population>
                GET_CITIES_AT_DISTANCE <distance> <country-name>
                INSERT_CITY ‹alfa2> <city-name> ‹region> ‹population>
                REMOVE_COUNTRY <country-name>
                HELP
                QUIT
                -------------------------
                """;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandCountCities(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("COUNT_CITIES 10000");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetCitiesByCountry(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_CITIES_BY_COUNTRY 20 Portugal");

        System.out.println(abuga.result);

    }

    @Test
    public void testCommandSumPopulation(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("SUM_POPULATIONS Portugal");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetHistory(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_HISTORY 2016 2020 Portugal");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetMissingHistory(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_MISSING_HISTORY 2019 2024");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetMostPopulous(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_MOST_POPULOUS 5");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetTopCitiesByCountry(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_TOP_CITIES_BY_COUNTRY 5 Portugal");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetDuplicateCities(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_DUPLICATE_CITIES 1000");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetCountriesGenderGap(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_COUNTRIES_GENDER_GAP 0");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetTopPopulationIncrease(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_TOP_POPULATION_INCREASE 2023 2024");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetDuplicateCitiesDifferentCountries(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 1000");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandGetCitiesAtDistance(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("GET_CITIES_AT_DISTANCE 27 Portugal");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandInsertCity(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("INSERT_CITY pt Lisboa 01 1000000");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }

    @Test
    public void testCommandRemoveCountry(){
        Boolean LoadFiles = Main.parseFiles(new File("test-files"));
        Result abuga = Main.execute("REMOVE_COUNTRY Brasil");
        String resultadoEsperado = null;
        String resultadoAtual = abuga.result;
        Assertions.assertEquals(resultadoEsperado,resultadoAtual,
                "Teste comando com erros: Incorreto");
    }


    /*
    @Test
    public void tempo_leitura_parseFiles() {

        long[] tempos = Main.parseFiles(new File("test-files"));

        System.out.println(tempos[0]);
        System.out.println(tempos[1]);
        System.out.println(tempos[2]);

        System.out.println(Main.csvCountries.size());
        System.out.println(Main.csvCitiesWithCountry.size());
        System.out.println(Main.csvPopulationWCountryAndCity.size());
    }
     */
}

