# 実装rule

## require exception, Errorクラスについて

require, exceptionこちらは例外 実装者が想定していないErrorになるため
exceptionは基底classでcatchする以外は基本的にスルーする実装とする。

requireを元に入力チェックを行いたい場合は改めてErrorClassを用意してそちらでハンドリングする
