package vision;

public class Blob {
	
	float minx, miny, maxx, maxy;
	float distThreshold = 80;
	
	boolean intrest = false;
	
	Blob(float x, float y){
		minx = x;
		miny = y;
		maxx = x;
		maxy = y;
	}
	
	
	float get_minx(){return minx;}
	float get_miny(){return miny;}
	float get_maxx(){return maxx;}
	float get_maxy(){return maxy;}
	
	float get_center_x(){return minx + (maxx-minx)/2;}
	
	float get_size_x(){return maxx-minx;}
	float get_size_y(){return maxy-miny;}
	
	float get_size(){return ((maxx-minx)+(maxy-miny))/2;}
	
	boolean is_intrest(){return intrest;}
	void set_intrest(boolean new_intrest){intrest = new_intrest;}
	
	void add(float x, float y){
		minx = min(minx, x);
		miny = min(miny, y);
		maxx = max(maxx, x);
		maxy = max(maxy, y)	;
	}
	
	float size(){
		return (maxx-minx)*(maxy-miny);
	}
	
	boolean isNear(float x, float y){
		
		float cx = (minx + maxx)/2;
		float cy = (miny + maxy)/2;
		
		float d = distSq(cx, cy, x, y);
		
		if(d < distThreshold*distThreshold){
			return true;
		}else {
			return false;
		}
		
	}
	
	
	float distSq(float x1, float y1, float x2, float y2) {
		return (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
	}
	
	
	float min(float x, float y){
		
		if(x < y){
			return x;
		}else {
			return y;
		}
		
	}
	
	float max(float x, float y){
		
		if(x > y){
			return x;
		}else {
			return y;
		}
	}
	
}
