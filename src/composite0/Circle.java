package jp.ac.hosei.object.composite0;

import jp.ac.hosei.utils.Canvas;

public class Circle implements Figure {
	public int x;
	public int y;
	public int r;
	public int red;
	public int green;
	public int blue;

	public Circle(int x, int y, int r, int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public void draw() {
		Canvas.setColor(red, green, blue);
		Canvas.fillCircle(x, y, r);
	}

	public void delete() {
		Canvas.setColor(255, 255, 255);
		Canvas.fillCircle(x, y, r);
	}

	public void move(int x, int y) {
		delete();
		this.x += x;
		this.y += y;
		draw();
	}
}
