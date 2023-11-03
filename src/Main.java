import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> compress(String data){
        Dictionary dictionary = new Dictionary();
        ArrayList<Integer> compressionCodes = new ArrayList<>();
        int ptr = 128;
        for (int i = 0; i < data.length(); i++){
            String search = "";
            search += data.charAt(i);
            while (i+1 < data.length() && dictionary.get(search) != null){
                i++;
                search += data.charAt(i);
            }
            if (i+1 == data.length()){
                compressionCodes.add(dictionary.get(search));
                return compressionCodes;
            }
            Pair pair = new Pair(ptr++, search);
            dictionary.add(pair);
            String coded = search.substring(0, search.length()-1);
            compressionCodes.add(dictionary.get(coded));
            i--;
        }
        return compressionCodes;
    }

    public static void writeToFileCompression(ArrayList<Integer> compressedCodes) throws IOException {
        FileWriter write = new FileWriter("outputOfCompression.txt");
        for (Integer i : compressedCodes){
            write.append(i.toString());
            write.append("\n");
        }
        write.close();
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int choice;
        while(true) {
            System.out.println("Please choose one of these options:-");
            System.out.println("1. Compress");
            System.out.println("2. Decompress");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            choice = in.nextInt();
            StringBuilder unCompressedData = null;
            switch (choice) {
                case 1:
                    BufferedReader reader = new BufferedReader(new FileReader("inputBeforeCompression.txt"));
                    unCompressedData = new StringBuilder();
                    String data = reader.readLine();
                    while (data != null) {
                        unCompressedData.append(data);
                        data = reader.readLine();
                    }
                    ArrayList<Integer> compressionCodes = compress(unCompressedData.toString());
                    writeToFileCompression(compressionCodes);
                    break;
                case 2:

                    break;
                case 3:
                    return;
            }
        }

    }
}
