// PlayfairCipher.java
// Program to perform Playfair Cipher Encryption in Java
// Key1: "OCTOBER" encrypts your name
// Key2: "ARJUNPRABHU" encrypts "SECURITY"

import java.util.*;

public class PlayfairCipher {
    
    // Function to prepare key matrix
    static char[][] generateKeyMatrix(String key) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        boolean[] seen = new boolean[26];
        char[][] matrix = new char[5][5];
        int row = 0, col = 0;

        // Fill letters from key
        for (char c : key.toCharArray()) {
            if (!seen[c - 'A']) {
                matrix[row][col++] = c;
                seen[c - 'A'] = true;
                if (col == 5) { col = 0; row++; }
            }
        }

        // Fill remaining letters
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue; // J is merged with I
            if (!seen[c - 'A']) {
                matrix[row][col++] = c;
                seen[c - 'A'] = true;
                if (col == 5) { col = 0; row++; }
            }
        }
        return matrix;
    }

    // Helper: find position of character in matrix
    static int[] findPosition(char[][] matrix, char ch) {
        if (ch == 'J') ch = 'I';
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (matrix[i][j] == ch)
                    return new int[]{i, j};
        return null;
    }

    // Prepare plaintext for encryption
    static String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i + 1 < text.length()) {
                if (text.charAt(i) == text.charAt(i + 1))
                    sb.append('X');
            }
        }
        if (sb.length() % 2 != 0) sb.append('X');
        return sb.toString();
    }

    // Encrypt a pair of letters
    static String encryptPair(char[][] matrix, char a, char b) {
        int[] posA = findPosition(matrix, a);
        int[] posB = findPosition(matrix, b);

        if (posA[0] == posB[0]) { // Same row
            return "" + matrix[posA[0]][(posA[1] + 1) % 5] +
                        matrix[posB[0]][(posB[1] + 1) % 5];
        } else if (posA[1] == posB[1]) { // Same column
            return "" + matrix[(posA[0] + 1) % 5][posA[1]] +
                        matrix[(posB[0] + 1) % 5][posB[1]];
        } else { // Rectangle swap columns
            return "" + matrix[posA[0]][posB[1]] +
                        matrix[posB[0]][posA[1]];
        }
    }

    // Encrypt entire text
    static String encrypt(String key, String plaintext) {
        char[][] matrix = generateKeyMatrix(key);
        String text = prepareText(plaintext);
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2)
            cipher.append(encryptPair(matrix, text.charAt(i), text.charAt(i + 1)));

        return cipher.toString();
    }

    // Print key matrix
    static void printMatrix(char[][] matrix) {
        System.out.println("\nKey Matrix:");
        for (char[] row : matrix) {
            for (char c : row) System.out.print(c + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // First encryption: key = "OCTOBER", text = your name
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        String key1 = "OCTOBER";
        String cipher1 = encrypt(key1, name);
        System.out.println("\nEncryption using key '" + key1 + "': " + cipher1);

        // Second encryption: key = your name, text = "SECURITY"
        String key2 = name;
        String cipher2 = encrypt(key2, "SECURITY");
        System.out.println("Encryption using key '" + key2 + "': " + cipher2);

        sc.close();
    }
}
