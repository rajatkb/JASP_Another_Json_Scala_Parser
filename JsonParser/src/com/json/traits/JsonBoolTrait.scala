package com.json.traits

abstract class JsonBoolTrait(b:Boolean) extends JsonValue with JsonKey{
  override def copy(a:Any):JsonBoolTrait
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonBooleanTrait does not supports apply, try getValue()")
  override def apply(key:Int):JsonValue = throw new IllegalAccessException("JsonBooleanTrait does not supports apply, try getValue()")
  override def getValue() = b
  
  override def getStringStream() = Stream(b.toString())

}