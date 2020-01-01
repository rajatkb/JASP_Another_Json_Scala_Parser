package com.json.basic
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

object JsonObject{
  def apply(value:Map[JsonKey , JsonValue] = Map()) = new JsonObject(value)
  def apply(args:(JsonKey,JsonValue)*) = new JsonObject(args.toMap)
}

class JsonObject(value:Map[JsonKey , JsonValue] = Map()) extends JsonMapTrait(value) {
  
  override def toString() = "{"+value.toList.map(f => f._1 + ":" + f._2).mkString(",\n")+"}"
  override def copy(v:Any) = new JsonObject(v.asInstanceOf[Map[JsonKey,JsonValue]])
  
  
  override def apply(key:String) = super.apply(new JsonString(key))
  override def apply(key:Double) = super.apply(new JsonNumber(key.toInt ))
  override def apply(key:Boolean) = super.apply(new JsonBoolean(key))
  
  
  def this(args:(JsonKey,JsonValue)* ) = this(args.toMap)
  

}
