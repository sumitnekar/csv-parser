package com.assignment.csv.processor

import spray.json._

import scala.util.{Failure, Success}

object CsvProcessorApp extends App with FloorAccessEventJsonProtocol {

  println("CSV processor")

  val csvFileDirectory = "/Users/sumitn/my-github/csv-parser/src/main/resources/csv_files/"

  val csvFilesTry = CsvLoader.loadCSVFiles(csvFileDirectory)

  csvFilesTry match {
    case Failure(ex) =>
      println("Directory doesn't exist!!")
    case Success(csvFiles) =>
      //loading for only first file
      CsvLoader.loadFloorEvents(csvFiles.head)
        .map(event => event.toJson) //spray json library support
        .foreach(println(_))
  }

}






