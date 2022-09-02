# AnyValと契約プログラミングについて

## Status
採用

## Context
未着手


## Decision

ValueObjectはAnyValを使えとあるので下記で記載する。
```scala
case class Name(value: String) extends AnyVal
```

ここまではいいのだけど、AnyVal+契約プログラミングっぽくすると
```scala
case class Name(value: String) extends AnyVal {
  require(value.length <= 10)
}
```

```
[error] .../modules/domain/src/main/scala/com/example/domain/user/Hoge.scala:4:10: this statement is not allowed in value class
[error]   require(value.length <= 10)
[error]          ^
[error] one error found
```

こげな感じでerrorになる。

両立させるなら

```scala
case class Name(value: String) extends AnyVal

object Name extends (String => Name) {
  def apply(value: String): Name = {
    require(value.length <= 10)
    new Name(value)
  }
}
```

って感じになるのかな。 `extends (String => Name)`を入れないと

`Some("a").map(Name)`がerrorとなるので`Some("a").map(Name(_))`と書かなければいけなくなる。

個人的には入れる方向かな。

## Consequences

結果
