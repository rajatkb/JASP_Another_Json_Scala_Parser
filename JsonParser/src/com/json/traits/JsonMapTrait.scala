package com.json.traits
//import scala.collection.mutable.HashMap

abstract class JsonMapTrait(value:Map[JsonKey , JsonValue]) extends JsonUnit with JsonValue {
  
  def copy(a:Map[JsonKey , JsonValue]):JsonMapTrait
  
  override def apply(key:JsonKey) = value.get(key).getOrElse(null)
  
  override def getValue():Map[JsonKey,JsonValue] = value 
  
  override def getStringStream() = {
     
    (value.toStream.flatMap(f => {
      val v = f._1 match {  case e:JsonStringTrait => e.getStringStream(); 
                            case e => Stream("\"")++e.getStringStream()++Stream("\"") }
      
      Stream(",")++ v ++ Stream(":")++f._2.getStringStream()}
    )) match {
      case Stream() => Stream("{") ++ Stream("}")
      case v => Stream("{")++v.tail++Stream("}")
    }
    
  }
  

  
}


object JsonMapTrait {
  implicit def value2Map(a:JsonValue) = a match { case e:JsonMapTrait => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}
