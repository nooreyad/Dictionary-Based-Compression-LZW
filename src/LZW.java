import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class LZW extends JFrame{
    private JPanel panel1;
    private  JTextField fileName;
    private JButton compressButton;
    private JButton decompressButton;
    private JLabel label1;

    static Dictionary dictionary = new Dictionary();
    static DictionaryDecompress dicDecompress = new DictionaryDecompress();
    public static void compress(String fileName) throws FileNotFoundException {
        String fileSeparator = System.getProperty("file.separator");
        String path = System.getProperty("user.dir") + fileSeparator + fileName;
        File file = new File(path);
        String data;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            data = reader.readLine();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dictionary.initializeDictionary();
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
                break;
            }
            Pair pair = new Pair(ptr++, search);
            dictionary.add(pair);
            String coded = search.substring(0, search.length()-1);
            compressionCodes.add(dictionary.get(coded));
            if(dictionary.get(coded) != null){
                int code = dictionary.get(coded);
                Pair pair2 = new Pair(code, coded);
                dicDecompress.add(pair2);
            }
            i--;
        }
        try {
            String path2 = System.getProperty("user.dir") + fileSeparator + "CompressedText.txt";
            File file2 = new File(path2);
            FileWriter myWriter = new FileWriter(file2);
            for (int code : compressionCodes) {
                char temp = (char) code;
                myWriter.write(temp);
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public static void decompress(String fileName) throws FileNotFoundException {
        String fileSeparator = System.getProperty("file.separator");
        ArrayList<Integer> codes = new ArrayList<Integer>();
        try {

            String path = System.getProperty("user.dir") + fileSeparator + fileName;
            File file = new File(path);
            FileReader reader = new FileReader(file);
            int ch;
            while ((ch = reader.read()) != -1) {
                codes.add(ch);
                System.out.print(ch + " ");
            }
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String path2 = System.getProperty("user.dir") + fileSeparator + "DecompressedText.txt";
            File file2 = new File(path2);
            FileWriter myWriter = new FileWriter(file2);
            for (int code : codes) {
                System.out.print(code + " ");
               String text = dicDecompress.get(code);
               if(text != null) {
                   System.out.print(text + " ");
                   myWriter.write(text);
               }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        dictionary.clear();
        dicDecompress.clear();
    }
    public LZW() {
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = fileName.getText();
                try {
                    compress(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = fileName.getText();
                try {
                    decompress(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        LZW test = new LZW();
        test.setContentPane(test.panel1);
        test.setTitle("LZW");
        test.setSize(300, 400);
        test.setVisible(true);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
