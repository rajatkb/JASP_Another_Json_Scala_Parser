package com.json.basic

import com.json.traits.JsonBool
import com.json.traits.JsonWriteable
import com.json.traits.JsonValue

object JsonBoolean{
  def apply(b:Boolean) = new JsonBoolean(b)
  implicit def value2Bool(a:JsonValue) = a.asInstanceOf[Boolean]
}

case class JsonBoolean(b:Boolean) extends JsonBool(b) {
  override def toString() =  b.toString()
}

