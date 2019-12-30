package com.unit.test

import com.json.traits.JsonUnit
import com.json.struct.JsonArray
import com.json.struct.JsonObject
import com.json.traits.JsonKey
import com.json.traits.JsonValue
import com.json.factory.JsonPrototypeFactory
import com.json.struct.JsonNumber
import com.json.struct.JsonString
import com.logger.Logger
import com.json.traits.JsonMapTrait
import scala.annotation.tailrec

import com.json.builder.JsonIteratorBuilder
import com.json.struct.JsonObject
import com.json.struct.JsonArray
import com.json.struct.JsonObject
import com.json.traits.JsonMapTrait
import scala.math.pow
import com.json.traits.JsonMapTrait
import com.json.struct.JsonArray
import com.file.tokenizer.Tokenizer
import com.lexer.analyzer.LexemeGenerator
import com.parser.director.Parser



object Main {
  
  Logger.logging = true
  Logger.timing = true
  
  
  def main(args:Array[String]):Unit = {
    
  val filename = "e://Scala Language//JsonParser//test.json"
  val reader = new Tokenizer(filename)
  
  val lexer = new LexemeGenerator(reader.getStream())
  
  val parser = new Parser(lexer,null)
  
  parser.parse()
      
    
  
  }
  
}