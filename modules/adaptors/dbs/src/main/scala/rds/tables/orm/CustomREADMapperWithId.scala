package com.example.adaptors.dbs
package rds.tables.orm

import skinny.orm.SkinnyMapperWithId

/**
  * CRUDではなくREADOnlyに限定用と定義しておきます。
  * @tparam Id id
  * @tparam Entity entity
  */
protected[tables] trait CustomREADMapperWithId[Id, Entity] extends SkinnyMapperWithId[Id, Entity]
