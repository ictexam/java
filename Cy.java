// PlayfairCipher

import java.util.*;

public class PlayfairCipher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().toUpperCase().replace("J", "I");

        System.out.print("Enter text (plaintext or ciphertext): ");
        String inputText = scanner.nextLine().toUpperCase().replace("J", "I");

        System.out.print("Choose (E)Encrypt or (D)Decrypt: ");
        char choice = scanner.nextLine().toUpperCase().charAt(0);

        char[][] keyMatrix = generateKeyMatrix(keyword);
        displayKeyMatrix(keyMatrix);

        if (choice == 'E') {
            String prepared = prepareText(inputText);
            String encrypted = encrypt(prepared, keyMatrix);
            System.out.println("\nEncrypted Text: " + encrypted);
            String decrypted = decrypt(encrypted, keyMatrix);
            System.out.println("Decrypted Text: " + cleanDecryptedText(decrypted));
        } else if (choice == 'D') {
            String decrypted = decrypt(inputText, keyMatrix);
            System.out.println("\nDecrypted Text: " + cleanDecryptedText(decrypted));
        } else {
            System.out.println("Invalid option!");
        }

        scanner.close();
    }

    public static char[][] generateKeyMatrix(String keyword) {
        LinkedHashSet<Character> seen = new LinkedHashSet<>();
        for (char ch : keyword.toCharArray()) {
            if (Character.isLetter(ch)) seen.add(ch);
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch != 'J') seen.add(ch);
        }

        Iterator<Character> iterator = seen.iterator();
        char[][] matrix = new char[5][5];

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrix[i][j] = iterator.next();

        return matrix;
    }

    public static void displayKeyMatrix(char[][] matrix) {
        System.out.println("\nKey Matrix:");
        for (char[] row : matrix) {
            for (char ch : row)
                System.out.print(ch + " ");
            System.out.println();
        }
    }

    public static String prepareText(String input) {
        input = input.replaceAll("[^A-Z]", "");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); ) {
            char first = input.charAt(i++);
            char second = (i < input.length()) ? input.charAt(i) : 'X';

            if (first == second) {
                result.append(first).append('X');
            } else {
                result.append(first).append(second);
                i++;
            }
        }

        if (result.length() % 2 != 0) result.append('X');
        return result.toString();
    }

    public static String encrypt(String text, char[][] matrix) {
        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i), b = text.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            if (posA[0] == posB[0]) {
                // Same row
                encrypted.append(matrix[posA[0]][(posA[1] + 1) % 5]);
                encrypted.append(matrix[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) {
                // Same column
                encrypted.append(matrix[(posA[0] + 1) % 5][posA[1]]);
                encrypted.append(matrix[(posB[0] + 1) % 5][posB[1]]);
            } else {
                // Rectangle
                encrypted.append(matrix[posA[0]][posB[1]]);
                encrypted.append(matrix[posB[0]][posA[1]]);
            }
        }

        return encrypted.toString();
    }

    public static String decrypt(String text, char[][] matrix) {
        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i), b = text.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            if (posA[0] == posB[0]) {
                decrypted.append(matrix[posA[0]][(posA[1] + 4) % 5]);
                decrypted.append(matrix[posB[0]][(posB[1] + 4) % 5]);
            } else if (posA[1] == posB[1]) {
                decrypted.append(matrix[(posA[0] + 4) % 5][posA[1]]);
                decrypted.append(matrix[(posB[0] + 4) % 5][posB[1]]);
            } else {
                decrypted.append(matrix[posA[0]][posB[1]]);
                decrypted.append(matrix[posB[0]][posA[1]]);
            }
        }

        return decrypted.toString();
    }

    public static int[] findPosition(char[][] matrix, char ch) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (matrix[i][j] == ch)
                    return new int[]{i, j};
        return null;
    }

    public static String cleanDecryptedText(String text) {
        StringBuilder cleaned = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (i < text.length() - 2 && text.charAt(i + 1) == 'X' && text.charAt(i) == text.charAt(i + 2)) {
                cleaned.append(current);
                i += 1; // skip 'X'
            } else {
                cleaned.append(current);
            }
        }
        return cleaned.toString();
    }
}

 
//RailFenceCipher
import java.util.Scanner;

public class RailFenceCipher{
    public static void  main(String [] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text: ");
        String text = scanner.nextLine();
        System.out.println("Enter Key : ");
        int key = scanner.nextInt();

        String encrypted = encrypt(text, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("Encrypted text: "+encrypted);
        System.out.println("Decrypted text: "+decrypted);

        scanner.close();
    }

    public static String encrypt(String text, int key){
        if(key<=1) return text;
        
        char[][] rail = new char[key][text.length()];
        for(int i=0; i<key; i++){
            for(int j=0; j<text.length(); j++){
                rail[i][j] = ' ';
            }
        }
        int dir=1;
        int row=0;
        for(int i=0; i<text.length(); i++){
            rail[row][i] = text.charAt(i);
            row += dir;
            if(row == 0 || row == key-1){
                dir =- dir;
            }
        }

        System.out.println("encrypted matrix");
        for(int i=0; i<key; i++){
            for(int j=0; j<text.length(); j++){
                System.out.print(rail[i][j]);
            }
            System.out.println();
        }

       StringBuilder result = new StringBuilder();
       for(int i=0; i<key; i++){
        for(int j=0; j<text.length(); j++){
            if(rail[i][j] != ' '){
                result.append(rail[i][j]);
            }
        }
       }
       return result.toString();
    }

    public static String decrypt(String cipher, int key){
        if(key<=1) return cipher;

        char[][] rail = new char[key][cipher.length()];
        for(int i=0; i<key; i++){
            for(int j=0; j<cipher.length(); j++){
                rail[i][j] = ' ';
            }
        }
        int dir=1;
        int row=0;

        for(int i=0; i<cipher.length(); i++){
            rail[row][i] = '*';
            row += dir;
            if(row == 0 || row == key-1){
                dir =-dir;
            }
        }

        int index=0;
        for(int i=0; i<key; i++){
            for(int j=0; j<cipher.length(); j++){
                if(rail[i][j] == '*' && index<cipher.length()){
                    rail[i][j] = cipher.charAt(index++);
                } 
            }
        }

        System.out.println("decrypted matrix");
        for(int i=0; i<key; i++){
            for(int j=0; j<cipher.length(); j++){
                System.out.print(rail[i][j]);
            }
            System.out.println();
        }

       StringBuilder result = new StringBuilder();
       row=0;
       dir=1;

       for(int i=0; i<cipher.length(); i++){
            result.append(rail[row][i]);
            row+=dir;
            if(row == 0 || row == key-1){
                dir =- dir;
            }
       }
       return result.toString();
    }
}

 
//CeasarCipher Encryption 
import java.util.Scanner;

public class CeasarCipher{
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in); 
        System.out.print("Enter the Text : ");

        String text = scanner.nextLine();

        System.out.print("Enter the shift : "); 
        int shift = scanner.nextInt();

        System.out.println("Encrypted text: " + encrypt(text,shift));

        scanner.close();
    }

    public static String encrypt(String text, int shift){
        String result = "";

        for (char ch: text.toCharArray()){
            if (Character.isLetter(ch)){
                char base = Character.isUpperCase(ch) ? 'A' : 'a' ;

                ch = (char) ((ch - base + shift) % 26 + base);
            }
            result += ch;
        }
        return result;
    }
}

//CaesarCipher Decryption
import java.util.Scanner;

public class CaesarCipherDecryption {
    public static String decrypt(String text, int shift) {
        StringBuilder result = new StringBuilder(); 

        for (char ch : text.toCharArray()) { 
            if (Character.isLetter(ch)) { 
                char base = Character.isUpperCase(ch) ? 'A' : 'a'; 
                ch = (char) ((ch - base - shift + 26) % 26 + base); 
        }
        result.append(ch); 
    }
    return result.toString(); 
}

public static void main(String[] args) { 
    Scanner scanner = new Scanner(System.in); 

    System.out.print("Enter a sentence: "); 
    String sentence = scanner.nextLine(); 

    System.out.print("Enter shift value: ");
    int shift = scanner.nextInt(); 

    String decryptedText = decrypt(sentence, shift); 
    System.out.println("Decrypted Sentence: " + decryptedText);

    scanner.close();
    }
}

//CaesarCipher en,de
import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = input.nextLine();

        System.out.print("Enter shift value: ");
        int shift = input.nextInt();

        String encrypted = encrypt(text, shift);
        String decrypted = decrypt(encrypted, shift);

        System.out.println("Encrypted text: " + encrypted);
        System.out.println("Decrypt text: " + decrypted);

        input.close();
    }

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + shift) % 26 + base);
            }
            result.append(c);
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {  
        return encrypt(text, 26 - (shift % 26));
    }
}
 
//RSA encrypt 
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.Cipher;
import java.util.Scanner;

public class Encrypt {
    public static void main(String[] args) throws Exception{
        KeyPairGenerator keypairgen = KeyPairGenerator.getInstance("RSA");
        keypairgen.initialize(2048);

        KeyPair pair = keypairgen.generateKeyPair();

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter text");
        String text = scanner.nextLine();
        byte[] input = text.getBytes();
        cipher.update(input);

        byte[] cipherText = cipher.doFinal();
        System.out.println(new String(cipherText, "UTF8"));
    
    }

}

//RSA decrypt
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;

public class Decrypt {
    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);

        KeyPair pair = keyPairGen.generateKeyPair();
        PublicKey publicKey = pair.getPublic();

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the text to be encrypted: ");
        String inputText = scanner.nextLine();

        byte[] input = inputText.getBytes();
        cipher.update(input);

        byte[] cipherText = cipher.doFinal(input); 

        String encryptedText = Base64.getEncoder().encodeToString(cipherText);
        System.out.println("Encrypted text (Base64): " + encryptedText);

        cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate()); 

        byte[] decodedCipherText = Base64.getDecoder().decode(encryptedText);

        byte[] decipheredText = cipher.doFinal(decodedCipherText);

        System.out.println("Decrypted text: " + new String(decipheredText));

        scanner.close();
    }
}

