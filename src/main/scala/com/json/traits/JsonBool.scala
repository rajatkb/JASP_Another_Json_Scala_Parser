package com.json.traits

abstract class JsonBool(b:Boolean) extends JsonValue with JsonKey{
  override def getValue():Boolean = b
  override def toStream() = Stream(b.toString())
  override def apply(key:JsonKey) = throw new IllegalAccessError(s"Cannot apply on ${this.getClass().toString()}") 
}
