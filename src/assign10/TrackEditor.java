package assign10;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * A TrackEditor is the interactive GUI component for drawing a sequence of
 * note events or volume changes in a track.
 * 
 * @author CS 1420 course staff and Tyler Davidson
 * @version 11/19/25
 */
public class TrackEditor extends JPanel {
	
	public static enum Mode{NOTE, VOLUME, COPY};
	private Mode mode;
	
	private int trackNumber;
	private SimpleSynthesizer synth;
	private ArrayList<AudioEvent> events;     // The AudioEvents for this track
	private ArrayList<NoteEvent> notesToCopy; // Stores notes during a copy operation
	
	private int columns, rows; // The number of columns and rows in the grid
	private boolean drawing;   // Set to true during drawing operations
	private int currentRow, currentColumn; // Used by drawing operations
	private int noteDuration;  // The duration of a note being drawn
	
	// For defining an area of the grid to copy
	private int copyFromRow, copyFromColumn; // Top and left of area
	private int copyToRow, copyToColumn;     // Bottom and right of area

	public Object setPrefferedSize;
	
	// This pitch range matches a piano. You can change these values if desired.
	private static final int lowestPitch = 21;
	private static final int highestPitch = 108;
	
	/**
	 * Create a new TrackEditor with the default configuration.
	 * 
	 * @param trackNumber assigned to this track in the midi system
	 * @param synthesizer for making sounds
	 * @param sequencer for sequencing note events
	 */
	public TrackEditor(int trackNumber, int songLength, ArrayList<AudioEvent> events, SimpleSynthesizer synth) {
		columns = songLength;
		rows = highestPitch - lowestPitch + 1;
		
		this.trackNumber = trackNumber;
		this.synth = synth;
		this.events = events;
		notesToCopy = new ArrayList<NoteEvent>();
		drawing = false;
		mode = Mode.NOTE;
		
		setBackground(Color.WHITE);
		
		// TODO Uncomment the next two lines when implementing the MouseListener and MouseMotionListener interfaces.
		// addMouseListener(this);
		// addMouseMotionListener(this);
	}
	
	/**
	 * Removes all events from the track.
	 */
	public void clearTrack() {
		events.clear();
		repaint();
	}
	
	/**
	 * Set the song duration in ticks.
	 * 
	 * @param songLength in ticks
	 */
	public void setSongLength(int songLength) {
		columns = songLength;
		if (columns < 1)
			columns = 1;
		repaint();
	}
	
	/**
	 * Set the editor to the specified mode.
	 * 
	 * @param mode - either Mode.NOTE, Mode.VOLUME, or Mode.COPY
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
		// Set volume back to default in case it was changed in volume mode
		synth.setVolume(trackNumber, 100);
	}
	
	/**
	 * This method is called by the system when a component needs to be painted.
	 * Which can be at one of three times: --when the component first appears --when
	 * the size of the component changes (including resizing by the user) --when
	 * repaint() is called
	 * 
	 * Partially overrides the paintComponent method of JPanel.
	 * 
	 * @param g -- graphics context onto which we can draw
	 */
	public void paintComponent(Graphics g) {
		// TODO Call superclass JPanel's paintComponent method to fill in panel with background color.
		
		
		/* 
		 * TODO Draw the volume bars
		 * These should be a very light color so that other colors can be easily seen.
		 * To make a light color, set red, green, and blue components to all be between 230 and 255.
		 * The volume stays constant between volume events, so follow the procedure described here.
		*/
		int previousVolume = 100; // the volume begins with value 100 by default
		int previousTime = 0; // the beginning of the song
		for(AudioEvent event : events) {
			if(event instanceof VolumeEvent) {
				// TODO Draw a rectangle showing the volume prior to this event
				//  - The left edge is from the column corresponding to previousTime.
				//  - The top edge is from the row corresponding to previousVolume (using volumeToRow).
				//  - The right edge is the column corresponding to this event's time.
				//    (The rectangle does not include the column for this event's time)
				//  - The bottom edge is the bottom of the panel.
				// Recall that the fillRect method has width and height parmeters. These are the
				// differences between the right and left edges and between the bottom and top edges.
				
				// TODO Update previousVolume and previousTime with values from this event.
				
			}
		}
		// TODO Draw one more rectangle for the last volume event. Use the instructions for the
		// rectangle from above, but this time the right edge is the right edge of the panel.
		
		
		// TODO Draw the grid using a different, darker color (black is a good choice for this)
		// This involves drawing either lines to show each of the rows and columns in the grid.
		// The number of rows and columns are in the instance variables named rows and columns.
		

		// TODO Draw some thicker lines every few rows to make it easier to use.
		// 12 rows represents one octave, so that is a natural choice for spacing.
		// Thicker lines can be made by drawing a rectangle with height 2.
		
		
		// TODO Draw some thicker lines every few columns to make it easier to use.
		// You can choose a spacing you think works well. A good starting point is every four columns.
		// In this case, draw a rectangle with width 2.
		
		
		// Draw preview only if something is currently being drawn by the mouse
		if(drawing) {
			if(mode == Mode.VOLUME) {
				// TODO Set the color to your volume drawing color and draw a rectangle in
				// the current column from the current row to the bottom of the panel.
				// current column and row are in variables currentRow and currentVolume.
				
			}
			else if(mode == Mode.NOTE && noteDuration > 0){
				// TODO Set the color to a new color for drawing notes. Choose a color that is easy
				// to distinguish from your volume color. Draw a rectangle beginning in the current row and 
				// current column with a height equal to the height of that row (subtract the pixel location
				// of the current row from that of the next row). The noteDuration variable represents
				// the width of this rectangle in number of columns.
				
			}
			else { // mode is Mode.COPY
				// TODO Set the color to a new color for copy selection. To make the background visible
				// behind this rectangle, set the transparency of this color using the fourth argument
				// to the Color constructor, alpha in "new Color(red, green, blue, alpha)". Adjust this
				// transparency value as needed. A good starting point is 100.
				// Draw a rectangle that covers all cells from (copyFromColumn, copyFromRow) to 
				// (copyToColumn, copyToRow)
				
			}
		}
		
		// Draw note events
		// TODO Set the color to your note drawing color.
		
		for(AudioEvent event : events) {
			if(event instanceof NoteEvent) {
				NoteEvent note = (NoteEvent)event;
				// TODO Draw a rectangle for this note. The row is found by passing this note's pitch
				// to the pitchToRow method. The column is this note's time. The height is the height
				// of one row. The width is from this note's duration, which is a number of columns.
				
			}
		}
		
		// Optional: You can draw other indicators if you want, such as middle C at pitch 60.
		
	} // end of paintComponent
	
	// TODO Put methods required by MouseListener and MouseMotionListener here
	
	
	
	
	

	//////////////////////////////////////////////////////////////////////
	// Private helper methods
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * Convert a row index in the grid to a pitch number.
	 * 
	 * @param rowNumber - to convert
	 * @return pitch corresponding to that row
	 */
	private int rowToPitch(int rowNumber) {
		return highestPitch - rowNumber;
	}
	
	/**
	 * Convert a pitch number to a row index in the grid.
	 * 
	 * @param pitch - to convert
	 * @return row index corresponding to that pitch
	 */
	private int pitchToRow(int pitch) {
		return highestPitch - pitch;
	}
	
	/**
	 *  Convert a row index in the grid to a volume value.
	 *  
	 * @param rowNumber - to convert
	 * @return volume value corresponding to that row
	 */
	private int rowToVolume(int rowNumber) {
		return 127 - rowNumber * 127 / rows;
	}
	
	/**
	 * Convert a volume value to a row index in the grid.
	 * 
	 * @param volume - to convert
	 * @return row index corresponding to that volume
	 */
	private int volumeToRow(int volume) {
		return rows - volume * rows / 127;
	}
	
	/**
	 * Converts a row index to pixel y value of the TOP edge of the row.
	 * 
	 * @param row - index
	 * @return pixel y value of the top edge
	 */
	private int rowToPixel(int row) {
		return row * getHeight() / rows;
	}

	/**
	 * Converts a column index to pixel x value of the LEFT edge of the column.
	 * 
	 * @param col - column index
	 * @return pixel x value of the left side
	 */
	private int colToPixel(int col) {
		return col * getWidth() / columns;
	}

	/**
	 * Converts a pixel y value to a row index.
	 * 
	 * @param py - pixel y value
	 * @return index of row containing that pixel
	 */
	private int pixelToRow(int py) {
		return rows * py / getHeight();
	}

	/**
	 * Converts a pixel x value to a column index.
	 * 
	 * @param px - pixel x value
	 * @return index of column containing that pixel
	 */
	private int pixelToCol(int px) {
		return columns * px / getWidth();
	}

	// Required by a serializable class (ignore for now)
	private static final long serialVersionUID = 1L;
}
