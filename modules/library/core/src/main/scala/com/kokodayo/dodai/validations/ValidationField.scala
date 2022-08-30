package com.kokodayo.dodai.validations

/** fieldにjsonのpath等を設定する想定。
  * @param field
  *   String field識別子
  * @param message
  *   String エラーメッセージ
  */
case class ValidationField(field: String, message: String)
