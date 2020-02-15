package com.lexer.analyzer

import com.file.tokenizer.TextToken
import com.lexer.lexicon.BooleanLexeme
import com.lexer.lexicon.NumberLexeme
import com.lexer.lexicon.StringLexeme
import com.lexer.lexicon.SymbolLexeme
import com.lexer.traits.Lexeme
import com.lexer.traits.LexemeGen
import com.lexer.traits.SymbolTable

class LexemeGenerator(tokens:Iterator[TextToken]) extends LexemeGen(tokens){

    
    
    
    
    private trait State extends SymbolTable{
      
      protected def cleanBuffer(buffer:StringBuilder) :(String, StringBuilder) = {
        val string = buffer.toString()
        buffer.clear()
        (string , buffer)
      }
      def apply(token:TextToken , buffer:StringBuilder):(State,StringBuilder,List[Lexeme]) 
    }
    
    
    private case object startState extends State {
        override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
          
          case this.DOUBLEQUOTE => (quoteState ,buffer , Nil )
          case v if this.checkValidJsonStructureIdentifier(v) => (this , buffer , new SymbolLexeme(v , token.lineNumber , token.columnNumber) :: Nil)
          case 'f' => (fState , buffer , Nil)
          case 't' => (tState , buffer , Nil)
          case 'n' => (nState , buffer , Nil)
          case '+' => (numberState , buffer.append('+') , Nil)
          case '-' => (numberState , buffer.append('-') , Nil)
          case v if v.isDigit => (numberState , buffer.append(v) , Nil)
          case _ => (startState , buffer , Nil)
        }
    }
    
    private case object numberState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case v if v.isDigit => (numberState , buffer.append(v) , Nil)
        case v if this.checkValidJsonStructureIdentifier(v) => {
          val (string , b) = this.cleanBuffer(buffer)
          (startState , b ,new NumberLexeme(string.toDouble , token.lineNumber , token.columnNumber) :: new SymbolLexeme(v , token.lineNumber , token.columnNumber) :: Nil)
        }
        case '.' => (afterDecimalState , buffer.append('.') , Nil)
        case _ => {
          val (string , b) = this.cleanBuffer(buffer)
          (startState , b ,new NumberLexeme(string.toDouble , token.lineNumber , token.columnNumber) :: Nil)
        } 
      }
    }
    
    private case object afterDecimalState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case v if v.isDigit => (afterDecimalState , buffer.append(v) , Nil)
        case v if this.checkValidJsonStructureIdentifier(v) => {
          val (string , b) = this.cleanBuffer(buffer)
          (startState , b ,new NumberLexeme(string.toDouble , token.lineNumber , token.columnNumber) :: new SymbolLexeme(v , token.lineNumber , token.columnNumber) :: Nil)
        }
        case 'e' => (exponentState , buffer.append('e') , Nil)
        case 'E' => (exponentState , buffer.append('E') , Nil)
        case _ => {
          val (string , b) = this.cleanBuffer(buffer)
          (startState , b ,new NumberLexeme(string.toDouble , token.lineNumber , token.columnNumber) :: Nil)
        }
      }
    }
    
    private case object exponentState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case '+' => (afterExponentState , buffer.append('+') , Nil)
        case '-' => (afterExponentState , buffer.append('-') , Nil)
        case v if v.isDigit => (afterExponentState , buffer.append(v) , Nil)
        case _ => throw new IllegalStateException(s"Bad number literal at line: ${token.lineNumber} column:${token.columnNumber}")
      }
    }
    
    
    private case object afterExponentState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case v if v.isDigit => (numberState , buffer.append(v) , Nil)
        case v if this.checkValidJsonStructureIdentifier(v) => {
          val (string , b) = this.cleanBuffer(buffer)
          (startState , b ,new NumberLexeme(string.toDouble , token.lineNumber , token.columnNumber) :: new SymbolLexeme(v , token.lineNumber , token.columnNumber) :: Nil)
        }
        case _ => {
          val (string , b) = this.cleanBuffer(buffer)
          (startState , b ,new NumberLexeme(string.toDouble , token.lineNumber , token.columnNumber) :: Nil)
        }
      }
    }
    
    
    
    private case object quoteState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case this.BACKSLASH => (backSlashState , buffer , Nil)
        case this.DOUBLEQUOTE => {
          val (string , b) = this.cleanBuffer(buffer)
          (startState, b ,  new StringLexeme(string , token.lineNumber , token.columnNumber) ::Nil)
        }
        case v => {
          (quoteState , buffer.append(v) , Nil)
        }
      }
    }
    
    private case object backSlashState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
          case '"' => (quoteState, buffer.append('\"') , Nil)
          case '\\' => (quoteState, buffer.append('\\') , Nil)
          case '/' => (quoteState, buffer.append('/') , Nil)
          case 'b' => (quoteState, buffer.append('\b') , Nil)
          case 'f' => (quoteState, buffer.append('\f') , Nil)
          case 'n' => (quoteState , buffer.append('\n') , Nil)
          case 'r' => (quoteState , buffer.append('\r') , Nil)
          case 't' => (quoteState, buffer.append('\t') , Nil)
          case v   => throw new IllegalStateException(s" Bad character encountered at line: ${token.lineNumber} , column: ${token.columnNumber} , found $v")
      }
    }
    
    
    private case object tState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 'r' => (rState , buffer , Nil)
        case _ => throw new IllegalStateException(s"Bad supposed boolean value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    private case object rState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 'u' => (uState , buffer , Nil)
        case _ => throw new IllegalStateException(s"Bad supposed boolean value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    private case object uState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 'e' => (startState , buffer , new BooleanLexeme(true,token.lineNumber,token.columnNumber) :: Nil)
        case 'l' => (lState , buffer , Nil)
        case _ => throw new IllegalStateException(s"Bad supposed boolean value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    private case object fState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 'a' => (aState, buffer , Nil)
        case _ => throw new IllegalStateException(s"Bad supposed boolean value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    private case object aState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 'l' => (lState , buffer , Nil)
        case _ => throw new IllegalStateException(s"Bad supposed boolean value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    private case object lState extends State {
       override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 's' => (sState , buffer , Nil)
        case 'l' => (startState , buffer , new StringLexeme(null,token.lineNumber,token.columnNumber) :: Nil)
        case _ => throw new IllegalStateException(s"Bad supposed boolean value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    private case object sState extends State {
       override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 'e' => (startState , buffer , new BooleanLexeme(false,token.lineNumber,token.columnNumber) :: Nil)
        case _ => throw new IllegalStateException(s"Bad supposed boolean value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    private case object nState extends State {
      override def apply(token:TextToken , buffer:StringBuilder) = token.char match {
        case 'u' => (uState , buffer , Nil)
        case _ => throw new IllegalStateException(s"Bad supposed null value at line: ${token.lineNumber}, column: ${token.columnNumber}")
      }
    }
    
    
    
    
    
    private val stringBuffer = new StringBuilder("")
    
    
    
    override def getStream() = tokens.scanLeft((startState:State, stringBuffer, List[Lexeme]()))( ( acc , token) => {
        val (state , buffer , _) = acc
        state(token , buffer)
    }).map(f => f._3).flatten
    
    

        
}