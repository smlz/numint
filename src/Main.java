/**
 * Hauptklasse mit der main-Methode
 */
public class Main {

    /**
     * Hautmethode.
     * Testet alle angegebenen numerischen Integrationsalgorithmen für die
     * aufgelisteten Einstellungen (Settings).
     *
     * Die Settings beinhalten:
     *  - Die Funktion, von welcher das bestimmte Integral berechnet werden soll.
     *  - Das Interval von a bis b über welches integriert werden soll.
     *  - Das genaue Resultat, welches dabei herauskommen soll.
     *  - Der maximal erlaubte relative Fehler der Näherung.
     *  - Und schliesslich, wie viele Wiederholungen der Berechnung zur Zeitmessung
     *    gemacht werden.
     *
     */
    public void run() {
        double epsilon = 0.0000001;

        Settings[] settingsListe = new Settings[] {
            new Settings(new LineareFunktion(1, 1), 0, 3, 7.5, epsilon, 100000),
            new Settings(new QuadratischeFunktion(1 ,0, 0), 0, 3, 9, epsilon, 1000),
            new Settings(new KubischeFunktion(1, 0, 0, 0), 0, 3, 20.25, epsilon, 100),
            new Settings(new PolynomSummenFunktion(new PolynomFunktion(0.1, 15),
                                                   new PolynomFunktion(-10, 6),
                                                   new PolynomFunktion(50, 1)),
                         0, 1.65, 39.3608103118, epsilon, 10),
            new Settings(new KomplizierteFunktion(), 0.1, 3, 2.8847360777, epsilon, 10)
        };

        NumerischeIntegration[] algorithmen = new NumerischeIntegration[] {
            new TrapezregelI(),
            new TrapezregelII(),
            new TrapezregelIII(),
            new SimpsonRegel()
        };

        for (Settings settings: settingsListe) {
            print("Funktion:  f(x)  = %s", settings.funktion);
            print("Ableitung: f'(x) = %s", settings.funktion.ableitung());
            print("Interval:  i     = [%f, %f]", settings.a, settings.b);
            print("Epsilon:   ε     = %f", settings.relativeGenauigkeit);
            print();
            print("Algorithmus                    | Ausführungszeit");
            print("------------------------------------------------");
            for (NumerischeIntegration algorithmus: algorithmen) {
                long startTime = System.nanoTime();
                double resultat = 0;
                for (int i=0; i<settings.wiederholungen; i++) {
                    resultat = algorithmus.berechneIntegral(settings.funktion,
                                                            settings.a,
                                                            settings.b,
                                                            settings.relativeGenauigkeit);
                }
                long endTime = System.nanoTime();
                if (Math.abs(resultat - settings.resultat)/resultat <= settings.relativeGenauigkeit) {
                    print("%-30s | %12.6f ms", algorithmus, (endTime - startTime) / 1e6);
                } else {
                    print("%-30s | Fehler: %.10f != %.10f", algorithmus, resultat, settings.resultat);
                }
            }
            print("------------------------------------------------");
            print();
            print();
        }
    }

    /**
     * Hilfsklasse zum Speichern aller nötigen Einstellungen.
     */
    class Settings {
        /**
         * Die Funktion, welche integriert werden soll
         */
        public final Funktion funktion;
        /**
         * Anfang des Intervals des bestimmten Integrals
         */
        public final double a;
        /**
         * Ende des Intervals des bestimmten Integrals
         */
        public final double b;
        /**
         * Gewünschte Genauigkeit relativ zum Wert der Näherung des bestimmten Integrals
         */
        public final double relativeGenauigkeit;
        /**
         * Das richtige Resultat, welches bei der numerischen Integration
         * herauskommen soll.
         */
        public final double resultat;
        /**
         * Anzahl Wiederholungen, wie oft der Algorithmus hintereinander
         * ausgeführt wird, um die Zeit zu messen.
         *
         * Diesen Wert erhöhen, falls die Zeitmesswerte zu klein sind, um daraus
         * vernünftige Aussagen daraus zu schliessen.
         *
         * Den Wert verringern, falls die Berechnung zu lange dauert.
         */
        public final int wiederholungen;

        public Settings(Funktion funktion, double a, double b, double resultat, double relativeGenauigkeit, int wiederholungen) {
            this.funktion = funktion;
            this.a = a;
            this.b = b;
            this.relativeGenauigkeit = relativeGenauigkeit;
            this.resultat = resultat;
            this.wiederholungen = wiederholungen;
            if (a >= b || wiederholungen < 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Main-Methode von Java
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    /**
     * Hilfsmethode zum einfacheren Ausgeben von formatierten Daten
     * @see java.lang.String#format(String, Object...)
     */
    static void print(Object format, Object... objects) {
        System.out.println(String.format(format.toString(), objects));
    }

    /**
     * Hilfsmethode zum Ausgeben einer leeren Zeile
     */
    static void print() {
        System.out.println();
    }
}
