package assign11;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main application window for the SoundSketcher. It manages the controls of
 * the application.
 * 
 * @author Tyler Davidson
 * @version 11/13/25
 */
public class SoundSketcherFrame extends JFrame implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	private static final int TEMPO = 300;
	private static final int DURATION = 16;

	private static final int DEFAULT_SONG_LENGTH = 32;

	private Song song;

	private TrackPanel[] trackPanels = new TrackPanel[10];

	private JToggleButton playButton;
	private JToggleButton loopToggleButton;
	private JSlider tempoSlider;
	private JSpinner durationSpinner;

	/**
	 * Constructs the SoundSketcherFrame, creates all GUI components, connects event
	 * listeners, and creates the layout.
	 */
	public SoundSketcherFrame() {

		this.song = new Song(TEMPO, DURATION);

		for (int trackNum = 0; trackNum < 10; trackNum++) {
			trackPanels[trackNum] = new TrackPanel(trackNum, song.getSongLength(), song.getTrack(trackNum),
					song.getSynthesizer());
		}

		setPreferredSize(new Dimension(800, 800));
		setTitle("Sound Sketcher");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane trackTabbedPane = new JTabbedPane();

		for (int trackNum = 0; trackNum < 10; trackNum++) {
			trackTabbedPane.addTab("Track" + (trackNum + 1), trackPanels[trackNum]);
		}

		add(trackTabbedPane, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new BorderLayout(5, 0));
		JPanel tempoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));

		playButton = new JToggleButton("▶ ︎Play");
		loopToggleButton = new JToggleButton("⟳ Loop");
		tempoSlider = new JSlider(SwingConstants.HORIZONTAL, 20, 600, 120);
		JLabel sliderLabel = new JLabel("Tempo (Playback speed):");
		durationSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_SONG_LENGTH, 4, 1024, 4));
		JLabel durationLabel = new JLabel("Duration: ");

		playButton.addActionListener(this);
		loopToggleButton.addActionListener(this);
		tempoSlider.addChangeListener(this);
		durationSpinner.addChangeListener(this);

		tempoSlider.setPaintTicks(true);
		tempoSlider.setMajorTickSpacing(100);
		tempoSlider.setMinorTickSpacing(20);
		tempoSlider.setPaintLabels(true);

		buttonPanel.add(playButton, BorderLayout.WEST);
		buttonPanel.add(loopToggleButton, BorderLayout.EAST);
		buttonPanel.setBackground(new Color(218, 218, 218));

		tempoPanel.add(sliderLabel);
		tempoPanel.add(tempoSlider);
		tempoPanel.setBackground(new Color(218, 218, 218));

		durationPanel.add(durationLabel);
		durationPanel.add(durationSpinner);
		durationPanel.setBackground(new Color(218, 218, 218));

		controlPanel.add(buttonPanel, BorderLayout.WEST);
		controlPanel.add(tempoPanel, BorderLayout.CENTER);
		controlPanel.add(durationPanel, BorderLayout.EAST);
		controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		add(controlPanel, BorderLayout.NORTH);

		pack();
	}

	/**
	 * Handles state change events from the Jslider and JSpinner
	 * 
	 * @param e The change event.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {

		System.out.println("State Changed in Frame");

		if (e.getSource() == tempoSlider) {
			int tempo = tempoSlider.getValue();
			song.setTempo(tempo);
		}

		if (e.getSource() == durationSpinner) {

			int newLength = (Integer) durationSpinner.getValue();

			song.setSongLength(newLength);

			for (TrackPanel panel : trackPanels) {
				if (panel != null) {
					panel.setSongLength(newLength);
				}
			}
		}
	}

	/**
	 * Handles the action events from the button controls.
	 * 
	 * @param e The action event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action Performed in Frame: " + e.getActionCommand());

		if (e.getSource() == playButton) {
			boolean isPlaying = playButton.isSelected();
			if (isPlaying) {
				song.play();
				playButton.setText("⏸ Stop");
			} else {
				song.stop();
				playButton.setText("▶ Play");
			}
		}

		if (e.getSource() == loopToggleButton) {
			boolean isLooping = loopToggleButton.isSelected();
			song.enableLoop(isLooping);
		}
	}
}