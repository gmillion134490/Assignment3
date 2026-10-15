/*
 * Class: CMSC203 CRN 202720
 * Instructor: Huseyin Aygun
 * Description: Locks and unlocks messages with three ciphers:
 *              Vigenere, Playfair, and Caesar.
 * Due: 10/05/2026
 * Platform/compiler: Eclipse
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any source.
 * I have not given my code to any student.
 * Print your Name here: Gabriela Million
 */

/**
 * Locks and unlocks messages with three ciphers.
 * @author Gabriela Million
 */
public class CryptoManager {

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // the 64 allowed characters
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";
    private static final String ERROR = "The selected string is not in bounds, Try again.";

    /**
     * Checks if every character is allowed.
     * @param plainText the text to check
     * @return true if all are allowed, false if not
     */
    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            // -1 means the character is not in ALPHABET64
            if (ALPHABET64.indexOf(plainText.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vigenere encryption.
     * @param plainText the text to lock
     * @param key the keyword
     * @return the locked text
     */
    public static String vigenereEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText) || !isStringInBounds(key)) {
            return ERROR;
        }

        String result = "";
        int keySpot = 0;

        for (int i = 0; i < plainText.length(); i++) {
            int plainPos = ALPHABET64.indexOf(plainText.charAt(i));
            int keyPos = ALPHABET64.indexOf(key.charAt(keySpot));
            int newPos = plainPos + keyPos;

            // go back to the start if too big
            while (newPos >= RANGE) {
                newPos = newPos - RANGE;
            }

            result = result + ALPHABET64.charAt(newPos);

            // next key letter, start over at the end of the key
            keySpot++;
            if (keySpot == key.length()) {
                keySpot = 0;
            }
        }
        return result;
    }

    /**
     * Vigenere decryption.
     * @param encryptedText the locked text
     * @param key the keyword
     * @return the original text
     */
    public static String vigenereDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText) || !isStringInBounds(key)) {
            return ERROR;
        }

        String result = "";
        int keySpot = 0;

        for (int i = 0; i < encryptedText.length(); i++) {
            int encPos = ALPHABET64.indexOf(encryptedText.charAt(i));
            int keyPos = ALPHABET64.indexOf(key.charAt(keySpot));
            int newPos = encPos - keyPos;

            // go to the end if too small
            while (newPos < 0) {
                newPos = newPos + RANGE;
            }

            result = result + ALPHABET64.charAt(newPos);

            keySpot++;
            if (keySpot == key.length()) {
                keySpot = 0;
            }
        }
        return result;
    }

    /**
     * Makes the 8 by 8 grid for Playfair.
     * @param key the keyword
     * @return the grid
     */
    private static char[][] buildMatrix(String key) {
        char[][] grid = new char[8][8];
        int row = 0;
        int column = 0;
        String used = "";
        String letters = key + ALPHABET64;

        for (int i = 0; i < letters.length(); i++) {
            char ch = letters.charAt(i);
            // skip it if we already used it
            if (used.indexOf(ch) == -1) {
                grid[row][column] = ch;
                used = used + ch;
                column++;
                if (column == 8) {
                    column = 0;
                    row++;
                }
            }
        }
        return grid;
    }

    /**
     * Finds the row of a letter in the grid.
     * @param grid the grid
     * @param letter the letter to find
     * @return the row number
     */
    private static int findRow(char[][] grid, char letter) {
        int found = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (grid[row][col] == letter) {
                    found = row;
                }
            }
        }
        return found;
    }

    /**
     * Finds the column of a letter in the grid.
     * @param grid the grid
     * @param letter the letter to find
     * @return the column number
     */
    private static int findCol(char[][] grid, char letter) {
        int found = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (grid[row][col] == letter) {
                    found = col;
                }
            }
        }
        return found;
    }

    /**
     * Playfair encryption.
     * @param plainText the text to lock
     * @param key the keyword
     * @return the locked text
     */
    public static String playfairEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText) || !isStringInBounds(key)) {
            return ERROR;
        }

        // odd length, so add an X at the end
        if (plainText.length() % 2 == 1) {
            plainText = plainText + "X";
        }

        char[][] grid = buildMatrix(key);
        String result = "";
        int i = 0;

        while (i < plainText.length()) {
            char first = plainText.charAt(i);
            char second = plainText.charAt(i + 1);

            int row1 = findRow(grid, first);
            int col1 = findCol(grid, first);
            int row2 = findRow(grid, second);
            int col2 = findCol(grid, second);

            char newFirst;
            char newSecond;

            if (row1 == row2) {
                // same row, move right
                int newCol1 = col1 + 1;
                int newCol2 = col2 + 1;
                if (col1 == 7) {
                    newCol1 = 0;
                }
                if (col2 == 7) {
                    newCol2 = 0;
                }
                newFirst = grid[row1][newCol1];
                newSecond = grid[row2][newCol2];
            } else if (col1 == col2) {
                // same column, move down
                int newRow1 = row1 + 1;
                int newRow2 = row2 + 1;
                if (row1 == 7) {
                    newRow1 = 0;
                }
                if (row2 == 7) {
                    newRow2 = 0;
                }
                newFirst = grid[newRow1][col1];
                newSecond = grid[newRow2][col2];
            } else {
                // rectangle, swap the columns
                newFirst = grid[row1][col2];
                newSecond = grid[row2][col1];
            }

            result = result + newFirst + newSecond;
            i = i + 2;
        }
        return result;
    }

    /**
     * Playfair decryption.
     * @param encryptedText the locked text
     * @param key the keyword
     * @return the original text
     */
    public static String playfairDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText) || !isStringInBounds(key)) {
            return ERROR;
        }

        char[][] grid = buildMatrix(key);
        String result = "";
        int i = 0;

        while (i + 1 < encryptedText.length()) {
            char first = encryptedText.charAt(i);
            char second = encryptedText.charAt(i + 1);

            int row1 = findRow(grid, first);
            int col1 = findCol(grid, first);
            int row2 = findRow(grid, second);
            int col2 = findCol(grid, second);

            char newFirst;
            char newSecond;

            if (row1 == row2) {
                // same row, move left
                int newCol1 = col1 - 1;
                int newCol2 = col2 - 1;
                if (col1 == 0) {
                    newCol1 = 7;
                }
                if (col2 == 0) {
                    newCol2 = 7;
                }
                newFirst = grid[row1][newCol1];
                newSecond = grid[row2][newCol2];
            } else if (col1 == col2) {
                // same column, move up
                int newRow1 = row1 - 1;
                int newRow2 = row2 - 1;
                if (row1 == 0) {
                    newRow1 = 7;
                }
                if (row2 == 0) {
                    newRow2 = 7;
                }
                newFirst = grid[newRow1][col1];
                newSecond = grid[newRow2][col2];
            } else {
                // rectangle, same as encryption
                newFirst = grid[row1][col2];
                newSecond = grid[row2][col1];
            }

            result = result + newFirst + newSecond;
            i = i + 2;
        }

        // take off the extra X
        if (result.length() > 0 && result.charAt(result.length() - 1) == 'X') {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * Caesar encryption.
     * @param plainText the text to lock
     * @param key how many spots to move
     * @return the locked text
     */
    public static String caesarEncryption(String plainText, int key) {
        if (!isStringInBounds(plainText)) {
            return ERROR;
        }

        String result = "";

        for (int i = 0; i < plainText.length(); i++) {
            // use the ASCII number
            int newChar = plainText.charAt(i) + key;

            // wrap around
            while (newChar > UPPER_RANGE) {
                newChar = newChar - RANGE;
            }
            while (newChar < LOWER_RANGE) {
                newChar = newChar + RANGE;
            }

            result = result + (char) newChar;
        }
        return result;
    }

    /**
     * Caesar decryption.
     * @param encryptedText the locked text
     * @param key how many spots it was moved
     * @return the original text
     */
    public static String caesarDecryption(String encryptedText, int key) {
        if (!isStringInBounds(encryptedText)) {
            return ERROR;
        }

        String result = "";

        for (int i = 0; i < encryptedText.length(); i++) {
            int newChar = encryptedText.charAt(i) - key;

            while (newChar > UPPER_RANGE) {
                newChar = newChar - RANGE;
            }
            while (newChar < LOWER_RANGE) {
                newChar = newChar + RANGE;
            }

            result = result + (char) newChar;
        }
        return result;
    }

}
