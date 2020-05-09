package com.assignment.csv.processor

import java.io.File

import com.assignment.event.model.FloorAccessEvent
import spray.json._

import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

//spray.json custom format for FloorAccessEvent
trait FloorAccessEventJsonProtocol extends DefaultJsonProtocol {
  implicit val floorAccessEventFormat = jsonFormat4(FloorAccessEvent.apply)
}


//Converts contents of given csv file to json
class CsvProcessor extends FloorAccessEventJsonProtocol {

  def convertCsvToJson(directoryPath: String): Try[List[String]] = {
    val csvFilesTry = loadCSVFiles(directoryPath)

    csvFilesTry match {
      case Failure(ex) =>
        Failure(new Exception("Please specify correct directory name which contains valid csv file !!!!!"))

      case Success(csvFiles) =>
        //loading  only first file
        Success(loadFloorAccessEvents(csvFiles.head)
          .map(event => event.toJson.toString)) //spray json library support
    }
  }

  private def loadCSVFiles(directoryPath: String): Try[List[File]] = {
    Try {
      val csvDirectory = new File(directoryPath)
      csvDirectory
        .listFiles()
        .filter(_.canRead)
        .filter(_.isFile)
        .filter(_.getCanonicalPath.endsWith(".csv"))
        .toList
    }
  }

  //convert the contents of a csv file to FloorAccessEvent
  private def loadFloorAccessEvents(csvFile: File): List[FloorAccessEvent] = {

    val bufferedSource: BufferedSource = Source.fromFile(csvFile)
    val PersonIdColumnNumber = 0
    val FloorAccessTimeColumnNumber = 1
    val FloorLevelColumnNumber = 2
    val BuildingColumnNumber = 3

    val floorPropertiesTuple = bufferedSource
      .getLines()
      .drop(1)
      .map(line => {
        val cells = line.split(",")
        (
          cells(PersonIdColumnNumber),
          cells(FloorAccessTimeColumnNumber),
          cells(FloorLevelColumnNumber).toInt,
          cells(BuildingColumnNumber)
        )
      })
      .filterNot(cellValue => cellValue._1.isEmpty) //Expecting personId to be number always.Filtering out rest.
      .filterNot(cellValue => validDate(cellValue._2).isEmpty) //Expecting date should be in valid format .Filtering out rest.
      .filterNot(cellValue => cellValue._3.isNaN) //Expecting floorLevel to be number always.Filtering out rest.

    val floorAccessEvents = floorPropertiesTuple.map(floorPropertiesTuple => {
      FloorAccessEvent(
        floorPropertiesTuple._1,
        floorPropertiesTuple._2,
        floorPropertiesTuple._3.toInt,
        floorPropertiesTuple._4
      )
    }).toList
    floorAccessEvents

  }

  private def validDate(dateString: String): Option[String] = {
    val pattern = "MM/dd/yy HH:mm"
    val dateTry = Try {
      val dateFormat = new java.text.SimpleDateFormat(pattern)
      val date = dateFormat.parse(dateString)
      dateFormat.format(date)
    }
    dateTry match {
      case Failure(exception) =>
        println(s"Failed to parse value as date. Date should be in a format ${pattern}.Reason : ${exception.getMessage}")
        None
      case Success(date) => Some(date)
    }
  }

}









