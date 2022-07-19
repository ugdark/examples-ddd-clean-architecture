//package com.example.adaptors.dbs
//package rds2
//
//import org.scalatest.{BeforeAndAfterEach, EitherValues, fixture}
//import scalikejdbc._
//
//trait TestBase extends fixture.FunSpec with BaseAutoRollback with BeforeAndAfterEach with EitherValues {
//  self: fixture.TestSuite =>
//
//  override implicit def session(implicit ioc: IOContext): DBSession =
//    ioc.asInstanceOf[IOContextOnConnect].session
//
//  override implicit def ioc(implicit session: DBSession): IOContext =
//    IOContextOnConnect(session, ConnectPoolName.Write)
//
//  override def db(): DB = NamedDB(ConnectPoolName.Write.name).toDB
//
//}
