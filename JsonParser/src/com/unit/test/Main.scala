package com.unit.test

import com.api.Jasp._
import com.logger.Logger
import com.json.basic.JsonObject
import com.json.basic.JsonArray
import com.json.traits.JsonUnit
import com.json.basic.JsonString
import com.json.basic.JsonNumber
import com.json.basic.JsonBoolean
import com.json.traits.JsonNumberTrait
import com.json.traits.JsonStringTrait

/**
 * Benchmark with citylots.json : Elapsed time: 227.2318s
 * More optimization needed, need to reduce it under a second
 *
 * Further work
 *
 * 
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
    val newfile = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//newfile.json"
    
    val a = Logger.timer( JSON.parseFile(filename) )
    
//    print(a("context")("date"))
    
    val map = JsonObject(1 -> 2 , 3 -> JsonArray(-1.00,2.00,-3.00564,4.35656) , "hello" -> JsonBoolean(true) )
    
    println(map)
    
    val str = map.getStringStream().toList mkString ""
    
    println(str)
    
    val smap = JSON.parseString(str)
    
    JSON.toFile(smap, newfile)
   
  }

}