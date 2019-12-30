package com.parser.director

import com.lexer.analyzer.LexemeGenerator
import com.json.traits.JsonBuilderTrait
import com.json.traits.JsonMapTrait
import com.file.tokenizer.Tokenizer
import com.lexer.traits.LexemeGeneratorTrait

class Parser(lexer:LexemeGeneratorTrait, builder:JsonBuilderTrait) extends ParseTable{
  
  
   
  def parse() = {
    
    val stack = lexer.getStream().foldLeft(List(this.S,this.STOP))((stack,lexeme) => {
      this.stackOperation(stack, lexeme) 
    })
    
    if(stack.head == this.STOP)
      println("parsed")
  }
}