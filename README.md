# examples-ddd-clean-architecture

## 次やること
- [x] projectの構成変更 [ADR](docs/src/main/paradox/pages/adr/アーキテクチャについて.md)
- [ ] validatorを実装　[ADR](docs/src/main/paradox/pages/adr/入力チェックについて.md)

## 忘れそうなんで

### format 

```shell
$ sbt format
```

### docker

- mysql 8
```shell
$ docker-compose up -d
```
### migration

```sh
./bin/flyway.sh development migrate 
./bin/flyway.sh test migrate 
```

```sh
./bin/flyway.sh development clean 
./bin/flyway.sh test clean 
```

```sh
./bin/flyway.sh development info 
./bin/flyway.sh test info 
```


### 参考

- [crossroad0201/ddd-on-scala](https://github.com/crossroad0201/ddd-on-scala)
- [crossroad0201/doc](https://speakerdeck.com/crossroad0201/scala-on-ddd)

とりあえずのリンク。まだ参考にしてるわけではない
- [クリーンアーキテクチャ Application Business Rules の役割](https://qiita.com/u-dai/items/f670c3fc5302861aef0b)
