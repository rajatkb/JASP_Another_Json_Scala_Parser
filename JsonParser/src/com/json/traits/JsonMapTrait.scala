package com.json.traits
//import scala.collection.mutable.HashMap

abstract class JsonMapTrait(value:Map[JsonKey , JsonValue]) extends JsonUnit with JsonValue {
  override def copy(a:Any):JsonMapTrait
  override def apply(key:JsonKey) = value.get(key).getOrElse(null)
  override def apply(int:Int) = throw new IllegalAccessException("JsonMapTrait does not supports apply on Int, try getValue()")
  override def getValue():Map[JsonKey,JsonValue] = value
  
  override def getStringStream() = {
    Stream("{") ++ value.toStream.flatMap(f => f._1.getStringStream()++Stream(":")++f._2.getStringStream() ++ Stream(",")) ++ Stream("}")
  }
  

  
}

