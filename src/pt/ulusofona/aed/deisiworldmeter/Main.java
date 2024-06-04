package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {


    static ArrayList<Pais> csvCountries = new ArrayList<>();
    static ArrayList<Cidade> csvCitiesWithCountry = new ArrayList<>();
    static ArrayList<Populacao> csvPopulationWCountryAndCity = new ArrayList<>();
    static ArrayList<String> inputInvalido = new ArrayList<>();

    static HashMap<String, Pais> nomePaisesValidos = new HashMap<>();
    static HashMap<Integer, Pais> idPaisesValidos = new HashMap<>();


    public static boolean parseFiles(File folder) {

        MainAuxReadingFunctions.cleanStructures();

        //long[] tempos = new long[3];

        //long start, end;

        //start = System.currentTimeMillis();
        boolean leituraFilePaises = MainAuxReadingFunctions.leFilePaises(new File(folder, "paises.csv"));
        //end = System.currentTimeMillis();
        //tempos[0] = (end-start);

        //start = System.currentTimeMillis();
        boolean leituraFileCidades = MainAuxReadingFunctions.leFileCidades(new File(folder, "cidades.csv"));
        //end = System.currentTimeMillis();
        //tempos[1] = (end - start);

        //start = System.currentTimeMillis();
        boolean leituraFilePopulacao = MainAuxReadingFunctions.leFilePolulacao(new File(folder, "populacao.csv"));
        //end = System.currentTimeMillis();
        //tempos[2] = end - start;

        MainAuxReadingFunctions.atualizarEstruturas(leituraFilePaises, leituraFileCidades, leituraFilePopulacao);

        //return tempos;

        return leituraFileCidades && leituraFilePaises && leituraFilePopulacao;



    }

    public static ArrayList getObjects(TipoEntidade tipo) {

        if (tipo == TipoEntidade.PAIS) {

            return csvCountries;

        } else if (tipo == TipoEntidade.CIDADE) {

            return csvCitiesWithCountry;

        } else if (tipo == TipoEntidade.INPUT_INVALIDO) {

            return inputInvalido;
        }

        return new ArrayList<>();
    }

    public static Result execute(String command) {

        try {
            String[] commandParts = command.split(" ");

            switch (commandParts[0]) {

                case "HELP":
                    return MainQueryFunctions.commandHelp();

                case "COUNT_CITIES":
                    try {
                        return MainQueryFunctions.commandCountCities(Integer.parseInt(commandParts[1]));
                    } catch (IndexOutOfBoundsException e) {
                        return new Result(false, "comando invalido", null);
                    }

                case "GET_CITIES_BY_COUNTRY":
                    try {
                        return MainQueryFunctions.commandGetCitiesByCountry(Integer.parseInt(commandParts[1]), commandParts[2]);
                    } catch (IndexOutOfBoundsException e) {
                        return new Result(false, "comando invalido", null);
                    }


                case "SUM_POPULATIONS": //ano 2024
                    try {
                        return MainQueryFunctions.commandSumPopulation(commandParts[1]);
                    } catch (IndexOutOfBoundsException e) {
                        return new Result(false, "comando invalido", null);
                    }

                case "GET_HISTORY":
                    return MainQueryFunctions.commandGetHistory(Integer.parseInt(commandParts[1]),
                            Integer.parseInt(commandParts[2]),commandParts[3]);

                case "GET_MISSING_HISTORY":
                    return MainQueryFunctions.commandGetMissingHistory(Integer.parseInt(commandParts[1]),
                            Integer.parseInt(commandParts[2]));

                case "GET_MOST_POPULOUS":
                    return MainQueryFunctions.commandGetMostPopulous(Integer.parseInt(commandParts[1]));

                case "GET_TOP_CITIES_BY_COUNTRY":
                    return MainQueryFunctions.commandGetTopCitiesByCountry(Integer.parseInt(commandParts[1]),
                            commandParts[2]);

                case "GET_DUPLICATE_CITIES":
                    return MainQueryFunctions.commandGetDuplicateCities(Integer.parseInt(commandParts[1]));

                case "GET_COUNTRIES_GENDER_GAP":
                    return MainQueryFunctions.commandGetCountriesGenderGap(Integer.parseInt(commandParts[1]));

                case "GET_TOP_POPULATION_INCREASE":
                    return MainQueryFunctions.commandGetTopPopulationIncrease(Integer.parseInt(commandParts[1]),
                            Integer.parseInt(commandParts[2]));

                case "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES":
                    return MainQueryFunctions.commandGetDuplicateCitiesDifferentCountries(Integer.parseInt(commandParts[1]));

                case "GET_CITIES_AT_DISTANCE":
                    return MainQueryFunctions.commandGetCitiesAtDistance(Integer.parseInt(commandParts[1]),commandParts[2]);

                case "INSERT_CITY":
                    return MainQueryFunctions.commandInsertCity(commandParts[1],commandParts[2],
                            Integer.parseInt(commandParts[3]),Integer.parseInt(commandParts[4]));

                case "REMOVE_COUNTRY":
                    return MainQueryFunctions.commandRemoveCountry(commandParts[1]);

                default:
                    return new Result(false, "comando invalido", null);
            }



        } catch (NullPointerException e) {

            return new Result(false, "comando invalido", null);

        }

    }


    public static void main(String[] args) {

        System.out.println("Welcome to DEISI World Meter");

        long start = System.currentTimeMillis();
        boolean parseOK = parseFiles(new File("test-files"));

        if (!parseOK) {
            System.out.println("Error loading files");
            return;
        }
        long end = System.currentTimeMillis();

        System.out.println("Loaded files in " + (end - start) + " ms");

        Result result = execute("HELP");
        System.out.println(result.result);

        Scanner in = new Scanner(System.in);

        String line;

        do {

            System.out.print("> ");
            line = in.nextLine(); //Read input at the start of the loop

            if (line != null && !line.equals("QUIT")) {

                start = System.currentTimeMillis();
                result = execute(line);
                end = System.currentTimeMillis();

                if (!result.success) {
                    System.out.println("Error: " + result.error);
                } else {
                    System.out.println(result.result);
                    System.out.println("(took " + (end - start) + " ms)");
                }
            }

        } while (line != null && !line.equals("QUIT"));

    }
}