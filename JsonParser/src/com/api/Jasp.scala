package com.api

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
import com.json.traits.JsonValue
import com.json.traits.JsonKey
import java.io.IOException
import java.io.FileNotFoundException
import scala.io.Source
import com.logger.Logger
import scala.util.{ Try, Success, Failure }
import scala.io.BufferedSource
import com.json.traits.JsonFactory
import com.json.traits.JsonMapTrait
import com.json.traits.JsonMapTrait
import com.json.traits.JsonFactory
import com.json.traits.JsonMapTrait
import com.json.traits.JsonListTrait
import com.json.traits.JsonBoolTrait
import com.json.traits.JsonNumberTrait
import com.json.traits.JsonStringTrait
import java.nio.channels.FileChannel
import java.util.RandomAccess
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import com.json.traits.JsonWriteable
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import com.json.builder.JsonIteratorBuilderV2
import com.json.traits.JsonBuilderTrait
import com.lexer.traits.LexemeGeneratorTrait
import java.nio.file.Files
import java.nio.file.Paths


object Jasp {

  private val defaultInstance = JsonPrototypeFactory.getInstance(new JsonObject(), new JsonArray(), new JsonNumber(0), new JsonString(null), new JsonBoolean(false))

  private def getDefaultJsonBuilder = (factory:JsonFactory) => new JsonIteratorBuilder(factory)
  private def getDefaultTokenizer = (src:Source) => new Tokenizer(src)
  private def getDefaultParser = (t:LexemeGeneratorTrait ,f:JsonBuilderTrait ) => new Parser(t , f)
  
  
  
  // THE IMPLICIT ARE ALWAYS BOUND TO THE CURRENT FACTORY OBJECT THAT CREATES THE OBJECTS BASED ON USER IMPLEMENTATION
  // OR WITH DEFAULT SUPPORTED BY THE LIBRARY
  implicit def num2Value(a: Double): JsonValue = JsonPrototypeFactory.getCurrentInstance().createJsonNumberEntity(a)
  implicit def num2Key(a: Double): JsonKey = JsonPrototypeFactory.getCurrentInstance().createJsonNumberEntity(a)
  implicit def string2Value(a: String): JsonValue = JsonPrototypeFactory.getCurrentInstance().createJsonStringEntity(a)
  implicit def string2Key(a: String): JsonKey = JsonPrototypeFactory.getCurrentInstance().createJsonStringEntity(a)
  implicit def bool2Key(a: Boolean): JsonKey = JsonPrototypeFactory.getCurrentInstance().createJsonBooleanEntity(a)
  implicit def bool2Value(a: Boolean): JsonValue = JsonPrototypeFactory.getCurrentInstance().createJsonBooleanEntity(a)
  implicit def num2KeyArrowAssoc(a: Double): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  implicit def string2KeyArrowAssoc(a: String): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  implicit def boolean2KeyArrowAssoc(a: Boolean): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  
  
  
  // A minimal Source implementation with close and getLines implemented
  private class NioSource(src:String) extends Source {
    val iter = null
    val file = Files.lines(Paths.get(src))
    val stream= file.iterator()
    
    override def getLines() = new Iterator[String] {
      override def hasNext() = stream.hasNext()
      override def next() = stream.next()
    } 
    override def close() = file.close()
  }
  

  object JSON {

    private val fromFile = (a: String) => new NioSource(a)
    private val fromString = (a: String) => Source.fromString(a)

    private def parseWith(tokenizer: Tokenizer, jsonFactory: JsonFactory) = {
      val lexer = new LexemeGenerator(tokenizer.getStream())
      val builder = getDefaultJsonBuilder(jsonFactory)
      val parser = getDefaultParser(lexer , builder)
      val value = parser.parse()
      value
    }

    private def parseWithAutoclose(filename: String, jsonFactory: JsonFactory)(op: String => Source) = {
      Try(op(filename)) match {
        case Success(fileBufferSource) => {
          val tokenizer = getDefaultTokenizer(fileBufferSource)
          val value = parseWith(tokenizer, jsonFactory)
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

    def parseString(data: String): JsonMapTrait = {
      parseWithAutoclose(data, defaultInstance)(fromString)
    }

    def parseString(
      data: String,
      jsonMap: JsonMapTrait, jsonList: JsonListTrait,
      jsonNumber: JsonNumberTrait, jsonString: JsonStringTrait,
      jsonBool: JsonBoolTrait) = {

      parseWithAutoclose(data, JsonPrototypeFactory.getInstance(
        jsonMap,
        jsonList,
        jsonNumber,
        jsonString,
        jsonBool))(fromString)

    }

    def parseFile(filename: String): JsonMapTrait = {

      parseWithAutoclose(filename, defaultInstance)(fromFile)

    }

    def parseFile(
      filename: String,
      jsonMap: JsonMapTrait, jsonList: JsonListTrait,
      jsonNumber: JsonNumberTrait, jsonString: JsonStringTrait,
      jsonBool: JsonBoolTrait) = {

      parseWithAutoclose(filename, JsonPrototypeFactory.getInstance(
        jsonMap,
        jsonList,
        jsonNumber,
        jsonString,
        jsonBool))(fromFile)

    }

    def toFile(jsonObject: JsonWriteable, filename: String) = {
      try {
        val target = new BufferedOutputStream( new FileOutputStream(filename) );
        jsonObject.getStringStream().foreach(s => target.write(s.getBytes) )
        target.close()
      } catch {
        case e: FileNotFoundException =>
          Logger.error("File $filename not found"); throw e
        case e: IOException => Logger.error("Something went wrong when writing file"); throw e
      }
    }

  }

}

