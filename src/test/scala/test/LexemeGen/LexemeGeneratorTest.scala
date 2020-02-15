package test.LexemeGen

import org.junit.Assert._

import org.junit.Test
import java.nio.file.Paths
import com.api.Jasp._
import com.json.basic._
import com.file.tokenizer.Tokenizer
import com.lexer.analyzer.LexemeGenerator
import scala.io.Source



class LexemeGeneratorTest {
  @Test
  def testGetStreamForString() = {
    val testCases = Array("\"Hello\"" , "\" Hel\\\" lo\"" , "\" \\t \\t \\t something \""  , "\"kuch v \"" ) 
    val testRes = Array("Hello" , " Hel\" lo" , " \t \t \t something " , "kuch v ")
    val src = Source.fromString(s"${testCases(0)} ${testCases(1)} ${testCases(2)} ${testCases(3)} ")
    var tokenStream = new Tokenizer(src)
    var lexemeGen = new LexemeGenerator(tokenStream.getStream()) 
    var array = (for( lex <- lexemeGen.getStream()) yield { lex.getString() }).toArray
    assert( array.sameElements(testRes) , "LexemeGenerator Not working")
  }
  
  @Test
  def testGetStreamForSymbol() = {
    val testCases = Array("{" , "}" , ":" , "[" , "]" , ",") 
    val testRes = Array("{" , "}" , ":" , "[" , "]" , ",")
    val src = Source.fromString(s"${testRes.mkString(" ")}")
    var tokenStream = new Tokenizer(src)
    var lexemeGen = new LexemeGenerator(tokenStream.getStream()) 
    var array = (for( lex <- lexemeGen.getStream()) yield { lex.getSymbol().toString() }).toArray
    assert( array.sameElements(testRes) , "LexemeGenerator Not working")
  }
  
  @Test
  def testGetStreamForBoolean() = {
    val testCases = Array("true" ,"false" , "true" ) 
    val testRes = testCases
    val src = Source.fromString(s"${testRes.mkString(" ")}")
    var tokenStream = new Tokenizer(src)
    var lexemeGen = new LexemeGenerator(tokenStream.getStream())
    var array = (for( lex <- lexemeGen.getStream()) yield { lex.getString() }).toArray
    assert( array.sameElements(testRes) , "LexemeGenerator Not working")
  }
  
  @Test
  def testGetStreamForNumber() = {
    val testCases = Array( "+12.89" ,"-78.89" , "+67.89e+90" , "-89.09E+90" ) 
    val testRes = testCases.map(f => f.toDouble)
    val src = Source.fromString(s"${testRes.mkString(" ")}")
    var tokenStream = new Tokenizer(src)
    var lexemeGen = new LexemeGenerator(tokenStream.getStream())
    var array = (for( lex <- lexemeGen.getStream()) yield { lex.getDouble() }).toArray
    assert( array.sameElements(testRes) , "LexemeGenerator Not working")
  }
 
  
  
}