package com.json.traits

abstract class JsonBool(b:Boolean) extends JsonValue with JsonKey{
  override def getValue():Boolean = b
  override def toStream() = Stream(b.toString())
  override def apply(key:JsonKey) = throw new UnsupportedOperationException(s"Cannot apply on ${this.getClass().toString()}")
}

object JsonBool {
  implicit def value2Bool(a:JsonValue) = a.asInstanceOf[JsonBool]
}