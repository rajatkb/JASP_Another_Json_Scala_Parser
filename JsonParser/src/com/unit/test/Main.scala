package com.unit.test

import com.json.api.JSON
import com.logger.Logger
import com.json.struct.JsonString

object Main {

   def main(args:Array[String]):Unit = {
      val filename = "e://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test.json"
      val a = Logger.timer( JSON.getParser(filename).parse() )
  }
  
}