package com.digiassist.listener;

import java.io.IOException;
import java.lang.reflect.Method;

import com.digiassist.tts.Communicator;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

public class Speech_Recognizer extends Thread{
	
private LiveSpeechRecognizer recognizer = null;
private Configuration configuration = null;
private SpeechResult speechResult = null;
public String result = "";
public Thread recognizerThread;
Communicator communicator = new Communicator();
//Controller controller = new Controller();
private boolean isReady=false;

public Class<? extends Object> classToCall;
public Object classObject;
public Method methodToCall;
private int sampleRate = 16000;

public Speech_Recognizer() {
    	init();
    }
    
public Speech_Recognizer(Object obj, String classMethod) {
	this.classObject = obj;
	setClassToCall(classObject);
	setMethodToCall(classMethod);
	init();
}

private void init() {
	configuration = new Configuration();
	//en_in config
	configuration.setAcousticModelPath("src/resources/en_in/en_in.cd_cont_5000");
    configuration.setDictionaryPath("src/resources/en_in/en_in.dic");
    
    //en_us config
    //configuration.setAcousticModelPath("src/resources/en_us/en-us");
    //configuration.setDictionaryPath("src/resources/en_us/cmudict-en-us.dict");
    //configuration.setDictionaryPath("src/resources/en_us/cmudict.dict");
    
    configuration.setGrammarPath("src/resources/grammar");
    configuration.setGrammarName("grammar");
    configuration.setUseGrammar(true);
    //configuration.setSampleRate(sampleRate );
    
    
    try {
		recognizer = new LiveSpeechRecognizer(configuration);
	} catch (IOException e) {
		e.printStackTrace();
	}
    recognizer.startRecognition(true);
	}

public void startListening() {
	startRecognizerThread();
}

protected synchronized void startRecognizerThread() {
	/*if(recognizerThread.isAlive()) {
		return;
	}*/
	
	recognizerThread = new Thread(new Runnable() {
		public void run() {
			listenAndIdentifySpeech();
		}
	});
	recognizerThread.start();
	System.out.println("I am listening................");
	setReady(true);
}

public synchronized void listenAndIdentifySpeech() {
	try {
		while(true) {
			speechResult = recognizer.getResult();
			if(speechResult != null) {
				setResult(speechResult.getHypothesis());
				//System.out.println("List of recognized words and their times:");
	            //System.out.println(speechResult.getWords());
				getMethodToCall().invoke(classObject, getResult());
				System.out.println("You said "+getResult());				
			}else {
				System.out.println("I cant understand what you said");
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

public boolean isReady() {
	return isReady;
}

public void setReady(boolean isReady) {
	this.isReady = isReady;
}

public String getResult() {
	return result;
}

private void setResult(String result) {
	this.result = result;
}

public void setClassToCall(Object obj) {
	this.classToCall = obj.getClass();
	System.out.println("CLASS TO CALL - "+classToCall.getName());
	
}
public Class<? extends Object> getClassToCall() {
	return this.classToCall;
	
}
public Method getMethodToCall() {
	return methodToCall;
}

public void setMethodToCall(String methodNameToCall) {
	try {
		this.methodToCall = getClassToCall().getMethod(methodNameToCall, String.class);
		System.out.println("METHOD TO CALL - "+methodToCall.getName());
	} catch (NoSuchMethodException | SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
