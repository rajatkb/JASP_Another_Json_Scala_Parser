# JASP_Another_Json_Scala_Parser
It's a Scala Json parser. It parses JSON, and create Map , Arrays and String and Numbers and Booleans embedded in them. It' supposed to be functional in nature, so no mutation. Also it's to support streaming which is work progress, getting immutability and streaming is a tough nut to work out together.  

## How to use ?

```scala
package com.unit.test


import com.api.Jasp._
import com.logger.Logger
import com.json.basic.JsonObject
import com.json.basic.JsonArray


object Main {
  

   def main(args:Array[String]):Unit = {
      val filename = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test.json"
      
      val a = Logger.timer( JSON.parseFile(filename) )
  
      val b = a("context")("caches")(2)
     
      
      
      val e = JsonArray( 1 , 2 , 3 )
      
      
      val m = new JsonObject(1 -> 2 , "hello" -> new JsonArray(1,2,3,4) , 4 -> new JsonArray(1,2,3)  )
      
      print(m("hello")(0))

   }
  
}

```  

You can run the above for testing the package by changing the filename , 
* Just import the release Jar or 
* Create your own by cloning the repository and compile from source.
* Add the jar as external dependency


## Why a Scala Json Parser again ?  
* Expose the JSON object API allowing you to build complex composition without actually walking through the JSON.
  You can just inherit the MapTrait , ListTrait etc and push them into the parser.
* Because It's fun and I did for educational purpose also it was a good way of practicing OOP design pattern  
* It now have tons of implicits to make working with the JsonArray and JsonObject a syntactical sugarry breeze

## TO-DO 

* **Replace case classes in static stack based parser with chars**: Hopefully should make the processing faster. 
    Tuple in place of case class have same performance So makes no difference.
* **Create a lazy builder for JSON** : The current builder returns a JSON by creating the entire structure. 
    Useful for smaller documents not so much for large docs i.e 100+mb with 1 million+ nested json entries.
    A lazy builder would not resolve the entire strcuture but only the top level strcuture. Parsing will be done
    in a on demand fashion.
* **Fix the lexer and create a less verboe and faster variant** : A possible stack based solution or a Derivative Lexer
    should be able to tackle the same
* ~~**Turn this project into a Maven Artifact Library**~~ 

## How to Contribute
* Just move to the com.unit.test package. then move to Object JSON. From there on you can follow the code
* Parser is built with a builder pattern and objects are initialised using Prototypes of their instance
* The idea was to program using interfaces. The only place InstanceOf was used in the prototype copy method. (Need working out)  


Thanks for checking out ! If you find this educational , useful for your project or easy to use API leave a star.
