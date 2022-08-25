package com.kokodayo.dodai

/**
  * IDGeneratorの抽象クラス
  * @tparam Id Idの型を明記
  */
trait IdGenerator[Id] {

  def generate: Id
}
