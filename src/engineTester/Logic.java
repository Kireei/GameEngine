package engineTester;

import terrain.Planet;

public class Logic implements Runnable{
	String name;
	Thread t;
	public Logic(String threadName) {
		name = threadName;
		t = new Thread(this, name);
		System.out.println("New Thread: " + t);
		t.start();
		
	}

	public void run() {
		Planet.createPlanet();
		while(true) Planet.checkPlanetResolution();
	}
}
