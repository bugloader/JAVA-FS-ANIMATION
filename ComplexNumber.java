package fsa;
public class ComplexNumber {
    double x, y;
    final static double TWO_PI = Math.PI * 2;
    public ComplexNumber(double x, double y)//real imaginary || x y
    {
        this.x = x;
        this.y = y;
    }
    public static ComplexNumber addition(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.x+b.x,a.y+b.y);
    }
    //(a + bi)(c + di)=(ac - bd) + (ad+bc)i
    public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.x * b.x - a.y * b.y, a.x * b.y + a.y * b.x);
    }
    //e^(ix) = cos(x) + i sin(x)
    public static ComplexNumber Eto2PIiT(double t) {
        return new ComplexNumber(
                Math.cos(t * TWO_PI), Math.sin(t * TWO_PI));
    }
    // z * e^ix or z rotate by radian of x
    public static ComplexNumber rotate(ComplexNumber z, double x, double t) {
        ComplexNumber factor = Eto2PIiT(x*t);
        return multiply(z, factor);
    }
}