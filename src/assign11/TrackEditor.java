package assign11;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

/**
 * A TrackEditor is the interactive GUI component for drawing a sequence of
 * note events or volume changes in a track.
 * 
 * @author CS 1420 course staff and Tyler Davidson
 * @version 11/19/25
 */
public class TrackEditor extends JPanel implements MouseListener, MouseMotionListener {
	
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
	
	// This pitch range matches a piano. You can change these values if desired.
	private static final int lowestPitch = 21;
	private static final int highestPitch = 108;
	
	/**
	 * Create a new TrackEditor with the default configuration.
	 * 
	 * @param trackNumber assigned to this track in the midi system
	 * @param songLength in ticks (columns)
	 * @param events the list of audio events belonging to this track
	 * @param synth synthesizer for making sounds
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
		
		addMouseListener(this);
		addMouseMotionListener(this);
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

		super.paintComponent(g);
		
		int previousVolume = 100; // the volume begins with value 100 by default
		int previousTime = 0; // the beginning of the song
			
		for (AudioEvent event : events) {
		    if (event instanceof VolumeEvent) {
		        VolumeEvent volumeEvent = (VolumeEvent) event;

		        int leftX = colToPixel(previousTime);
		        int rightX = colToPixel(volumeEvent.getTime());
		        int volRow = volumeToRow(previousVolume);
		        int topY = rowToPixel(volRow);
		        int bottomY = getHeight();

		        int width = Math.max(1, rightX - leftX);
		        int height = Math.max(1, bottomY - topY);

		        g.setColor(new Color(173, 216, 230, 200));
		        g.fillRect(leftX, topY, width, height);

		        g.setColor(new Color(0, 102, 204));
		        g.drawRect(leftX, topY, Math.max(1, width - 1), Math.max(1, height - 1));

		        previousTime = volumeEvent.getTime();
		        previousVolume = volumeEvent.getValue(); 
		    }
		}

		int leftX = colToPixel(previousTime);
		int rightX = colToPixel(columns);
		int volRow = volumeToRow(previousVolume);
		int topY = rowToPixel(volRow);
		int bottomY = getHeight();
		int width = Math.max(1, rightX - leftX);
		int height = Math.max(1, bottomY - topY);
		
		g.setColor(new Color(173, 216, 230, 200));
		g.fillRect(leftX, topY, width, height);
		g.setColor(new Color(0, 102, 204));
		
		g.drawRect(leftX, topY, Math.max(1, width - 1), Math.max(1, height - 1));
		

		g.setColor(Color.BLACK);

		for (int c = 0; c <= columns; c++) {
			int x = colToPixel(c);
			g.drawLine(x, 0, x, getHeight());
		}

		for (int r = 0; r <= rows; r++) {
			int y = rowToPixel(r);
			g.drawLine(0, y, getWidth(), y);
		}
		
		for (int r = 0; r <= rows; r += 12) {
			int y = rowToPixel(r);
			g.fillRect(0, Math.max(0, y - 1), getWidth(), 2);
		}
		for (int c = 0; c <= columns; c += 4) {
			int x = colToPixel(c);
			g.fillRect(Math.max(0, x - 1), 0, 2, getHeight());
		}
		
		// Draw preview only if something is currently being drawn by the mouse
		
		if (drawing) {
			if (mode == Mode.VOLUME) {
				g.setColor(new Color(255, 236, 0));
				
				int x = colToPixel(currentColumn);
				int y = rowToPixel(currentRow);
				
				g.fillRect(x, y, Math.max(1, colToPixel(currentColumn + 1) - x), getHeight() - y);
			}
			else if (mode == Mode.NOTE && noteDuration > 0) {
				g.setColor(new Color(80, 160, 80)); 
				
				int x = colToPixel(currentColumn);
				int y = rowToPixel(currentRow);
				int w = Math.max(2, colToPixel(currentColumn + noteDuration) - x);
				int h = Math.max(2, rowToPixel(currentRow + 1) - y);
				
				g.fillRect(x, y, w, h);
			}
			else if (mode == Mode.COPY) {
				g.setColor(new Color(255, 0, 255, 100));
				
				int left = Math.min(copyFromColumn, copyToColumn);
				int right = Math.max(copyFromColumn, copyToColumn);
				int top = Math.min(copyFromRow, copyToRow);
				int bottom = Math.max(copyFromRow, copyToRow);
				int x = colToPixel(left);
				int y = rowToPixel(top);
				int w = Math.max(1, colToPixel(right + 1) - x);
				int h = Math.max(1, rowToPixel(bottom + 1) - y);
				
				g.fillRect(x, y, w, h);
				g.setColor(Color.MAGENTA);
				
				g.drawRect(x, y, w - 1, h - 1);
			}
		}
		
		// Draw note events
		
		g.setColor(new Color(255, 36, 0));
		for (AudioEvent event : events) {
			if (event instanceof NoteEvent) {
				NoteEvent note = (NoteEvent) event;
				
				int row = pitchToRow(note.getPitch());
				int x = colToPixel(note.getTime());
				int y = rowToPixel(row);
				int w = Math.max(2, colToPixel(note.getTime() + note.getDuration()) - x);
				int h = Math.max(2, rowToPixel(row + 1) - y);
				
				g.fillRect(x + 1, y + 1, Math.max(1, w - 2), Math.max(1, h - 2));
			}
		}
		
		// Optional: You can draw other indicators if you want, such as middle C at pitch 60.
		
	} // end of paintComponent
	

	/**
	 * Handles the mouse press event, typically when the left mouse button is clicked.
	 * This method determines the user's intended action based on the current
	 * mode note, volume, or copy and updates accordingly.
	 * It also converts the pixel coordinates of the click into grid column and row indices.
	 *
	 * @param e The MouseEvent object that contains information about the click,
	 * such as button pressed and coordinates.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (e.getButton() != MouseEvent.BUTTON1) return;
		drawing = true;
		int x = e.getX();
		int y = e.getY();
		currentColumn = pixelToCol(x);
		currentRow = pixelToRow(y);
		noteDuration = 1;
		
		if (mode == Mode.NOTE) {
			int pitch = rowToPitch(currentRow);
			synth.noteOn(trackNumber, pitch);
			noteDuration = 1;
		} else if (mode == Mode.VOLUME) {
			int vol = rowToVolume(currentRow);
			synth.setVolume(trackNumber, vol);

			synth.noteOn(trackNumber, 60);
		} else if (mode == Mode.COPY) {
			copyFromColumn = currentColumn;
			copyFromRow = currentRow;
			copyToColumn = currentColumn;
			copyToRow = currentRow;
		}
		repaint();
	}
	
	/**
	 * Processes the end of a mouse click or drag operation when released.
	 * If an action was started, this method finalizes
	 * the action based on the current mode, such as saving a new event or completing a copy selection.
	 *
	 * @param e The MouseEvent object containing release details.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

		if (!drawing) return;
		drawing = false;
		
		if (mode == Mode.NOTE) {

			synth.noteOff(trackNumber, rowToPitch(currentRow));
			if (noteDuration > 0) {
				NoteEvent ne = new NoteEvent(currentColumn, trackNumber, noteDuration, rowToPitch(currentRow));
				events.add(ne);
				Collections.sort(events);
			}
		} else if (mode == Mode.VOLUME) {
	
			synth.noteOff(trackNumber, 60);
			int vol = rowToVolume(currentRow);
			VolumeEvent ve = new VolumeEvent(currentColumn, trackNumber, vol);
			events.add(ve);
			Collections.sort(events);
		} else if (mode == Mode.COPY) {

			notesToCopy.clear();
			int left = Math.min(copyFromColumn, copyToColumn);
			int right = Math.max(copyFromColumn, copyToColumn);
			int top = Math.min(copyFromRow, copyToRow);
			int bottom = Math.max(copyFromRow, copyToRow);
			for (AudioEvent a : events) {
				if (a instanceof NoteEvent) {
					NoteEvent ne = (NoteEvent) a;
					int t = ne.getTime();
					int r = pitchToRow(ne.getPitch());
					if (t >= left && t <= right && r >= top && r <= bottom) {
					
						notesToCopy.add(new NoteEvent(ne.getTime(), ne.getTrackNumber(), ne.getDuration(), ne.getPitch()));
					}
				}
			}
		}
		repaint();
	}
	
	/**
	 * Handles the mouse drag event, which occurs when the mouse button is held down
	 * and the mouse is moved. This method is used to extend the duration of a note,
	 * dynamically adjust volume, or define the selection area for a copying.
	 * It only executes if an interaction was initiated by MousePressed.
	 *
	 * @param e The MouseEvent object containing the current mouse coordinates.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!drawing) return;
		int x = e.getX();
		int y = e.getY();
		int col = pixelToCol(x);
		int row = pixelToRow(y);
		
		if (mode == Mode.NOTE) {
	
			noteDuration = col - currentColumn + 1;
			if (noteDuration < 1) noteDuration = 1;
			if (row != currentRow) {
				
				synth.noteOff(trackNumber, rowToPitch(currentRow));
				currentRow = row;
				synth.noteOn(trackNumber, rowToPitch(currentRow));
			}
		}
		else if (mode == Mode.VOLUME) {
			if (row != currentRow) {
				currentRow = row;
				int vol = rowToVolume(currentRow);
				synth.setVolume(trackNumber, vol);
				synth.noteOn(trackNumber, 60); 
			}
		}
		else if (mode == Mode.COPY) {
			copyToColumn = col;
			copyToRow = row;
		}
		repaint();
	}
	
	/**
	 * Handles a right mouse click event.
	 * This method primarily handles deleting a note or pasting a note.
	 *
	 * @param e The MouseEvent object containing details about the click.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) return;
		
		int col = pixelToCol(e.getX());
		int row = pixelToRow(e.getY());
		
		if (mode == Mode.NOTE) {
	
			for (int i = 0; i < events.size(); i++) {
				AudioEvent a = events.get(i);
				if (a instanceof NoteEvent) {
					NoteEvent ne = (NoteEvent) a;
					if (ne.getTime() == col && pitchToRow(ne.getPitch()) == row) {
						events.remove(i);
						break;
					}
				}
			}
		} else if (mode == Mode.VOLUME) {
		
		} else if (mode == Mode.COPY) {

			int timeOffset = col - copyFromColumn;
			int pitchOffset = row - copyFromRow;
			for (NoteEvent original : notesToCopy) {
				int newTime = original.getTime() + timeOffset;
				int originalRow = pitchToRow(original.getPitch());
				int targetRow = originalRow + pitchOffset;
		
				if (targetRow < 0) targetRow = 0;
				if (targetRow >= rows) targetRow = rows - 1;
				int targetPitch = rowToPitch(targetRow);
		
				NoteEvent ne = new NoteEvent(newTime, trackNumber, original.getDuration(), targetPitch);
				events.add(ne);
			}
			Collections.sort(events);
		}
		repaint();
	}
	
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {}
	
	
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
	 * @param pixelY - pixel y value
	 * @return index of row containing that pixel
	 */
	private int pixelToRow(int pixelY) {
		return rows * pixelY / getHeight();
	}

	/**
	 * Converts a pixel x value to a column index.
	 * 
	 * @param pixelX - pixel x value
	 * @return index of column containing that pixel
	 */
	private int pixelToCol(int pixelX) {
		return columns * pixelX / getWidth();
	}

	// Required by a serializable class (ignore for now)
	private static final long serialVersionUID = 1L;
}
