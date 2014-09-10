/**
 * Eine abstrakte Funktion
 */
public abstract class Funktion {
    /**
     * Den Funktionswert an der Stelle x berechnen.
     */
    public abstract double anwenden(double x);

    /**
     * Eine neue Funktion, welche der Ableitung dieser Funktion entspricht
     * zurückgeben.
     */
    public abstract Funktion ableitung();
}

class KonstanteFunktion extends Funktion {
    double c;

    /** f(x) = c */
    public KonstanteFunktion(double c) {
        this.c = c;
    }
    public double anwenden(double x) {
        return this.c;
    }
    public Funktion ableitung() {
        return new KonstanteFunktion(0);
    }
    public String toString() {
        return String.format("%+5.2f", this.c);
    }
}

class LineareFunktion extends Funktion {
    double a, c;

    /** f(x) = a*x + c */
    public LineareFunktion(double a, double c) {
        this.a = a; this.c = c;
    }
    public double anwenden(double x) {
        return this.a * x + this.c;
    }
    public Funktion ableitung() {
        return new KonstanteFunktion(this.a);
    }
    public String toString() {
        return String.format("%+5.2f·x %+5.2f", this.a, this.c);
    }
}

class QuadratischeFunktion extends Funktion {
    double a, b, c;

    /** f(x) = a*x^2 + b*x + c */
    public QuadratischeFunktion(double a, double b, double c) {
        this.a = a; this.b = b; this.c = c;
    }
    public double anwenden(double x) {
        return this.a * x * x + this.b * x + this.c;
    }
    public Funktion ableitung() {
        return new LineareFunktion(2 * this.a, this.b);
    }
    public String toString() {
        return String.format("%+5.2f·x² %+5.2f·x %+5.2f",
                             this.a, this.b, this.c);
    }
}

class KubischeFunktion extends Funktion {
    double a, b, c, d;

    /** f(x) = a*x^3 + b*x^2 + c*x + d */
    public KubischeFunktion(double a, double b, double c, double d) {
        this.a = a; this.b = b; this.c = c; this.d = d;
    }
    public double anwenden(double x) {
        return this.a * Math.pow(x, 3) + this.b * Math.pow(x, 2) +
                this.c * x + this.d;
    }
    public Funktion ableitung() {
        return new QuadratischeFunktion(3 * this.a, 2 * this.b, this.c);
    }
    public String toString() {
        return String.format("%+5.2f·x³ %+5.2f·x² %+5.2f·x %+5.2f",
                this.a, this.b, this.c, this.d);
    }
}

class PolynomFunktion extends Funktion {
    double a;
    int n;
    /** f(x) = a * x^n */
    public PolynomFunktion(double a, int n) {
        if (n < 0)
            throw new IllegalArgumentException("Der Exponent des Polynoms muss positiv sein");
        this.a = a;
        this.n = n;
    }
    public double anwenden(double x) {
        return this.a * Math.pow(x, this.n);
    }
    public PolynomFunktion ableitung() {
        if (this.n == 0)
            return new PolynomFunktion(0, 0);
        else
            return new PolynomFunktion(this.a * this.n, this.n - 1);
    }
    public String toString() {
        return String.format("%+5.2f·x^%d", this.a, this.n);
    }
}

class PolynomSummenFunktion extends Funktion {
    PolynomFunktion[] polynome;
    public PolynomSummenFunktion(PolynomFunktion... polynome) {
        this.polynome = polynome;
    }
    public double anwenden(double x) {
        double summe = 0.0;
        for (Funktion f: this.polynome) {
            summe += f.anwenden(x);
        }
        return summe;
    }
    public Funktion ableitung() {
        PolynomFunktion[] ableitungen = new PolynomFunktion[this.polynome.length];
        for (int i=0; i<this.polynome.length; i++) {
            ableitungen[i] = this.polynome[i].ableitung();
        }
        return new PolynomSummenFunktion(ableitungen);
    }
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Funktion f: this.polynome) {
            b.append(f.toString());
            b.append(' ');
        }
        return b.toString();
    }
}

class KomplizierteFunktion extends Funktion {
    public double anwenden(double x) {
        return Math.exp(-x) * Math.sin(8 * Math.pow(x, 2.0/3.0)) + 1;
    }
    public Funktion ableitung() {
        return new KomplizerteFunktionAbleitung();
    }
    public String toString() {
        return "e^(-x) · sin(8 · x^(2/3)) + 1";
    }
}

class KomplizerteFunktionAbleitung extends Funktion {
    public double anwenden(double x) {
        return Math.exp(-x) * ((16 * Math.cos(8 * Math.pow(x, 2.0/3.0)))/(3 * Math.pow(x, 1.0/3.0)) - Math.sin(8 * Math.pow(x, 2.0/3.0)));
    }
    public Funktion ableitung() {
        throw new UnsupportedOperationException();
    }
    public String toString() {
        return "1/3 · e^(-x) · [(16·cos(8·x^(2/3))/x^(1/3) - 3·sin(8·x^(2/3))]";
    }
}
