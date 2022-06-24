package com.example.domain
package problem

/**
  * @param value タイトル
  */
case class ProblemTitle(value: String) {
  require(value.nonEmpty && value.length <= 255)
}

/**
  * @param value 内容
  */
case class ProblemStatement(value: String) {
  require(value.nonEmpty && value.length <= 2000)
}

case class ProblemId(value: Long) extends IdentifierLong

case class problem(
    id: ProblemId,
    title: ProblemTitle,
    statement: ProblemStatement,
    override val createdAt: Option[CreatedAt] = None,
    override val updatedAt: Option[UpdatedAt] = None
) extends EntityTimestamp
