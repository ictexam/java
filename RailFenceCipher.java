import java.util.Scanner;

public class RailFenceCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = scanner.nextLine();

        System.out.print("Enter key (number of rails): ");
        int key = scanner.nextInt();

        String encrypted = encrypt(text, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("\nEncrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        scanner.close();
    }

    public static String encrypt(String text, int key) {
        char[][] rail = new char[key][text.length()];

        // Fill with blanks
        for (int i = 0; i < key; i++)
            for (int j = 0; j < text.length(); j++)
                rail[i][j] = ' ';

        int row = 0, dir = 1;
        for (int i = 0; i < text.length(); i++) {
            rail[row][i] = text.charAt(i);
            row += dir;

            if (row == 0 || row == key - 1)
                dir = -dir;
        }

        // Print Encryption Matrix
        System.out.println("\nEncryption Matrix:");
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < text.length(); j++) {
                System.out.print(rail[i][j]);
            }
            System.out.println();
        }

        // Read encrypted text
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < key; i++)
            for (int j = 0; j < text.length(); j++)
                if (rail[i][j] != ' ')
                    result.append(rail[i][j]);

        return result.toString();
    }

    public static String decrypt(String cipher, int key) {
        char[][] rail = new char[key][cipher.length()];

        // Fill with blanks
        for (int i = 0; i < key; i++)
            for (int j = 0; j < cipher.length(); j++)
                rail[i][j] = ' ';

        // Mark positions
        int row = 0, dir = 1;
        for (int i = 0; i < cipher.length(); i++) {
            rail[row][i] = '*';
            row += dir;

            if (row == 0 || row == key - 1)
                dir = -dir;
        }

        // Fill marked positions with cipher letters
        int index = 0;
        for (int i = 0; i < key; i++)
            for (int j = 0; j < cipher.length(); j++)
                if (rail[i][j] == '*' && index < cipher.length())
                    rail[i][j] = cipher.charAt(index++);

        // Print Decryption Matrix
        System.out.println("\nDecryption Matrix:");
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < cipher.length(); j++) {
                System.out.print(rail[i][j]);
            }
            System.out.println();
        }

        // Read original text
        StringBuilder result = new StringBuilder();
        row = 0;
        dir = 1;
        for (int i = 0; i < cipher.length(); i++) {
            result.append(rail[row][i]);
            row += dir;

            if (row == 0 || row == key - 1)
                dir = -dir;
        }

        return result.toString();
    }
}
