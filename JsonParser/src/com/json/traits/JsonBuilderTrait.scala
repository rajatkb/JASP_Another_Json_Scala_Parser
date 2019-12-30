package com.json.traits

abstract class JsonBuilderTrait(factory:JsonFactory) {
  

  def pushS()
  def pushP()
  def pushL() 
  def pushA() 
  def pushVAS()
  def pushVAE()
  def pushVSS()
  def pushVSE()
  def pushK(str: String)
  def pushV(str: String)
  def pushK(num: Double)
  def pushV(num: Double)
  def pushK(b:Boolean)
  def pushV(b:Boolean)
  def build():JsonMapTrait
}