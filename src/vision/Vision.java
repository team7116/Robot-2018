package vision;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements Runnable {
		
	private CameraServer camServer;
	
	public float closestCenter = 1000;
	public float centerX;
	
	private boolean is_enabled = true;
	
	int width = 320;
	int height = 240;
	
	public int hueMin = 15;
	public int hueMax = 35;
	public int satMin = 60;
	public int satMax = 220;
	public int volMin = 130;
	public int volMax = 250;
	
	
	public Vision() {
		
	}

	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		camServer = CameraServer.getInstance();

		UsbCamera camFront = camServer.startAutomaticCapture(0);
		//UsbCamera camRear = camServer.startAutomaticCapture(1);
		
		camFront.setResolution(320, 240);
		camFront.setFPS(15);
		
		//camRear.setResolution(160, 120);
		//camRear.setFPS(10);
		
		CvSink sinkFront = CameraServer.getInstance().getVideo(camFront);
		//CvSink sinkRear =  CameraServer.getInstance().getVideo(camRear);
		//      CvSink cvSink2 = CameraServer.getInstance().getVideo(camera2);
		
		int width = 320;
		int height = 240;
		
		CvSource outputStream = camServer.putVideo("openCV", width, height);
		
		sinkFront.setEnabled(true);
		//sinkRear.setEnabled(true);
		
		Mat image = new Mat();
		Mat output = new Mat();
		
		Mat imgSmall = null;
		Size smallSize = new Size (160, 120);

		int hueMin = 20;
		int hueMax = 35;
		int satMin = 30;
		int satMax = 125;
		int volMin = 150;
		int volMax = 240;
		
		ArrayList<Blob> blobs = new ArrayList<>();
		
		while(true) {
			try {
				sinkFront.grabFrame(image);
				
				if(image.channels() == 3) {
					
					//Imgproc.threshold(image, output, 127, 255, Imgproc.THRESH_BINARY);
					//SmartDashboard.putNumber("OpenCV", image.channels());
					Imgproc.cvtColor(image, output, Imgproc.COLOR_BGR2HSV);
					//org.opencv.imgproc.Imgproc.rectangle(output, point1, point2, new Scalar(255, 255, 255));
					Core.inRange(output, new Scalar(hueMin, satMin, volMin), new Scalar(hueMax, satMax, volMax), output);
					
					
					
					blobs.clear();
					
					float threshold = 80;
					
					for(int x = 0; x < width; x++) {
						for(int y = 0; y < height; y++) {
							
							double[] colour = output.get(x, y);
							
							double currentHue;
							if(colour != null) {
								
								SmartDashboard.getNumber("Colour Size", colour.length);
								
								currentHue = colour[0];
								
								float d = distSq((float)currentHue ,(float)currentHue, (float)currentHue, 255, 255, 255);
								
								if(d < threshold*threshold) {
									
									boolean found = false;
									for(Blob b : blobs) {
										if(b.isNear((float)x, (float)y)){
											b.add((float)x, (float)y);
											found = true;
											break;
										}
									}
									
									if(!found) {
										Blob b = new Blob((float)x, (float)y);
										blobs.add(b);
									}
									
								}
								
							}
							
						}
					}
					
					/*
					
					for(Blob b : blobs) {
						float x1 = b.get_minx();
						float y1 = b.get_miny();
						float x2 = b.get_maxx();
						float y2 = b.get_maxy();
						
						Point point1 = new Point(x1, y1);
						Point point2 = new Point(x2, y2);

						org.opencv.imgproc.Imgproc.rectangle(output, point1, point2, new Scalar(255, 255, 255, 100));
						
						
					}
					
					*/
					
					outputStream.putFrame (output);
					
				}
				
				
				Thread.sleep(15);
			} catch (InterruptedException e) {
				stopCamServer();
				return;
			}
			
			if (Thread.interrupted()) {
				stopCamServer();
				return;
			}
			
		}
	}
	
	float distSq(float x1, float y1, float z1, float x2, float y2, float z2) {
		return (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1);
	}

	
	void stopCamServer() {
		SmartDashboard.putString("Message", "Stopping camera server");
	}
	
	Mat equalizeIntensity (Mat input) {
		
		if (input.channels() > 3) {
			Mat ycrcb = null;
			
			Imgproc.cvtColor(input, ycrcb, Imgproc.COLOR_BGR2YCrCb);
			List<Mat> channels = null;
			
			Core.split(ycrcb, channels);
			Imgproc.equalizeHist(channels.get(0), channels.get(0));
			
			Mat result = null;
			Core.merge(channels, ycrcb);
			Imgproc.cvtColor(ycrcb, result, Imgproc.COLOR_YCrCb2BGR);
			
			return result;
			
		}
		return input;
	}
	
	public float get_blob_center(){
		return centerX;
	}
	
	public int get_width(){
		return width;
	}
	
	
	public void toggleEnabled(){
		is_enabled = !is_enabled;
		if(is_enabled){
			SmartDashboard.putString("OpenCV status", "on");
		}else {
			SmartDashboard.putString("OpenCV status", "off");
		}
	}
	

}
