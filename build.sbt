name := "csv-parser"

version := "0.1"

scalaVersion := "2.13.2"

val ScalaTestVersion = "3.1.1"
val sprayJsonVersion = "1.3.5"

libraryDependencies ++= Seq(
  "io.spray" %% "spray-json" % sprayJsonVersion,
  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test
)


//Task to convert avsc file to Case classes. This task is part of sbt-avrohugger plugin
sourceGenerators in Compile += (avroScalaGenerate in Compile).taskValue