package com.json.traits

trait JsonWriteable {
  def toStream():Stream[String]
}