/**
 * Abstrakte Basis-Klasse für verschiedene numerische Integrationsverfahren.
 */
public abstract class NumerischeIntegration {

    /**
     * Die Näherung eines bestimmten Integrals berechnen
     * @param f        Die Funktion, welche integriert wird.
     * @param a        Der Start des Intervals, über welches integriert wird.
     * @param b        Das Ende des Intervals, über welches integriert wird.
     * @param epsilon  Die Gnauigkeit der Näherung relativ gemessen zum Resultat.
     * @return         Der Wert der berechneten Näherung des bestimmten Integrals
     */
    public abstract double berechneIntegral(Funktion f, double a, double b, double epsilon);

    /**
     * Name des Integrationsverfahrens (name der Klasse)
     */
    public String toString() {
        return this.getClass().getName();
    }
}

/**
 * Trapezregel I: Sehnentrapezregel
 */
class TrapezregelI extends NumerischeIntegration {
    public double berechneIntegral(Funktion f, double a, double b, double epsilon) {
        int anzahlStreifen = 1;
        double Tn = -1;
        double Tn1 = (b - a) / 2 * (f.anwenden(a) + f.anwenden(b));

        while (((Math.abs(Tn1 - Tn) / Math.abs(Tn1)) > epsilon)) {
            Tn = Tn1;
            Tn1 = 0.0;
            anzahlStreifen = anzahlStreifen * 2;
            double h = (b - a) / anzahlStreifen;
            for (int i=0; i<anzahlStreifen; i++) {
                double links = a + i * h;  // Linker Wert des Trapezes auf der X-Achse
                double rechts = links + h; // Rechter ...

                // Einzelne Trapezflächen zur aktuellen Näherung addieren
                Tn1 += h / 2.0 * (f.anwenden(links) + f.anwenden(rechts));
            }
        }
        return Tn1;
    }
}

/**
 * Trapezregel II: Tangententrapezregel
 */
class TrapezregelII extends NumerischeIntegration {
    public double berechneIntegral(Funktion f, double a, double b, double epsilon) {
        Funktion f_ = f.ableitung();

        int anzahlStreifen = 1;
        double Tn = -1;
        double Tn1 = (b - a) / 2 * (f.anwenden(a) + f.anwenden(b)) +
                (b - a) * (b - a) / 12.0 * (f_.anwenden(a) - f_.anwenden(b));

        while (((Math.abs(Tn1 - Tn) / Math.abs(Tn1)) > epsilon)) {
            Tn = Tn1;
            Tn1 = 0.0;
            anzahlStreifen = anzahlStreifen * 2;
            double h = (b - a) / anzahlStreifen;
            for (int i=0; i<anzahlStreifen; i++) {
                double links = a + i * h;  // Linker Wert des Trapezes auf der X-Achse
                double rechts = links + h; // Rechter ...

                // Einzelne Trapezflächen zur aktuellen Näherung addieren
                Tn1 += h / 2.0 * (f.anwenden(links) + f.anwenden(rechts)) +
                        h * h / 4.0 * (f_.anwenden(links) - f_.anwenden(rechts));
            }
        }
        return Tn1;
    }
}

/**
 * Trapezregel III: Kombination aus Trapezregel I und II
 */
class TrapezregelIII extends NumerischeIntegration {
    public double berechneIntegral(Funktion f, double a, double b, double epsilon) {
        Funktion f_ = f.ableitung();

        int anzahlStreifen = 1;
        double Tn = -1;
        double Tn1 = (b - a) / 2 * (f.anwenden(a) + f.anwenden(b)) +
                (b - a) * (b - a) / 12.0 * (f_.anwenden(a) - f_.anwenden(b));

        while (((Math.abs(Tn1 - Tn) / Math.abs(Tn1)) > epsilon)) {
            Tn = Tn1;
            Tn1 = 0.0;
            anzahlStreifen = anzahlStreifen * 2;
            double h = (b - a) / anzahlStreifen;
            for (int i=0; i<anzahlStreifen; i++) {
                double links = a + i * h;  // Linker Wert des Trapezes auf der X-Achse
                double rechts = links + h; // Rechter ...

                // Einzelne Trapezflächen zur aktuellen Näherung addieren
                Tn1 += h / 2.0 * (f.anwenden(links) + f.anwenden(rechts)) +
                        h * h / 12.0 * (f_.anwenden(links) - f_.anwenden(rechts));
            }
        }
        return Tn1;
    }
}

/**
 * Simpsonregel (aka. Kepplersche Fassregel)
 */
class SimpsonRegel extends NumerischeIntegration {
    public double berechneIntegral(Funktion f, double a, double b, double epsilon) {

        int anzahlStreifen = 1;
        double Tn = -1;
        double Tn1 = (b - a) / 6.0 *
                (f.anwenden(a) + 4 * f.anwenden((a + b) / 2.0) + f.anwenden(b));

        while (((Math.abs(Tn1 - Tn) / Math.abs(Tn1)) > epsilon)) {
            Tn = Tn1;
            Tn1 = 0.0;
            anzahlStreifen = anzahlStreifen * 2;
            double h = (b - a) / anzahlStreifen;
            for (int i=0; i<anzahlStreifen; i++) {
                double links = a + i * h;  // Linker x-Wert
                double rechts = links + h; // Rechter x-Wert

                // Einzelne Flächen zur aktuellen Näherung addieren
                Tn1 += h / 6.0 *
                       (f.anwenden(links) + 4 * f.anwenden((links + rechts) / 2.0) + f.anwenden(rechts));
            }
        }
        return Tn1;
    }
}
