//package com.example.adaptors.gateways.db.tables.orm
//
//import scalikejdbc._
//import skinny.orm.SkinnyCRUDMapperWithId
//import skinny.orm.feature.{
//  CRUDFeatureWithId,
//  OptimisticLockWithTimestampFeatureWithId,
//  SoftDeleteWithTimestampFeatureWithId
//}
//
//import java.time.ZonedDateTime
//
///** 楽観ロック, 論理削除を使用する。
//  * @tparam Id
//  *   id
//  * @tparam Entity
//  *   entity
//  */
//protected[tables] trait CustomCRUDMapperWithId[Id, Entity]
//    extends SkinnyCRUDMapperWithId[Id, Entity]
//    with OptimisticLockWithTimestampFeatureWithId[Id, Entity]
//    with SoftDeleteWithTimestampFeatureWithId[Id, Entity] {
//
//  /** これを宣言することによって、本番のスキーマ変更が、検知できます。
//    */
//  override def columns: collection.Seq[String] = super.columns
//
//  override def lockTimestampFieldName: String = "updatedAt"
//
//  override def updateBy(where: SQLSyntax): UpdateOperationBuilder =
//    new UpdateOperationBuilderWithVersion(this, where)
//
//  /** 　OptimisticLockWithTimestampFeatureWithIdを参考に変更した 通常はsqls.currentTimestampを使用していた Update query
//    * builder/executor.
//    * @param mapper
//    *   mapper
//    * @param where
//    *   condition
//    */
//  class UpdateOperationBuilderWithVersion(mapper: CRUDFeatureWithId[Id, Entity], where: SQLSyntax)
//      extends UpdateOperationBuilder(
//        mapper = mapper,
//        where = where,
//        beforeHandlers = beforeUpdateByHandlers.toIndexedSeq,
//        afterHandlers = afterUpdateByHandlers.toIndexedSeq
//      ) {
//
//    private[this] val c = defaultAlias.support.column.field(lockTimestampFieldName)
//    addUpdateSQLPart(sqls"$c = ${ZonedDateTime.now()}")
//  }
//
//  override def deletedAtFieldName: String = "deletedAt"
//
//  // countを使うとwhereが空になるのでactiveならこっちが必要になる
//  def countByDeletedAtIsNull()(implicit s: DBSession = autoSession): Long =
//    withSQL {
//      countQueryWithAssociations.where(defaultScopeWithDefaultAlias)
//    }.map(_.long(1)).single.apply().getOrElse(0L)
//
//  // 削除された物をcount
//  def countByDeletedAtIsNotNull()(implicit s: DBSession = autoSession): Long =
//    withSQL {
//      countQueryWithAssociations.where(sqls.isNotNull(column.field(deletedAtFieldName)))
//    }.map(_.long(1)).single.apply().getOrElse(0L)
//}
