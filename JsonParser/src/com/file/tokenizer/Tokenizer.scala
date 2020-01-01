package com.file.tokenizer

import scala.io.Source
import java.io.IOException
import com.logger.Logger
import java.io.FileNotFoundException
import scala.io.BufferedSource


class Tokenizer(file:Source) {
  
  def getStream() = file.getLines().toStream
                          .zipWithIndex.toStream
                          .flatMap {
                              case (line,lineNumber) => {
                                (line+"\n").view.zipWithIndex.toStream.map {
                                  case (char,columnNumber) => new TextToken(char,lineNumber+1,columnNumber+1)
                                }
                              }  
                          }
  
}