package com.json.traits

trait JsonWriteable {
  def getStringStream():Stream[String]
}