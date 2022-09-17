package com.kokodayo.dodai.validations

/** ModelがすべてOptionalのフィールドを持ってる場合などで親を空として扱うのに使用
  */
object ClassRules {
  def allOptionalParametersIsEmpty(t: Option[Product]): Boolean =
    t.forall(allOptionalParametersIsEmpty)

  def allOptionalParametersIsEmpty(p: Product): Boolean =
    p.productIterator.forall {
      case o: Option[Any] => o.isEmpty
      case _              => false
    }
}
