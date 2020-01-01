package com.json.traits

abstract class JsonStringTrait(str:String) extends JsonKey with JsonValue {
  override def copy(a:Any):JsonStringTrait
  override def getValue():String = str
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonStringTrait does not supports apply, try getValue()")
  override def apply(key:Int):JsonValue = throw new IllegalAccessException("JsonStringTrait does not supports apply, try getValue()")
}

