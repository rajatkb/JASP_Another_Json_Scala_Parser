package com.json.struct

import com.json.traits.JsonUnit
import com.json.traits.JsonNumberTrait
/**
 * :JsonNumber => Double
 *  Represents a number entity in a Json, can be extended for creating new prototypes
 */
case class JsonNumber(value:Double) extends JsonNumberTrait(value) {
  override def toString() =  value.toString()
  override def getValue() = value
  override def copy(v:Any) = new JsonNumber(v.asInstanceOf[Double])
  
}