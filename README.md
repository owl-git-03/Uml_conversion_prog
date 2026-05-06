# javaソースコードUMLクラス図変換プログラム
# ライブラリと実行環境
MacOS,eclipse2026-6,javaSE-21,javaparser-core-3.25.10.jar,javaFX-sdk-21.0.8
# VM引数はこれを使いました。環境に合わせてパスを変えてください。
--module-path "/Users/ir/development/javafx-sdk-21.0.8/lib" --add-modules javafx.controls,javafx.fxml
# 実行手順
1.srcのnew_refuctaringのクラスたちをeclipseにインポートしてください
2.javafx をビルドパスしてください
3.VM引数を設定してください
4.main.javaを実行してください
不慣れなため間違っていると思います。もしそうなった場合この手順以外での実行をお願いします。
# 構成
Mainクラス
 別ファイルのソースコードをJavaparserで解析、Visiteパターンを使った情報の取得、自身が設定したクラス構造におけるそれぞれのクラスオブジェクトを生成。クラスオブジェクトをjavaFXにてステージに設定して各オブジェクトのdraw()を呼び出し表示する。
UmlFigureインターフェース
 表示するクラスオブジェクトが集約するもの。Draw()メソッドを持つ。
Class,Field,Methodクラス
 表示する情報の属性と描画部分の関数を持つクラス。コンポジットパターンにおけるリストも持っている。
Carクラス
 今回変換するソースコードのプログラム。車クラスの最低限の定義がある。
