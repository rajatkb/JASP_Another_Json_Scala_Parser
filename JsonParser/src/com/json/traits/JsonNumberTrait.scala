package com.json.traits

abstract class JsonNumberTrait(num:Double) extends JsonKey with JsonValue {
  override def copy(a:Any):JsonNumberTrait
  override def getValue():Double
}