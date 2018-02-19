package vision;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements Runnable {
	private CameraServer camServer;
	
	
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
		UsbCamera camRear = camServer.startAutomaticCapture(1);
		
		camFront.setResolution(320, 240);
		camFront.setFPS(15);
		
		camRear.setResolution(160, 120);
		camRear.setFPS(10);
		
		CvSink sinkFront = CameraServer.getInstance().getVideo(camFront);
		CvSink sinkRear =  CameraServer.getInstance().getVideo(camRear);
		//      CvSink cvSink2 = CameraServer.getInstance().getVideo(camera2);
		  
		//CvSource outputStream = camServer.putVideo("cam0", 320, 240);
		
		sinkFront.setEnabled(true);
		sinkRear.setEnabled(true);
		
		Mat image = new Mat();
		Mat imgSmall = null;
		Size smallSize = new Size (160, 120);
		
		
		while(true) {
			try {
				sinkFront.grabFrame(image);
				
				//outputStream.putFrame (equalizeIntensity(image));
				
				
				
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

}
