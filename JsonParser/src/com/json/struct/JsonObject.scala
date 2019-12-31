package com.json.struct
//import scala.collection.mutable.HashMap

import com.json.traits.JsonUnit
import com.json.traits.JsonValue
import com.json.traits.JsonKey
import com.json.traits.JsonMapTrait
import com.json.traits.JsonValue

/**
 * :JsonObject => HashMap(JsonKey -> JsonValue)
 * Since the pair is only to be used by the parser the Map is built using a list of (JsonKey,JsonValue) 
 * which can be easily be obtained using getValue() of JsonPair object.
 */
class JsonObject(value:Map[JsonKey , JsonValue]) extends JsonMapTrait(value) {
  override def toString() = "{"+value.toList.map(f => f._1 + ":" + f._2).mkString(",\n")+"}"
  override def getValue():Map[JsonKey,JsonValue] = value.toMap
  override def copy(v:Any) = new JsonObject(v.asInstanceOf[Map[JsonKey,JsonValue]])
  override def apply(key:String):JsonValue =  value.get(new JsonString(key)).getOrElse(null)
  override def apply(key:JsonKey) = value.get(key).getOrElse(null)
  override def apply(key:Double) = value.get(new JsonNumber(key)).getOrElse(null)
  override def apply(key:Boolean) = value.get(new JsonBoolean(key)).getOrElse(null)
}
