package com.digiassist.mouseController;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;

public class MouseController {
	
	private int currentX;
	private int currentY;
	private int moveXTo;
	private int moveYTo;
	private static Robot robot = null;
	
	
	
	public MouseController() {
		this.setCurrentX(MouseInfo.getPointerInfo().getLocation().x);
		this.setCurrentY(MouseInfo.getPointerInfo().getLocation().y);
		if(getRobot()==null) {
			setRobot();
		}
	}
	public MouseController(int x, int y) {
		this.setCurrentX(x);
		this.setCurrentY(y);
		if(getRobot()==null) {
			setRobot();
		}
	}
	public MouseController(Point point) {
		this.setCurrentX(point.x);
		this.setCurrentY(point.y);
		if(robot==null) {
			setRobot();
		}
	}
	
	public void moveMouse(int moveXBy, int moveYBy) {
		setCurrentX();
		setCurrentY();
		setMoveXTo(getCurrentX() + moveXBy);
		setMoveYTo(getCurrentY() + moveYBy);
		moveMouse(new Point(getMoveXTo(), getMoveYTo()));
		//robot.mouseMove(getMoveXTo(), getMoveYTo());
		setCurrentX(getMoveXTo());
		setCurrentY(getMoveYTo());
	}
	public void moveMouse(Point point) {
		for (int i=0; i<100; i++){  
		    int mov_x = ((getMoveXTo()* i)/100) + (getCurrentX()*(100-i)/100);
		    int mov_y = ((getMoveYTo() * i)/100) + (getCurrentY()*(100-i)/100);
		    robot.mouseMove(mov_x,mov_y);
		    robot.delay(2);
		}

		//robot.mouseMove(point.getLocation().x, point.getLocation().y);
		
	}
	
	public static void mouseLeftClick() {
		if(robot==null) {
			setRobot();
		}
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void processMotionData(int xData, int yData) {
		
	}
	
	
	
	
	
	
	

	
	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}
	public void setCurrentX() {
		this.currentX = MouseInfo.getPointerInfo().getLocation().x;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	public void setCurrentY() {
		this.currentY = MouseInfo.getPointerInfo().getLocation().y;
	}
	
	public int getMoveXTo() {
		return moveXTo;
	}
	public void setMoveXTo(int moveXTo) {
		this.moveXTo = moveXTo;
	}
	public int getMoveYTo() {
		return moveYTo;
	}
	public void setMoveYTo(int moveYTo) {
		this.moveYTo = moveYTo;
	}
	public static Robot getRobot() {
		return robot;
	}
	public static void setRobot() {
		try {
			MouseController.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
