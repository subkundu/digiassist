package com.digiassist.motionDetection;

import java.io.IOException;

import com.digiassist.mouseController.MouseController;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

public class DetectMotion implements WebcamMotionListener{
	boolean first = true;
	private int prevCogX = 0;
	private int prevCogY = 0;
	private int currCogX = 0;
	private int currCogY = 0;
	private String xMovement = null;
	private String yMovement = null;
	private int xMoveBy = 0;
	private int yMoveBy = 0;
	MouseController mouseController;
	
	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		if(first) {
			prevCogX = wme.getCog().x;
			prevCogY = wme.getCog().y;
			first = false;
		}else {
			currCogX = wme.getCog().x;
			currCogY = wme.getCog().y;
			
			if(currCogX > prevCogX) {
				xMovement = "Left";
				xMoveBy = prevCogX - currCogX;
			}else {
				xMovement = "Right";
				xMoveBy = prevCogX - currCogX;
			}			
			if(currCogY > prevCogY) {
				yMovement = "Down";
				yMoveBy = currCogY - prevCogY;
			}else {
				yMovement = "Up";
				yMoveBy = currCogY - prevCogY;
			}			
			prevCogX = currCogX;
			prevCogY = currCogY;			
			System.out.println(xMovement + " " + yMovement + " " + "(" + xMoveBy + "," + yMoveBy + ")");
			mouseController.moveMouse(xMoveBy*5, yMoveBy*5);
		}
	}

	public DetectMotion() {
		mouseController = new MouseController();
		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(100); // one check per 500 ms
		detector.addMotionListener(this);
		detector.start();
	}
	
	

	public static void main(String[] args) throws InterruptedException {
		new DetectMotion();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		} // keep program open
	}

	
}
