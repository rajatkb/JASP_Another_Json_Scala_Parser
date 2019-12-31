package com.json.traits

trait JsonValue extends JsonUnit {
  def apply(key:JsonKey):JsonValue
  def apply(key:String):JsonValue
  def apply(key:Double):JsonValue
  def apply(key:Boolean):JsonValue
}
