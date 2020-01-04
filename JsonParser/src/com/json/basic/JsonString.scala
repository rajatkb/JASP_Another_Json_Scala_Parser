package com.json.basic

import com.json.traits.JsonStringTrait
import com.json.traits.JsonWriteable
import com.json.traits.JsonValue

/**
 * :JsonString => string
 * represents a string entity similar to a number can be extended and made stuff with
 */

object JsonString{
  def apply(value:String) = new JsonString(value)
  implicit def value2String(a:JsonValue) = a match { case e:JsonString => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}

case class JsonString(value:String) extends JsonStringTrait(value) {
  override def toString() = if(value == null) "undefined" else "\""+value+"\""
  override def copy(v:String) = new JsonString(v)
}