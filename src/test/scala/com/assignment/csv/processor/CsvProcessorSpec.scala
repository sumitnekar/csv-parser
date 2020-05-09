package com.assignment.csv.processor

import org.scalatest.wordspec.AnyWordSpec

import scala.util.Try


class CsvProcessorSpec extends AnyWordSpec {

  "CSV processor" should {
    "parse csv file and convert each record in csv file to json" in {
      val csvFileDirectory = "./src/test/resources/csv_file/"
      val csvProcessor = new CsvProcessor
      val eventsInJsonFormatTry = csvProcessor.convertCsvToJson(csvFileDirectory)
      assert(eventsInJsonFormatTry.isSuccess)

      val firstJsonEvent: String = eventsInJsonFormatTry.get.head
      val expectedFirstJsonEvent =
        """{"building":"B","datetime":"10/1/15 8:02","floor_level":6,"person_id":"1"}"""

      assert(firstJsonEvent == expectedFirstJsonEvent)

      val expectedSecondJsonEvent =
        """{"building":"C","datetime":"10/1/15 8:02","floor_level":5,"person_id":"2"}"""

      val secondJsonEvent = eventsInJsonFormatTry.get(1)
      assert(secondJsonEvent == expectedSecondJsonEvent)
    }


    "failure when directory is wrong" in {
      //wrong directory
      val csvFileDirectory = "./src/test/resources/noFile"
      val csvProcessor = new CsvProcessor
      val eventsInJsonFormatTry = csvProcessor.convertCsvToJson(csvFileDirectory)

      assert(eventsInJsonFormatTry.isFailure)
      assert(eventsInJsonFormatTry.failed.get.getMessage == "Please specify correct directory name which contains valid csv file !!!!!")


    }
  }

}
