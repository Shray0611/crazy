import java.math.BigInteger;
import java.util.Scanner;

public class SimpleRSAText {
    int p, q, n, phi, e, d;

    // GCD function
    int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    // Modular inverse (brute-force)
    int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1)
                return x;
        }
        return 1;
    }

    // Constructor
    public SimpleRSAText() {
        // Step 1: Choose small primes
        p = 17;
        q = 23;

        // Step 2: Compute n and phi
        n = p * q; // 391
        phi = (p - 1) * (q - 1); // 352

        // Step 3: Choose e
        e = 3;
        while (gcd(e, phi) != 1)
            e++;

        // Step 4: Find d (modular inverse)
        d = modInverse(e, phi);
    }

    // Encrypt a single character using BigInteger for accuracy
    int encryptChar(int m) {
        BigInteger M = BigInteger.valueOf(m);
        BigInteger C = M.pow(e).mod(BigInteger.valueOf(n));
        return C.intValue();
    }

    // Decrypt a single character
    int decryptChar(int c) {
        BigInteger C = BigInteger.valueOf(c);
        BigInteger M = C.pow(d).mod(BigInteger.valueOf(n));
        return M.intValue();
    }

    // Encrypt entire message
    String encryptMessage(String msg) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch : msg.toCharArray()) {
            int enc = encryptChar((int) ch);
            encrypted.append(enc).append(" ");
        }
        return encrypted.toString().trim();
    }

    // Decrypt entire message
    String decryptMessage(String encrypted) {
        StringBuilder decrypted = new StringBuilder();
        String[] parts = encrypted.trim().split(" ");
        for (String part : parts) {
            int c = Integer.parseInt(part);
            int dec = decryptChar(c);
            decrypted.append((char) dec);
        }
        return decrypted.toString();
    }

    public static void main(String[] args) {
        SimpleRSAText rsa = new SimpleRSAText();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Simple RSA Text Encryption ===");
        System.out.println("Public Key (e, n): (" + rsa.e + ", " + rsa.n + ")");
        System.out.println("Private Key (d, n): (" + rsa.d + ", " + rsa.n + ")\n");

        System.out.print("Enter a message to encrypt: ");
        String message = sc.nextLine();

        // Encrypt
        String encrypted = rsa.encryptMessage(message);
        System.out.println("\nEncrypted Message (numeric form): " + encrypted);

        // Decrypt
        String decrypted = rsa.decryptMessage(encrypted);
        System.out.println("\nDecrypted Message: " + decrypted);

        sc.close();
    }
}
