import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;


abstract public class GameObject {

	protected Rectangle2D.Double bounds, scope;
	protected Polygon shadow;
	protected Shape shape, scopeShape;
	protected double velocity, angle;
	
	public GameObject() {
		
	}
	
	public GameObject(Rectangle2D.Double bounds) {
		this.bounds = bounds;
		shadow = new Polygon();
		shadow.addPoint(0, 0);
		shadow.addPoint((int)bounds.getWidth(), 0);
		shadow.addPoint((int)bounds.getWidth(),  (int)bounds.getHeight());
		shadow.addPoint(0, (int)bounds.getHeight());
		int radius = (int) Math.sqrt(
							(bounds.getWidth()*5/6)*(bounds.getWidth()*5/6)
							+(bounds.getHeight()*1/2)*(bounds.getHeight()*1/2) )+1;
		scope = new Rectangle2D.Double(
					(bounds.getX()+bounds.getWidth()*1/6-radius),
					(bounds.getY()+bounds.getHeight()*1/2-radius),
					2*radius, 2*radius);
	}

	public void moveObject(double x, double y)
	{
		this.bounds.setRect(bounds.getX()+x, bounds.getY()+y, bounds.getWidth(), bounds.getHeight());
		this.scope.setRect(scope.getX()+x, scope.getY()+y, scope.getWidth(), scope.getHeight());
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getVelocity() {
		return this.velocity;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return this.angle;
	}
	
}
