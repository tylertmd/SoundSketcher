package assign09;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Tests for the Song class without a setup method.
 * @author Tyler Davidson
 * @version 11/6/25
 */
class SongTester {

    @Test
    void testConstructorInitializesCorrectly() {
        Song testSong = new Song(120, 200);

        assertEquals(120, testSong.getTempo());
        assertEquals(200, testSong.getSongLength());

        for (int i = 0; i < 10; i++) {
            ArrayList<AudioEvent> track = testSong.getTrack(i);
            assertNotNull(track);
            assertEquals(0, track.size());
        }
    }

    @Test
    void testAddNoteEvent() {
        Song testSong = new Song(120, 200);
        
        testSong.addNoteEvent(50, 3, 16, 60);

        assertEquals(1, testSong.getTrack(3).size());
        assertEquals(0, testSong.getTrack(4).size());

        AudioEvent event = testSong.getTrack(3).get(0);
        assertEquals(50, event.getTime());
    }

    @Test
    void testClearTrack() {
        Song testSong = new Song(120, 200);

        testSong.addVolumeEvent(10, 7, 100);
        assertEquals(1, testSong.getTrack(7).size());

        testSong.clearTrack(7);

        assertEquals(0, testSong.getTrack(7).size());
    }

    @Test
    void testClearAll() {
        Song testSong = new Song(120, 200);

        testSong.addNoteEvent(10, 0, 8, 60);
        testSong.addVolumeEvent(15, 4, 100);
        testSong.addNoteEvent(20, 9, 16, 72);

        assertEquals(1, testSong.getTrack(0).size());
        assertEquals(1, testSong.getTrack(4).size());
        assertEquals(1, testSong.getTrack(9).size());

        testSong.clearAll();

        for (int i = 0; i < 10; i++) {
            assertEquals(0, testSong.getTrack(i).size());
        }
    }

    @Test
    void testGetTrackInvalidIndex() {
        Song testSong = new Song(120, 200);

        assertThrows(IllegalArgumentException.class, () -> {
            testSong.getTrack(10);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testSong.getTrack(-1);
        });
    }
}