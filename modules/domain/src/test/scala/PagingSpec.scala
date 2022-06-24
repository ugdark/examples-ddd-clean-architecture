package com.example.domain

import org.scalatest.funspec.AnyFunSpec

class PagingSpec extends AnyFunSpec {

  describe("Pagingを確認") {

    it("from, until, toを返す") {
      val paging = Paging(2, 1000)
      assert(paging.from == 1000)
      assert(paging.until == 1999)
      assert(paging.to == 2000)
    }
  }
}
