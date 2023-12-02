import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Caela Mills
// 12/02/23
// Since Morris Code uses two symbols - dots and dashes
// I am using the symbols "$" and "#" and "!"


public class SpacePersonConverter {


    private static final String SPACE_PERSON_ALPHABET = "$#!";

    private static final int CAESAR_SHIFT = 5; // The key is a positive 5


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        // Create Space Person alphabet mapping
        Map<Character, Character> spacePersonAlphabetMap = createSpacePersonAlphabet();


        // Prompt user for English string
        System.out.print("Please enter an English string: ");
        String englishString = scanner.nextLine();


        // Convert to Space Person string
        String spacePersonString = convertToSpacePerson(englishString, spacePersonAlphabetMap);


        // Display the Space Person string
        System.out.println("\nConversion to Space Person String: " + spacePersonString);


        // Display the SHA-256 hash for the Space String
        String sha256Hash = calculateSHA256(spacePersonString);
        System.out.println("\nSHA-256 Hash for Space Person: " + sha256Hash);

        // Display the SHA-256 hash for the English String
        // Just in case you wanted to
//        sha256Hash = calculateSHA256(englishString);
//        System.out.println("\nSHA-256 Hash for English String: " + sha256Hash);


        // Apply the Caesar cipher with a 5-character shift
        String caesarCipheredString = caesarCipher(englishString, CAESAR_SHIFT);
        System.out.println("\nCaesar Cipher (5-character shift): " + caesarCipheredString);


        // Brute force all 0-25 shifts
        bruteForceCaesarCipher(englishString);


        // Close the scanner
        scanner.close();
    }


    private static Map<Character, Character> createSpacePersonAlphabet() {
        Map<Character, Character> spacePersonAlphabetMap = new HashMap<>();
        String englishAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // All capital letters


        for (int i = 0; i < englishAlphabet.length(); i++) {
            char englishChar = englishAlphabet.charAt(i);
            char spacePersonChar = SPACE_PERSON_ALPHABET.charAt(i % SPACE_PERSON_ALPHABET.length());
            spacePersonAlphabetMap.put(englishChar, spacePersonChar);
        }


        return spacePersonAlphabetMap;
    }


    private static String convertToSpacePerson(String englishString, Map<Character, Character> mapping) {
        StringBuilder spacePersonString = new StringBuilder();


        for (char character : englishString.toUpperCase().toCharArray()) {
            if (Character.isLetter(character)) {
                spacePersonString.append(mapping.getOrDefault(character, character));
            } else {
                spacePersonString.append(character);
            }
        }


        return spacePersonString.toString();
    }


    private static String calculateSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());


            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }


            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    private static String caesarCipher(String input, int shift) {
        StringBuilder result = new StringBuilder();


        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                // The "?" aka the ternary operator treats the isUpperCase(character) method as an if else true/false statement concerning 'A' (true) and 'a' (false)
                // "char" is the letter whether 'A' or 'a'
                // "base" is the value type of the capital or lower case letter based on their placement in the Ascii table
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                result.append((char) ((character - base + shift) % 26 + base));
            } else {
                result.append(character);
            }
        }


        return result.toString();
    }


    private static void bruteForceCaesarCipher(String input) {
        System.out.println("\nBrute Force Caesar Cipher:");


        for (int shift = 0; shift < 26; shift++) {
            String result = caesarCipher(input, shift);
            System.out.println("Shift " + shift + ": " + result);
        }
    }
}




