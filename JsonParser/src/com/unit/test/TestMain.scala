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
import com.json.factory.JsonPrototypeFactory
import com.file.tokenizer.Tokenizer
import com.json.traits.JsonFactory
import com.lexer.analyzer.LexemeGenerator
import com.json.builder.JsonIteratorBuilder
import com.parser.director.Parser
import java.io.IOException
import java.io.FileNotFoundException
import scala.util.Success
import scala.util.Try
import scala.io.Source
import scala.util.Failure
import com.json.traits.JsonMapTrait
import com.json.builder.JsonIteratorBuilderV2
import com.json.traits.JsonBuilderTrait

import java.nio.file.{Files, Paths}


object TestMain {

  val defaultInstance = JsonPrototypeFactory.getInstance(new JsonObject(), new JsonArray(), new JsonNumber(0), new JsonString(null), new JsonBoolean(false))


  
  val fromFile = (a: String) => Source.fromFile(a)
  val fromString = (a: String) => Source.fromString(a)

  private def parseWith(tokenizer: Tokenizer, jsonFactory: JsonFactory)( getJsonBuilder:(JsonFactory => JsonBuilderTrait)) = {
    val lexer = new LexemeGenerator(tokenizer.getStream())
    val builder = getJsonBuilder(jsonFactory)
    val parser = new Parser(lexer, builder)
    val value = parser.parse()
    value
  }

  private def parseWithAutoclose(filename: String, jsonFactory: JsonFactory)( getJsonBuilder:(JsonFactory => JsonBuilderTrait))(op: String => Source) = {
    Try(op(filename)) match {
      case Success(fileBufferSource) => {
        val tokenizer = new Tokenizer(fileBufferSource)
        val value = parseWith(tokenizer, jsonFactory)(getJsonBuilder)
        fileBufferSource.close()
        value
      }
      case Failure(e) => {
        e match {
          case e: FileNotFoundException =>
            Logger.error("Could not find the file"); throw e
          case e: IOException => Logger.error("Could not read file"); throw e
        }
      }
    }
  }

  def parseString(data: String)( getJsonBuilder:(JsonFactory => JsonBuilderTrait)): JsonMapTrait = {
    parseWithAutoclose(data, defaultInstance)(getJsonBuilder)(fromString)
  }
  
  def parseFile(filename: String)( getJsonBuilder:(JsonFactory => JsonBuilderTrait)): JsonMapTrait = {

      parseWithAutoclose(filename, defaultInstance)(getJsonBuilder)(fromFile)

    }

  def main(args: Array[String]):Unit = {
  
    val filename3 = "E://Project Work//citylots.json"
    val filename2 = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test.json"
    
//    val run = 1
//    
//    println("Warm Up")
//    Logger.timer( parseFile(filename2)((factory:JsonFactory) => {
//    println("Using JsonIteratorBuilder"); new JsonIteratorBuilder(factory) 
//  }))
//    
//  
//
//  
//    println("Benchmarking with V1")
//    val v = (for(i <- 1 to run ) yield { 
//      Logger.time( parseFile(filename3)((factory:JsonFactory) => {
////      println("Using JsonIteratorBuilder"); 
//      new JsonIteratorBuilder(factory) 
//    }) ) 
//    }).reduce(_+_) / run
//     
//    println(f"Avg across $run run $v")
//    
//    println("Benchmarking with V2")
//    val v2 = (for(i <- 1 to run ) yield { 
//      Logger.time(parseFile(filename3)((factory:JsonFactory) => {
////      println("Using JsonIteratorBuilderV2"); 
//      new JsonIteratorBuilderV2(factory) 
//    }) ) 
//    }).reduce(_+_) / run
//  
//    println(f"Avg across $run run $v2")
    
     
    

    /**
     * Observation Notes
     * 
     * JsonIteratorBuilderV2 with ints and custom mutable stack class is faster by a significant difference for small files.
     * JsonIteratorBuilderV2 is performing averagely the same for slightly larger file with similar structure
     * JsonIteratorBuilderV2 is performing 45s faster than the earlier one on CityLots.json. This is significant,
     * Can be improved by optimzing the way VAS -> A -> V -> A ... is parsed.
     * 
     * Warm Up
       Using JsonIteratorBuilder
       Elapsed time: 0.504703s
       Benchmarking with V1
       Avg across 100 run 0.003077730966033414
       Benchmarking with V2
       Avg across 100 run 0.0015405788959469645
     * 
     * This shows that we have achieved some improvement for small files i.e test2.json
     * 
     *  Warm Up
        Using JsonIteratorBuilder
        Elapsed time: 0.7107868s
        Benchmarking with V1
        Avg across 100 run 0.0344208980537951
        Benchmarking with V2
        Avg across 100 run 0.028202014807611704
     *  
     *  For test.json which is a larger file it gives almost similar performance
     *  
     *  For much larger file the performance remains the same. Once the JVM is warmed up the difference is only visible in small files
     * 
     * 
     */

    
    
  }

}