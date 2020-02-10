package test.api

import org.junit.Assert._

import org.junit.Test
import java.nio.file.Paths
import com.api.Jasp._
import com.json.basic._

class JaspTest {
  
  
  def timer[R](block: => R): Double = {
      val t0 = System.nanoTime()
      val result = block    // call-by-name
      val t1 = System.nanoTime()
      (t1 - t0).toFloat/1000000000
  }
  
  
  val filename =  Paths.get(getClass().getClassLoader().getResource("test.json").toURI()).toString()
  
  @Test
  def testParsing() = {
    val json = JSON.parseFile(filename)
    val data:JsonString = json("context")("date")    
    assertEquals("12/25/18 00:05:14" , data.getValue())
  }
  
  @Test
  def testParsingSpeed() = {
    JSON.parseFile(filename)
    val times = 10
    val time = ((1 until times) toList).map(f => this.timer((JSON.parseFile(filename)))).reduce(_+_) / times
    assert(time < 0.05, s"Time taken $time") 
  }
  
  
  
 
  
  
}