package jp.ac.hosei.object.composite0;

import jp.ac.hosei.utils.Canvas;

public class Car implements Figure {
	int x;
	int y;
	int bodyWidth;
	int bodyHeight;
	int wheel;
	int windowWidth;
	int windowHeight;
	int hood;
	int red;
	int green;
	int blue;
	Figure body;
	Figure window;
	Circle frontWheel;
	Circle rearWheel;

	public Car(int x, int y, int bodyWidth, int bodyHeight, int wheel, int windowWidth, int windowHeight, int hood,
			int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.bodyWidth= bodyWidth;
		this.bodyHeight = bodyHeight;
		this.wheel = wheel;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.hood = hood;
		this.red = red;
		this.green = green;
		this.blue = blue;

		this.body = new Rectangle(x, y + windowHeight, bodyWidth, bodyHeight, red, green, blue);
		this.frontWheel = new Circle(x + wheel, y + windowHeight + bodyHeight, wheel, 0, 0, 0);
		this.rearWheel = new Circle(x + bodyWidth - wheel, y + windowHeight + bodyHeight, wheel, 0, 0, 0);
	}

	@Override
	public void draw() {
		Canvas.setColor(red, green, blue);
		Canvas.fillRect(x + hood, y, windowWidth, windowHeight);
		Canvas.fillRect(x, y + windowHeight, bodyWidth, bodyHeight);
		Canvas.setColor(0, 0, 0);
		Canvas.fillCircle(x + wheel, y + windowHeight + bodyHeight, wheel);
		Canvas.fillCircle(x + bodyWidth - wheel, y + windowHeight + bodyHeight, wheel);
	}

	@Override
	public void delete() {
		Canvas.setColor(255, 255, 255);
		Canvas.fillRect(x + hood, y, windowWidth, windowHeight);
		Canvas.fillRect(x, y + windowHeight, bodyWidth, bodyHeight);
		Canvas.fillCircle(x + wheel, y + windowHeight + bodyHeight, wheel);
		Canvas.fillCircle(x + bodyWidth - wheel, y + windowHeight + bodyHeight, wheel);
	}

	@Override
	public void move(int x, int y) {
		delete();
		this.x += x;
		this.y += y;
		draw();
	}
}
