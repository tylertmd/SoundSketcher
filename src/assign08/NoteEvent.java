package assign08;

/**
 * This class represents a musical note being played on a specific track at a given time. Includes duration and pitch.
 * 
 * @author Tyler Davidson
 * @version 10/30/25
 */
public class NoteEvent extends AudioEvent {
	
	private int duration;
	private int pitch;
	
	/**
	 * Constructs a new note event with given time, track number, duration, and pitch.
	 * 
	 * @param time the time at which this note begins playing.
	 * @param trackNumber the track this note belongs to (0-9).
	 * @param duration how long the note lasts. Can't be negative.
	 * @param pitch the pitch of the note. (0-127).
	 * @throws IllegalArgumentException if duration is negative or if pitch is outside the range.
	 */
	public NoteEvent(int time, int trackNumber, int duration, int pitch) {

		super(time, trackNumber);
		
		this.duration = duration;
		this.pitch = pitch;
		
		if (duration < 0) {
			throw new IllegalArgumentException("Duration canont be negative: " + duration);
		}
		if (pitch < 0 || pitch > 127) {
			throw new IllegalArgumentException("Pitch must be between the range of 0 and 127: " + pitch);
		}
	}
	
	/**
	 * Returns the duration of this note.
	 * 
	 * @return the duration in time units.
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Returns the pitch of this note.
	 * 
	 * @return the pitch value. (0-127).
	 */
	public int getPitch() {
		return pitch;
	}
	
	/**
	 * Stops this note on the given synthesizer.
	 * 
	 * @param synth the SimpleSyntesizer to stop the note.
	 */
	public void complete(SimpleSynthesizer synth) {
		synth.noteOff(getTrackNumber(), pitch);
	}
	
	/**
	 * Compares this note event to another audio event. Higher pitch comes first.
	 * 
	 * @param other the other audio event to compare.
	 * @return a negative integer, zero, or a positive integer if this event should come before, is equal to, or comes after the other event.
	 */
	public int compareTo(AudioEvent other) {
		
		if (other instanceof VolumeEvent) {
			return -1;
		}
		
		if (other instanceof NoteEvent) {
			NoteEvent otherNote = (NoteEvent) other;
			
			if (this.pitch > otherNote.pitch) {
				return -1;
			}
			if (this.pitch < otherNote.pitch) {
				return 1;
			}
			return 0;
		}
		
		return 0;
	}

	/**
	 * Executes this note on the given synthesizer, starting play back.
	 * 
	 * @param synth the SimpleSynthesizer used to play the note.
	 */
	public void execute(SimpleSynthesizer synth) {
		synth.noteOn(getTrackNumber(), pitch);	
	}
	
	/**
	 * Returns a string representation of this note event.
	 * 
	 * @return the string in the format "Note[time, trackNumber, duration, pitch]".
	 */
	public String toString() {
		return "Note[" + getTime() + ", " + getTrackNumber() + ", " + duration + ", " + pitch + "]";
	}
}
