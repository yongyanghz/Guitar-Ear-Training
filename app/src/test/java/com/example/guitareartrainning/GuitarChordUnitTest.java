package com.example.guitareartrainning;
import org.junit.Test;

import static com.example.guitareartrainning.GuitarChords.getChordType;
import static com.example.guitareartrainning.GuitarChords.isCorrectType;
import static com.example.guitareartrainning.GuitarChords.isSameChord;
import static org.junit.Assert.*;

public class GuitarChordUnitTest {


    @Test
    public void getChordType_Test() {

        assertEquals(getChordType("Am"), GuitarChords.MINOR_CHORD);
        assertEquals(getChordType("A"), GuitarChords.MAJOR_CHORD);
        assertEquals(getChordType("C7"), GuitarChords.SEVEN_CHORD);
        assertEquals(getChordType("A#5_R5"), GuitarChords.POWER_CHORD);
        assertEquals(getChordType("Fmaj7"), GuitarChords.MAJ7_CHORD);
        assertEquals(getChordType("Esus4"), GuitarChords.SUS_CHORD);

        assertNotEquals(getChordType("A"), GuitarChords.MINOR_CHORD);
        assertNotEquals(getChordType("AM"), GuitarChords.MAJOR_CHORD);
        assertNotEquals(getChordType("Fmaj7"), GuitarChords.SEVEN_CHORD);
        assertNotEquals(getChordType("C7"), GuitarChords.POWER_CHORD);
        assertNotEquals(getChordType("Am"), GuitarChords.SUS_CHORD);

    }

    @Test
    public void isCorrectType_Test(){
        assertTrue(isCorrectType("Major", GuitarChords.MAJOR_CHORD));
        assertTrue(isCorrectType("Minor", GuitarChords.MINOR_CHORD));
        assertTrue(isCorrectType("Maj7", GuitarChords.MAJ7_CHORD));
        assertTrue(isCorrectType("Power", GuitarChords.POWER_CHORD));
        assertTrue(isCorrectType("7", GuitarChords.SEVEN_CHORD));
        assertTrue(isCorrectType("Sus", GuitarChords.SUS_CHORD));

        assertFalse(isCorrectType("Minor", GuitarChords.MAJOR_CHORD));
        assertFalse(isCorrectType("Major", GuitarChords.MINOR_CHORD));
        assertFalse(isCorrectType("Major", GuitarChords.MAJ7_CHORD));
        assertFalse(isCorrectType("7", GuitarChords.POWER_CHORD));
        assertFalse(isCorrectType("Power", GuitarChords.SEVEN_CHORD));
        assertFalse(isCorrectType("Minor", GuitarChords.SUS_CHORD));
    }

    @Test
    public void isSameChord_Test(){
        assertTrue(isSameChord("C7", "C7"));
        assertTrue(isSameChord("C", "C"));
        assertTrue(isSameChord("C5", "C5_R5"));
        assertTrue(isSameChord("C5", "C5_R6"));
        assertTrue(isSameChord("Am", "Am"));

        assertFalse(isSameChord("C", "C7"));
        assertFalse(isSameChord("C7", "C"));
        assertFalse(isSameChord("Cm", "C5_R5"));
        assertFalse(isSameChord("A5", "C5_R6"));
        assertFalse(isSameChord("Am", "A"));
    }


}
