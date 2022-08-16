public class NBody {
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments.
        double tau = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        double G = 6.67 * Math.pow(10, -11);

        // Step 2. Read universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] Fx = new double[n];
        double[] Fy = new double[n];
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];
        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // Step 3. Initialize standard drawing.
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // Step 4. Play music on standard audio.
        StdAudio.play("2001.wav");

        // Step 5. Simulate the universe.
        for (double t = 0; t < tau; t += dt) {
            for (int i = 0; i < n; i++) {
                Fx[i] = 0;
                Fy[i] = 0;
            }

            // Step 5A. Calculate net forces.
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    if (i != j) {
                        double dx = px[i] - px[j];
                        double dy = py[i] - py[j];
                        double r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                        double F = (G * mass[i] * mass[j]) / Math.pow(r, 2);
                        double Fcosd = (F * dx) / r;
                        double Fsind = (F * dy) / r;
                        Fx[j] += Fcosd;
                        Fy[j] += Fsind;
                    }
                }
            }

            // Step 5B. Update velocities and positions.
            for (int i = 0; i < n; i++) {
                double ax = Fx[i] / mass[i];
                double ay = Fy[i] / mass[i];
                vx[i] = vx[i] + ax * dt;
                vy[i] = vy[i] + ay * dt;
                px[i] = px[i] + vx[i] * dt;
                py[i] = py[i] + vy[i] * dt;
            }

            StdDraw.picture(0, 0, "starfield.jpg");
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);
            }
            
            //Step 5C. Draw universe to standard drawing.
            StdDraw.show();
            StdDraw.pause(20);
        }

        // Step 6. Print universe to standard output.
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
