package engineTester;

public class Main implements Runnable{
	String name;
	Thread t;
	public Main(String threadName) {
		name = threadName;
		t = new Thread(this, name);
		System.out.println("New Thread: " + t);
		t.start();
	}

	public void run() {
		MainGameLoop.run();
	}
}
