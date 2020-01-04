package com.json.traits

abstract class JsonBoolTrait(b:Boolean) extends JsonValue with JsonKey{
  def copy(a:Boolean):JsonBoolTrait
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonBooleanTrait does not supports apply, try getValue()")
  override def getValue():Boolean = b
  
  override def getStringStream() = Stream(b.toString())

}

object JsonBoolTrait {
  implicit def value2Bool(a:JsonValue) = a match { case e:JsonBoolTrait => e; 
                                                   case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}