package pt.ulusofona.aed.deisiworldmeter;

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

    static Result commandGetCitiesByCountry(int num_results,String country_name) {

        StringBuilder cidadesDeUmPais = new StringBuilder();

        Pais pais = Main.nomePaisesValidos.get(country_name);
        int count = 0;

        for (Cidade cidade : pais.cidades) {

            if (count < num_results) {
                cidadesDeUmPais.append(cidade.cidade).append("\n");
            } else {
                break;
            }
            count++;
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

                    return new Result(true, "Pais invalido: " + country, "Pais invalido: " + country); //sempre que comando inv

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

    static Result commandGetHistory(int year_start,int year_end,String country_name) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetMissingHistory(int year_start,int year_end) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetMostPopulous(int num_results) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetTopCitiesByCountry(int num_results,String country_name) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetDuplicateCities(int min_population) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetCountriesGenderGap(int min_gender_gap) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetTopPopulationIncrease(int year_start, int year_end) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetDuplicateCitiesDifferentCountries(int min_population) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandGetCitiesAtDistance(int distance, String country_name) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }

    static Result commandInsertCity(String alfa2,String city_name,int region,int population) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }
    static Result commandRemoveCountry(String country_name) {
        return new Result(false, "comando invalido", null); //sempre que comando inv
    }
}
