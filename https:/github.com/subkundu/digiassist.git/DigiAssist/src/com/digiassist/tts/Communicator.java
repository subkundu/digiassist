package com.digiassist.tts;
import javax.sound.sampled.AudioInputStream;
import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;

public class Communicator {
	
	MaryInterface marytts;
	AudioInputStream audio;
	AudioPlayer audioPlayer;
	
	public Communicator() {
		init();
	}

	private void init() {
		try {
			marytts = new LocalMaryInterface();
			audioPlayer = new AudioPlayer(audio, null, null, audioPlayer.STEREO);
			
		} catch (MaryConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void communicate(String sentence) {
		try {
			/*System.out.println("EFFECTS "+marytts.getAudioEffects());
			System.out.println("AVAILABLE INPUT TYPES "+marytts.getAvailableInputTypes());
			System.out.println("INPUT TYPES "+marytts.getInputType());
			System.out.println("STYLE "+marytts.getStyle());
			System.out.println("AVAILABLE OUT TYPES "+marytts.getAvailableOutputTypes().toString());
			System.out.println("OUTPUT TYPES "+marytts.getOutputType());
			System.out.println("AVAILABLE VOICES "+marytts.getAvailableVoices().toString());
			System.out.println("VOICE "+marytts.getVoice());
			System.out.println("AVAILABLE LOCALES "+marytts.getAvailableLocales().toString());
			System.out.println("LOCALE "+marytts.getLocale().toString());*/
			
			
			audio = marytts.generateAudio(sentence);
			audioPlayer.setAudio(audio);
			audioPlayer.start();
		} catch (SynthesisException e) {
			e.printStackTrace();
		}
	}
		
}
