package com.unit.test


import com.api.Jasp._
import com.logger.Logger
import com.json.basic.JsonObject
import com.json.basic.JsonArray


object Main {
  

   def main(args:Array[String]):Unit = {
      val filename = "E://Project Work//citylots.json"
      val filename2 = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test.json" 
//      val a = Logger.timer( JSON.parseFile(filename) )
      /**
       * Benchmark with citylots.json : Elapsed time: 227.2318s
       * More optimization needed, need to reduce it under a second
       */
      
      val map = JsonObject(1 -> JsonArray(1,2,3,"Hello",false), 
                           3 -> JsonArray(JsonObject(1->2,3->4),3,4,"false",false))
      println(map)
      
      

   }
  
}