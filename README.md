# examples-ddd-clean-architecture

自分ならDDDでこうやって実装してみたい的な物  
再学習用

## 次やること

- [x] projectの構成変更 [ADR](docs/src/main/paradox/pages/adr/アーキテクチャについて.md)
- [x] validatorを実装 [ADR](docs/src/main/paradox/pages/adr/入力チェックについて.md)
- [x] repositoryを実装
- [x] useCase実装 
- [ ] controller実装
- [ ] validatorのDBチェック周りをbulkにも対応させたい fieldの調整も
- [ ] testのSupportの乱立に見えるなんかもうちっとまとめたい

色々やりたい事あるけど、とりあえず全体像把握のためにcontroller優先で
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

### docker化して起動(失敗)

```shell
$ sbt api-server/docker:publishLocal
$ docker run -it -p 4649:4649 --rm api-server:1.0.0
```

### migration

```sh
.bin/flyway.sh development migrate 
.bin/flyway.sh test migrate 
```

```sh
.bin/flyway.sh development clean 
.bin/flyway.sh test clean 
```

```sh
.bin/flyway.sh development info 
.bin/flyway.sh test info 
```

### 参考

- [crossroad0201/ddd-on-scala](https://github.com/crossroad0201/ddd-on-scala)
- [crossroad0201/doc](https://speakerdeck.com/crossroad0201/scala-on-ddd)

とりあえずのリンク。まだ参考にしてるわけではない

- [クリーンアーキテクチャ Application Business Rules の役割](https://qiita.com/u-dai/items/f670c3fc5302861aef0b)

試して置きた
https://qiita.com/urano_ryoya/items/bb1cbf245354a23cb967
