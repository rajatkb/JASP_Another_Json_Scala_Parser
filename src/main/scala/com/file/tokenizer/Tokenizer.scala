package com.file.tokenizer

import scala.io.Source
import java.io.IOException
import java.io.FileNotFoundException
import scala.io.BufferedSource


class Tokenizer(file:Source) {
  
  def getStream() = file.getLines()
                          .zipWithIndex
                          .flatMap {
                              case (line,lineNumber) => {
                                (line+"\n").zipWithIndex.map {
                                  case (char,columnNumber) => new TextToken(char,lineNumber+1,columnNumber+1)
                                }
                              }  
                          }.toStream
  
}