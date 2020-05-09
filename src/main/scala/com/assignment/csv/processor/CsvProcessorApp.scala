package com.assignment.csv.processor

import scala.util.{Failure, Success, Try}


//Contains main class to start the app
object CsvProcessorApp {

  def main(args: Array[String]): Unit = {

    val fileDirectory = "./src/main/resources/csv_files/"

    val csvProcessor = new CsvProcessor
    val eventsInJsonFormatTry: Try[List[String]] = csvProcessor.convertCsvToJson(fileDirectory)
    eventsInJsonFormatTry match {
      case Failure(exception) =>
        println(exception.getMessage)

      case Success(eventsInJsonFormat) =>
        eventsInJsonFormat.foreach(println(_))
    }
  }


}
