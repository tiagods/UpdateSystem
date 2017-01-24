package br.com.tiagods.model;

import java.io.IOException;

public class ActionRuntime {
	public static boolean executeCommand(String command){
		try{
			Runtime.getRuntime().exec(command);
			return true;
		}catch(IOException e){
			return false;
		}
	}
}
