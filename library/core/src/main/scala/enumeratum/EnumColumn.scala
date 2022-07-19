package com.kokodayo.dodai
package enumeratum

/**
  * textはカスタムする場合はlabel同様にoverride して利用してください。
  * textの用途はAPIで数字でやりとりをせずにtextでやる(※他社のAPIなども参考にしてます)事を目的としています。
  * プログラム内部で使う事を想定していません。
  *
 * labelは数字を表示する際にわざわざ利用側で変換テーブルを持たなくていいように設計してる。
  * また日本語の意味がわかりやすいのもあり実装してる。
  *
 * apiですべてのenumは公開する設計にする。利用側でcacheして使って貰えるといい。
  */
trait EnumColumn {
  // DB側のEnumの数字用途、またはAPIで直接送られる
  val id: Int
  // Presenter層での数字以外のkeywordになる。基本的にはclass名と依存させる。
  val keyword: String = getClass.getSimpleName.replace("$", "")
  // 表示側で使う用途
  val name: String = ""
}
