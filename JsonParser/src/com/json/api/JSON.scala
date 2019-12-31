package com.json.api

import com.file.tokenizer.Tokenizer
import com.lexer.analyzer.LexemeGenerator
import com.json.builder.JsonIteratorBuilder
import com.parser.director.Parser
import com.json.basic.JsonObject
import com.json.basic.JsonArray
import com.json.basic.JsonNumber
import com.json.basic.JsonString
import com.json.basic.JsonBoolean
import com.json.factory.JsonPrototypeFactory
import com.json.traits.JsonMapTrait
import com.json.traits.JsonListTrait
import com.json.traits.JsonNumberTrait
import com.json.traits.JsonStringTrait
import com.json.traits.JsonBoolTrait

object JSON {
  def getParser(filename:String) = {
    val reader = new Tokenizer(filename)
  
    val lexer = new LexemeGenerator(reader.getStream())
  
    val builder = new JsonIteratorBuilder(JsonPrototypeFactory.getInstance( new JsonObject(null), 
                                                                            new JsonArray(Nil), 
                                                                            new JsonNumber(0), 
                                                                            new JsonString(null) , 
                                                                            new JsonBoolean(false)))
  
    val parser = new Parser(lexer,builder)   
    parser
  }
  
  def getParser(filename:String , 
                jsonMap:JsonMapTrait ,jsonList:JsonListTrait, 
                jsonNumber:JsonNumberTrait , jsonString:JsonStringTrait , 
                jsonBool:JsonBoolTrait) = {
                

    val reader = new Tokenizer(filename)
  
    val lexer = new LexemeGenerator(reader.getStream())
  
    val builder = new JsonIteratorBuilder(JsonPrototypeFactory.getInstance( jsonMap, 
                                                                            jsonList, 
                                                                            jsonNumber, 
                                                                            jsonString , 
                                                                            jsonBool))
  
    val parser = new Parser(lexer,builder)   
    parser
    
  
  }
}