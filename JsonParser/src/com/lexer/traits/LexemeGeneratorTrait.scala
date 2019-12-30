package com.lexer.traits

import com.file.tokenizer.TextToken

abstract class LexemeGeneratorTrait(tokens:Stream[TextToken]) extends SymbolTable {
  def getStream():Stream[Lexeme]
}