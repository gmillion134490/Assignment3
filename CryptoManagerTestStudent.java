/*
 * Class: CMSC203 CRN 202720
 * Instructor: Huseyin Aygun
 * Description: JUnit tests for every public method in CryptoManager.
 * Due: 10/05/2026
 * Platform/compiler: Eclipse
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any source.
 * I have not given my code to any student.
 * Print your Name here: Gabriela Million
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the CryptoManager class.
 * @author Gabriela Million
 */
public class CryptoManagerTestStudent {

    private static final String ERROR = "The selected string is not in bounds, Try again.";

    // ---------- isStringInBounds ----------

    @Test
    public void testIsStringInBoundsTrue() {
        assertTrue(CryptoManager.isStringInBounds("MONTGOMERY COLLEGE 2026!"));
    }

    @Test
    public void testIsStringInBoundsLowercaseFalse() {
        assertFalse(CryptoManager.isStringInBounds("Gabriela"));
    }

    @Test
    public void testIsStringInBoundsTildeFalse() {
        assertFalse(CryptoManager.isStringInBounds("~"));
    }

    @Test
    public void testIsStringInBoundsEmptyTrue() {
        assertTrue(CryptoManager.isStringInBounds(""));
    }

    // ---------- caesarEncryption / caesarDecryption ----------

    @Test
    public void testCaesarEncryption() {
        assertEquals("RTSYLTRJW^", CryptoManager.caesarEncryption("MONTGOMERY", 5));
    }

    @Test
    public void testCaesarDecryption() {
        assertEquals("MONTGOMERY", CryptoManager.caesarDecryption("RTSYLTRJW^", 5));
    }

    @Test
    public void testCaesarNegativeKey() {
        assertEquals("LIKBG@Y+)+/", CryptoManager.caesarEncryption("SPRING 2026", -7));
    }

    @Test
    public void testCaesarBigKeyRoundTrip() {
        String encrypted = CryptoManager.caesarEncryption("GAME DAY!", 70);
        assertEquals("MGSK&JG_'", encrypted);
        assertEquals("GAME DAY!", CryptoManager.caesarDecryption(encrypted, 70));
    }

    @Test
    public void testCaesarOutOfBounds() {
        assertEquals(ERROR, CryptoManager.caesarEncryption("hello", 3));
        assertEquals(ERROR, CryptoManager.caesarDecryption("hello", 3));
    }

    // ---------- vigenereEncryption / vigenereDecryption ----------

    @Test
    public void testVigenereEncryption() {
        assertEquals("OY=FOZ", CryptoManager.vigenereEncryption("MY DOG", "CAT"));
    }

    @Test
    public void testVigenereDecryption() {
        assertEquals("MY DOG", CryptoManager.vigenereDecryption("OY=FOZ", "CAT"));
    }

    @Test
    public void testVigenereLongerKeyRoundTrip() {
        String encrypted = CryptoManager.vigenereEncryption("SPRING 2026", "TIGER");
        assertEquals("!XXM4Z(84)/", encrypted);
        assertEquals("SPRING 2026", CryptoManager.vigenereDecryption(encrypted, "TIGER"));
    }

    @Test
    public void testVigenereOutOfBoundsKey() {
        assertEquals(ERROR, CryptoManager.vigenereEncryption("HELLO", "key"));
        assertEquals(ERROR, CryptoManager.vigenereDecryption("HELLO", "key"));
    }

    // ---------- playfairEncryption / playfairDecryption ----------

    @Test
    public void testPlayfairEncryption() {
        assertEquals("FP9URMEQ", CryptoManager.playfairEncryption("GO TEAM", "RAMS"));
    }

    @Test
    public void testPlayfairDecryption() {
        assertEquals("GO TEAM", CryptoManager.playfairDecryption("FP9URMEQ", "RAMS"));
    }

    @Test
    public void testPlayfairExampleFromClarification() {
        assertEquals("ORRGMNK3", CryptoManager.playfairEncryption("MEETYOU", "MONTGOMERY"));
    }

    @Test
    public void testPlayfairDoubleLetters() {
        String encrypted = CryptoManager.playfairEncryption("BOOK", "LIB");
        assertEquals("EJPM", encrypted);
        assertEquals("BOOK", CryptoManager.playfairDecryption(encrypted, "LIB"));
    }

    @Test
    public void testPlayfairOddLengthRoundTrip() {
        String encrypted = CryptoManager.playfairEncryption("CLASS 203", "SPRING");
        assertEquals("DCBPN6315V", encrypted);
        assertEquals("CLASS 203", CryptoManager.playfairDecryption(encrypted, "SPRING"));
    }

    @Test
    public void testPlayfairOutOfBounds() {
        assertEquals(ERROR, CryptoManager.playfairEncryption("hello", "KEY"));
        assertEquals(ERROR, CryptoManager.playfairDecryption("hello", "KEY"));
    }
}
