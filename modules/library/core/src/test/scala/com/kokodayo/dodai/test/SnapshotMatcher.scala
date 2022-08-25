package com.kokodayo.dodai.test

import com.kokodayo.dodai.print.PrettyPrint
import com.typesafe.config.ConfigFactory
import difflib.DiffUtils
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FixtureTestSuite, Outcome, SuiteMixin, TestData}

import java.io.{File, PrintWriter}
import scala.annotation.unused
import scala.io.Source
import scala.jdk.CollectionConverters._
import scala.util.Try

trait DefaultSerializers {
  implicit def anySerializer[T]: SnapshotSerializer[T] = (in: T) => PrettyPrint.print(in)
}

trait SnapshotSerializer[-T] {
  def serialize(@unused in: T): String
}

object SnapshotSerializer {
  def serialize[T](in: T)(implicit s: SnapshotSerializer[T]): String =
    s.serialize(in)
}

private object SnapshotLoader {
  val DefaultSnapshotFolder = "src/test/scala"
}

trait SnapshotLoader {

  import com.kokodayo.dodai.test.SnapshotLoader.DefaultSnapshotFolder

  private val conf = ConfigFactory.load()
  // ここらを外せばconfig依存も解消できる
  private val idePackagePrefix: String =
    Try {
      val packagePrefix = conf.getString("snapshotmatcher.idePackagePrefix")
      if (packagePrefix.isEmpty) "" else s"$packagePrefix."
    }.getOrElse("")

  // private val testFolder = getClass.getName.replaceAll("\\.", "/")
  // Name=>PackageName, idePrefix対応
  private val testFolder = getClass.getPackageName
    .replace(idePackagePrefix, "")
    .replaceAll("\\.", "/")

  private val snapshotFolder =
    Try(conf.getString("snapshotmatcher.snapshotFolder")).toOption
      .getOrElse(DefaultSnapshotFolder)
  //  private val fullWritePath = s"${getClass.getResource("").getPath.split("target").head}$snapshotFolder/$testFolder"
  // idePackagePrefix分を除外しないといけない
  private val fullWritePath =
    s"${getClass.getResource("").getPath.split("target").head}$snapshotFolder/$testFolder/__snapshots__"

  private def folderPath: String = new File(s"$fullWritePath").getAbsolutePath

  //  private def filePath(id: String): String = new File(s"$folderPath/$id.snap").getAbsolutePath
  private def filePath(id: String): String =
    new File(
      s"$folderPath/${getClass.getName.split("\\.").last}-$id.snap"
    ).getAbsolutePath

  def loadSnapshot(id: String): Option[String] =
    Try {
      val source = Source.fromFile(filePath(id))
      try source.mkString
      finally source.close()
    }.toOption

  def writeSnapshot[T](id: String, content: T, isEscape: Boolean = true)(implicit
      s: SnapshotSerializer[T]
  ): Unit = {
    new File(folderPath).mkdirs()

    val file = new File(filePath(id))
    new PrintWriter(file) {
      write(if (isEscape) s.serialize(content) else content.toString)
      close()
    }
  }
}

trait TestDataArgs extends SuiteMixin {
  this: FixtureTestSuite =>
  type FixtureParam = TestData

  override def withFixture(test: OneArgTest): Outcome =
    withFixture(test.toNoArgTest(test))
}

trait TestDataEnhancer {
  implicit class TestDataEnhancer(in: TestData) {
    def key: String = in.name.replaceAll("[^A-Za-z0-9]", "-").toLowerCase()
  }
}

trait SnapshotMessages {
  val ContentsAreEqual = "Contents Are Equal"
  val DefaultError     = "Contents Are Different"

  def snapshotPreventedError(key: String) =
    s"Snapshot [$key] was not generated due to Environment flag."

  def errorMessage(current: String, found: String): String = {
    val patch = DiffUtils.diff(
      found.split("\n").toList.asJava,
      current.split("\n").toList.asJava
    )
    val diff = DiffUtils
      .generateUnifiedDiff(
        "Original Snapshot",
        "New Snapshot",
        found.split("\n").toList.asJava,
        patch,
        10
      )
      .asScala
    val parsedLines = diff.map { line =>
      if (line.startsWith("+"))
        s"${Console.GREEN}$line${Console.RESET}"
      else if (line.startsWith("-"))
        s"${Console.RED}$line${Console.RESET}"
      else
        s"${Console.WHITE}$line${Console.RESET}"
    }

    s"""|Text Did not match:
        |${parsedLines.mkString("\n")}
        |
        |End Diff;""".stripMargin
  }
}

trait SnapshotMatcher extends SnapshotLoader with SnapshotMessages with TestDataArgs with DefaultSerializers {
  self: FixtureTestSuite =>

  private var testMap: Map[String, Int] = Map.empty
  private val ShouldGenerateSnapshot =
    sys.env.getOrElse("UPDATE_SNAPSHOT_TEST", "false").toBoolean

  private def getCurrentAndSetNext(id: String, isExplicit: Boolean): String = {
    val next = testMap.getOrElse(id, 0) + 1
    testMap += (id -> next)
    val current = next - 1
    if (current == 0) id
    else if (!isExplicit) s"$id-$current"
    else
      throw new Exception(
        "Cannot reuse snapshot for explicit identifier. There should be only a single snapshot match"
      )
  }

  class SnapshotShouldMatch[T](
      explicitId: Option[String],
      isEscape: Boolean = true
  )(implicit s: SnapshotSerializer[T], test: TestData)
      extends Matcher[T]
      with TestDataEnhancer {
    override def apply(left: T): MatchResult = {
      // val testIdentifier = getCurrentAndSetNext(explicitId.getOrElse(test.key), isExplicit = explicitId.nonEmpty)
      // 日本語対応するため
      val testIdentifier = getCurrentAndSetNext(
        explicitId.getOrElse(test.name),
        isExplicit = explicitId.nonEmpty
      )
      loadSnapshot(testIdentifier) match {
        case Some(content) if isEqual(content, left, isEscape) =>
          MatchResult(matches = true, DefaultError, ContentsAreEqual)
        case Some(_) if ShouldGenerateSnapshot =>
          println(s"${Console.YELLOW} ### Updating Snapshots: ${test.name} ###")
          writeSnapshot(testIdentifier, left, isEscape)
          MatchResult(matches = true, DefaultError, ContentsAreEqual)
        case Some(content) =>
          MatchResult(
            matches = false,
            errorMessage(if (isEscape) s.serialize(left) else left.toString, content),
            ContentsAreEqual
          )
        case None => // first time / new test
          writeSnapshot(testIdentifier, left, isEscape)
          MatchResult(matches = true, DefaultError, ContentsAreEqual)
      }
    }

    private def isEqual(content: String, left: T, isEscape: Boolean): Boolean =
      if (isEscape) s.serialize(left) == content
      else left.toString == content
  }

  def matchSnapshot[T]()(implicit
      s: SnapshotSerializer[T],
      test: TestData
  ): Matcher[T] =
    new SnapshotShouldMatch[T](None)

  // ファイル名を変更したい場合に使用
  def matchSnapshot[T](
      explicitId: String
  )(implicit s: SnapshotSerializer[T], test: TestData): Matcher[T] =
    new SnapshotShouldMatch[T](Option(explicitId))

  def matchSnapshotNoEscape[T]()(implicit
      s: SnapshotSerializer[T],
      test: TestData
  ): Matcher[T] =
    new SnapshotShouldMatch[T](None, isEscape = false)

  // ファイル名を変更したい場合に使用
  def matchSnapshotNoEscape[T](
      explicitId: String
  )(implicit s: SnapshotSerializer[T], test: TestData): Matcher[T] =
    new SnapshotShouldMatch[T](Option(explicitId), isEscape = false)

}
