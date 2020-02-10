package com.json.basic

import com.json.traits.JsonFactory
import com.json.traits.JsonValue
import com.json.traits.JsonKey

object JsonDefaultFactory extends JsonFactory{
    override def createJsonStringEntity(str:String , args:Any*) = new JsonString(str) 
    override def createJsonNumberEntity(num:Double , args:Any*) = new JsonNumber(num)
    override def createJsonList(list:Seq[JsonValue] , args:Any*) = new JsonArray(list)
    override def createJsonMap(keyValueMap: Map[JsonKey,JsonValue] , args:Any*) = new JsonObject(keyValueMap)
    override def createJsonBooleanEntity(b:Boolean , args:Any*) = new JsonBoolean(b)
}