package com.lexer.traits

trait Lexeme {
  def getValue():Any
  def getLineNumber():Int
  def getColumnNumber():Int
  def getSymbol():Char
}