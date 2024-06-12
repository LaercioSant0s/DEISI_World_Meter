package pt.ulusofona.aed.deisiworldmeter;

import java.util.*;

public class MainQueryFunctions {

    static Result commandHelp() {

        String inf = """
                -------------------------
                Commands available:
                COUNT_CITIES ‹min_population>
                GET_CITIES_BY_COUNTRY ‹num-results> <country-name>
                SUM_POPULATIONS <countries-list>
                GET_HISTORY <year-start> <year-end> ‹country_name>
                GET_MISSING_HISTORY <year-start> <year-end›
                GET_TOP_CITIES_BY_COUNTRY <num-results> ‹country-name>
                GET_DUPLICATE_CITIES ‹min_population>
                GET_COUNTRIES_GENDER_GAP <min-gender-gap>
                GET_COUNTRIES_BY_FIRST_LETTER <first-letter>
                INSERT_CITY ‹alfa2> <city-name> ‹region> ‹population>
                REMOVE_COUNTRY <country-name>
                HELP
                QUIT
                -------------------------
                """;

        return new Result(true, null, inf); //Sempre que o resultado for válido

    }

    static Result commandCountCities(int min_population) {

        int count = 0;

        for (Pais pais : Main.csvCountries) {
            for (Cidade cidade : pais.cidades) {
                if (cidade.populacao >= min_population) {
                    count++;
                }
            }
        }

        return new Result(true, null, "" + count);
    }

    static Result commandGetCitiesByCountry(int num_results, String country_name) {

        StringBuilder cidadesDeUmPais = new StringBuilder();

        Pais pais = Main.nomePaisesValidos.get(country_name);
        int count = 0;

        for (Cidade cidade : pais.cidades) {

            if (num_results == -1) {

                cidadesDeUmPais.append(cidade.cidade).append("\n");

            } else if (count < num_results) {

                cidadesDeUmPais.append(cidade.cidade).append("\n");
                count++;

            }
        }

        return new Result(true, null, cidadesDeUmPais.toString()); //sempre que comando inv
    }

    static Result commandSumPopulation(String countries_list) { //Ano 2024

        try {

            int sum = 0; //Pop, masc+fem. ficheiro pop, daquele pais

            String[] countriesList = countries_list.split(",");

            for (String country : countriesList) {

                Pais pais = Main.nomePaisesValidos.get(country);

                if (pais == null) {

                    return new Result(true, null, "Sem resultado");

                } else {

                    for (Populacao populacao : pais.populacao) {
                        if (populacao.ano == 2024) {
                            sum += populacao.populacaoFeminina + populacao.populacaoMasculina;
                        }
                    }
                }
            }


            return new Result(true, null, Integer.toString(sum));


        } catch (IndexOutOfBoundsException e) {
            return new Result(false, "comando invalido", null); //sempre que comando inv
        }


    }

    static Result commandGetHistory(int year_start, int year_end, String country_name) {
        try {
            String Resultado = "";
            Pais pais = Main.nomePaisesValidos.get(country_name);

            if (pais == null) {
                return new Result(true, "Pais invalido: " + country_name, "Pais invalido: " + country_name);
            } else {
                for (int i = year_start; i <= year_end; i++) {
                    for (Populacao populacao : pais.populacao) {
                        if (populacao.ano == i) {
                            int popMasc = populacao.populacaoMasculina / 1000;
                            int popFemi = populacao.populacaoFeminina / 1000;
                            Resultado += i + ":" + popMasc + "k:" + popFemi + "k\n";
                        }
                    }
                }
                return new Result(true, null, Resultado);
            }
        } catch (IndexOutOfBoundsException e) {
            return new Result(false, "comando invalido", null);
        }
    }

    static Result commandGetMissingHistory(int year_start, int year_end) {
        try {
            StringBuilder resultado = new StringBuilder();

            for (Pais pais : Main.csvCountries) {
                for (int i = year_start; i <= year_end; i++) {
                    boolean ano = false;
                    for (Populacao populacao : pais.populacao) {
                        if (populacao.ano == i) {
                            ano = true;
                            break;
                        }
                    }
                    if (!ano) {
                        resultado.append(pais.alfa2).append(":").append(pais.nome).append("\n");
                        break;
                    }
                }
            }

            if (!resultado.toString().isEmpty()) {
                return new Result(true, null, resultado.toString());
            } else {
                return new Result(true, null, "Sem resultados");
            }

        } catch (IndexOutOfBoundsException e) {
            return new Result(false, "comando invalido", null);
        }
    }

    static Result commandGetTopCitiesByCountry(int num_results, String country_name) {

        try {

            StringBuilder result = new StringBuilder();
            Pais pais = Main.nomePaisesValidos.get(country_name);

            pais.cidades.sort(Comparator.comparing((Cidade a) -> (a.populacao / 1000)).reversed().thenComparing((Cidade a) -> (a.cidade)));

            int count = 0;

            for (Cidade cidade : pais.cidades) {

                if (num_results == -1) {

                    int rounded = cidade.populacao / 1000;
                    result.append(cidade.cidade).append(":").append(rounded).append("K").append("\n");

                } else if (count < num_results) {

                    int rounded = cidade.populacao / 1000;
                    result.append(cidade.cidade).append(":").append(rounded).append("K").append("\n");

                    count++;

                } else {

                    break;

                }

            }

            return new Result(true, null, result.toString());


        } catch (Exception e) {
            return new Result(false, "comando invalido", null); //sempre que comando inv

        }

    }

    static Result commandGetDuplicateCities(int min_population) {

        ArrayList<Cidade> cidadesDuplicadas = new ArrayList<>();

        for (Cidade cidade : Main.csvCitiesWithCountry) {

            for (int i = 1; i < Main.csvCitiesWithCountry.size(); i++) {

                if (cidade.cidade.equals(Main.csvCitiesWithCountry.get(i).cidade)) {

                    cidadesDuplicadas.add(Main.csvCitiesWithCountry.get(i));

                }

            }

        }

        StringBuilder result = new StringBuilder();

        for (Cidade cidade1 : cidadesDuplicadas) {

            if (cidade1.populacao >= min_population) {
                result.append(cidade1.cidade).append("  (").append(cidade1.pais).append(",").append(cidade1.regiao).append(")\n");
            }

        }

        if (result.toString().isEmpty()) {

            return new Result(true, null, "Sem resultado");

        } else {

            return new Result(true, null, result.toString());
        }

    }

    static Result commandGetCountriesGenderGap(int min_gender_gap) {
        try {
            StringBuilder result = new StringBuilder();
            for (Pais pais : Main.csvCountries) {
                float conta;
                for (Populacao populacao : pais.populacao) {
                    if (populacao.ano == 2024) {
                        if (populacao.populacaoMasculina > populacao.populacaoFeminina) {
                            conta = ((float) (populacao.populacaoMasculina - populacao.populacaoFeminina)
                                    / (populacao.populacaoMasculina + populacao.populacaoFeminina)) * 100;
                        } else {
                            conta = ((float) (populacao.populacaoFeminina - populacao.populacaoMasculina)
                                    / (populacao.populacaoMasculina + populacao.populacaoFeminina)) * 100;
                        }
                        if (conta > min_gender_gap) {
                            String contaDecimais = String.format("%.2f", conta);
                            result.append(pais.nome).append(":").append(contaDecimais).append("\n");
                        }
                    }
                }
            }
            if (result.toString().isEmpty()) {
                return new Result(true, null, "Sem resultados");
            } else {
                return new Result(true, null, result.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            return new Result(false, "comando invalido", null);
        }
    }

    static Result commandGetCountryFirstLetter(char first_letter) {

        try {

            StringBuilder countries = new StringBuilder();

            for (Pais pais : Main.csvCountries) {

                if (pais.nome.charAt(0) == first_letter) {

                    countries.append(pais).append(": ").append(pais.cidades).append(" cities\n");

                }

            }

            return new Result(true, null, countries.toString());

        } catch (NumberFormatException e) {
            return new Result(false, "comando invalido", null);
        }
    }

    static Result commandInsertCity(String alfa2, String city_name, String region, int population) {

        try {

            Cidade cidade;
            cidade = new Cidade();
            cidade.alfa2 = alfa2.trim();
            cidade.cidade = city_name;
            cidade.regiao = region;
            cidade.populacao = population;

            Pais paisesAlfa2 = Main.alfa2PaisesValidos.get(alfa2.trim());

            if (paisesAlfa2 != null) {

                if (paisesAlfa2.alfa2.equals(alfa2)) {

                    paisesAlfa2.cidades.add(cidade); // alfa2PaisesValidos
                    Main.csvCitiesWithCountry.add(cidade); // csv cities

                    return new Result(true, null, "Inserido com sucesso");

                }

            }

            return new Result(true, null, "Pais invalido");

        } catch (Exception e) {

            return new Result(false, "comando invalido", null);

        }

    }

    static Result commandRemoveCountry(String country_name) {

        try {

            Iterator<Pais> iterator = Main.csvCountries.iterator();

            while (iterator.hasNext()) {

                Pais pais = iterator.next();

                if (pais.nome.equals(country_name)) {
                    pais.cidades = new ArrayList<>();
                    pais.populacao = new ArrayList<>();
                    iterator.remove();
                    break;
                }

            }

            return new Result(true, null, "Removido com sucesso");

        } catch (NullPointerException e) {

            return new Result(false, "comando invalido", null); //sempre que comando inv

        }

    }

}