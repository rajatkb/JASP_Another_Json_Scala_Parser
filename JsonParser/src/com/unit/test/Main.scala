package com.unit.test


import com.api.Jasp._
import com.logger.Logger
import com.json.basic.JsonObject
import com.json.basic.JsonArray


object Main {
  

   def main(args:Array[String]):Unit = {
      val filename = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test.json"
      
      val a = Logger.timer( JSON.parseFile(filename) )
  
      val b = a("context")("caches")(2)
     
      
      
      val e = JsonArray( 1 , 2 , 3 )
      
      
      val m = new JsonObject(1 -> 2 , "hello" -> new JsonArray(1,2,3,4) , 4 -> new JsonArray(1,2,3)  )
      
      print(m("hello")(0))

   }
  
}