import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSA {

    private BigInteger p, q, n, phi, e, d;
    private int bitlength = 1024;
    private SecureRandom random = new SecureRandom();

    // Constructor - Generates keys
    public RSA() {
        // Step 1: Generate two random primes p and q
        p = BigInteger.probablePrime(bitlength / 2, random);
        q = BigInteger.probablePrime(bitlength / 2, random);

        // Step 2: Compute n = p * q
        n = p.multiply(q);

        // Step 3: Compute phi(n) = (p-1)*(q-1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Step 4: Choose e such that 1 < e < phi and gcd(e, phi) = 1
        e = BigInteger.probablePrime(bitlength / 2, random);
        while (phi.gcd(e).intValue() > 1 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }

        // Step 5: Compute d such that (d * e) % phi = 1
        d = e.modInverse(phi);
    }

    // Encryption: c = m^e mod n
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    // Decryption: m = c^d mod n
    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(d, n);
    }

    // Main Method
    public static void main(String[] args) {
        RSA rsa = new RSA();
        Scanner sc = new Scanner(System.in);

        System.out.println("===== RSA Cryptosystem Implementation =====");
        System.out.print("Enter a message to encrypt: ");
        String message = sc.nextLine();

        BigInteger msg = new BigInteger(message.getBytes());
        System.out.println("\nOriginal Message: " + message);
        System.out.println("Message as Integer: " + msg);

        // Encrypt
        BigInteger encrypted = rsa.encrypt(msg);
        System.out.println("\nEncrypted Message: " + encrypted);

        // Decrypt
        BigInteger decrypted = rsa.decrypt(encrypted);
        String decryptedMessage = new String(decrypted.toByteArray());
        System.out.println("\nDecrypted Message: " + decryptedMessage);

        System.out.println("\n===== RSA Key Details =====");
        System.out.println("p = " + rsa.p);
        System.out.println("q = " + rsa.q);
        System.out.println("n = " + rsa.n);
        System.out.println("phi = " + rsa.phi);
        System.out.println("Public Key (e, n) = (" + rsa.e + ", " + rsa.n + ")");
        System.out.println("Private Key (d, n) = (" + rsa.d + ", " + rsa.n + ")");
        
        sc.close();
    }
}
