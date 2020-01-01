package com.json.traits

trait JsonValue extends JsonUnit {
  def apply(key:JsonKey):JsonValue
  def apply(key:Int):JsonValue
}
