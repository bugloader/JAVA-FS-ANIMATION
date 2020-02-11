package fsa;
import java.util.ArrayList;
public class Fouriers {
    final Vector origin;
    int length;
    int bounds;
    Vector constant;
    Vector[] timeDomainSignal;
    Vector[] positiveVectors, negativeVectors;
    public Fouriers(Vector origin, ArrayList<Vector> points, int bounds) {
        this.origin = origin;
        positiveVectors = new Vector[bounds];
        negativeVectors = new Vector[bounds];
        timeDomainSignal = new Vector[points.size()];
        length = points.size();
        for (int i = 0; i < length; i++) {
            timeDomainSignal[i]
                    = Vector.minus(points.get(i), origin);
        }
        this.bounds = bounds;
    }
    //constant = Σ(f_t)/t
    public void generateConstantVector() {
        Vector sum = new Vector(0, 0);
        for (int i = 0; i < length; i++) {
            sum = Vector.add(sum, timeDomainSignal[i]);
        }
        constant = new Vector(sum.x / length, sum.y / length);
    }
    //f_t= Σ(c_n*e^(2πnit))
    //∫f_t dt= ∫(Σ(c_n*e^(2πnit))) dt
    //Σ(f_t)/t= Σ(Σ(c_n*e^(2πnit))/t)
    //Σt(f_t*e^(-2πxit))/t = Σt(e^(-2πxit)Σn(c_n*e^(2πnit))/t)
    public void generateAllVectors() {
        ComplexNumber c_x, c_xNeg, f_t;
        for (int i = 0; i < bounds; i++) {
            c_x = new ComplexNumber(0, 0);
            c_xNeg = new ComplexNumber(0, 0);
            //c_x = Σt(f_t*e^(-2πxit))/t
            //c_-x = Σt(f_t*e^(2πxit))/t
            for (int j = 0; j < length; j++) {
                f_t = new ComplexNumber(
                        timeDomainSignal[j].x, timeDomainSignal[j].y);
                c_x = ComplexNumber.addition(c_x,
                        ComplexNumber.rotate(f_t, i + 1, -(double) j / length));
                c_xNeg = ComplexNumber.addition(c_xNeg,
                        ComplexNumber.rotate(f_t, i + 1, (double) j / length));
            }
            positiveVectors[i] = new Vector(c_x.x/length,c_x.y/length);
            negativeVectors[i] = new Vector(c_xNeg.x/length,c_xNeg.y/length);
        }
    }
}
