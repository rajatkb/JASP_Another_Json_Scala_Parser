package com.json.traits

abstract class JsonListTrait(value:Seq[JsonValue]) extends JsonUnit with JsonValue {
   override def copy(a:Any):JsonListTrait 
  
   override def apply(key:Int):JsonValue = value.applyOrElse(key, null)
   override def apply(key:JsonKey):JsonValue = throw new IllegalAccessException("JsonArrayTrait does not supports apply on JsonKey, try getValue()")
   override def getValue() = value
   
     
  override def getStringStream() = {
     Stream("[")++value.toStream.flatMap(f => f.getStringStream() ++ Stream(","))++Stream("]")
   }

}