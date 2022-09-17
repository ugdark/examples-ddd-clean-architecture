# rule

## require exception, Errorクラスについて

require, exceptionこちらは例外 実装者が想定していないErrorになるため
exceptionは基底classでcatchする以外は基本的にスルーする実装とする。

requireを元に入力チェックを行いたい場合は改めてErrorClassを用意してそちらでハンドリングする

## クラスの名前

基本的にはsuffixで統一する。

- XXXSpec テスト用の機能Baseクラス
- XXXTest テストクラス
- XXXUseCase ユースケース
- XXXRepository 永続化
- XXXValidator 検証クラス
- XXXFixture テスト用の共通Object
- XXXTestSupport テスト用の共通trait 注入を簡略化


極力共通のtraitというかInterfaceを実装する用にしてる

ファイル名が小文字で始まるpackageと同じファイルは色々クラスを混ぜ込んでる。packageに書くと今は警告でるんで。

