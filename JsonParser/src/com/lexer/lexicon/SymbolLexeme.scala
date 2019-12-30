package com.lexer.lexicon

import com.lexer.traits.Lexeme
import com.lexer.traits.SymbolTable
import java.util.IllegalFormatException

class SymbolLexeme(symbol:Char, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  
  if(!this.checkValidJsonStructureIdentifier(symbol)) throw new IllegalArgumentException("Illegal Symbol, something wrong with Lexer symbol:"+symbol + " (l,c):"+lineNumber+" : "+columnNumber)
  
  def getValue():String = throw new IllegalAccessError("Symbol Lexeme does not have any value. Error from unit using Lexemes")
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  def getSymbol() =  symbol
  override def toString() = "("+this.getSymbol()+"|"+symbol+","+lineNumber+","+columnNumber+")"
  
  override def getString() = throw new IllegalAccessError("String value asked fom Symbol lexeme "+symbol+" at (l,c):"+this.lineNumber+","+this.columnNumber)
  override def getDouble() = throw new IllegalAccessError("Double value asked fom Symbol lexeme "+symbol+" at (l,c):"+this.lineNumber+","+this.columnNumber)
  override def getBoolean() = throw new IllegalAccessError("Boolean value asked fom Symbol lexeme "+symbol+" at (l,c):"+this.lineNumber+","+this.columnNumber)

}