package com.parser.grammer.traits

trait GrammerToken {
  def expand():List[GrammerToken]
}