package com.json.traits

abstract class JsonList(value:Seq[JsonValue]) extends JsonUnit with JsonValue {
  
   override def apply(key:JsonKey):JsonValue = key match {
     case key:JsonNumeric => value.applyOrElse(key.getValue().toInt, null)
     case _ => throw new IllegalArgumentException("Non JsonNumber argument given ")
   }
  
   override def getValue():Seq[JsonValue] = value
   
     
  override def toStream() = {
     (value.toStream.flatMap(f => Stream(",") #::: f.toStream())) match {
       case Stream() => Stream("[")#:::Stream("]")
       case v =>  Stream("[")#:::v.tail#:::Stream("]")
     }
   }

}

object JsonList {
  implicit def value2Array(a:JsonValue) = a match { case e:JsonList => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}