package com.lexer.lexicon

import com.lexer.traits.Lexeme
import com.logger.Logger
import com.lexer.traits.SymbolTable


class NumberLexeme(num:String, lineNumber:Int, columnNumber:Int) extends Lexeme with SymbolTable {
  def getValue():Double = try { num.toDouble }
                          catch{ 
                            case e:NumberFormatException => Logger.error("Something wrong with Lexeme have bad string data for number"+num)
                                                            throw e                                      
                          }
  def getSymbol() = this.NUMBER
  def getLineNumber():Int = lineNumber
  def getColumnNumber():Int = columnNumber
  override def toString() = "("+this.getSymbol()+"|"+num+","+lineNumber+","+columnNumber+")"
}