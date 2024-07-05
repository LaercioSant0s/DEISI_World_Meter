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
    static HashMap<String, Pais> alfa2PaisesValidos = new HashMap<>();

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

    public static String[] commandTreatment(String commandInput) {

        if (commandInput == null || commandInput.isEmpty()) {
            return null;
        }

        String queryName = commandInput.split(" ")[0];
        String commandWithoutQueryName = commandInput.replace(queryName, "").trim();

        String[] commandParts = commandWithoutQueryName.split(",");

        if (commandParts.length > 1) {
            return new String[]{queryName, commandWithoutQueryName};
        }

        String[] commands = commandWithoutQueryName.split(" ");

        if (commands.length == 1) { // <1000> v <country>

            return new String[]{queryName, commands[0]};


        } else if (commands.length == 2) { //>1000> <África>

            // 1000 2000
            if (Character.isDigit(commands[0].charAt(0)) && Character.isDigit(commands[1].charAt(0))) {

                return new String[]{queryName, commands[0], commands[1]};

            }

            // Arábia Saudita
            if (Character.isLetter(commands[0].charAt(0)) && Character.isLetter(commands[1].charAt(0))) {

                StringBuilder countryName = new StringBuilder();

                for (String i : commands) {
                    if (i.equals(" ")) {
                        countryName.append(" ");
                    } else {
                        countryName.append(i);
                    }
                }

                return new String[]{queryName, countryName.toString()};

            }

            // >1000> <África>
            if (Character.isLetter(commands[1].charAt(0))) {

                return new String[]{queryName, commands[0], commands[1]};

            }


        } else if (commands.length == 3) { //  <1000> <2000> <África>

            //Africa do Sul
            if (Character.isLetter(commands[0].charAt(0)) && Character.isLetter(commands[1].charAt(0)) && Character.isLetter(commands[2].charAt(0))) {

                StringBuilder countryName = new StringBuilder();

                for (String i : commands) {
                    if (i.equals(" ")) {
                        countryName.append(" ");
                    } else {
                        countryName.append(i);
                    }
                }

                return new String[]{queryName, countryName.toString()};

            }

            // <year-start> <year-end> ‹country_name>
            if (Character.isDigit(commands[0].charAt(0)) && Character.isDigit(commands[1].charAt(0)) && Character.isLetter(commands[2].charAt(0))) {

                return new String[]{queryName, commands[0], commands[1], commands[2]};

            }

            // ‹num-results> Arábia Saudita
            if (Character.isDigit(commands[0].charAt(0)) && Character.isLetter(commands[1].charAt(0)) && Character.isLetter(commands[2].charAt(0))) {

                StringBuilder countryName = new StringBuilder();

                boolean firstLine = true;
                for (String i : commands) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    if (i.equals(" ")) {
                        countryName.append(" ");
                    } else {
                        countryName.append(i);
                    }
                }

                return new String[]{queryName, commands[0], countryName.toString()};

            }


        } else if (commands.length == 4) {

            if (Character.isLetter(commands[0].charAt(0)) && Character.isLetter(commands[1].charAt(0)) && Character.isDigit(commands[2].charAt(0)) && Character.isDigit(commands[3].charAt(0))) {

                return new String[]{queryName, commands[0], commands[1], commands[2], commands[3]};

            }

        }

        return null;

    }

    public static Result execute(String command) {

        try {

            String[] commandParts = commandTreatment(command);

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
                    try {
                        return MainQueryFunctions.commandGetHistory(Integer.parseInt(commandParts[1]),
                                Integer.parseInt(commandParts[2]), commandParts[3]);
                    } catch (IndexOutOfBoundsException e) {
                        return new Result(false, "comando invalido", null);
                    }

                case "GET_MISSING_HISTORY":
                    try {
                        return MainQueryFunctions.commandGetMissingHistory(Integer.parseInt(commandParts[1]),
                                Integer.parseInt(commandParts[2]));

                    } catch (IndexOutOfBoundsException e) {
                        return new Result(false, "comando invalido", null);
                    }

                case "GET_TOP_CITIES_BY_COUNTRY":
                    try {
                        return MainQueryFunctions.commandGetTopCitiesByCountry(Integer.parseInt(commandParts[1]),
                                commandParts[2]);

                    } catch (IndexOutOfBoundsException e) {
                        return new Result(false, "comando invalido", null);
                    }

                case "GET_DUPLICATE_CITIES":
                    try {
                        return MainQueryFunctions.commandGetDuplicateCities(Integer.parseInt(commandParts[1]));
                    } catch (IndexOutOfBoundsException e) {
                        return new Result(false, "comando invalido", null);
                    }

                case "GET_COUNTRIES_GENDER_GAP":
                    try {
                        return MainQueryFunctions.commandGetCountriesGenderGap(Integer.parseInt(commandParts[1]));
                    } catch (Exception e){
                        return new Result(false, "comando invalido", null);
                    }

                case "GET_COUNTRIES_NUM_CITIES_BY_FIRST_LETTER":

                    try {

                        if (commandParts.length == 2 && commandParts[1].length() == 1) {
                            return MainQueryFunctions.commandGetCountriesNumCitiesByFirstLetter(commandParts[1].charAt(0));
                        } else {
                            return new Result(false, "comando invalido", null);
                        }

                    } catch (NumberFormatException e){
                        return new Result(false, "comando invalido", null);
                    }

                case "INSERT_CITY":

                    try {

                        return MainQueryFunctions.commandInsertCity(commandParts[1],commandParts[2],
                                commandParts[3],Integer.parseInt(commandParts[4]));

                    } catch (IndexOutOfBoundsException e) {

                        return new Result(false, "comando invalido", null);

                    }


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