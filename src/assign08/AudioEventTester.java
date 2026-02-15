package assign08;

import static org.junit.Assert.*;
import org.junit.Test;

public class AudioEventTester {
	
	@Test
	public void testCompareToVolume() {
        VolumeEvent volumeLow = new VolumeEvent(100, 2, 10);
        VolumeEvent volumeHigh = new VolumeEvent(500, 1, 72);
		
		assertTrue(volumeLow.compareTo(volumeHigh) < 0);
		assertTrue(volumeHigh.compareTo(volumeLow) > 0);
	}
	
	@Test
	public void testCompareToPitch() {
        NoteEvent noteMiddle = new NoteEvent(7142, 3, 1000, 60);
        NoteEvent noteHigh = new NoteEvent(7000, 2, 500, 80);
		
		assertTrue(noteHigh.compareTo(noteMiddle) < 0);
		assertTrue(noteMiddle.compareTo(noteHigh) > 0);
	}
	
	@Test
	public void testCompareToSamePitch() {
		 NoteEvent noteA = new NoteEvent(1000, 1, 500, 60);
		 NoteEvent noteB = new NoteEvent(2000, 2, 1000, 60);
		 
		 assertEquals(0, noteA.compareTo(noteB));
	}
	
	@Test
	public void testCompareToSameVolume() {
	    VolumeEvent v1 = new VolumeEvent(500, 1, 60);
	    VolumeEvent v2 = new VolumeEvent(500, 2, 100);
	    
	    assertEquals(0, v1.compareTo(v2));
	}
	
	@Test
	public void testCompareToNoteBeforeVolume() {
	    NoteEvent note = new NoteEvent(1000, 1, 500, 60);
	    VolumeEvent volume = new VolumeEvent(1000, 2, 80);
	    
	    assertTrue(note.compareTo(volume) < 0);
	}
	
	@Test
	public void testCompareToPitchEdge() {
	    NoteEvent lowPitch = new NoteEvent(0, 0, 100, 0);
	    NoteEvent highPitch = new NoteEvent(0, 0, 100, 127);
	    
	    assertTrue(highPitch.compareTo(lowPitch) < 0);
	    assertTrue(lowPitch.compareTo(highPitch) > 0);
	}
	
	@Test
	public void testNoteConstructorsAndGetters() {
        NoteEvent note = new NoteEvent(7142, 3, 1000, 60);

        assertEquals(7142, note.getTime());
        assertEquals(3, note.getTrackNumber());
        assertEquals(1000, note.getDuration());
        assertEquals(60, note.getPitch());
        assertEquals("Note[7142, 3, 1000, 60]", note.toString());
	}
	
    @Test
    public void testVolumeConstructorAndGetters() {
        VolumeEvent volume = new VolumeEvent(100, 2, 72);

        assertEquals(100, volume.getTime());
        assertEquals(2, volume.getTrackNumber());
        assertEquals(72, volume.getValue());
        assertEquals("Volume[100, 2, 72]", volume.toString());
    }
}
