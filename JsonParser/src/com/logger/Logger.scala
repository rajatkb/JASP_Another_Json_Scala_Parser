package com.logger

object Logger {
  var logging = false
  var infol = false
  var debugl = false
  var timing = false
  def info(m:String) = if(logging && infol) println("[INFO]:"+m)
  def error(m:String) = println("[ERROR]:"+m)
  def debug(m:String) = if(logging && debugl) println("[DEBUG]:"+m)
  def timer[R](block: => R): R = {
    if(timing) {
          val t0 = System.nanoTime()
          val result = block    // call-by-name
          val t1 = System.nanoTime()
          println("Elapsed time: " + (t1 - t0).toFloat/1000000000 + "s")
          result
      }
    else
      block
  }
}