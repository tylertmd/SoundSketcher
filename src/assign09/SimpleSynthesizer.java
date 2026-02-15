package assign09;

/**
 * A stubbed implementation of a midi synthesizer.
 *
 * Do not modify this class for Assignment 9.
 *
 * @author CS 1420 course staff
 * @version 2025-10-17
 */
public class SimpleSynthesizer {
	
	/**
     * **Stub method, doesn't do anything yet**
	 * Begins playing the specified pitch on the track.
	 * The note will not end until noteOff is called for the same pitch and track.
	 * The pitch value is clamped between 0 and 127
	 *
	 * This has no effect if the midi system is not available or if the
	 * track number is not valid.
	 *
	 * @param trackNumber - number between 0 and 9
	 * @param pitch - number between 0 and 127 of pitch to turn on
	 */
	public void noteOn(int trackNumber, int pitch) {
            System.out.println("Note on: " + trackNumber + " " + pitch);
	}
	
	/**
     * **Stub method, doesn't do anything yet**
	 * Stops playing the specified pitch on the track.
	 * If the pitch was not already playing, nothing happens.
	 * The pitch value is clamped between 0 and 127
	 *
	 * This has no effect if the midi system is not available or if the
	 * track number is not valid.
	 *
	 * @param trackNumber - number between 0 and 9
	 * @param pitch - number between 0 and 127 of pitch to turn off
	 */
	public void noteOff(int trackNumber, int pitch) {
            System.out.println("Note off: " + trackNumber + " " + pitch);
	}
	/**
     * **Stub method, doesn't do anything yet**
	 * Sets the volume of the specified track.
	 * The value is clamped between 0 and 127.
	 * This has no effect if the midi system is not available or it the
	 * track number index is not valid.
	 *
	 * @param trackNumber - number between 0 and 9 of track to set the volume of
	 * @param volume - value that will be clamped between 0 and 127
	 */
	public void setVolume(int trackNumber, int volume) {
            System.out.println("Set Volume: " + trackNumber + " " + volume);
	}
	
	/**
	 * **Stub method, doesn't do anything yet**
	 * Turns off all notes on all tracks.
	 */
	public void allNotesOff() {}
}
