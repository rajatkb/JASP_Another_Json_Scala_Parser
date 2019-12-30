package com.file.tokenizer

import scala.io.Source
import java.io.IOException
import com.logger.Logger
import java.io.FileNotFoundException

class Tokenizer(filename:String) {
  
  val reader = try{
    Source.fromFile(filename)
  }catch{
    case e:IOException => Logger.error("Could not read file"); throw e 
    case e:FileNotFoundException => Logger.error("Could not find the file"); throw e
  }
  
  def getStream() = reader.getLines()
                          .zipWithIndex.toStream
                          .flatMap {
                              case (line,lineNumber) => {
                                (line+"\n").zipWithIndex.toStream.map {
                                  case (char,columnNumber) => new TextToken(char,lineNumber+1,columnNumber+1)
                                }
                              }  
                          }
  
}