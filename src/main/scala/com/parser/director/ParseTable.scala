package com.parser.director

import scala.annotation.tailrec
import com.lexer.traits.Lexeme
import com.lexer.traits.SymbolTable
import com.json.traits.JsonBuilder

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
   
  
  @tailrec final def stackOperation(stack:List[Char],lexeme:Lexeme , builder:JsonBuilder):List[Char] = {
    
    (stack.head , lexeme.getSymbol()) match {
      case(`S`,`OPENBRACE`) =>  builder.pushS() ;stackOperation(OPENBRACE :: B :: CLOSEBRACE ::stack.tail, lexeme , builder)
      case(`B`,`CLOSEBRACE`) => stackOperation(stack.tail, lexeme , builder)
      
      case(`B`,`STRING`) => stackOperation(T:: L ::stack.tail, lexeme , builder)
      case(`B`,`NUMBER`) => stackOperation(T :: L ::stack.tail, lexeme , builder)
      case(`B`,`BOOL`) => stackOperation( T :: L ::stack.tail, lexeme , builder)
      
      // since when the object is ending the L symbol needs to be added 
      case(`L`,`CLOSEBRACE`) => builder.pushL();builder.pushVSE(); stackOperation(stack.tail, lexeme , builder)
      case(`L`,`COMMA`) => builder.pushL(); stackOperation(COMMA :: B ::stack.tail, lexeme , builder)
      
      case(`T`,`STRING`) => builder.pushP(); stackOperation( K :: COLON :: V ::stack.tail, lexeme , builder)
      case(`T`,`NUMBER`) => builder.pushP(); stackOperation(K :: COLON :: V ::stack.tail, lexeme , builder)
      case(`T`,`BOOL`) => builder.pushP(); stackOperation(K :: COLON :: V ::stack.tail, lexeme , builder)
      
      case(`V`,`OPENBRACE`) => builder.pushVSS();stackOperation( S :: stack.tail, lexeme , builder)
      
      case(`V`,`STRING`) => builder.pushV(lexeme.getString()); stackOperation( STRING ::stack.tail, lexeme, builder)
      case(`V`,`NUMBER`) => builder.pushV(lexeme.getDouble()); stackOperation( NUMBER ::stack.tail, lexeme , builder)
      case(`V`,`BOOL`) => builder.pushV(lexeme.getBoolean()); stackOperation( BOOL ::stack.tail, lexeme , builder)
      
      case(`V`,`OPENBRACKET`) => builder.pushVAS(); builder.pushA(); stackOperation( OPENBRACKET :: M :: E ::CLOSEBRACKET ::stack.tail, lexeme , builder)
      case(`E`,`CLOSEBRACKET`) => builder.pushVAE(); stackOperation(stack.tail, lexeme , builder)
      case(`E`,`COMMA`) => builder.pushA(); stackOperation( COMMA :: M :: E ::stack.tail, lexeme , builder)
      case(`M`,`OPENBRACE`) => stackOperation(V ::stack.tail, lexeme , builder)
      
      case(`M`,`STRING`) => stackOperation( V ::stack.tail, lexeme , builder)
      case(`M`,`NUMBER`) => stackOperation( V ::stack.tail, lexeme , builder)
      case(`M`,`BOOL`) => stackOperation( V ::stack.tail, lexeme , builder)
      
      case(`M`,`OPENBRACKET`) => stackOperation( V ::stack.tail, lexeme , builder)
      case(`M`,`CLOSEBRACKET`) => stackOperation(stack.tail, lexeme , builder)
      case(`M`,`COMMA`) => builder.pushV(null:String); stackOperation(stack.tail, lexeme , builder)
      
      case(`K`,`STRING`) =>builder.pushK(lexeme.getString()); stackOperation( STRING ::stack.tail, lexeme , builder)
      case(`K`,`NUMBER`) =>builder.pushK(lexeme.getDouble()); stackOperation( NUMBER ::stack.tail, lexeme , builder)
      case(`K`,`BOOL`) => builder.pushK(lexeme.getBoolean()); stackOperation( BOOL ::stack.tail, lexeme , builder)
      
      case (stackSymb,lex) if (stackSymb == lex) => stack.tail
      case _ =>try{
        throw new IllegalStateException(s"Look ahead or action for $stack.head not found on $lexeme cannot parse Json, illegal symbol at (l,c): ${lexeme.getLineNumber()} , ${lexeme.getColumnNumber()}")
      }catch{
        case e:IllegalAccessError => throw new IllegalStateException(s"found ${lexeme.getSymbol()} cannot parse Json, illegal symbol at (l,c):${lexeme.getLineNumber()} ${lexeme.getColumnNumber()} : $e")
      }
    }
  }
  
}