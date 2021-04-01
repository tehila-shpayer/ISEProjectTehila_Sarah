package primitives;

/**
 * Class Vector is the basic class representing a Vector by three variables - x,y,z,
 * The vector exits the beginning of the axes and the point that presents it 
 * is the point to which the vector beam reaches in the Cartesian 3-Dimensional coordinate system.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

public class Vector {
	Point3D head;
	
	/**
	 * The constructor receives a 3D point and creates the vector with the same x,y,z
	 * The constructor sends an exception if the given point is (0, 0, 0)
	 * @param p - a 3D point of the format (x, y, z)
	 */
	public Vector(Point3D p) {
		if(p.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Vector can't have all coordinates 0.");
		this.head = new Point3D(p.getX(), p.getY(), p.getZ());
	}
	
	/**
	 * The constructor receives 3 real numbers (type Double)
	 * and creates a new vector of the format (x, y, z) represented by a 3D point
	 * The constructor sends an exception if the given point is (0, 0, 0)
	 * @param x - distance of the point from axis x in the Cartesian 3-Dimensional coordinate system.
	 * @param y - distance of the point from axis y in the Cartesian 3-Dimensional coordinate system.
	 * @param z - distance of the point from axis z in the Cartesian 3-Dimensional coordinate system.
	 */
    public Vector(double x, double y, double z) {
		this.head = new Point3D(x, y, z);
		if(head.equals(Point3D.ZERO))
			throw new IllegalArgumentException();
	}
    
    /**
     * Returns the field head of the vector
     * @return head - the point representing the vector
     */
    public Point3D getHead() {
		return head;
	}
    
    /**
     * Addition between two vectors by algebraic operation
     * The algebraic operation - v + u = (v1+u1, v2+u2, v3+u3)
     * @param other - the second vector
     * @return the new vector resulted in the addition
     */
    public Vector add(Vector other) {
    	return new Vector(head.add(other));
    }
    
    /**
     * Subtraction between two vectors by algebraic operation
     * The algebraic operation - v - u = (v1-u1, v2-u2, v3-u3)
     * @param other - the second vector
     * @return the new vector resulted in the subtraction
     */
    public Vector subtract(Vector other) {
		return other.getHead().subtract(head);
	}
    
    /**
     * Multiplication of a vector in scalar by algebraic operation
     * The algebraic operation - a*v = (a*v1, a*v2, a*v3)
     * @param a - a scalar number (type double)
     * @return the new vector resulted in the scalar multiplication
     */
    public Vector scale(double a) {
		return new Vector(a*head.getX(), a*head.getY(), a*head.getZ());
	}
    
    /**
     * Scalar product between two vectors
     * The algebraic operation - v*u = v1*u1 + v2*u2 + v3-u3
     * @param other - the second vector
     * @return the calculated scalar number
     */
    public double dotProduct(Vector other) {
    	Point3D p = other.getHead();
		return p.getX()*head.getX() + p.getY()*head.getY() + p.getZ()*head.getZ();
	}

    /**
     * Cross product between two vectors
     * @param other - the second vector
     * @return the new vector resulted in the cross product calculated
     */
    public Vector crossProduct(Vector other) {
		Point3D p = other.getHead();
		return new Vector(head.getY()*p.getZ() - head.getZ()*p.getY(),
			  	head.getZ()*p.getX() - head.getX()*p.getZ(),
				head.getX()*p.getY() - head.getY()*p.getX());
	}
    
    /**
     * The length of the vector squared
     * @return the squared length (type Double)
     */
    public double lengthSquared() {
		return head.getX()*head.getX() + head.getY()*head.getY() + head.getZ()*head.getZ();
	}
    
    /**
     * The original length of the vector
     * @return the length (type Double)
     */
    public double length() {
		return Math.sqrt(this.lengthSquared());
	}
    
    /**
     * Normalization of the vector itself - (devision in the vector's length)
     * @return the vector normalized
     */
    public Vector normalize() {
		this.head = scale(Math.abs(1/this.length()))                                                                                                                                                                     .getHead();
		return this;
	}
    
    /**
     * Calculation of the vector normalized (not changing the vector itself!!)
     * @return the vector normalized
     */
    public Vector normalized() {
    	Vector vector = scale(Math.abs(1/this.length()));
    	return vector.normalize();
	}
    
	@Override
	public boolean equals(Object obj) {
	   if (this == obj) return true;
	   if (obj == null) return false;
	   if (!(obj instanceof Vector)) return false;
	   Vector other = (Vector)obj;
	   return this.head.equals(other.head);
	}
	
	@Override
	public String toString()
	{
		return this.head.toString();
	}
}
