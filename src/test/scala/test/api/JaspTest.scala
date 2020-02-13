package test.api

import org.junit.Assert._

import org.junit.Test
import java.nio.file.Paths
import com.api.Jasp._
import com.json.basic._
import com.file.tokenizer.Tokenizer
import com.lexer.analyzer.LexemeGenerator
import scala.io.Source

class JaspTest {
  
  object Logger {
    def info(m:String) = println(s"[INFO] $m")

    
  }
  
  def timer[R](block: => R): Double = {
      val t0 = System.nanoTime()
      val result = block    // call-by-name
      val t1 = System.nanoTime()
      (t1 - t0).toFloat/1000000000
  }
  
  def benchmark[R](time: Int)(block: => R): Double = {
    ((1 until time) toList).map(f => timer(block)).reduce(_ + _) / time
  }

  def readTime(filename:String) = {
    val src = new NioSource(filename)
    val time = benchmark(100)((for{i <- src.getLines()}{}))
    Logger.info(s"Time for file reading $time")
  }
  
  
  def tokenizerTime(filename:String) = {
    val src2 = new NioSource(filename)
    var tokenStream = new Tokenizer(src2)
    val time2 = benchmark(100)((for(i <- tokenStream.getStream()){}))
    Logger.info(s"Time for getting token $time2")
  }
  
  def lexerTime(filename:String) = {
    val src = new NioSource(filename)
    var tokenStream = new Tokenizer(src)
    var lexemeGen = new LexemeGenerator(tokenStream.getStream())
    val time = benchmark(100)((for(i <- lexemeGen.getStream()){}))
    Logger.info(s"Time for getting Lexicons $time")
  }
  
  
  val filename =  Paths.get(getClass().getClassLoader().getResource("test.json").toURI()).toString()
//  val filename = "E://Project Work//citylots.json"
  
  @Test
  def testParsing() = {
    val json = JSON.parseFile(filename)
    val data:JsonString = json("context")("date")    
    assertEquals("12/25/18 00:05:14" , data.getValue())
  }
  
  @Test
  def testParsingSpeed() = {
    JSON.parseFile(filename)
    val time = benchmark(100)(JSON.parseFile(filename))
    assert(time < 0.05, s"Time taken $time , revert to last commit !!") 
  }
  
  
  @Test
  def testReadTime() = {
    this.readTime(filename)
  }
  
  @Test
  def testTokenizerTime() = {
    this.tokenizerTime(filename)
  }
  
  @Test
  def testLexerTime() = {
    this.lexerTime(filename)
  }
  
  
  
}


