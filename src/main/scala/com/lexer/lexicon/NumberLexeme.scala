package com.lexer.lexicon

import com.lexer.traits.Lexeme
import com.lexer.traits.SymbolTable


class NumberLexeme(num:String, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  def getValue():Double = try { num.toDouble }
                          catch{ 
                            case e:NumberFormatException => throw new IllegalStateException(s"Something wrong with Lexeme have bad string data for number $num")
                                                                                                
                          }
  def getSymbol() = this.NUMBER
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  override def toString() = "("+this.getSymbol()+"|"+num+","+lineNumber+","+columnNumber+")"
  override def getString() = num
  override def getDouble() = getValue()
  override def getBoolean() = throw new IllegalAccessError(s"Boolean value asked fom Double lexeme $num at (l,c):${this.lineNumber},${this.columnNumber}")

}