package org.usfirst.frc.team7116.robot;

import org.opencv.core.Core;

import edu.wpi.first.wpilibj.CameraServer;

public class Vision implements Runnable {
	private CameraServer cameraServer;
	
	
	public Vision() {
		
	}

	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		cameraServer = CameraServer.getInstance();
		cameraServer.putVideo("CamFront", 640, 480);
	}
	
	
	

}
