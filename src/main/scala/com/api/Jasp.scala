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
import com.json.traits.JsonValue
import com.json.basic.JsonDefaultFactory
import com.json.traits.JsonKey
import java.io.IOException
import java.io.FileNotFoundException
import scala.io.Source
import scala.util.{ Try, Success, Failure }
import scala.io.BufferedSource
import com.json.traits.JsonMap
import com.json.traits.JsonList
import com.json.traits.JsonBool
import com.json.traits.JsonNumeric
import com.json.traits.JsonChars
import java.nio.channels.FileChannel
import java.util.RandomAccess
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import com.json.traits.JsonWriteable
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import com.json.builder.JsonIteratorBuilderV2
import com.json.traits.JsonBuilder
import com.lexer.traits.LexemeGen
import java.nio.file.Files
import java.nio.file.Paths
import com.json.traits.JsonFactory



object Jasp {

  
  
  
  
  private def getDefaultJsonBuilder = (factory:JsonFactory) => new JsonIteratorBuilder(factory)
  private def getDefaultTokenizer = (src:Source) => new Tokenizer(src)
  private def getDefaultParser = (t:LexemeGen ,f:JsonBuilder ) => new Parser(t , f)
  
  
  
  // THE IMPLICIT ARE ALWAYS BOUND TO THE DEFAULT INSTANCE OF FACTORY
  implicit def num2Value(a: Double): JsonValue = JsonDefaultFactory.createJsonNumberEntity(a)
  implicit def num2Key(a: Double): JsonKey = JsonDefaultFactory.createJsonNumberEntity(a)
  implicit def string2Value(a: String): JsonValue = JsonDefaultFactory.createJsonStringEntity(a)
  implicit def string2Key(a: String): JsonKey = JsonDefaultFactory.createJsonStringEntity(a)
  implicit def bool2Key(a: Boolean): JsonKey = JsonDefaultFactory.createJsonBooleanEntity(a)
  implicit def bool2Value(a: Boolean): JsonValue = JsonDefaultFactory.createJsonBooleanEntity(a)
  implicit def num2KeyArrowAssoc(a: Double): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  implicit def string2KeyArrowAssoc(a: String): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  implicit def boolean2KeyArrowAssoc(a: Boolean): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  
  
  // A minimal Source implementation with close and getLines implemented
  class NioSource(src:String) extends Source {
    val iter = null
    private val file = Files.lines(Paths.get(src))
    private val lineIter= file.iterator()
    
    override def getLines():Iterator[String] = new Iterator[String] {
      override def hasNext() = lineIter.hasNext()
      override def next() = lineIter.next()
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

    private def parseWithAutoclose(filename: String)(readFrom: String => Source)(implicit jsonFactory: JsonFactory) = {
      Try(readFrom(filename)) match {
        case Success(fileBufferSource) => {
          val tokenizer = getDefaultTokenizer(fileBufferSource)
          val value = parseWith(tokenizer, jsonFactory)
          fileBufferSource.close()
          value
        }
        case Failure(e) => throw e
      }
    }

    def parseString(data: String): JsonMap = {
      parseWithAutoclose(data)(fromString)(JsonDefaultFactory)
    }

    def parseString( data: String, factoryInstance: JsonFactory  ) = {

      parseWithAutoclose(data)(fromString)(factoryInstance)

    }

    def parseFile(filename: String): JsonMap = {

      parseWithAutoclose(filename)(fromFile)(JsonDefaultFactory)

    }

    def parseFile(filename: String, factoryInstance: JsonFactory) = {

      parseWithAutoclose(filename)(fromFile)(factoryInstance)

    }

    def toFile(jsonObject: JsonWriteable, filename: String) = {
        Try(new BufferedOutputStream( new FileOutputStream(filename) )) match {
          case Success(target) => {
                  jsonObject.toStream().foreach(s => target.write(s.getBytes))  
                  target.close()
              }
          case Failure(e) => throw e 
        }
      }
    }

}

