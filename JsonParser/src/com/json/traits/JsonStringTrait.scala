package com.json.traits

abstract class JsonStringTrait(str:String) extends JsonKey with JsonValue {
  def copy(a:String):JsonStringTrait
  override def getValue():String = str 
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonStringTrait does not supports apply, try getValue()")
 
  override def getStringStream() = {
    Stream("\""+str+"\"")
  }
 

}

object JsonStringTrait {
  implicit def value2String(a:JsonValue) = a match { case e:JsonStringTrait => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}

