name := "csv-parser"

version := "0.1"

scalaVersion := "2.12.6"

val ScalaTestVersion = "3.1.1"
val sprayJsonVersion = "1.3.5"
val FlinkVersion = "1.10.0"

libraryDependencies ++= Seq(
  "io.spray" %% "spray-json" % sprayJsonVersion,
  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test,
  "org.apache.flink" % "flink-java" % FlinkVersion,
  "org.apache.flink" %% "flink-scala" % FlinkVersion,
  "org.apache.flink" %% "flink-streaming-scala" % FlinkVersion
)


//Task to convert avsc file to Case classes. This task is part of sbt-avrohugger plugin
sourceGenerators in Compile += (avroScalaGenerate in Compile).taskValue