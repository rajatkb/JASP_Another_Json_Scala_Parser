package com.lexer.lexicon

import com.lexer.traits.Lexeme
import scala.reflect.runtime.SymbolTable
import com.lexer.traits.SymbolTable


class BooleanLexeme(b:Boolean, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  def getValue():Boolean =  b
                            
  def getSymbol() = this.BOOL
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  override def toString() = "("+this.getSymbol()+"|"+b+","+lineNumber+","+columnNumber+")"
  override def getString() = b.toString()
  override def getDouble() = throw new UnsupportedOperationException(s"Double value asked fom Boolean lexeme $b at (l,c): ${this.lineNumber} , ${this.columnNumber}")
  override def getBoolean() = getValue()
}