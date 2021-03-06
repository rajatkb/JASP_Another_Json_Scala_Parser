package com.lexer.lexicon

import com.lexer.traits.Lexeme
import com.lexer.traits.SymbolTable


class NumberLexeme(num:Double, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  def getValue():Double = num
  def getSymbol() = this.NUMBER
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  override def toString() = "("+this.getSymbol()+"|"+num+","+lineNumber+","+columnNumber+")"
  override def getString() = num.toString()
  override def getDouble() = getValue()
  override def getBoolean() = throw new UnsupportedOperationException(s"Boolean value asked fom Double lexeme $num at (l,c):${this.lineNumber},${this.columnNumber}")

}