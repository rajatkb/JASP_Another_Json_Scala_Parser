package com.lexer.lexicon

import com.lexer.traits.Lexeme
import scala.reflect.runtime.SymbolTable
import com.lexer.traits.SymbolTable
import com.logger.Logger

class BooleanLexeme(b:String, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  def getValue():Boolean = try { b.toBoolean }
                          catch{ 
                            case e:Exception => Logger.error("Something wrong with Lexeme have bad string data for boolean "+b)
                                                            throw e                                      
                          }
  def getSymbol() = this.BOOL
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  override def toString() = "("+this.getSymbol()+"|"+b+","+lineNumber+","+columnNumber+")"
}