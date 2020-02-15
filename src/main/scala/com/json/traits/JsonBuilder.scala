package com.json.traits

abstract class JsonBuilder(factory:JsonFactory) {
  

  def pushS() // push a JsonMap creation intent
  def pushP() // push a key value pair intent
  def pushL() // push more upcoming key value pair intent (recursively parses value)
  def pushA() // push more upcoming array value intent
  def pushVAS() // push Array value start
  def pushVAE() // push Array value end
  def pushVSS() // push JsonMap value start
  def pushVSE() // push JsonMap value end
  // push key value
  def pushK(str: String) 
  def pushV(str: String) 
  def pushK(num: Double) 
  def pushV(num: Double)
  def pushK(b:Boolean)
  def pushV(b:Boolean)
  // return the final product
  def build():JsonMap
}