package com.json.traits

trait JsonUnit {
  override def toString():String
  def getValue():Any
  def copy(a:Any):JsonUnit
}