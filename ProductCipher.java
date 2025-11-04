import java.util.*;

public class ProductCipher {

    // Substitution cipher (Caesar shift)
    public static String substitutionEncrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char ch = (char) (((c - 'A' + key) % 26) + 'A');
                result.append(ch);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String substitutionDecrypt(String text, int key) {
        return substitutionEncrypt(text, 26 - key);  // reverse shift
    }

    // Transposition cipher (Columnar)
    public static String transpositionEncrypt(String text, String key) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] matrix = new char[rows][cols];

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (k < text.length())
                    matrix[i][j] = text.charAt(k++);
                else
                    matrix[i][j] = 'X'; // padding
            }
        }

        // Order columns based on key
        char[] keyChars = key.toCharArray();
        Integer[] order = new Integer[cols];
        for (int i = 0; i < cols; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Character.compare(keyChars[a], keyChars[b]));

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                ciphertext.append(matrix[j][order[i]]);
            }
        }
        return ciphertext.toString();
    }

    public static String transpositionDecrypt(String text, String key) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] matrix = new char[rows][cols];

        char[] keyChars = key.toCharArray();
        Integer[] order = new Integer[cols];
        for (int i = 0; i < cols; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Character.compare(keyChars[a], keyChars[b]));

        int k = 0;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[j][order[i]] = text.charAt(k++);
            }
        }

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                plaintext.append(matrix[i][j]);
            }
        }
        return plaintext.toString().replaceAll("X+$", "");
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== PRODUCT CIPHER IMPLEMENTATION =====");
        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine().replaceAll(" ", "").toUpperCase();

        System.out.print("Enter numeric key for substitution (e.g. 3): ");
        int subKey = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter key string for transposition (e.g. KEY): ");
        String transKey = sc.nextLine().toUpperCase();

        // Step 1: Substitution
        String subEncrypted = substitutionEncrypt(plaintext, subKey);
        System.out.println("\nAfter Substitution: " + subEncrypted);

        // Step 2: Transposition
        String finalEncrypted = transpositionEncrypt(subEncrypted, transKey);
        System.out.println("Final Ciphertext: " + finalEncrypted);

        // Decryption process
        String transDecrypted = transpositionDecrypt(finalEncrypted, transKey);
        String finalDecrypted = substitutionDecrypt(transDecrypted, subKey);

        System.out.println("\nAfter Decryption Steps:");
        System.out.println("After Reverse Transposition: " + transDecrypted);
        System.out.println("After Reverse Substitution: " + finalDecrypted);

        sc.close();
    }
}
