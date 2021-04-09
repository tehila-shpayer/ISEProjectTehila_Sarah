package primitives;

/**
 * Class Point3D is the class representing a point In a space of 3 dimensions
 * 
 * @authors Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */

public class Point3D
{
	/**
     * 3 Coordinate values
     * ZERO - a constant variable representing the first the hinges
     */
	private Coordinate x;
	private Coordinate y;
	private Coordinate z;
	public static Point3D ZERO = new Point3D(0, 0, 0);
	
	/**
     * Point3D constructor receiving 3 values
     * 
     * @param x - The value for the first coordinate of the point
     * @param y - The value for the second coordinate of the point
     * @param z - The value for the third coordinate of the point
     */
	public Point3D(double x, double y, double z)
	{
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);
	}
	
	/**
     * Returns the field x of the point
     * @return x - the first coordinate of the point
     */
	public double getX() {
		return x.coord;
	}
	/**
     * Returns the field y of the point
     * @return y - the second coordinate of the point
     */
	public double getY() {
		return y.coord;
	}
	/**
     * Returns the field z of the point
     * @return z - the third coordinate of the point
     */
	public double getZ() {
		return z.coord;
	}
	
	/**
     * Addition of a vector to the point
     * The algebraic operation - p + v = (p1+v1, p2+v2, p3+v3)
     * @param v - the vector
     * @return the new point after the vector addition
     */
	public Point3D add(Vector v)
	{
		return new Point3D(this.getX() + v.getHead().getX(), this.getY() + v.getHead().getY(), this.getZ() + v.getHead().getZ());
	}
	
	 /**
     * Subtraction between two points by algebraic operation
     * The algebraic operation - q - p = (q1-p1, q2-p2, q3-p3)
     * @param p - the second point
     * @return a vector resulted in the subtraction
     */
	public Vector subtract(Point3D p)
	{
		return new Vector(this.getX() - p.getX(), this.getY() - p.getY(), this.getZ() - p.getZ());
	}
	
	 /**
     * Find the square of the distance by algebraic operation
     * The algebraic operation - ((q1-p1)^2 + (q2-p2)^2 + (q3-p3)^2)
     * @param p - the second point
     * @return the Square of the distance between the points
     */
	public double distanceSquared(Point3D p)
	{
		return ((this.getX() - p.getX())*(this.getX() - p.getX()) + (this.getY() - p.getY())*(this.getY() - p.getY()) + (this.getZ() - p.getZ())*(this.getZ() - p.getZ()));
	}
	
	 /**
     * Find the distance by using the distanceSquared function
     * returns the positive square root of the result of the distanceSquared function
     * @param p - the second point
     * @return the distance between the points
     */
	public double distance(Point3D p)
	{
		return Math.sqrt(this.distanceSquared(p));
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D)obj;
        return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
	}
	
	@Override
	public String toString()
	{
		return "(" + x.toString() + ", " + y.toString() + ", " + z.toString() + ")";
	}
}

