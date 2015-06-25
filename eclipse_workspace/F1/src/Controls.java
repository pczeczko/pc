
public class Controls {
	
	private boolean throttle, brake, steeringLeft, steeringRight, handbrake;

	public Controls() {
		throttle = false;
		brake = false;
		steeringLeft = false;
		steeringRight = false;
		handbrake = false;
	}

	public void setThrottle(boolean value) {
		throttle = value;
	}
	
	public void setBrake(boolean value) {
		brake = value;
	}
	
	public void setSteeringLeft(boolean value) {
		steeringLeft = value;
	}

	public void setSteeringRight(boolean value) {
		steeringRight = value;
	}

	public void setHandbrake(boolean value) {
		handbrake = value;
	}

	public boolean getThrottle() {
		return throttle;
	}
	
	public boolean getBrake() {
		return brake;
	}

	public boolean getSteeringLeft() {
		return steeringLeft;
	}

	public boolean getSteeringRight() {
		return steeringRight;
	}

	public boolean getHandbrake() {
		return handbrake;
	}

	public static void main(String[] args) {

	}

}
