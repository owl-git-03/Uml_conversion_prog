package new_refactoring_uml_package;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
class SomeVisitor extends VoidVisitorAdapter<Void> {// if分岐しなくてもNodeクラスを指定することでその情報をとることができる。
	ArrayList<UmlFigure> list = new ArrayList<UmlFigure>();
	Class class1;
	@Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {// classの情報を取得
//		class1 = new Class(n.getNameAsString());
//        super.visit(n, arg);
//        list.add(class1);
//        class1 = null;
        //
        String className = n.getNameAsString();
        if (!n.getExtendedTypes().isEmpty()) {
            String parentName = n.getExtendedTypes().get(0).getNameAsString();
            class1 = new Class(className, parentName);
        } else {
            class1 = new Class(className);
        }
        super.visit(n, arg);
        list.add(class1);
        class1 = null;
    }
    @Override
    public void visit(MethodDeclaration md, Void arg) {// methodの情報を取得
        super.visit(md, arg);
        for(var e: md.getModifiers()) {
            	class1.add(new Method(""+md.getName(),""+md.getType(),e.toString().trim()));
            }
        }
    @Override
    public void visit(Modifier md, Void arg) {
        super.visit(md, arg);
    }
    @Override
    public void visit(FieldDeclaration n, Void arg) {// feildの情報取得
        super.visit(n, arg);
        String value;
        for(var e: n.getModifiers()) {
        	for(var i :n.getVariables()) {
            	var variable_num = i.getInitializer();// optionalなので値を取り出す　初期値
            	if(variable_num.isPresent()) {
            		 value = variable_num.get().toString();
            		 class1.add( new Feild(""+i.getName(),i.getType().asString(),e.toString().trim(),value));
            	}else {
            		 class1.add(new Feild(""+i.getName(),""+i.getType().asString(),e.toString().trim()));
            	}
            }
        }
    }
    
    public static void showAllBPS(Node unit,int level) {
    	System.out.println(" ".repeat(level)+"┗トークン："+unit.getMetaModel().getTypeName());
    	System.out.println(" ".repeat(level)+"┗値："+unit.toString());
    	for(var e: unit.getChildNodes()) {
    		showAllBPS(e,level+1);
    	}
    	
    }
}

public class Main extends Application{
	static CompilationUnit unit;
	static double w;
	// オブジェクトの生成
	static ArrayList<UmlFigure> uml_figures = new ArrayList<UmlFigure>();
	@Override
    public void start(Stage primaryStage) {// JACAFXを動かし描画する。描画自体はオブジェクトごとのdrawメソッドから
		primaryStage.setMaximized(true);
		GridPane gp = new GridPane();
		int gap = 30;// 垂直に並べる時のもの
        gp.setHgap(gap); 
	    gp.setVgap(gap);
	    int col = 0;
	    int row = 0;
	    int maxCol = 5;
		for(UmlFigure e: uml_figures) {// nodeオブジェクトを受け取って垂直に配置する
			gp.add(e.draw(), col, row);
		    col++;
		    if (col >= maxCol) { // 端まで来たら
		        col = 0;         // 左端に戻って
		        row++;           // 次の行へ
		    }
		}
		Rectangle2D d = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(gp,d.getWidth(), d.getHeight());
		primaryStage.setScene(scene);
		primaryStage.setTitle("ソースコード→UML");
		primaryStage.show();
		
	}
	public static void getAst() {// ファイルからjavaparserを呼び出しオブジェクトを生成する。
		Path source = Paths.get("src/new_refactoring_uml_package/Car.java");
		try {
			unit = StaticJavaParser.parse(source);
            SomeVisitor visitor = new SomeVisitor();
            unit.accept(visitor, null); 
            SomeVisitor.showAllBPS(unit,0);
            for(var e :visitor.list) {
            	uml_figures.add(e);
            	System.out.println("classlists:"+e);
            }
          //BufferedWriterで文字をバッファリングすることによって、文字の入出力を効率化させる。BufferedWriterを使わないと文字をprint()で呼び出して、変換して、書き込んで…という作業が効率悪くなるらしいです。

          //そのままCSV化すると文字化けするのでOutputStreamWriterでエンコーディングする。

          //ファイル名を指定して、上書きモード(false)か、追記モード(True)かを選択する。
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	public static void main(String[] args) {
		//Canvas.show();
		getAst();//抽象構文木を表示する。
        System.out.println("***********************************************");
        System.out.println(unit);
        System.out.println("***********************************************");
        launch(args);
	}
}
