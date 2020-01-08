# JASP_Another_Json_Scala_Parser
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/dwyl/esta/issues)  

It's a Scala Json parser. It parses JSON, and create Map , Arrays and String and Numbers and Booleans embedded in them. It' supposed to be functional in nature, so no mutation. Also it's to support streaming which is work progress, getting immutability and streaming is a tough nut to work out together.  

## How to use ?

```scala
import com.api.Jasp._
import com.logger.Logger
import com.json.basic.JsonObject
import com.json.basic.JsonArray


object Main {
  

   def main(args:Array[String]):Unit = {
      val filename = "E://Project Work//citylots.json"
      val filename2 = "E://Project Work//JASP_Another_Json_Scala_Parser//JsonParser//test.json" 
//      val a = Logger.timer( JSON.parseFile(filename) )
      /**
       * Benchmark with citylots.json : Elapsed time: 227.2318s
       * More optimization needed, need to reduce it under a second
       * 
       * 
       */
      
      
	val a = Logger.timer( JSON.parseFile(filename2) )
    
        print(a("context")("date"))
    

    	// This pretty syntax alas is only possible in Scala with tons of implicits
       	// You can also have compile time type safety because of the same. 
	// Though ugly conversions are there just hidden

	val map:JsonMap = JsonObject(	1 -> 2 , 3 -> JsonArray(-1.00,2.00,-3.00564,4.35656) , "
					hello" -> JsonBoolean(true) )
    

        println(map)
   	
	// A lazy stream based iterator is returned by each JsonUnit 
	// since they all implement JsonWritable interface  
        
	val str = map.getStringStream().toList mkString ""
    
        println(str)
    
    	// You can also directly pass a valid string and have it parsed there itself
        
	val smap = JSON.parseString(str)
    
    	// You can now write to file
        
	JSON.toFile(smap, newfile)

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

#### Check the Issues for TO-DOs .... 
#### It's works in progress

* ~~**Create a lazy writable iterator for the JSON objects**~~ ~~Create a fast writer consumer for the writables.~~

* ~~**Turn this project into a Maven Artifact Library**~~ 



## How to Contribute
* Just move to the com.unit.test package. then move to Object JSON. From there on you can follow the code
* Parser is built with a builder pattern and objects are initialised using Prototypes of their instance
* The idea was to program using interfaces. The only place InstanceOf was used in the prototype copy method. ~~(Need working out)~~
* Create pull request against one of the To-Dos, or maybe add your own issue and also do post some test case for same in a sepparate unit test if you can.


Thanks for checking out ! If you find this educational , useful for your project or easy to use API leave a star.
