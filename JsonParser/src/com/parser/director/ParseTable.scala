package com.parser.director

import scala.annotation.tailrec
import com.lexer.traits.Lexeme
import com.lexer.traits.SymbolTable

trait ParseTable extends SymbolTable {
  
  val S = 'S'
  val B = 'B'
  val L = 'L'
  val T = 'T'
  val V = 'V'
  val E = 'E'
  val M = 'M'
  val K = 'K'
  val STOP = '$'
   
  
  @tailrec final def stackOperation(stack:List[Char],lexeme:Lexeme):List[Char] = {
    println(lexeme,stack)
    (stack.head , lexeme.getSymbol()) match {
      case(`S`,`OPENBRACE`) => stackOperation(List(OPENBRACE,B,CLOSEBRACE):::stack.tail, lexeme)
      case(`B`,`CLOSEBRACE`) => stackOperation(stack.tail, lexeme)
      
      case(`B`,`STRING`) => stackOperation(List(T,L):::stack.tail, lexeme)
      case(`B`,`NUMBER`) => stackOperation(List(T,L):::stack.tail, lexeme)
      case(`B`,`BOOL`) => stackOperation(List(T,L):::stack.tail, lexeme)
      
      case(`L`,`CLOSEBRACE`) => stackOperation(stack.tail, lexeme)
      case(`L`,`COMMA`) => stackOperation(List(COMMA,B):::stack.tail, lexeme)
      
      case(`T`,`STRING`) => stackOperation(List(K,COLON,V):::stack.tail, lexeme)
      case(`T`,`NUMBER`) => stackOperation(List(K,COLON,V):::stack.tail, lexeme)
      case(`T`,`BOOL`) => stackOperation(List(K,COLON,V):::stack.tail, lexeme)
      
      case(`V`,`OPENBRACE`) => stackOperation(List(S):::stack.tail, lexeme)
      
      case(`V`,`STRING`) => stackOperation(List(STRING):::stack.tail, lexeme)
      case(`V`,`NUMBER`) => stackOperation(List(NUMBER):::stack.tail, lexeme)
      case(`V`,`BOOL`) => stackOperation(List(BOOL):::stack.tail, lexeme)
      
      case(`V`,`OPENBRACKET`) => stackOperation(List(OPENBRACKET,M,E,CLOSEBRACKET):::stack.tail, lexeme)
      case(`E`,`CLOSEBRACKET`) => stackOperation(stack.tail, lexeme)
      case(`E`,`COMMA`) => stackOperation(List(COMMA,M,E):::stack.tail, lexeme)
      case(`M`,`OPENBRACE`) => stackOperation(List(V):::stack.tail, lexeme)
      
      case(`M`,`STRING`) => stackOperation(List(V):::stack.tail, lexeme)
      case(`M`,`NUMBER`) => stackOperation(List(V):::stack.tail, lexeme)
      case(`M`,`BOOL`) => stackOperation(List(V):::stack.tail, lexeme)
      
      case(`M`,`OPENBRACKET`) => stackOperation(List(V):::stack.tail, lexeme)
      case(`M`,`CLOSEBRACKET`) => stackOperation(stack.tail, lexeme)
      case(`M`,`COMMA`) => stackOperation(stack.tail, lexeme)
      
      case(`K`,`STRING`) => stackOperation(List(STRING):::stack.tail, lexeme)
      case(`K`,`NUMBER`) => stackOperation(List(NUMBER):::stack.tail, lexeme)
      case(`K`,`BOOL`) => stackOperation(List(BOOL):::stack.tail, lexeme)
      
      case (stackSymb,lex) if (stackSymb == lex) => stack.tail
      case _ =>try{
        throw new IllegalStateException("found '"+lexeme.getValue()+"' cannot parse Json, illegal symbol at (l,c):"+lexeme.getLineNumber()+":"+lexeme.getColumnNumber())
      }catch{
        case e:IllegalAccessError => throw new IllegalStateException("found '"+lexeme.getSymbol()+"' cannot parse Json, illegal symbol at (l,c):"+lexeme.getLineNumber()+":"+lexeme.getColumnNumber()+"\n"+e)
      }
    }
  }
  
}