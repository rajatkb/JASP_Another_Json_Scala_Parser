package com.unit.test

import com.json.api.JSON
import com.logger.Logger
import com.json.struct.JsonString
import com.file.tokenizer

object Main {

   def main(args:Array[String]):Unit = {
      val filename = "c://Users//rajat.b//Desktop//study stuff//JASP_Another_Json_Scala_Parser//JsonParser//test.json"
      val a = Logger.timer( JSON.getParser(filename).parse() )
  
      val b = a("context")("caches")(2)
      
      print(b)
      
      
   }
  
}