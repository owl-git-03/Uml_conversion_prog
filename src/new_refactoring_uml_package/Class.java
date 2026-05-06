package new_refactoring_uml_package;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Class implements UmlFigure{
	String name;
	String superclass_name;
	double width;
	ArrayList<UmlFigure> list = new ArrayList<UmlFigure>();
	public Class(String name) {
		this.name = name;
		this.superclass_name = null;
	}
	public Class(String name, String superclass) {
		this.name = name;
		this.superclass_name = superclass;
	}
	
	public void maxWidth() {
		// 計算用の隠しTextを作る
		double max = 0;
		for(var e: list) {
			if(max < e.getWidth()) {
				max = e.getWidth();
			}
		}
		if(max < this.getWidth()) {
			max = this.getWidth();
		}
		Text textNode = new Text(superclass_name);
		textNode.setFont(new Font(20)); // ラベルと同じフォントにする
		if(max < textNode.getLayoutBounds().getWidth() + 20) {
			max = textNode.getLayoutBounds().getWidth() + 20;
		}
		width = max;
	}
	public double getWidth() {
		Text textNode = new Text(name);
		textNode.setFont(new Font(20)); // ラベルと同じフォントにする
		return textNode.getLayoutBounds().getWidth() + 20;
	}
	public Node draw() {
	    VBox vb = new VBox();
	    this.maxWidth();
        vb.setPrefWidth(this.width);
	    vb.setMaxHeight(VBox.USE_PREF_SIZE);
        vb.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        // クラス名
        Label nameLabel = new Label(name);
        nameLabel.setFont(new Font(20));
        nameLabel.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        nameLabel.setMaxWidth(Double.MAX_VALUE); // これも広げる
        nameLabel.setAlignment(Pos.CENTER);
        vb.getChildren().add(nameLabel);
        if(superclass_name != null) {
        	Label nameLabel2 = new Label(superclass_name);
            nameLabel2.setFont(new Font(20));
            nameLabel2.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0;");
            nameLabel2.setMaxWidth(Double.MAX_VALUE); // これも広げる
            nameLabel2.setAlignment(Pos.CENTER);
            vb.getChildren().add(nameLabel2);
        }
        // 子要素の描画
        for (UmlFigure child : list) {
            vb.getChildren().add(child.draw());
        }

        return vb;
	}
	public void add(UmlFigure obj) {
		list.add(obj);
	}
}
