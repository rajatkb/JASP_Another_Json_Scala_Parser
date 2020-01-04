package com.json.traits

abstract class JsonNumberTrait(num:Double) extends JsonKey with JsonValue {
  def copy(a:Double):JsonNumberTrait
  override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonNumberTrait does not supports apply, try getValue()")
  override def getValue():Double = num
  
  override def getStringStream() = Stream( this.printDouble(num)) 

  private def printDouble(a:Double) = { 
      if(a < Int.MaxValue )
        if((a - a.toInt) != 0) f"$a%e" else a.toInt.toString()
      else
        f"$a%e"
    }

}

object JsonNumberTrait {
  implicit def value2Number(a:JsonValue) = a match { case e:JsonNumberTrait => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}