name := "ConversionOfBaconAnalyzer"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies += "org.apache.avro" % "avro" % "1.7.6"

initialCommands in console += """
print("I guess it works!")
"""
