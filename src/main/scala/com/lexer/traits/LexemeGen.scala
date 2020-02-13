package com.lexer.traits

import com.file.tokenizer.TextToken

abstract class LexemeGen(tokens: => Iterator[TextToken]) extends SymbolTable {
  def getStream():Iterator[Lexeme]
}