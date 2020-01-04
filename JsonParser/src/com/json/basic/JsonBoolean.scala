package com.json.basic

import com.json.traits.JsonBoolTrait
import com.json.traits.JsonWriteable
import com.json.traits.JsonValue

object JsonBoolean{
  def apply(b:Boolean) = new JsonBoolean(b)
  implicit def value2Boolean(a:JsonValue) = a match { case e:JsonBoolean => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}

class JsonBoolean(b:Boolean) extends JsonBoolTrait(b) {
  override def toString() =  b.toString()
  override def copy(v:Boolean) = new JsonBoolean(v)
  
}

