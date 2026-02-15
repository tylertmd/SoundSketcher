package assign11;

/**
 * This class serves as a base for all events that occur in an audio track.
 * Each event happens at a specific time and belongs to a particular track number.
 * 
 * Subclasses contain audio events like notes and changing volume. 
 * Implements comparable.
 * 
 * @author Tyler Davidson
 * @version 10/30/25
 */
public abstract class AudioEvent implements Comparable<AudioEvent> {
	
	private int time;
	private int trackNumber;

	/**
	 * Constructs a new audio event with the given time and track number.
	 * 
	 * @param time the time at which the event occurs. Can't be negative.
	 * @param trackNumber the track number (0-9) this event belongs to.
	 * @throws IllegalArugmentExcpetion if time is negative, or if track number is outside of the range (0-9).
	 */
	public AudioEvent(int time, int trackNumber) {
		
		this.time = time;
		this.trackNumber = trackNumber;
		
		if (time < 0) {
			throw new IllegalArgumentException("Time cannont be negative: " + time);
		}
		if (trackNumber < 0 || trackNumber > 9) {
			throw new IllegalArgumentException("Track number must be between 0 and 9: " + trackNumber);
		}	
	}
	
	/**
	 * Returns the time when this audio event occurs.
	 * 
	 * @return the event time.
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Returns the track number this audio event belongs to.
	 * 
	 * @return the track number.
	 */
	public int getTrackNumber() {
		return trackNumber;
	}
	
	/**
	 * Executes the behavior of this event on the given synthesizer.
	 * 
	 * @param synth the SimpleSynthesizer to execute this event.
	 */
	public abstract void execute(SimpleSynthesizer synth);
	
	/**
	 * Compares this audio event to another based on time and track number.
	 * 
	 * @param other the other audio event to compare to.
	 * @return a negative integer, zero, or a positive integer as this audio event is less than, equal to, or greater than the event.
	 */
	public abstract int compareTo(AudioEvent other);
}
