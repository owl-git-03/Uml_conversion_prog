package jp.ac.hosei.object.composite0;

import java.util.ArrayList;
import java.util.List;

import jp.ac.hosei.utils.Canvas;

public class Main {
	public static void animation(List<Figure> figures) {
		for (int x = 0; x < 100; x++) {
			for(Figure figure: figures) {
				figure.move(1, 0);
			}
			try {
				Thread.sleep(10);
			} catch(Exception e) {

			}
		}
	}

	public static void main(String[] args) {
		Canvas.show();

		Figure fig1 = new Circle(100, 50, 40, 255, 0, 0);
		Figure fig2 = new Car(100, 200, 150, 40, 20, 80, 30, 40, 255, 0, 0);
		ArrayList<Figure> figures = new ArrayList<Figure>();
		figures.add(fig1);
		figures.add(fig2);

		animation(figures);
	}
}
