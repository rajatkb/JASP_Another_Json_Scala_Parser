package com.json.traits

trait JsonValue extends JsonUnit with JsonWriteable {
  def apply(key:JsonKey):JsonValue
  def apply(key:Int):JsonValue
}
