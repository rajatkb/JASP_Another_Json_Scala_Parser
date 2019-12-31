package com.json.factory

import com.json.traits.JsonKey
import com.json.traits.JsonValue
import com.json.traits.JsonUnit
import com.logger.Logger
import com.json.traits.JsonMapTrait
import com.json.traits.JsonListTrait
import com.json.traits.JsonNumberTrait
import com.json.traits.JsonStringTrait
import com.json.traits.JsonFactory
import com.json.traits.JsonFactory
import com.json.traits.JsonBoolTrait

//import scala.collection.mutable.HashMap

/**
 * Json Prototype Factory object singleton for creating Json Traits and Abstract class type objects
 * using create methods assigned for each types
 */
object JsonPrototypeFactory {

  private class JsonPrototypeFactory(jsonMap:JsonMapTrait ,jsonList:JsonListTrait, jsonNumber:JsonNumberTrait , jsonString:JsonStringTrait , jsonBool:JsonBoolTrait ) extends JsonFactory {
  
    override def createJsonStringEntity(str:String):JsonStringTrait = jsonString.copy(str)
    override def createJsonNumberEntity(num:Double):JsonNumberTrait = jsonNumber.copy(num)
    override def createJsonList(list:Seq[JsonValue]):JsonListTrait = jsonList.copy(list)
    override def createJsonMap(keyValueMap: Map[JsonKey,JsonValue]):JsonMapTrait = jsonMap.copy(keyValueMap)
    override def createJsonBooleanEntity(b:Boolean):JsonBoolTrait = jsonBool.copy(b)
  }
  
  private var factory:JsonFactory = null
  /**
   * Take argument of prototypes of the following types
   * The JsonPair is a final type and cannot be extended and only used internally for the purpose of creating JsonObject
   * Hence initialised concretely, even though following prototype pattern
   */
  def getInstance(jsonMap:JsonMapTrait ,jsonList:JsonListTrait, jsonNumber:JsonNumberTrait , jsonString:JsonStringTrait , jsonBool:JsonBoolTrait) = {
      factory = new JsonPrototypeFactory(jsonMap,jsonList,jsonNumber,jsonString,jsonBool)
      factory
  }
  
  
}