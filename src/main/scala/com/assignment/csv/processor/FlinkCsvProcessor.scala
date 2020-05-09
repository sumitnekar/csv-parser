package com.assignment.csv.processor

import com.assignment.event.model.FloorAccessEvent
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.streaming.api.scala.createTypeInformation
import spray.json._

object FlinkCsvProcessor extends App with FloorAccessEventJsonProtocol {
  implicit val typeInfo = createTypeInformation[(String, String, String, String)]
  val env = ExecutionEnvironment.getExecutionEnvironment;

  val filePath = "./src/main/resources/csv_files/data.csv"
  val csvContent =
    env
      .readCsvFile[(String, String, String, String)](filePath, fieldDelimiter = ",", ignoreFirstLine = true)
  val floorAccessEvents: DataSet[FloorAccessEvent] = csvContent.map(line =>
    FloorAccessEvent(
      line._1,
      line._2,
      line._3.toInt,
      line._4
    )
  )

  floorAccessEvents.map(_.toJson).print()

}


