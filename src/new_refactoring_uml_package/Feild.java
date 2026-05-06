package new_refactoring_uml_package;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Feild implements UmlFigure {
	String name;
	String type;
	String access;
	String value;
	String stm;
	public Feild(String name,String type,String access) {
			this(name,type,access,"null");
	}
	public Feild(String name,String type,String access,String value) {
		this.name = name;
		this.type = type;
		this.access = access;
		this.value = value;
	}
	public double getWidth() {
		Text textNode = new Text(access+":"+name+":"+type+":"+value);
		textNode.setFont(new Font(20)); // ラベルと同じフォントにする
		return textNode.getLayoutBounds().getWidth() + 20;
	}
	public Node draw() {
		String stm = "";
		if(access.equals( "public")) {
			stm = "+"+name+":"+type+" = "+value;
		}else {
			stm = "-"+name+":"+type+" = "+value;
		}
	    var lab = new Label(stm);
	    lab.setFont(new Font(20));
	    //lab.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
	    lab.setMaxWidth(Double.MAX_VALUE);
	    lab.setAlignment(Pos.CENTER);
		return lab;
	}
}
