package com.file.tokenizer

import scala.io.Source
import java.io.IOException
import java.io.FileNotFoundException
import scala.io.BufferedSource


class Tokenizer(file:Source) {
  
  
  def getStream() = file.getLines().scanLeft((-1,""))( (acc , line) => (acc._1+1 , line)).drop(1)
                          .flatMap {
                              case (lineNumber , line) => {
                                (line+"\n").scanLeft((-1,null:TextToken)) {
                                  case (acc , char) => (acc._1+1 , new TextToken(char,lineNumber+1,acc._1+1))
                                }.drop(1)
                              }  
                          }.map(f => f._2)
  
}