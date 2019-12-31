package com.json.traits

abstract class JsonNumberTrait(num:Double) extends JsonKey with JsonValue {
  override def copy(a:Any):JsonNumberTrait
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonNumberTrait does not supports apply, try getValue()")
  override def apply(key:String):JsonValue = throw new IllegalAccessException("JsonNumberTrait does not supports apply, try getValue()")
  override def apply(key:Double):JsonValue =  throw new IllegalAccessException("JsonNumberTrait does not supports apply, try getValue()")
  override def apply(key:Boolean):JsonValue =  throw new IllegalAccessException("JsonNumberTrait does not supports apply, try getValue()")
  
  override def getValue() = num
}