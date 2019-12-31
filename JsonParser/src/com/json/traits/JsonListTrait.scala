package com.json.traits

abstract class JsonListTrait(value:Seq[JsonValue]) extends JsonUnit with JsonValue {
   override def copy(a:Any):JsonListTrait 
   override def getValue():Iterable[JsonValue]
   override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonListTrait does not supports apply, try getValue()")
   override def apply(key:String):JsonValue = throw new IllegalAccessException("JsonListTrait does not supports apply, try getValue()")
   override def apply(key:Double):JsonValue
   override def apply(key:Boolean):JsonValue =  throw new IllegalAccessException("JsonListTrait does not supports apply, try getValue()")
}