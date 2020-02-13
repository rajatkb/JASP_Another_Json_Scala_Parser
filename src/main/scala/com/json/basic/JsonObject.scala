package com.json.basic
//import scala.collection.mutable.HashMap

import com.json.traits.JsonUnit
import com.json.traits.JsonValue
import com.json.traits.JsonKey
import com.json.traits.JsonMap
import com.json.traits.JsonValue
import com.json.traits.JsonWriteable

/**
 * :JsonObject => HashMap(JsonKey -> JsonValue)
 * Since the pair is only to be used by the parser the Map is built using a list of (JsonKey,JsonValue) 
 * which can be easily be obtained using getValue() of JsonPair object.
 */

object JsonObject{
  def apply(value:Map[JsonKey , JsonValue] = Map()) = new JsonObject(value)
  def apply(args:(JsonKey,JsonValue)*) = new JsonObject(args.toMap)
  implicit def value2Map(a:JsonValue) = a.asInstanceOf[JsonObject]
}

class JsonObject(value:Map[JsonKey , JsonValue] = Map()) extends JsonMap(value) {
  
  override def toString() = "{"+value.toList.map(f => f._1 + ":" + f._2).mkString(",\n")+"}"
  
  def this(args:(JsonKey,JsonValue)* ) = this(args.toMap)
 
 

}
