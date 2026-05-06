package jp.ac.hosei.object.composite0;

import jp.ac.hosei.utils.Canvas;

public class Rectangle implements Figure {
	public int x;
	public int y;
	public int w;
	public int h;
	public int red;
	public int green;
	public int blue;

	public Rectangle(int x, int y, int w, int h, int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public void draw() {
		Canvas.setColor(red, green, blue);
		Canvas.fillRect(x, y, w, h);
	}

	public void delete() {
		Canvas.setColor(255, 255, 255);
		Canvas.fillRect(x, y, w, h);
	}

	public void move(int x, int y) {
		delete();
		this.x += x;
		this.y += y;
		draw();
	}
}
