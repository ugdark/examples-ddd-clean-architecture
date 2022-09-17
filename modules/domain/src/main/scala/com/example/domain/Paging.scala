package com.example.domain

case class Paging(page: Page = Page(1), limit: Limit = Limit(1000)) {

  val offset: Int = (page.value - 1) * limit.value

  val from: Int = offset

  val to: Int = offset + limit.value

  val until: Int = page.value * limit.value - 1

}

object Paging {
  // 基本全件取る用
  val All: Paging = Paging(Page(Int.MaxValue), Limit(10000))

  def apply(page: Int, limit: Int): Paging =
    new Paging(Page(page), Limit(limit))
}

case class Page(value: Int) {
  require(value > 0)
  require(value <= Int.MaxValue)
}

case class Limit(value: Int) {
  require(value > 0)
  require(value <= 10000) // とりあえずの10000
}
