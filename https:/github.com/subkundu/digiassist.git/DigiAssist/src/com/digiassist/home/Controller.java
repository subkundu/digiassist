package com.digiassist.home;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

import com.digiassist.actionModules.Navigator;
import com.digiassist.listener.Speech_Recognizer;
import com.digiassist.tts.Communicator;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.MaryRuntimeUtils;
import marytts.util.data.audio.AudioPlayer;
import marytts.util.data.audio.MaryAudioUtils;

public class Controller extends Thread{
	Speech_Recognizer listener;
	public Thread controllerThread;
	String response = null;
	Navigator nav;
	
	public Controller(Speech_Recognizer listener) {
		this.listener = listener;
		startControllerThread();
	}
	public Controller() {
		//startControllerThread();
	}
	
	private void startControllerThread() {
		controllerThread = new Thread(new Runnable() {
			public void run() {
				checkListener();
			}
		});
		controllerThread.setName("controller");
		controllerThread.start();
		
	}

	public synchronized int process(String command) {
		nav = new Navigator();
		response = nav.process(command);
		Communicator communicator = new Communicator();
		if(!command.equals("") && !command.equals("<unk>")) {
			//communicator.communicate(command);
		}
		if (!response.equals(null)) {
			communicator.communicate(command);
		}
		return 0;
	}
	
	
	
	public synchronized void checkListener() {
		while(listener.getResult().equals("")) {
			try {
				Controller.class.wait();
			} catch (InterruptedException e) {
				process(listener.getResult());
				e.printStackTrace();
			}			
		}
		process(listener.getResult());
	}

}
