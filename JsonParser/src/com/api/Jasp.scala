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
import com.json.traits.JsonMapTrait
import com.json.traits.JsonListTrait
import com.json.traits.JsonNumberTrait
import com.json.traits.JsonStringTrait
import com.json.traits.JsonBoolTrait
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

object Jasp {

  implicit def numtoValue(a: Double): JsonValue = new JsonNumber(a)
  implicit def numtoKey(a: Double): JsonKey = new JsonNumber(a)
  implicit def stringtoValue(a: String): JsonValue = new JsonString(a)
  implicit def stringtoKey(a: String): JsonKey = new JsonString(a)
  implicit def booltoKey(a: Boolean): JsonKey = new JsonBoolean(a)
  implicit def booltoValue(a: Boolean): JsonValue = new JsonBoolean(a)
  implicit def numtoKeyArrowAssoc(a: Double): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  implicit def stringtoKeyArrowAssoc(a: String): ArrowAssoc[JsonKey] = new ArrowAssoc(a)
  implicit def booleantoKeyArrowAssoc(a: Boolean): ArrowAssoc[JsonKey] = new ArrowAssoc(a)

  object JSON {

    val fromFile = (a: String) => Source.fromFile(a)
    val fromString = (a: String) => Source.fromString(a)

    private def parseWith(tokenizer: Tokenizer, jsonFactory: JsonFactory) = {
      val lexer = new LexemeGenerator(tokenizer.getStream())
      val builder = new JsonIteratorBuilder(jsonFactory)
      val parser = new Parser(lexer, builder)
      val value = parser.parse()
      value
    }

    private def parseWithAutoclose(filename: String, jsonFactory: JsonFactory)(op: String => Source) = {
      Try(op(filename)) match {
        case Success(fileBufferSource) => {
          val tokenizer = new Tokenizer(fileBufferSource)
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
      parseWithAutoclose(data, JsonPrototypeFactory.getInstance(
        new JsonObject(),
        new JsonArray(),
        new JsonNumber(0),
        new JsonString(null),
        new JsonBoolean(false)))(fromString)
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

      parseWithAutoclose(filename, JsonPrototypeFactory.getInstance(
        new JsonObject(),
        new JsonArray(),
        new JsonNumber(0),
        new JsonString(null),
        new JsonBoolean(false)))(fromFile)

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
  }

}

