package fsa;
public class Vector {
    public double x,y;
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return (int)x;
    }
    public int getY() {
        return (int)y;
    }
    //link a vector to a point to get the end
    public static Vector add(Vector a,Vector b)
    {
        return new Vector(a.x+b.x,a.y+b.y);
    }
    public static Vector minus(Vector a,Vector b)
    {
        return new Vector(a.x-b.x,a.y-b.y);
    }
    public Vector rotate(double t)
    {
        ComplexNumber complexValue = new ComplexNumber(x,y);
        ComplexNumber factor = ComplexNumber.Eto2PIiT(t);
        ComplexNumber result = ComplexNumber.multiply(complexValue, factor);
        return new Vector(result.x,result.y);
    }
}
