import java.util.Scanner;

public class VigenereCipher {

    // Function to decrypt Vigenere Cipher text
    public static String decrypt(String cipherText, String key) {
        StringBuilder plaintext = new StringBuilder();

        cipherText = cipherText.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase();

        int keyIndex = 0;
        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);
            int shift = key.charAt(keyIndex) - 'A';
            char p = (char) ((c - 'A' - shift + 26) % 26 + 'A');
            plaintext.append(p);
            keyIndex = (keyIndex + 1) % key.length();
        }

        return plaintext.toString();
    }

    // Function to encrypt (for testing)
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase();

        int keyIndex = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            char p = plaintext.charAt(i);
            int shift = key.charAt(keyIndex) - 'A';
            char c = (char) ((p - 'A' + shift) % 26 + 'A');
            ciphertext.append(c);
            keyIndex = (keyIndex + 1) % key.length();
        }

        return ciphertext.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Vigenere Cipher Cryptanalysis =====");
        System.out.print("Enter Ciphertext: ");
        String cipherText = sc.nextLine();

        System.out.print("Enter Key: ");
        String key = sc.nextLine();

        String decrypted = decrypt(cipherText, key);
        System.out.println("\nDecrypted Plaintext: " + decrypted);

        sc.close();
    }
}
