package new_refactoring_uml_package;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Method implements UmlFigure{
	String name;
	String back_type;
	String access;
	public Method(String name,String back_type,String access) {
		this.name = name;
		this.back_type = back_type;
		this.access = access;
	}
	public double getWidth() {
			Text textNode = new Text(access + ":"+ name +":"+back_type);
			textNode.setFont(new Font(20)); // ラベルと同じフォントにする
			return textNode.getLayoutBounds().getWidth() + 20;
	}
	public Node draw() {
		String stm = "";
		if(access.equals( "public")) {
			stm = "+"+ name +":"+back_type;
		}else {
			stm = "-"+ name +":"+back_type;
		}
	    var lab = new Label(stm);
	    lab.setFont(new Font(20));
	    lab.setStyle("-fx-border-color: black; -fx-border-width: 1px 0 0 0;");
	    lab.setMaxWidth(Double.MAX_VALUE);
	    lab.setAlignment(Pos.CENTER);
		return lab;
	}
}
