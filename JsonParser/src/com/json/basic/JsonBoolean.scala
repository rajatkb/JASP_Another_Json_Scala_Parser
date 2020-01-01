package com.json.basic

import com.json.traits.JsonBoolTrait

object JsonBoolean{
  def apply(b:Boolean) = new JsonBoolean(b)
}

class JsonBoolean(b:Boolean) extends JsonBoolTrait(b) {
  override def toString() =  b.toString()
  override def copy(v:Any) = new JsonBoolean(v.asInstanceOf[Boolean])
}

