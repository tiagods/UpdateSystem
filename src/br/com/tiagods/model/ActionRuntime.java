package br.com.tiagods.model;

import java.io.IOException;

public class ActionRuntime {
	
	public static void main(String[] args){
		System.out.println("cmd /c \""+System.getProperty("user.dir")+"\\"+"closeApp.bat"+"\"");
		executeCommand("closeApp.bat");
	}
	
	public static boolean executeCommand(String fileBat){
		try{
			Runtime.getRuntime().exec("cmd /c \""+System.getProperty("user.dir")+"\\"+fileBat+"\"");
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
}
