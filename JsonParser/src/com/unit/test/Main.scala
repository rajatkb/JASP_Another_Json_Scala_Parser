package com.unit.test

import com.json.api.JSON
import com.logger.Logger
import com.json.struct.JsonString





object Main {
  
  Logger.logging = true
  Logger.timing = true
    
  def main(args:Array[String]):Unit = {
    
  val filename = "c://Users//rajat.b//Desktop//study stuff//JASP_Another_Json_Scala_Parser//JsonParser//test.json"
  
  
  
  val a = Logger.timer( JSON.getParser(filename).parse() )
  
  print(a.getValue().keys.toList)
  
  val b = a.getValue()(new JsonString("benchmarks"))
  
  print(b.getValue()) 
    
  
  }
  
}