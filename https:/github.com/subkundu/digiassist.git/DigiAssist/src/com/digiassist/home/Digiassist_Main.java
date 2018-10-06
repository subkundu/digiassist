package com.digiassist.home;
import com.digiassist.listener.*;
import com.digiassist.motionDetection.DetectMotion;
import com.digiassist.tts.*;

public class Digiassist_Main{
	static Speech_Recognizer listener;
	
	public static void main(String[] args) {
		
		Controller controller = new Controller();
		listener = new Speech_Recognizer(controller,"process");
		 
		listener.startListening();
		//DetectMotion motionDetector = new DetectMotion();
		
		
		
		}

}
