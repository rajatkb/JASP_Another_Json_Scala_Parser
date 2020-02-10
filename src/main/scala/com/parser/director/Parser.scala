package com.parser.director

import com.lexer.analyzer.LexemeGenerator
import com.json.traits.JsonBuilder
import com.file.tokenizer.Tokenizer
import com.lexer.traits.LexemeGeneratorTrait

class Parser(lexer:LexemeGeneratorTrait, builder:JsonBuilder) extends ParseTable{
    
  def parse() = {
    val stack = lexer.getStream().foldLeft(List(this.S,this.STOP))((stack,lexeme) => {
      this.stackOperation(stack, lexeme , builder) 
    })
    
    if(stack.head != this.STOP) throw new IllegalStateException("Parsing failed, text consumed but parser not reached it's final state")

    builder.build()
      
  }
}