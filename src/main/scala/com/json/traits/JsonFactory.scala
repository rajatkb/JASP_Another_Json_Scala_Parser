package com.json.traits

//import scala.collection.mutable.HashMap

/**
 * varargs for the createFunction. user can now add more argument to implementation
 */
trait JsonFactory {
    def createJsonStringEntity(str:String , args:Any*):JsonChars 
    def createJsonNumberEntity(num:Double , args:Any*):JsonNumeric 
    def createJsonList(list:Seq[JsonValue] , args:Any*):JsonList 
    def createJsonMap(keyValueMap:Map[JsonKey,JsonValue] , args:Any*):JsonMap
    def createJsonBooleanEntity(b:Boolean , args:Any*):JsonBool
}