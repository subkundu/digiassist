package com.digiassist.actionModules;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.digiassist.mouseController.MouseController;

public class Navigator {
	
	public String process(String command) {
		String response = "";
		switch (command.toLowerCase())
		{
		case "my computer":
			File home = new File(System.getProperty("user.home"));
			try {
				Desktop.getDesktop().open(home);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("HOME : "+home);
			response = "opened";
			break;
		case "chrome":
			try {
				Runtime.getRuntime().exec(new String[]{"bash", "-c", "chromium-browser http://ygoogle.com"});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = "opened";
			break;
		case "search":
			System.out.println("Opening Chromium");
			try {
				//Runtime.getRuntime().exec(new String[]{"bash", "-c", "chromium-browser http://google.com"});
				Desktop.getDesktop().browse(new URI("http://www.google.com"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = "opened";
			break;
		case "click":
			MouseController.mouseLeftClick();
			response = "clicked";
			break;
		}
		return response;		
	}

}
