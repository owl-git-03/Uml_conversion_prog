package uml_conversion_package;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
class SomeVisitor extends VoidVisitorAdapter<Void> {// if分岐しなくてもNodeクラスを指定することでその情報をとることができる。
	public static ArrayList<ArrayList<String>> class_list = new ArrayList<>();
	public ArrayList<ArrayList<ArrayList<String>>> class_lists = new ArrayList<>();// クラス {{アクセス修飾詞,フィールド名,値},アクセス修飾詞,メソッド名}
	public static ArrayList<ArrayList<String>> inheritance_list = new ArrayList<>();
	@Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        super.visit(n, arg);
        ArrayList<String> class_list1 = new ArrayList<>();
        for (ClassOrInterfaceType parent : n.getExtendedTypes()) {
            String parentName = parent.getNameAsString();
            var list = new ArrayList<String>();
            list.add(parentName);
            list.add(n.getNameAsString());
            inheritance_list.add(list);
            System.out.println(n.getNameAsString() + " は " + parentName + " を継承しています (実線矢印)");
            
        }
        class_list1.add("Class");
        class_list1.add(n.getNameAsString());
        class_list.add(class_list1);
        class_lists.add(class_list);
        class_list = new ArrayList<>();
        //System.out.println("class: " + n.getNameAsString());
    }
    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        ArrayList<String> method_list = new ArrayList<>();
        method_list.add("Method");
        String stm = "";
        for(var e: md.getModifiers()) {
            	switch(e.toString().trim()) {
	            	case "public":
	            		stm += "+";
	            		break;
	            	case"private":
	            		stm += "-";
	            		break;
            	}
            	stm += ""+md.getName()+"(): "+md.getType();// 型 名前

            	method_list.add(stm);
            	class_list.add(method_list);
            }
        }

    @Override
    public void visit(Modifier md, Void arg) {
        super.visit(md, arg);
        //System.out.println("modifier: " + md.toString());
    }
    @Override
    public void visit(FieldDeclaration n, Void arg) {// フィールドの情報取得
        super.visit(n, arg);
        for(var e: n.getModifiers()) {
        	for(var i :n.getVariables()) {
        		String stm = "";
        		ArrayList<String> field_info = new ArrayList<>();
                field_info.add("Field");
            	switch(e.toString().trim()) {
	            	case "public":
	            		stm += "+";
	            		break;
	            	case"private":
	            		stm += "-";
	            		break;
            	}
            	stm += i.getNameAsString() + ":" + i.getType().asString();// 型 名前
            	var variable_num = i.getInitializer();// optionalなので値を取り出す　初期値
            	if(variable_num.isPresent()) {
            		 stm += " = "+variable_num.get().toString();
            	}
            	field_info.add(stm);
            	class_list.add(field_info);
            }
        }
        //for(var e:field_info)System.out.println(e);
    }
    public static void showAllBPS(Node unit,int level) {
    	System.out.println(" ".repeat(level)+"┗トークン："+unit.getMetaModel().getTypeName());
    	System.out.println(" ".repeat(level)+"┗値："+unit.toString());
    	for(var e: unit.getChildNodes()) {
    		//System.out.println(" ".repeat(cnt)+e.getMetaModel().getTypeName());
    		showAllBPS(e,level+1);
    		
    	}
    	
    }
}
public class uml_conversion_prog extends Application{
	public static ArrayList<ArrayList<ArrayList<String>>> class_lists = new ArrayList<>();
	static CompilationUnit unit;
	public static ArrayList<ArrayList<String>> inheritance_list = new ArrayList<>();
	static double w;
	
	@Override
    public void start(Stage primaryStage) {// JACAFXを動かす
		primaryStage.setMaximized(true);
        Rectangle2D d = Screen.getPrimary().getVisualBounds();
//        Canvas canvas = new Canvas(d.getWidth(), d.getHeight());
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        
//        gc.strokeRect(120,80,260, 160);
        
        // クラスのそれぞれをラベルで生成する
        
        int stm_length = Integer.MIN_VALUE;// 文字数を記録する
        String stm_max = "";// 一番大きい文字列を保存
        // 位置を設定
        Pane pane = new Pane();
        GridPane gp = new GridPane();
        int gap = 30;// 垂直に並べる時のもの
        gp.setHgap(gap); 
	    gp.setVgap(gap);
	    gp.setPadding(new Insets(20));
        int cnt = 0;
        int col_num = 6;// 列の個数
        int start_x = 50;// 初期位置
        int start_y = 50;// 初期位置
        int gap_x = 30;//
        int gap_y = 30;// 
        double set_x = start_y;// 設置するVBoxの左端
    	double set_y = start_x;// 設置するVBoxの上端
    	double next_set_x = start_x;
    	double next_set_y = start_y;
    	// 座標の計算、文字列の用意、表示
        for(var class_list: class_lists) {// クラスリストから全てを並べるようにする
        	set_x = next_set_x;// 設置位置を決定
        	set_y = next_set_y;// 
        	VBox vb = new VBox(); //一つのクラス情報のラベルを縦に並べるレイアウト
        	String feilds = "";// それぞれの情報たちの文字列
            String classs = "";
            String methods = "";
            // 表示する文字の用意
        	for(var list:class_list) {//
        		// もしクラスリストで一番大きい文字列があれば更新する
        		if(stm_length < list.get(1).length()) {
        			stm_max = list.get(1);
        			stm_length = list.get(1).length();
        			}
        		// 対応する文字列に対して改行とそれぞれの変数に足し合わせる
            	switch (list.get(0)){
    	    		case "Class":
    	    			classs += list.get(1) + "\n";
    	    			break;
    	    		case "Field":
    	    			feilds += list.get(1)+ "\n";
    	    			break;
    	    		case "Method":
    	    			methods += list.get(1)+ "\n";
    	    			break;
            	}
        	}
        	System.out.println(classs);
            System.out.println(feilds);
            System.out.println(methods);
        	// ラベルそれぞれにクラス名　属性名　メソッド名を表示する。枠組みはCSSで表示する。
        	var lab_class = createLabel(classs,stm_max);
            var lab_field = createLabel(feilds,stm_max);
            var lab_method = createLabel(methods,stm_max);
            
            //VBoxの座標を設定する。
            vb.getChildren().addAll(lab_class, lab_field, lab_method);
            vb.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);//
            pane.getChildren().add(vb);
            System.out.println("x:"+next_set_x);
            vb.setLayoutX(next_set_x);
            vb.setLayoutY(next_set_y);

            
           
            next_set_x = (set_x + w + gap_x);// 新たな座標を更新しておく
            //gp.add(vb,cnt,0);
            cnt++;
            if(cnt % col_num == 0) {// 自動折り返し
            	set_x = start_x;
            }
            
            
        }
        Platform.runLater(()-> {
        	
        });
        Scene scene = new Scene(pane,d.getWidth(), d.getHeight());
        primaryStage.setTitle("ソースコード→UML");
        primaryStage.setScene(scene);
        primaryStage.show();
        // フィールド名
//        for(class_lists.get(3))
        // 属性名
        
//        BorderPane bp = new BorderPane();
//        bp.setCenter(canvas);
    }
	public static Label createLabel(String text,String stm_max_num) {
		double fontsize = 20;
		Text textNode = new Text(stm_max_num);// ラベルの大きさを決めるために事前にテキストノードにて確認する。
        textNode.setFont(new Font(fontsize)); // ラベルと同じフォントをセット
        double sq_width = textNode.getLayoutBounds().getWidth() + 10;
        w = sq_width;
        var lab = new Label(text);
        lab.setFont(new Font(fontsize));
        lab.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        lab.setPrefWidth(sq_width);
        lab.setAlignment(Pos.CENTER);
        return lab;
	}
	public static void getAst() {
		Path source = Paths.get("src/uml_conversion_package/example_program.java");
		try {
			unit = StaticJavaParser.parse(source);
            SomeVisitor visitor = new SomeVisitor();
            unit.accept(visitor, null); 
            SomeVisitor.showAllBPS(unit,0);
            for(int i = 0;i < visitor.class_lists.size();i++) {
            	class_lists.add(visitor.class_lists.get(i));
            	System.out.println("classlists:"+class_lists.get(i));
            }
            for(var e:class_lists) {
            	Collections.reverse(e);
            	System.out.println("classlists:"+e);
            }
            for(var e:visitor.inheritance_list) {
            	inheritance_list.add(e);
            	System.out.println("inheritance:"+e);
            }
         
          //BufferedWriterで文字をバッファリングすることによって、文字の入出力を効率化させる。BufferedWriterを使わないと文字をprint()で呼び出して、変換して、書き込んで…という作業が効率悪くなるらしいです。

          //そのままCSV化すると文字化けするのでOutputStreamWriterでエンコーディングする。

          //ファイル名を指定して、上書きモード(false)か、追記モード(True)かを選択する。
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	public static void writeCsv(ArrayList<ArrayList<ArrayList<String>>> lists) {
		for(var i: lists) {
			System.out.println(i.get(i.size()-1).get(1));
			if(i.get(0).get(0).equals("Class")){
				String file_name = "src/data/"+i.get(i.size()-1).get(1);// クラス名でファイルを作る
				System.out.println(file_name);
				File file = new File(file_name);
				try {
					if (file.createNewFile()){
			            System.out.println("ファイル作成成功");
			        }else{
			            System.out.println("ファイル作成失敗");
			        	}
					}catch(IOException e) {
				}
				try(PrintWriter pw = new PrintWriter(file_name,"Shift-JIS")){
					//ヘッダ列名の作成
					for(var u: i) {
						if(!i.get(0).get(0).equals("Class")) {
							System.out.println("1書き込んでいます");
							pw.write(u.get(0).toString());
							pw.write(",");
						}else {
							System.out.println("2書き込んでいます");
							pw.write(u.get(0).toString());
						}
					}
					pw.println();
				} catch (IOException e) {
					
				}
			}
		}
		
	}

    public static void main(String[] args) {
    	getAst();//抽象構文木を表示する。
		// CSVファイル作成
		uml_conversion_prog.writeCsv(class_lists);
        System.out.println("***********************************************");
        System.out.println(unit);
        System.out.println("***********************************************");
        launch(args);
    }
}
