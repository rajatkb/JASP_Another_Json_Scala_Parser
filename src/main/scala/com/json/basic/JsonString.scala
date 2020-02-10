package com.json.basic

import com.json.traits.JsonChars
import com.json.traits.JsonWriteable
import com.json.traits.JsonValue

/**
 * :JsonString => string
 * represents a string entity similar to a number can be extended and made stuff with
 */

object JsonString{
  def apply(value:String) = new JsonString(value)
  implicit def value2String(a:JsonValue) = a.asInstanceOf[JsonString]
}

case class JsonString(value:String) extends JsonChars(value) {
  override def toString() = if(value == null) "undefined" else "\""+value+"\""
  
}