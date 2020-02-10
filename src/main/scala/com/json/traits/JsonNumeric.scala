package com.json.traits

abstract class JsonNumeric(num:Double) extends JsonKey with JsonValue {
  override def getValue():Double = num
  
  override def toStream() = Stream( this.printDouble(num)) 

  private def printDouble(a:Double) = { 
      if(a < Int.MaxValue )
        if((a - a.toInt) != 0) f"$a%e" else a.toInt.toString()
      else
        f"$a%e"
    }
 
  override def apply(key:JsonKey) = throw new IllegalAccessError(s"Cannot apply on ${this.getClass().toString()}")
}
