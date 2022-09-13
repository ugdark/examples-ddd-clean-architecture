//package com.example.adaptors.gateways.db.tables.orm
//
//import skinny.orm.SkinnyCRUDMapperWithId
//import skinny.orm.feature.SoftDeleteWithTimestampFeatureWithId
//
///** 論理削除を使用する。
//  * @tparam Id
//  *   id
//  * @tparam Entity
//  *   entity
//  */
//protected[tables] trait CustomCRUDMapperWithIdNoTimestamp[Id, Entity]
//    extends SkinnyCRUDMapperWithId[Id, Entity]
//    with SoftDeleteWithTimestampFeatureWithId[Id, Entity] {
//
//  /** これを宣言することによって、本番のスキマ変更が、検知できます。
//    */
//  override def columns: collection.Seq[String] = super.columns
//
//  override def deletedAtFieldName: String = "deletedAt"
//}
