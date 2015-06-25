import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PlayerObject extends GameObject implements Serializable {

	public int rgb;
	public double grip;
	public BufferedImage rgbtarmac;
	
	public PlayerObject() {
		super();
	}

	public PlayerObject(Rectangle2D.Double bounds) {
		super(bounds);
		//rgbtarmac = new BufferedImage((int)bounds.getWidth()*2, (int)bounds.getHeight()*2, 5);
		rgbtarmac = new BufferedImage(107,107,5);
	}

	protected PlayerObject copy() {
		PlayerObject clonedObject = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            //writeObject(out);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            clonedObject = (PlayerObject) in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
		return clonedObject;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		List loc = new ArrayList();
		loc.add(this.velocity);
		loc.add(this.angle);
		loc.add(this.bounds);
		loc.add(this.shadow);
		loc.add(this.shape);
		loc.add(this.scope);
		oos.writeObject(loc);
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		List loc = (List)ois.readObject();
		this.velocity = (Double)loc.get(0);
		this.angle = (Double)loc.get(1);
		this.bounds = (Rectangle2D.Double)loc.get(2);
		this.shadow = (Polygon)loc.get(3);
		this.shape = (Shape)loc.get(4);
		this.scope = (Rectangle2D.Double)loc.get(5);
	}
}
