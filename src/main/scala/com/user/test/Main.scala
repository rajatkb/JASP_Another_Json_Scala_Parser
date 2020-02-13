package com.user.test

import com.api.Jasp._
import com.json.basic.JsonObject
import com.json.basic.JsonArray
import com.json.traits.JsonUnit
import com.json.basic.JsonString
import com.json.basic.JsonNumber
import com.json.basic.JsonBoolean
import java.nio.file.Paths
import scala.util.Try
import java.io.BufferedReader
import java.io.FileReader
import scala.util.Success
import scala.util.Failure
import scala.io.Source
import com.file.tokenizer.Tokenizer
import com.file.tokenizer.Tokenizer
import com.lexer.analyzer.LexemeGenerator

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
    val filename2 = Paths.get(getClass().getClassLoader().getResource("test.json").toURI()).toString()

     
    
    
    val a = JSON.parseFile(filename2)

    println(a("context")("date"))

    val map = JsonObject(1 -> 2, 3 -> JsonArray(-1.00, 2.00, -3.00564, 4.35656), "hello" -> JsonBoolean(true))

    val num = map(3)

    println(num)
    
    val str = map.toStream().toList mkString ""

    println(str)

    val smap = JSON.parseString(str)

    JSON.toFile(smap, "test_out.json")

  }

}