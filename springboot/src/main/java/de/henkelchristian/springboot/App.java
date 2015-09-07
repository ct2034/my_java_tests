package de.henkelchristian.springboot;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class App {
	public static void main(String[] args) {
		System.out.println("init ...");
		
		// ------------------------------
		// Spring boot
		ConfigurableApplicationContext cac2 = SpringApplication.run(SpringBootAppletClient.class, args);
		System.out.println("cac2.isRunning(): " + cac2.isRunning());

		boolean run = true;
		Random rand = new Random(2);
		while (run) {
			int randPosInt = 0;
			for (int i = 0; i<100; i++) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					run = false;
				}
				randPosInt = Math.abs(rand.nextInt() % 50);
				if (randPosInt == 1) randPosInt = -1; //failing rarely
				new GetPowerCommand(randPosInt, 82).execute();
			}
		}

	}
}
