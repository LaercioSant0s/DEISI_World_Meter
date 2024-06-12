# DEISI WORLD METER (JAVA)

Manual database of all world countries, cities, and populations. This project focuses on data manipulation and query implementation with functions, classes, and tests.

- paises.csv <br>
- cidades.csv <br>
- populacao.csv <br>

These are the CSV (comma-separated value) files used in the project, located in the "test-files" folder. The files are obtained from public datasets:

https://stefangabos.github.io/world_countries/ <br>
https://www.kaggle.com/datasets/max-mind/world-cities-database <br>
https://www.kaggle.com/datasets/joebeachcapital/world-population-data <br>

## Guide:

1. Copy the "src" folder and paste it into the root directory of your IntelliJ workspace.

2. Copy the "test-files" folder (note that these files are large, containing thousands of lines) and paste it into the root directory of your IntelliJ workspace.

**Optional**: you can add three small files with the exact name (under explained) in "test-files" folder, the basic structure of the files are:

file_name: "paises.csv" 

alfa2,cidade,regiao,populacao,latitude,longitude <br>
ad,andorra la vella,07,20430.0,42.5,1.5166667 <br>
ad,canillo,02,3292.0,42.5666667,1.6 <br>
ad,encamp,03,11224.0,42.533333299999995,1.5833333 <br>
ad,la massana,04,7211.0,42.55,1.5166667 <br>
ao,binga,06,,-10.399486,14.76953 <br>
ao,bissadi,15,,-6.331073,16.15173 <br>
ao,caala,08,21205.0,-12.8525,15.560556 <br>
ao,cabinda,03,66020.0,-5.55,12.2 <br>
ao,caconda,09,10551.0,-13.733332999999998,15.066667 <br>
ao,calonda quarto,17,,-8.51248,20.549670000000003 <br>
ao,caluquembe,09,30305.0,-13.783332999999999,14.683333 <br>
ao,camabatela,05,12839.0,-8.18812,15.37495 <br>

----------------------Separator----------------

file_name: "cidades.csv" 

id,alfa2,alfa3,nome <br>
4,af,afg,Afeganistão <br>
710,za,zaf,África do Sul <br>
8,al,alb,Albânia <br>
276,de,deu,Alemanha <br>
20,ad,and,Andorra <br>
24,ao,ago,Angola <br>
 
----------------------Separator----------------

file_name: "populacao.csv"

id,ano,populacao masculina,populacao feminina,densidade <br>
108,1950,1079773,1174340,86.8637 <br>
108,1951,1104544,1198705,88.7571 <br>
108,1952,1128810,1222726,90.6179 <br>
108,1953,1152736,1246363,92.4508 <br>
108,1954,1176706,1270051,94.2874 <br>
232,2017,1673563,1723370,28.0405   <br>
232,2018,1697963,1747412,28.4403   <br>
232,2019,1724860,1773958,28.8815   <br>
232,2020,1753513,1802355,29.3524   <br>
232,2021,1785840,1834472,29.8844   <br>
232,2022,1817878,1866154,30.4104   <br>
232,2023,1850523,1898379,30.9458   <br>
232,2024,1885109,1932542,31.5133   <br>


## Usage:

- In case of a compilation error, ensure that all the necessary Java utilities are included in the project.
Example:

   ![Captura de ecrã 2024-06-12 153303](https://github.com/LaercioSant0s/DEISI_World_Meter/assets/127317610/0536c333-cde5-4d94-a756-a7754e1a2a2a)

<br>

- If there are no errors, you are ready to run the project. Locate the Main class and click the run button.

The program will display a list of commands that you can choose from to interact with the information, such as adding a new "imaginary" city or removing a country.


### DEISI_World_Meter © 2024 by *Laércio Santos* is licensed under [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)

