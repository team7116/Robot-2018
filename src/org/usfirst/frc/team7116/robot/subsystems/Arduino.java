package org.usfirst.frc.team7116.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;

public class Arduino {
	private final static byte ADDRESS = 0x08;
	
	private I2C mcu;
	
	public Arduino () {
		this(ADDRESS);
	}
	
	public Arduino (int address) {
		mcu = new I2C(I2C.Port.kOnboard, address);
		
		
	}
	
	public AllAxes getAllAxes() {
		byte [] raw = new byte[6];
		mcu.read(1, raw.length, raw);
		
		AllAxes data = new AllAxes();
		
		data.XAxis = (raw[0] << 8 | raw[1]);
		data.YAxis = (raw[2] << 8 | raw[3]);
		data.ZAxis = (raw[4] << 8 | raw[5]);
		
		return data;
		
	}
	
	public static class AllAxes {

		public double XAxis;
		public double YAxis;
		public double ZAxis;
	}
}
