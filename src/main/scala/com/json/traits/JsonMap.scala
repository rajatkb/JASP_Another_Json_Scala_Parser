package com.json.traits
//import scala.collection.mutable.HashMap

abstract class JsonMap(value:Map[JsonKey , JsonValue]) extends JsonUnit with JsonValue {
  
  
  override def apply(key:JsonKey) = value.get(key).getOrElse(null)
  
  override def getValue():Map[JsonKey,JsonValue] = value 
  
  override def toStream() = {
     
    (value.toStream.flatMap(f => {
      val v = f._1 match {  case e:JsonChars => e.toStream(); 
                            case e => Stream("\"") #::: e.toStream() #::: Stream("\"") }
      
      Stream(",") #::: v #::: Stream(":") #::: f._2.toStream()}
    )) match {
      case Stream() => Stream("{") #::: Stream("}")
      case v => Stream("{") #::: v.tail #::: Stream("}")
    }
    
  }
  

  
}


object JsonMap {
  implicit def value2Map(a:JsonValue) = a match { case e:JsonMap => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}
