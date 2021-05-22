package Main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class FileReader {
    Vector<Integer> data;

    public FileReader() {
        data = new Vector<>();
    }

    public Vector<Integer> load() throws FileNotFoundException, IOException {
        JFileChooser ventana = new JFileChooser();
        ventana.setCurrentDirectory(new File(System.getProperty("user.home")));
        int resultado = ventana.showOpenDialog(null);
        if(resultado == JFileChooser.APPROVE_OPTION) {
            File seleccion = ventana.getSelectedFile();
            System.out.println("Archivo seleccionado es: " + seleccion.getAbsolutePath());

            try(BufferedReader br = new BufferedReader(new java.io.FileReader(seleccion))){
                String linea;
                while((linea = br.readLine()) != null) {
                    System.out.println(linea);

                    // parse and add data
                    String[] numbers = linea.split(",");
                    int[] nros = new int[numbers.length];

                    // validate
                    for (int i = 0; i < numbers.length; i++) {
                        int currentNumber = Integer.parseInt(numbers[i].trim());
                        nros[i] = currentNumber;
                    }

                    // add and return vector
                    for (int i = 0; i < numbers.length; i++) {
                        data.add(nros[i]);
                    }
                }
                return data;
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "Error while loading file...");
                return null;
            }
        }
        return null;
    }

}
