package com.json.struct

import com.json.traits.JsonBoolTrait

class JsonBoolean(b:Boolean) extends JsonBoolTrait(b) {
  override def toString() =  b.toString()
  override def getValue() = b
  override def copy(v:Any) = new JsonBoolean(v.asInstanceOf[Boolean])
}