package com.json.struct

import com.json.traits.JsonStringTrait
/**
 * :JsonString => string
 * represents a string entity similar to a number can be extended and made stuff with
 */
case class JsonString(value:String) extends JsonStringTrait(value){
  override def toString() = if(value == null) "" else "\""+value+"\""
  override def getValue() = value
  override def copy(v:Any) = new JsonString(v.asInstanceOf[String])
}