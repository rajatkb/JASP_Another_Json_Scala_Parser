package com.json.traits

abstract class JsonListTrait(value:Seq[JsonValue]) extends JsonUnit with JsonValue {
   def copy(a:Seq[JsonValue]):JsonListTrait 
  
   override def apply(key:JsonKey):JsonValue = key match {
     case key:JsonNumberTrait => value.applyOrElse(key.getValue().toInt, null)
     case _ => throw new IllegalArgumentException("Non JsonNumber argument given ")
   }
  
   override def getValue():Seq[JsonValue] = value
   
     
  override def getStringStream() = {
     (value.toStream.flatMap(f => Stream(",") #::: f.getStringStream())) match {
       case Stream() => Stream("[")#:::Stream("]")
       case v =>  Stream("[")#:::v.tail#:::Stream("]")
     }
   }

}

object JsonListTrait {
  implicit def value2Array(a:JsonValue) = a match { case e:JsonListTrait => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}