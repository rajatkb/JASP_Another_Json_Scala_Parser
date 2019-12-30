package com.json.traits

abstract class JsonStringTrait(str:String) extends JsonKey with JsonValue {
  override def copy(a:Any):JsonStringTrait
  override def getValue():String
}