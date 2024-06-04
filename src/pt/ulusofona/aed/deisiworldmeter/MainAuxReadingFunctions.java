package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MainAuxReadingFunctions {

    static void cleanStructures() {

        Main.csvCountries = new ArrayList<>();
        Main.csvCitiesWithCountry = new ArrayList<>();
        Main.csvPopulationWCountryAndCity = new ArrayList<>();
        Main.inputInvalido = new ArrayList<>();

    }

    static boolean isNotValidInteger(String s) {

        if (s == null || s.isEmpty()) {

            return true;

        }

        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {

                return true;

            }
        }

        return false;
    }

    static boolean isNotValidFloat(String s) {

        try {

            Double convertToDouble = Double.parseDouble(s);

        } catch (NumberFormatException e) {
            return true;
        }

        return false;
    }

    static boolean leFilePaises(File ficheiroPaises) {

        int lePaisesCertos;
        int lePaisesErrados = 0;
        int primeiraLinhaErrada = -1;
        int countLinhaCSV = 0;

        Scanner scanner;

        try {
            scanner = new Scanner(ficheiroPaises);
        } catch (FileNotFoundException e) {
            return false;
        }

        boolean firstline = true;

        while (scanner.hasNext()) {
            countLinhaCSV++;

            String line = scanner.nextLine();

            if (firstline) {

                firstline = false;

            } else {

                String[] parts = line.split(",");

                if (parts.length != 4) {

                    lePaisesErrados++;
                    if (primeiraLinhaErrada == -1) {
                        primeiraLinhaErrada = countLinhaCSV;
                    }

                } else {

                    String id = parts[0];
                    String alfa2 = parts[1];
                    String alfa3 = parts[2];
                    String nome = parts[3];

                    if (isNotValidInteger(id)) {

                        lePaisesErrados++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else if (alfa2.length() != 2) {

                        lePaisesErrados++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else if (alfa3.length() != 3) {

                        lePaisesErrados++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else if (nome.isEmpty()) {

                        lePaisesErrados++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else {

                        Pais pais1;
                        pais1 = new Pais();
                        pais1.id = Integer.parseInt(id);
                        pais1.alfa2 = alfa2;
                        pais1.alfa3 = alfa3;
                        pais1.nome = nome;
                        pais1.linhaCSV = countLinhaCSV;

                        // explicar a ideia

                        if (Main.csvCountries.isEmpty()) {

                            Main.csvCountries.add(pais1);

                        } else {

                            boolean dontHasCountry = true;

                            for (Pais pais : Main.csvCountries) {

                                if (pais.id == pais1.id) {

                                    // Contabilizar pais duplicado errado:
                                    lePaisesErrados++;
                                    if (primeiraLinhaErrada == -1) {
                                        primeiraLinhaErrada = countLinhaCSV;
                                    }

                                    dontHasCountry = false;
                                    break;

                                }

                            }

                            if (dontHasCountry) {

                                Main.csvCountries.add(pais1);

                            }
                        }
                    }
                }
            }
        }

        // (countLinhaCSV - 1) cabeçalho
        lePaisesCertos = (countLinhaCSV - 1) - lePaisesErrados;

        String InputsPaisesInvalidos = "paises.csv | " + lePaisesCertos + " | " + lePaisesErrados + " | " + primeiraLinhaErrada;
        Main.inputInvalido.add(InputsPaisesInvalidos);
        return true;

    }

    static boolean leFileCidades(File ficheiroCidades) {

        int leCidadesCertas = 0;
        int leCidadesErradas = 0;
        int primeiraLinhaErrada = -1;
        int countLinhaCSV = 0;

        Scanner scanner;

        try {
            scanner = new Scanner(ficheiroCidades);
        } catch (FileNotFoundException e) {
            return false;
        }

        boolean firstLine = true;

        while (scanner.hasNext()) {
            countLinhaCSV++;

            String line = scanner.nextLine();

            if (firstLine) {

                firstLine = false;

            } else {

                String[] parts = line.split(",");

                if (parts.length != 6) {

                    leCidadesErradas++;
                    if (primeiraLinhaErrada == -1) {
                        primeiraLinhaErrada = countLinhaCSV;
                    }

                } else {

                    String alfa2 = parts[0];
                    String cidade = parts[1];
                    String regiao = parts[2];
                    String populacao = parts[3];
                    String latitude = parts[4];
                    String longitude = parts[5];

                    if (cidade.isEmpty()) {

                        leCidadesErradas++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else if (isNotValidFloat(populacao) && isNotValidInteger(populacao)) {

                        leCidadesErradas++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else if (isNotValidFloat(latitude)) {

                        leCidadesErradas++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else if (isNotValidFloat(longitude)) {

                        leCidadesErradas++;
                        if (primeiraLinhaErrada == -1) {
                            primeiraLinhaErrada = countLinhaCSV;
                        }

                    } else { // Se chegar aqui tudo é válido, logo podemos converter e criar o objeto

                        Cidade cidade1;
                        cidade1 = new Cidade();
                        cidade1.alfa2 = alfa2;
                        cidade1.cidade = cidade;
                        cidade1.regiao = regiao;
                        cidade1.populacao = Float.valueOf(populacao).intValue();
                        cidade1.latitude = Double.parseDouble(latitude);
                        cidade1.longitude = Double.parseDouble(longitude);
                        cidade1.linhaCSV = countLinhaCSV;

                        boolean cityDontHasCountry = true;

                        for (Pais pais : Main.csvCountries) {

                            if (pais.alfa2.equals(cidade1.alfa2)) {

                                leCidadesCertas++;

                                cidade1.pais = new Pais();
                                cidade1.pais = pais;
                                pais.cidades.add(cidade1);
                                Main.csvCitiesWithCountry.add(cidade1);
                                cityDontHasCountry = false;

                                break;
                            }

                        }

                        if (cityDontHasCountry) {
                            leCidadesErradas++;
                            if (primeiraLinhaErrada == -1) {
                                primeiraLinhaErrada = countLinhaCSV;
                            }
                        }

                    }
                }
            }
        }

        String InputsCidadesInvalidos = "cidades.csv | " + leCidadesCertas + " | " + leCidadesErradas + " | " + primeiraLinhaErrada;
        Main.inputInvalido.add(InputsCidadesInvalidos);
        return true;
    }

    static boolean leFilePolulacao(File ficheiroPopulacao) {
        int lePopulacaoCerta = 0;
        int lePopulacaoErrada = 0;
        int primeiraLinhaPopulacaoErrada = -1;
        int countLinhaCSV = 0;

        Scanner scanner;

        try {
            scanner = new Scanner(ficheiroPopulacao);
        } catch (FileNotFoundException e) {
            return false;
        }

        boolean firstLine = true;

        while (scanner.hasNext()) {
            countLinhaCSV++;

            String line = scanner.nextLine();

            if (firstLine) {

                firstLine = false;

            } else {

                String[] parts = line.split(",");

                if (parts.length != 5) {

                    lePopulacaoErrada++;
                    if (primeiraLinhaPopulacaoErrada == -1) {
                        primeiraLinhaPopulacaoErrada = countLinhaCSV;
                    }

                } else {

                    String id = parts[0];
                    String ano = parts[1];
                    String populacaoMasculina = parts[2];
                    String populacaoFeminina = parts[3];
                    String densidade = parts[4];

                    if (isNotValidInteger(id)) {

                        lePopulacaoErrada++;
                        if (primeiraLinhaPopulacaoErrada == -1) {
                            primeiraLinhaPopulacaoErrada = countLinhaCSV;
                        }

                    } else if (isNotValidInteger(ano)) {

                        lePopulacaoErrada++;
                        if (primeiraLinhaPopulacaoErrada == -1) {
                            primeiraLinhaPopulacaoErrada = countLinhaCSV;
                        }

                    } else if (isNotValidInteger(populacaoMasculina)) {

                        lePopulacaoErrada++;
                        if (primeiraLinhaPopulacaoErrada == -1) {
                            primeiraLinhaPopulacaoErrada = countLinhaCSV;
                        }

                    } else if (isNotValidInteger(populacaoFeminina)) {

                        lePopulacaoErrada++;
                        if (primeiraLinhaPopulacaoErrada == -1) {
                            primeiraLinhaPopulacaoErrada = countLinhaCSV;
                        }

                    } else if (isNotValidFloat(densidade)) {

                        lePopulacaoErrada++;
                        if (primeiraLinhaPopulacaoErrada == -1) {
                            primeiraLinhaPopulacaoErrada = countLinhaCSV;
                        }

                    } else {

                        Populacao populacao1;
                        populacao1 = new Populacao();
                        populacao1.id = Integer.parseInt(id);
                        populacao1.ano = Integer.parseInt(ano);
                        populacao1.populacaoMasculina = Integer.parseInt(populacaoMasculina);
                        populacao1.populacaoFeminina = Integer.parseInt(populacaoFeminina);
                        populacao1.densidade = Float.parseFloat(densidade);
                        populacao1.linhaCSV = countLinhaCSV;

                        boolean popDontHasCountry = true;

                        for (Pais pais : Main.csvCountries) {

                            if (pais.id == populacao1.id) {

                                popDontHasCountry = false;
                                lePopulacaoCerta++;
                                pais.populacao.add(populacao1);
                                Main.csvPopulationWCountryAndCity.add(populacao1);
                                break;

                            }

                        }

                        if (popDontHasCountry) {

                            lePopulacaoErrada++;
                            if (primeiraLinhaPopulacaoErrada == -1) {
                                primeiraLinhaPopulacaoErrada = countLinhaCSV;
                            }

                        }

                    }
                }
            }
        }

        String InputsPopulacaoInvalidos = "populacao.csv | " + lePopulacaoCerta + " | " + lePopulacaoErrada + " | " + primeiraLinhaPopulacaoErrada;
        Main.inputInvalido.add(InputsPopulacaoInvalidos);

        return true;
    }

    static void atualizaEstruturaCSVCountries(boolean resultParsePaises, String[] inputPaisesInvalidosParts) {

        if (!resultParsePaises) {
            return;
        }

        int lePaisesCertos = Integer.parseInt(inputPaisesInvalidosParts[1]);
        int lePaisesErrados = Integer.parseInt(inputPaisesInvalidosParts[2]);
        int primeiraLinhaPaisesErrada = Integer.parseInt(inputPaisesInvalidosParts[3]);
        int countPaisesErrados = 0;

        Iterator<Pais> countryInterator = Main.csvCountries.iterator();

        while (countryInterator.hasNext()) {

            Pais pais = countryInterator.next();

            if (pais.cidades.isEmpty()) {

                countryInterator.remove();

                countPaisesErrados++;
                if (primeiraLinhaPaisesErrada > pais.linhaCSV) {
                    primeiraLinhaPaisesErrada = pais.linhaCSV;
                }

            } else {

                //Adicionar todos os paises num hashMap

                Main.nomePaisesValidos.put(pais.nome, pais);
                Main.idPaisesValidos.put(pais.id, pais);

            }
        }

        lePaisesCertos -= countPaisesErrados;
        lePaisesErrados += countPaisesErrados;

        Main.inputInvalido.set(0, "paises.csv | " + lePaisesCertos + " | " + lePaisesErrados + " | " + primeiraLinhaPaisesErrada);

    }

    static void atualizaEstruturacsvCitiesWithCountry(boolean resultParseCidades, String[] inputCidadesInvalidasParts) {

        if (!resultParseCidades) {
            return;
        }

        int leCidadesCertas = Integer.parseInt(inputCidadesInvalidasParts[1]);
        int leCidadesErradas = Integer.parseInt(inputCidadesInvalidasParts[2]);
        int primeiraLinhaCidadeErrada = Integer.parseInt(inputCidadesInvalidasParts[3]);
        int countCidadesErradas = 0;

        Iterator<Cidade> citiesInterator = Main.csvCitiesWithCountry.iterator();

        while (citiesInterator.hasNext()) {

            Cidade cidade = citiesInterator.next();
            Pais pais = Main.nomePaisesValidos.get(cidade.pais.nome);

            if (pais == null) {

                citiesInterator.remove();

                countCidadesErradas++;
                if (primeiraLinhaCidadeErrada > cidade.linhaCSV) {
                    primeiraLinhaCidadeErrada = cidade.linhaCSV;
                }

            }

        }

        leCidadesCertas -= countCidadesErradas;
        leCidadesErradas += countCidadesErradas;

        Main.inputInvalido.set(1, "cidades.csv | " + leCidadesCertas + " | " + leCidadesErradas + " | " + primeiraLinhaCidadeErrada);

    }

    static void atualizaEstruturacsvPopulationWCountryAndCity(boolean resultParsePopulacao, String[] inputPopulacaoInvalidaParts) {

        if (!resultParsePopulacao) {
            return;
        }

        int lePopulacaoCerta = Integer.parseInt(inputPopulacaoInvalidaParts[1]);
        int lePopulacaoErrada = Integer.parseInt(inputPopulacaoInvalidaParts[2]);
        int primeiraLinhaPopulacaoErrada = Integer.parseInt(inputPopulacaoInvalidaParts[3]);
        int countPopulacaoErrada = 0;

        Iterator<Populacao> populationIterator = Main.csvPopulationWCountryAndCity.iterator();

        while (populationIterator.hasNext()) {

            Populacao populacao = populationIterator.next();

            Pais pais = Main.idPaisesValidos.get(populacao.id);

            if (pais == null) {

                populationIterator.remove();

                countPopulacaoErrada++;
                if (primeiraLinhaPopulacaoErrada > populacao.linhaCSV) {
                    primeiraLinhaPopulacaoErrada = populacao.linhaCSV;
                }

            }

        }

        lePopulacaoCerta -= countPopulacaoErrada;
        lePopulacaoErrada += countPopulacaoErrada;

        Main.inputInvalido.set(2, "populacao.csv | " + lePopulacaoCerta + " | " + lePopulacaoErrada + " | " + primeiraLinhaPopulacaoErrada);

    }

    static void atualizarEstruturas(boolean resultParsePaisesCSV, boolean resultParseCidadesCSV, boolean resultParsePopulacaoCSV) {

        //Todos os arrays têm informação relacionada. Apenas é necessário excluir os que têm inf. incompleta
        // Atualização inputs_Invalidos de paises

        String[] paisesInvalidosParts = Main.inputInvalido.get(0).split(" \\| ");

        atualizaEstruturaCSVCountries(resultParsePaisesCSV, paisesInvalidosParts);

        //-----------------------------------------------
        // Atualização inputs_Invalidos de cidades

        String[] inputCidadesInvalidasParts = Main.inputInvalido.get(1).split(" \\| ");

        atualizaEstruturacsvCitiesWithCountry(resultParseCidadesCSV, inputCidadesInvalidasParts);

        //------------------------------------------------
        // Atualização inputs_Invalidos de populacao

        String[] inputPopulacaoInvalidaParts = Main.inputInvalido.get(2).split(" \\| ");

        atualizaEstruturacsvPopulationWCountryAndCity(resultParsePopulacaoCSV, inputPopulacaoInvalidaParts);

    }

}
