package com.json.traits

abstract class JsonBoolTrait(b:Boolean) extends JsonValue with JsonKey{
  override def copy(a:Any):JsonBoolTrait
  override def getValue():Boolean
}