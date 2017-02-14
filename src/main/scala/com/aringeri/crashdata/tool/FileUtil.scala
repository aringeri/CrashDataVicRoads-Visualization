package com.aringeri.crashdata.tool

import java.io.File

object FileUtil {
  def trimExtension(fileName: String): String = 
    if (fileName contains '.' ) fileName.take(fileName lastIndexOf "." )
    else fileName
  
  def trimExtension(file: File): String = trimExtension(file.getName)
}