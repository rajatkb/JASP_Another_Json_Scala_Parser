package com.json.traits

abstract class JsonBoolTrait(b:Boolean) extends JsonValue with JsonKey{
  override def copy(a:Any):JsonBoolTrait
  override def getValue():Boolean
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonBooleanTrait does not supports apply, try getValue()")
  override def apply(key:String):JsonValue = throw new IllegalAccessException("JsonBooleanTrait does not supports apply, try getValue()")
  override def apply(key:Double):JsonValue =  throw new IllegalAccessException("JsonBooleanTrait does not supports apply, try getValue()")
  override def apply(key:Boolean):JsonValue =  throw new IllegalAccessException("JsonBooleanTrait does not supports apply, try getValue()")
}