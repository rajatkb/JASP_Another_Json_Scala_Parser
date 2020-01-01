package com.json.api

import com.json.traits.JsonValue
import com.json.traits.JsonKey
import com.json.basic.JsonNumber
import com.json.basic.JsonString
import com.json.basic.JsonBoolean

object Implicits {
  
        implicit def numtoValue(a:Double):JsonValue = new JsonNumber(a)
      implicit def numtoKey(a:Double):JsonKey = new JsonNumber(a)
      implicit def stringtoValue(a:String):JsonValue = new JsonString(a)
      implicit def stringtoKey(a:String):JsonKey = new JsonString(a)
      implicit def booltoKey(a:Boolean):JsonKey = new JsonBoolean(a)
      implicit def booltoValue(a:Boolean):JsonValue = new JsonBoolean(a)
      implicit def numtoKeyArrowAssoc(a:Double):ArrowAssoc[JsonKey] = new ArrowAssoc(a)
      implicit def stringtoKeyArrowAssoc(a:String):ArrowAssoc[JsonKey] = new ArrowAssoc(a)
      implicit def booleantoKeyArrowAssoc(a:Boolean):ArrowAssoc[JsonKey] = new ArrowAssoc(a)

  
}