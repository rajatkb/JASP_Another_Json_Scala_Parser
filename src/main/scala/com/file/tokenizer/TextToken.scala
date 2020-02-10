package com.file.tokenizer

import com.lexer.traits.Lexeme

class TextToken(val char:Char,val lineNumber:Int ,val columnNumber:Int) {
  override def toString() = "<"+char+","+lineNumber+","+columnNumber+">"
}