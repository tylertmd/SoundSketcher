package assign09;

import java.awt.Color;
import java.io.IOException;

/**
 * When executed, this class measures and plots the time to add elements 
 * to a DynamicArray compared to BetterDynamicArray. The plot is displayed
 * on one's screen.
 * 
 * @author CS 1420 course staff
 * @version 2025-10-28
 *
 * DO NOT MODIFY THIS FILE
 */
public class DynamicArrayTimer {
	
	private static final AudioEvent event = new NoteEvent(1, 1, 1, 1);
	
	/**
	 * Collects the number of seconds required to add n elements to a DynamicArray.
	 * 
	 * @param n - the number of elements to add
	 * @return the number of seconds required to add n elements to a DynamicArray
	 */
    public static double addToDynamicArray(int n) {
        long startTime = System.nanoTime();
        DynamicArray array = new DynamicArray();
        for (int i = 0; i < n; i++) 
            array.add(event);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000_000.0;
    }

	/**
	 * Collects the number of seconds required to add n elements to a BetterDynamicArray.
	 * 
	 * @param n - the number of elements to add
	 * @return the number of seconds required to add n elements to a BetterDynamicArray
	 */
    public static double addToBetterDynamicArray(int n) {
        long startTime = System.nanoTime();
        BetterDynamicArray array = new BetterDynamicArray();
        for (int i = 0; i < n; i++) 
            array.add(event);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000_000.0;
    }

    public static void main(String[] args) throws IOException {
        // Set the desired number of iterations
        int lowerBound = 100;
        int step = 100;
        int iterations = 30;

        // Declare arrays to store our data
        double[] arraySizes = new double[iterations];
        double[] dynamicArrayTimes = new double[iterations];
        double[] betterDynamicArrayTimes = new double[iterations];

        // Time the operations
        for (int i = 0; i < iterations; i++) {
            int n = lowerBound + step * i;
            arraySizes[i] = n;
            System.out.println("Timing arrays of size: " + n);
            betterDynamicArrayTimes[i] = addToBetterDynamicArray(n);
            dynamicArrayTimes[i] = addToDynamicArray(n);
        }

        Plot plot = Plot
                .plot(Plot.plotOpts().title("Time (seconds) to add n items for two dynamic array implementations")
                        .legend(Plot.LegendFormat.TOP))
                .xAxis("n", null).yAxis("seconds", null)
                .series("DynamicArray", Plot.data().xy(arraySizes, dynamicArrayTimes),
                        Plot.seriesOpts().color(Color.RED).marker(Plot.Marker.DIAMOND))
                .series("BetterDynamicArray", Plot.data().xy(arraySizes, betterDynamicArrayTimes),
                        Plot.seriesOpts().color(Color.BLUE).marker(Plot.Marker.CIRCLE));
        plot.show();
    }
}