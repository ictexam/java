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
