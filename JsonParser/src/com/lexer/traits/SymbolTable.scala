package com.lexer.traits

import java.util.IllegalFormatException

trait SymbolTable {
  val OPENBRACE = '{'
  val CLOSEBRACE = '}'
  val OPENBRACKET = '['
  val CLOSEBRACKET = ']'
  val COLON = ':'
  val COMMA = ','
  val STRING = 's'
  val NUMBER = 'n'
  val BOOL = 'b'
  val DOUBLEQUOTE = '\"'
  
  def checkValidJsonStructureIdentifier = (char:Char) => char match {
    case `OPENBRACE` => true
    case `CLOSEBRACE` => true
    case `OPENBRACKET` => true
    case `CLOSEBRACKET` => true
    case `COLON` => true
    case `COMMA` => true
    case _ => false
  }
}