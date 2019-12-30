package com.json.traits

abstract class JsonListTrait(value:Iterable[JsonValue]) extends JsonUnit with JsonValue {
   override def copy(a:Any):JsonListTrait 
   override def getValue():Iterable[JsonValue]
}