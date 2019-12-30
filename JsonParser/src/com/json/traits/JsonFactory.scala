package com.json.traits

//import scala.collection.mutable.HashMap

trait JsonFactory {
    def createJsonStringEntity(str:String):JsonStringTrait
    def createJsonNumberEntity(num:Double):JsonNumberTrait 
    def createJsonList(list:Iterable[JsonValue]):JsonListTrait 
    def createJsonMap(keyValueMap:Map[JsonKey,JsonValue]):JsonMapTrait
    def createJsonBooleanEntity(b:Boolean):JsonBoolTrait
}