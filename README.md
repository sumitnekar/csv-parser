# csv-parser
Reading a csv a file and print it as a json using scala


Note :
1. I am using scala and sbt in this projects
2. I am using avro for json schema validations.
3. Refer schema.avsc which contains exact schema as provided in schema.json
4. Using sbt-avrohugger plugin to generate case classes from avro schema


Instructions to run on Intellij :

1. Install scala plugin for intellij
2. Import the project into ide.
3. Open sbt shell in intellij and type run
4. The program reads data.csv placed in directory [src/main/resources/csv_files/data.csv] file and prints each record in that file in json format.
   You can provide path for your directory which contains csv file in CsvProcessorApp.

Run tests :
Open sbt shell in intellij and type test

Instructions to run in terminal in Mac/Linux :

1. Install sbt 1.3.10 
2. git clone https://github.com/sumitnekar/csv-parser.git
3. cd csv-parser
4. sbt compile
5. sbt test
6. sbt run

