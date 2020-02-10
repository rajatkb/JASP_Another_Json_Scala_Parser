package com.lexer.lexicon

import com.lexer.traits.Lexeme
import scala.reflect.runtime.SymbolTable
import com.lexer.traits.SymbolTable


class BooleanLexeme(b:String, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  def getValue():Boolean =  try { b.toBoolean }
                            catch{ 
                              case e:Exception => throw new IllegalStateException(s"Something wrong with Lexeme have bad string data for boolean $b")                                     
                            }
                            
  def getSymbol() = this.BOOL
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  override def toString() = "("+this.getSymbol()+"|"+b+","+lineNumber+","+columnNumber+")"
  override def getString() = b
  override def getDouble() = throw new IllegalAccessError(s"Double value asked fom Boolean lexeme $b at (l,c): ${this.lineNumber} , ${this.columnNumber}")
  override def getBoolean() = getValue()
}