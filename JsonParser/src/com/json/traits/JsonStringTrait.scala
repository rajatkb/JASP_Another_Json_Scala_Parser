package com.json.traits

abstract class JsonStringTrait(str:String) extends JsonKey with JsonValue {
  override def copy(a:Any):JsonStringTrait
  override def getValue():String
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonStringTrait does not supports apply, try getValue()")
  override def apply(key:String):JsonValue = throw new IllegalAccessException("JsonStringTrait does not supports apply, try getValue()")
  override def apply(key:Double):JsonValue =  throw new IllegalAccessException("JsonStringTrait does not supports apply, try getValue()")
  override def apply(key:Boolean):JsonValue =  throw new IllegalAccessException("JsonStringTrait does not supports apply, try getValue()")
}