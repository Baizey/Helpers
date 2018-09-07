package lsm.datastructures.crypto;

class Constants {

    // Update these constants as higher security is needed
    // Do mind the asymmetric key do utilize the symmetric key, so they need to match in scales

    // Asymmetric algorithm
    static final String asymmetricKeyAlgorithm = "RSA";
    static final int asymmetricKeySize = 2048; // 2^11

    // Symmetric algorithm
    static final String symmetricKeyAlgorithm = "AES";
    static final int symmetricKeySize = 256; // 2^9

    // Hashing
    static final String hashingAlgorithm = "PBKDF2WithHmacSHA512";
    static final int hashingIterations = 100000;   // 100,000
    static final int hashingHashSize = 64;         // Length of generated hash
    static final int hashingSaltSize = 16;         // Byte length of salt

    // Random generator
    static final String PRNGAlgorithm = "SHA1PRNG";

    // Other constants
    // Used to seperate iterations/salt/hash
    static final String separator = ";";

}