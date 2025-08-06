import java.util.*;

public class PlayfairCipher{
    private char[][] keyMatrix = new char[5][5];

     public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the key : ");
        String keyword = scan.nextLine().toUpperCase();
        System.out.print("Enter the plaintext : ");
        String plaintext = scan.nextLine().toUpperCase();

        PlayfairCipher cipher = new PlayfairCipher();
        cipher.generateKeyMatrix(keyword);
        cipher.display();

        String preparedText = cipher.prepareText(plaintext);
        String encrypted = cipher.encrypt(preparedText);
        System.out.println("\nEncrypted text : " + encrypted);

        String decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted text : " + cipher.cleanDecryptedText(decrypted));
    }

    public void generateKeyMatrix(String keyword){
        boolean[] used = new boolean[26];
        keyword = keyword.replace('J', 'I');
        int index = 0;

        for(int k=0; k<keyword.length(); k++){
            char c = keyword.charAt(k);
            if(c<'A' || c>'Z') continue;
            if(c=='J') c='I';
            if(!used[c-'A']){
                keyMatrix[index/5][index%5] = c;
                used[c-'A']=true;
                index++;
            }
        }
 
        for(char c='A'; c<='Z'; c++){
            if(c=='J') continue;
            if(!used[c-'A']){
                keyMatrix[index/5][index%5] = c;
                used[c-'A']=true;
                index++;
            }
        }
    }

    public String prepareText(String text){
        text = text.replace('J', 'I').replaceAll("[^A-Z]", "");
        StringBuilder result = new StringBuilder();
        int i = 0;
        while(i<text.length()){
            char a = text.charAt(i);
            char b = (i+1< text.length()) ? text.charAt(i+1) : 'X';
            if(a==b){
                result.append(a).append('X');
                i++;
            }
            else{
                result.append(a).append(b);
                i+=2;
            }
        }
        if(result.length()%2 !=0) result.append('X');
        return result.toString();
    }

    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for(int i=0; i<text.length(); i+=2) {
            char a=text.charAt(i);
            char b=text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if(posA[0]==posB[0]){
                result.append(keyMatrix[posA[0]][(posA[1]+1)%5]);
                result.append(keyMatrix[posB[0]][(posB[1]+1)%5]);
            }
            else if(posA[1]==posB[1]){
                result.append(keyMatrix[(posA[0]+1)%5][posA[1]]);
                result.append(keyMatrix[(posB[0]+1)%5][posB[1]]);
            }
            else{
                result.append(keyMatrix[posA[0]][posB[1]]);
                result.append(keyMatrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }

    public String decrypt(String text){
        StringBuilder result = new StringBuilder();
        for(int i=0; i< text.length(); i+=2){
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if(posA[0]==posB[0]){
                result.append(keyMatrix[posA[0]][(posA[1]+4)%5]);
                result.append(keyMatrix[posB[0]][(posB[1]+4)%5]);
            }
            else if(posA[1]==posB[1]){
                result.append(keyMatrix[(posA[0]+4)%5][posA[1]]);
                result.append(keyMatrix[(posB[0]+4)%5][posB[1]]);
            }
            else{
                result.append(keyMatrix[posA[0]][posB[1]]);
                result.append(keyMatrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }

    public int[] findPosition(char c){
        if(c=='J') c='I';
        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++)
                if(keyMatrix[i][j]==c)
                    return new int[]{i, j};
        return null;
    }

    public void display(){
        System.out.println("\nPlayfaircipher Key Matrix : \n");
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++)
                System.out.print(keyMatrix[i][j] + " ");
            System.out.println();
        }
    }

    public String cleanDecryptedText(String text){
        return text.replaceAll("X$", "").replaceAll("([A-Z])X(?=\\1)", "$1");
    }       
}