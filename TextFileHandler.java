import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class TextFileHandler {
    private static Formatter output;
    private static Scanner input;
    private static ArrayList<String> palavrasArquivo;
    private static XGrafo grafo;
    
    public static ArrayList<String> getPalavrasArquivo(){
        return TextFileHandler.palavrasArquivo;
    }
    
    public static void openFile(String textFile){
        try{
            String textFilePath = textFile;
            Path path = Paths.get(textFilePath);
            if(Files.exists(path)){ //Se já existir o arquivo, preciso só lê-lo
                input = new Scanner(Paths.get(textFilePath));
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo nao encontrado!\n Fechando a execucao...\n");
            System.exit(1);
        }
        catch(IOException e){
            System.exit(1);
        }
    }
    
    public static void closeFile(){
        if(output != null){
            output.close();
            input.close();
        }
    }
    
    public static XGrafo getGrafo() {
        return grafo;
    }

    public static void readRecords(){
        XGrafo xGrafo = new XGrafo(1, false);
        int citiesQuantity = 0;
        String format = "";
        boolean comecarGrafo = false;
        String line = "";
        palavrasArquivo = new ArrayList<String>();
        try{
            while(input.hasNext()){

                if((citiesQuantity > 0) && (!(format.equals(""))) && (comecarGrafo)) {
                    switch(format) {
                        case "UPPER_DIAG_ROW":
                            for(int i = 0; i < citiesQuantity; i++) {
                                for(int j = i; j < citiesQuantity; j++) {
                                    xGrafo.inserirAresta(i, j, Integer.parseInt(input.next()));
                                }
                            }
                            break;
                        case "LOWER_DIAG_ROW":
                            for(int i = 0; i < citiesQuantity; i++) {
                                for(int j = 0; j <= i; j++) {
                                    xGrafo.inserirAresta(i, j, Integer.parseInt(input.next()));
                                }
                            }
                            break;
                    }
                }

                if((citiesQuantity == 0) || (format.equals("")) || (!comecarGrafo))
                    line = input.nextLine();

                if(line.contains("DIMENSION : ")) {
                    citiesQuantity = Integer.parseInt(line.split("DIMENSION : ", 2)[1]);
                    xGrafo = new XGrafo(citiesQuantity, false);
                }
                if(line.contains("EDGE_WEIGHT_FORMAT : "))
                    format = line.split("EDGE_WEIGHT_FORMAT : ", 2)[1];

                if(line.equals("EDGE_WEIGHT_SECTION"))
                    comecarGrafo = true;
            }
            grafo = xGrafo;
        }
        catch(NoSuchElementException e){
            System.err.println("Fechando a execucao...\n");
            System.exit(1);
        }
        catch(IllegalStateException e){
            System.err.println("Erro ao ler o arquivo\nFechando a execucao...");
            System.exit(1);
        }
        
        /*System.out.println("Palavras lidas do arquivo:\n");
        for(String a : palavrasArquivo){
            System.out.printf("%s\n", a);
        }*/
    }
}
