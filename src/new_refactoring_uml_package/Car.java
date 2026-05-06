package new_refactoring_uml_package;

public class Car {
	public int width = 30;
	public int height = 20;
	
	private void go() {
	}
	private int back() {
		return 0;
	}
	private int fuel() {
		return 0;
	}
}
class SuperCar extends Car{
	public int width = 30;
	public int height = 20;
	private int super_engyn = 100;
	private void go() {
	}
	private int back() {
		return 0;
	}
	private int fuel() {
		return 0;
	}
	private void super_go() {
		
	}
}
class ex extends Car{
	public int a;
	private void go() {
		
	}
}
