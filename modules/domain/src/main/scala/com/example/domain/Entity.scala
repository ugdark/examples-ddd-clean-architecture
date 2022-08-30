package com.example.domain

trait Entity[ID <: EntityId] {
  val id: ID
  val metaData: EntityMetaData

  // NOTE: 型 と ID でエンティティの同一性を判断します。
  override def equals(obj: Any): Boolean =
    obj match {
      case that: Entity[_] => this.getClass == that.getClass && this.id == that.id
      case _               => false
    }

  /* やっぱり値全比較も復活しておきたいので */
  def equalsValue(obj: Any): Boolean = super.equals(obj)

  override def hashCode(): Int = 31 + id.hashCode

}
