package assign11;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Represents the panel for a single track centered within the Sound Sketcher
 * application. Implements controls in the track panel and handles action
 * listener for those controls.
 * 
 * @author Tyler Davidson
 * @version 11/13/25
 */
public class TrackPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int trackNumber;
	private final SimpleSynthesizer synth;
	private final TrackEditor trackEditor;

	private JComboBox<String> instrumentSelector;
	private JToggleButton muteButton;
	private JToggleButton noteModeButton;
	private JToggleButton volumeModeButton;
	private JToggleButton copyPasteModeButton;
	private JButton clearButton;

	/**
	 * Constructs a TrackPanel for a specific track number. Initializes all track
	 * controls, setups up the layout, and connects action listener.
	 * 
	 * @param trackNumber The index of the track this panel controls.
	 * @param songLength  The duration of the song in ticks.
	 * @param events      The list of audio events belonging to this track
	 * @param synth       The SimpleSynthesizer instance used to track properties
	 */
	public TrackPanel(int trackNumber, int songLength, ArrayList<AudioEvent> events, SimpleSynthesizer synth) {

		this.trackNumber = trackNumber;
		this.synth = synth;

		setLayout(new BorderLayout());
		
		setPreferredSize(new Dimension(800, 600));
	    setMinimumSize(new Dimension(500, 400));

		this.trackEditor = new TrackEditor(trackNumber, songLength, events, synth);

		JPanel controlBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

		JPanel instrumentGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
		instrumentGroup.add(new JLabel("Instrument: "));
		this.instrumentSelector = new JComboBox<String>(new Vector<String>(synth.getInstrumentNames()));
		instrumentGroup.add(this.instrumentSelector);

		controlBar.add(instrumentGroup);

		this.muteButton = new JToggleButton("Mute");
		controlBar.add(this.muteButton);

		ButtonGroup modeGroup = new ButtonGroup();

		this.noteModeButton = new JToggleButton("Note Mode");
		this.volumeModeButton = new JToggleButton("Volume Mode");
		this.copyPasteModeButton = new JToggleButton("Copy/Paste");

		this.noteModeButton.setSelected(true);

		modeGroup.add(this.noteModeButton);
		modeGroup.add(this.volumeModeButton);
		modeGroup.add(this.copyPasteModeButton);

		controlBar.add(this.noteModeButton);
		controlBar.add(this.volumeModeButton);
		controlBar.add(this.copyPasteModeButton);

		this.clearButton = new JButton("Clear Track");
		controlBar.add(this.clearButton);

		add(controlBar, BorderLayout.NORTH);

		instrumentGroup.setBackground(new Color(230, 230, 230));
		controlBar.setBackground(new Color(230, 230, 230));

		muteButton.addActionListener(this);
		noteModeButton.addActionListener(this);
		volumeModeButton.addActionListener(this);
		copyPasteModeButton.addActionListener(this);
		clearButton.addActionListener(this);
		instrumentSelector.addActionListener(this);

		add(this.trackEditor, BorderLayout.CENTER);
	}

	/**
	 * Sets the instrument for this track in the synthesizer and updates UI.
	 * 
	 * @param instrument The instrument ID to be set.
	 */
	public void setInstrument(int instrument) {

		synth.setInstrument(this.trackNumber, instrument);

		instrumentSelector.setSelectedIndex(instrument);
	}

	/**
	 * Passes the new total song length to the Track Editor for updates.
	 * 
	 * @param songLength The new song duration in ticks.
	 */
	public void setSongLength(int songLength) {

		trackEditor.setSongLength(songLength);
	}

	/**
	 * Handles all action events originating from the buttons, toggles, and combo
	 * box within the track panel.
	 * 
	 * @param e The action event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == muteButton) {
			boolean isMuted = muteButton.isSelected();
			synth.setMute(trackNumber, isMuted);
			System.out.println("Track " + trackNumber + ": Mute toggled to: " + isMuted);
		}

		if (e.getSource() == clearButton) {
			trackEditor.clearTrack();
			System.out.println("Track " + trackNumber + ": Clear Track button pressed.");
		}

		if (e.getSource() == noteModeButton) {
			trackEditor.setMode(TrackEditor.Mode.NOTE);
			System.out.println("Track " + trackNumber + ": Mode set to NOTE.");
		}

		if (e.getSource() == volumeModeButton) {
			trackEditor.setMode(TrackEditor.Mode.VOLUME);
			System.out.println("Track " + trackNumber + ": Mode set to VOLUME.");
		}

		if (e.getSource() == copyPasteModeButton) {
			trackEditor.setMode(TrackEditor.Mode.COPY);
			System.out.println("Track " + trackNumber + ": Mode set to COPY.");
		}

		if (e.getSource() == instrumentSelector) {
			int selectedInstrument = instrumentSelector.getSelectedIndex();
			synth.setInstrument(trackNumber, selectedInstrument);
			System.out.println("Track " + trackNumber + ": Instrument changed to: " + selectedInstrument);
		}
	}
}