package br.com.tiagods.model;

import java.io.IOException;

public class ActionRuntime {
	public boolean executeCommand(String command){
		try{
			Runtime.getRuntime().exec(command).waitFor();
			return true;
		}catch(IOException e){
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}
}
