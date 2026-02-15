package assign08;

/**
 * The volume event class represents a change in volume on a specific track at a given time. It sets the tracks volume to a specified value from 0 - 127.
 *
 * @author Tyler Davidson
 * @version 10/30/25
 */
public class VolumeEvent extends AudioEvent{

	int value;
	
	/**
	 * Constructs a new volume event with the given time, track number, and value.
	 * 
	 * @param time the time when this volume change occurs.
	 * @param trackNumber the track this event affects (0-9)
	 * @param value the volume level to set (0-127).
	 * @throws IllegalArgumentException if value is outside of the (0-127) range.
	 */
	public VolumeEvent(int time, int trackNumber, int value) {
		
		super(time, trackNumber);
		
		this.value = value;
		
		if (value < 0 || value > 127) {
			throw new IllegalArgumentException("Value must be between the range of 0 and 127: " + value);
		}
	}
	
	/**
	 * Returns the volume of this event.
	 * 
	 * @return the volume level.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Compares this volume event to another audio event. Earlier time comes first.
	 * 
	 * 
	 * @param other the other audio event to compare to.
	 * @return a negative integer, zero, or a positive integer as this event should come before, is equal to, or comes after the other event.
	 */
	public int compareTo(AudioEvent other) {
		
	    if (other instanceof NoteEvent) {
	        return 1;
	    }

	    if (other instanceof VolumeEvent) {
	        VolumeEvent otherVolume = (VolumeEvent) other;
	        if (this.getTime() < otherVolume.getTime()) {
	            return -1;
	        }
	        if (this.getTime() > otherVolume.getTime()) {
	            return 1;
	        }
	        return 0;
	    }
	    
	    return 0;
	}
	
	/**
	 * Executes this event on the given synthesizer, settings the track's volume to this event's value.
	 * 
	 * @param synth the SimpleSynthesizer used to apply the volume change.
	 */
	public void execute(SimpleSynthesizer synth) {
		synth.setVolume(getTrackNumber(), value);	
	}
	
	/**
	 * Returns a string representation of this volume event.
	 * 
	 * @return a string in the format "Volume[time, trackNumber, value]"
	 */
	public String toString() {
		return "Volume[" + getTime() + ", " + getTrackNumber() + ", " + value + "]";
	}
}
