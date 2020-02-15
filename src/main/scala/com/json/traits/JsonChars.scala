package com.json.traits

abstract class JsonChars(str:String) extends JsonKey with JsonValue {
  override def getValue():String = str 
  override def toStream() = {
    Stream("\""+str+"\"")
  }
  override def apply(key:JsonKey) = throw new IllegalAccessError(s"Cannot apply on ${this.getClass().toString()}")
}

object JsonChars{
  implicit def value2Chars(a:JsonValue) = a.asInstanceOf[JsonChars]
}
