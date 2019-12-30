package com.json.traits
//import scala.collection.mutable.HashMap

abstract class JsonMapTrait(value:Map[JsonKey , JsonValue]) extends JsonUnit with JsonValue{
  override def copy(a:Any):JsonMapTrait
  override def getValue():Map[JsonKey,JsonValue]
}