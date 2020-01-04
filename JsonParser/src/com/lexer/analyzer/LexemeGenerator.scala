package com.lexer.analyzer

import com.lexer.traits.SymbolTable
import com.file.tokenizer.TextToken
import com.lexer.traits.Lexeme
import com.lexer.lexicon.StringLexeme
import com.lexer.lexicon.SymbolLexeme
import com.lexer.lexicon.NumberLexeme
import com.lexer.lexicon.BooleanLexeme
import com.lexer.traits.LexemeGeneratorTrait
import com.file.tokenizer.TextToken

class LexemeGenerator(tokens: => Stream[TextToken]) extends LexemeGeneratorTrait(tokens){
  
    val stateS = 0
    val state1 = 1 // string entry
    val state2 = 2 // boolean true
    val state3 = 3 // double number before decimal
    val state4 = 4 // double number after decimal
    val state5 = 5 // double number at e
    val state6 = 6 // double numbar at +
    val state7 = 7 // double number after +
    val state8 = 8 // null values like boolean
    val stateF = -1
    

        
    
     override def getStream() = tokens.scanLeft((stateS,false,new StringBuilder("") , Array[Lexeme](null,null)))((acc,token) => {
      

      
      val (state,output,buffer,prevlexArray) = acc

      (state,token.char) match {
        // Check for json structure symbol
        case (`stateS`,v) if this.checkValidJsonStructureIdentifier(v) => {
            prevlexArray(0) = new SymbolLexeme(v,token.lineNumber , token.columnNumber)
            prevlexArray(1) = null
            (stateS,true,buffer,prevlexArray)
          }
        
        // Check for json string literal
        case (`stateS`, this.DOUBLEQUOTE) => (state1,false,buffer,prevlexArray)
        case (`state1`, this.DOUBLEQUOTE) =>{
            val value = buffer.toString().trim()
            try{ 
              value.toDouble 
              prevlexArray(0) = new NumberLexeme(value,token.lineNumber,token.columnNumber)
              prevlexArray(1) = null    
            }catch{
              case e:NumberFormatException => {
                try{
                  value.toBoolean
                  prevlexArray(0) = new BooleanLexeme(value,token.lineNumber,token.columnNumber)
                  prevlexArray(1) = null
                }catch{
                  case e:Exception => {
                    prevlexArray(0) = new StringLexeme(buffer.toString() , token.lineNumber , token.columnNumber)
                    prevlexArray(1) = null    
                  }
                }
              }
            }

            buffer.clear()
            (stateS,true, buffer, prevlexArray) }
        
        case (`state1`, v) => (state1, false,buffer.append(v), prevlexArray )
        
        // Check for boolean
        case (`state2`,v) if((buffer.toString() == "false" || buffer.toString() == "true") && this.checkValidJsonStructureIdentifier(v))  =>{ 
            prevlexArray(0) = new BooleanLexeme(buffer.toString(),token.lineNumber,token.columnNumber)
            prevlexArray(1) = new SymbolLexeme(v,token.lineNumber , token.columnNumber)
            buffer.clear()
            (stateS,true, buffer , prevlexArray) 
          }
        case (`state2`,v) if(buffer.toString() == "false" || buffer.toString() == "true" ) =>{ 
            prevlexArray(0) = new BooleanLexeme(buffer.toString(),token.lineNumber,token.columnNumber)
            prevlexArray(1) = null
            buffer.clear()
            (stateS,true, buffer , prevlexArray) 
          }
        case (`stateS`,'f') => (state2,false,buffer.append('f'),prevlexArray)
        case (`stateS`,'t') => (state2,false,buffer.append('t'),prevlexArray)
        case (`state2`, b) if(Array('a','l','s','e','r','u').contains(b)) => (state2,false,buffer.append(b),prevlexArray)
        case (`state2`,_) => throw new IllegalStateException("Bad supposed boolean value at (l,n):"+token.lineNumber+","+token.columnNumber)
        
        //Check for null
        case (`state8`, v) if(buffer.toString() == "null" && this.checkValidJsonStructureIdentifier(v)) => {
          prevlexArray(0) = new StringLexeme(null,token.lineNumber,token.columnNumber)
          prevlexArray(1) = new SymbolLexeme(v,token.lineNumber , token.columnNumber)
          buffer.clear()
          (stateS,true, buffer , prevlexArray)
        }
        case (`state8`, v) if(buffer.toString() == "null") => {
          prevlexArray(0) = new StringLexeme(null,token.lineNumber,token.columnNumber)
          prevlexArray(1) = null
          buffer.clear()
          (stateS,true, buffer , prevlexArray)
        }
        case (`stateS`,'n') => (state8,false,buffer.append('n'),prevlexArray)
        case (`state8`, b) if(Array('u','l','l').contains(b)) => (state8,false,buffer.append(b),prevlexArray)
        
        
        // Check for number
        case (`stateS`,v) if (v == '-') => (state3,false,buffer.append(v), prevlexArray)
        case (`stateS`,v) if (v.isDigit) => (state3,false,buffer.append(v),prevlexArray)
        case (`state3`,v) if (v.isDigit) => (state3,false,buffer.append(v),prevlexArray)
        case (`state3`,v) if (v == '.') => (state4,false,buffer.append(v),prevlexArray)
        case (`state4`,v) if (v.isDigit) => (state4,false,buffer.append(v),prevlexArray)
        case (`state4`,v) if (v == 'e') => (state5,false,buffer.append(v),prevlexArray)
        case (`state5`,v) if (v == '+') => (state6,false,buffer.append(v),prevlexArray)
        case (`state6`,v) if (v.isDigit) => (state6,false,buffer.append(v),prevlexArray)
        
        case (`state6`,b) if this.checkValidJsonStructureIdentifier(b) => {
          prevlexArray(0) = new NumberLexeme(buffer.toString().trim(),token.lineNumber,token.columnNumber)
          prevlexArray(1) = new SymbolLexeme(b,token.lineNumber , token.columnNumber)
          buffer.clear()
          (stateS,true,buffer, prevlexArray)
        }
        case (`state3`,b) if this.checkValidJsonStructureIdentifier(b) => {
          prevlexArray(0) = new NumberLexeme(buffer.toString().trim(),token.lineNumber,token.columnNumber)
          prevlexArray(1) = new SymbolLexeme(b,token.lineNumber , token.columnNumber)
          buffer.clear()
          (stateS,true,buffer, prevlexArray)
        }
        case (`state4`,b) if this.checkValidJsonStructureIdentifier(b) => {
          prevlexArray(0) = new NumberLexeme(buffer.toString().trim(),token.lineNumber,token.columnNumber)
          prevlexArray(1) = new SymbolLexeme(b,token.lineNumber , token.columnNumber)
          buffer.clear()
          (stateS,true,buffer, prevlexArray)
        }
        
        case (`state6`,b)  => {
          prevlexArray(0) = new NumberLexeme(buffer.toString().trim(),token.lineNumber,token.columnNumber)
          prevlexArray(1) = null
          buffer.clear()
          (stateS,true,buffer, prevlexArray)
        }
        case (`state3`,b)  => {
          prevlexArray(0) = new NumberLexeme(buffer.toString().trim(),token.lineNumber,token.columnNumber)
          prevlexArray(1) = null
          buffer.clear()
          (stateS,true,buffer, prevlexArray)
        }
        case (`state4`,b)  => {
          prevlexArray(0) = new NumberLexeme(buffer.toString().trim(),token.lineNumber,token.columnNumber)
          prevlexArray(1) = null
          buffer.clear()
          (stateS,true,buffer, prevlexArray)
        }
        
        
        case _ => (state,false,buffer,prevlexArray)
        
      }
      
    })
    .filter { case (state,output,buffer,value) => output }
    .map(f => f._4)
    .flatten
    .filter(p => p != null ).toStream
    
    
    
    
    
    
    
}