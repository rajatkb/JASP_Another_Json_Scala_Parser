package com.unit.test

import com.api.Jasp._
import com.logger.Logger
import com.json.basic.JsonObject
import com.json.basic.JsonArray
import com.json.traits.JsonUnit
import com.json.basic.JsonString

/**
 * Benchmark with citylots.json : Elapsed time: 227.2318s
 * More optimization needed, need to reduce it under a second
 *
 * Further work
 *
 * 1. Write a Iterator writer method Print method or write method must case the string of number , string, bool with "" when in key position
 * 1.1 The above should also work as a pretty print but that's a later concern
 * 2. Add the map and array operators to the interface of map and arrays
 * 3. Write a lazy version of the Json Objects and arrays and numbers etc.
 * 4. Write a lazy version of the builder for the same.
 * 5. Write documentation for GRAMMER + ABSTRACT SYNTAX TREE
 */

object Main {

  def main(args: Array[String]): Unit = {
    val filename = "E://Project Work//citylots.json"
    val filename2 = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test.json"
    val filename3 = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test2.json"
     
//    val a = Logger.timer( JSON.parseFile(filename3) )
    


    
    val map = JsonObject(
      1 -> JsonArray(1, 2, 3, "Hello", false),
      3 -> JsonArray(JsonObject(1 -> 2, 3 -> 4), 3, 4, "false", false))
      
    var head2 = map.getStringStream() 
    for(i <- 1 to 10){
      print(head2.head)  
      head2 = head2.tail
    }
    


  }

}