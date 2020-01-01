package com.json.traits

abstract class JsonNumberTrait(num:Double) extends JsonKey with JsonValue {
  override def copy(a:Any):JsonNumberTrait
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonNumberTrait does not supports apply, try getValue()")
  override def apply(key:Int):JsonValue = throw new IllegalAccessException("JsonNumberTrait does not supports apply, try getValue()")
  override def getValue() = num
}