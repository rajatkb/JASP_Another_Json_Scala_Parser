package com.json.basic

import com.json.traits.JsonStringTrait
/**
 * :JsonString => string
 * represents a string entity similar to a number can be extended and made stuff with
 */

object JsonString{
  def apply(value:String) = new JsonString(value)
}

case class JsonString(value:String) extends JsonStringTrait(value){
  override def toString() = if(value == null) "null" else "\""+value+"\""
  override def copy(v:Any) = new JsonString(v.asInstanceOf[String])
}