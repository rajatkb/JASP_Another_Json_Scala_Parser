package com.lexer.lexicon

import com.lexer.traits.Lexeme
import com.lexer.traits.SymbolTable

class StringLexeme(str:String, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  def getValue():String = str
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  def getSymbol() =  this.STRING
  override def toString() = if(str == null) "("+this.getSymbol()+"|"+"null"+","+lineNumber+","+columnNumber+")" 
                            else "("+this.getSymbol()+"|"+str+","+lineNumber+","+columnNumber+")"
                            
  override def getString() = str
  override def getDouble() = throw new IllegalAccessError("Double value asked fom String lexeme "+str+" at (l,c):"+this.lineNumber+","+this.columnNumber)
  override def getBoolean() = throw new IllegalAccessError("Boolean value asked fom String lexeme "+str+" at (l,c):"+this.lineNumber+","+this.columnNumber)

}