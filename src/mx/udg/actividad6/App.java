package mx.udg.actividad6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    /**
     * Recibe la ruta del archivo a leer y regresa una lista con las lineas
     * leídas del archivo.
     *
     * @param rutaDelArchivo ruta absoluta de la ubicación del archivo a leer.
     * @return List<Stirng> con las lineas obtenidas del archivo
     */
    public static List<String> leerArchivo(String rutaDelArchivo) {
        File archivo;
        FileReader fr = null;
        BufferedReader br = null;

        List<String> lineasDelArchivo = new ArrayList<>();

        try {
            archivo = new File(rutaDelArchivo);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea;
            while ((linea = br.readLine()) != null) {
                lineasDelArchivo.add(linea);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fr != null) {
                    fr.close();
                }

                if (br != null) {
                    br.close();
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lineasDelArchivo;
    }

    /**
     * Guarda una cadena de texto en un archivo con extención txt
     *
     * @param nombreArchivo Stirng, es necesario escribir toda la ruta del archivo.
     * @param texto String con el contenido del archivo.
     */
    public static void escribirArchivo(String nombreArchivo, String texto) {
        FileWriter fichero = null;
        PrintWriter pw;
        try {
            fichero = new FileWriter(nombreArchivo);
            pw = new PrintWriter(fichero);
            pw.println(texto);
            System.out.println("\n ARCHIVO GUARDADO EN: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (fichero != null) {
                try {
                    fichero.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {

        //Expreciones regulares para validar los registros.
        String[] ER = new String[] {
            "C(0){5}[0-9]{2}$",
            "(ENG|FRE|GER|POR|RUS|SPA)$",
            "(S|P)$",
            "L(0|1)[0-9]{6}$",
            "(PF|VW|VC)$",
            "S[0-9]{7}$",
            "[\\S]{15,35}$",
            "(0|3)$"
        };

        //Leemos los registros desde un archivo .txt
        List<String> archivo = leerArchivo("/Users/JoaquinCoronado/archivo.txt");

        //Nos ayudará a guardar el resultado de la validación de los campos
        //para después guardarlos en un archivo.
        StringBuilder resultado = new StringBuilder();

        //Iteramos en cada linea del archivo
        archivo.forEach( linea -> {

            //Nos ayudará a guardar los resultados por registro.
            StringBuilder stringBuilder = new StringBuilder();

            //Separamos cada registro utilizando el aracter "|" como delimitador entre cada campo.
            String[] campos =  linea.split("\\|");

            //Verificamos que el registro tenga el numero de campos correcto.
            if(campos.length <= 8 && campos.length > 0) {

                //Iteramos en cada campo del registro.
                for(int i = 0; i < campos.length; i++) {

                    //verificamos si cada campo cumple con la expresión regular correspondiente utilizando
                    //el arreglo ER previamente definido y el indice de la iteración para vincular cada campo
                    //con su expresión regular.
                    //Utilizamos un operador ternario ( ER[i]) ?  "[√]" : "[X]" ) si se cumple con la expresión
                    //regular se marca el campo con una palimita ([√]) en caso contrario con una x ( [X] )
                    stringBuilder.append(campos[i].matches(ER[i]) ?  "[√]" : "[X]" ).append(" ");
                    stringBuilder.append(campos[i]).append(" | ");
                }

                //Se agrega el resultado al string global para agregarlo en un archivo de salida
                //al finalizar todas las validaciones.
                resultado.append(stringBuilder.toString()).append("\n");
                System.out.println(stringBuilder.toString());
            } else {
                System.out.println("El registro no es válido: " + linea);
            }
        });

        escribirArchivo("/Users/JoaquinCoronado/resultados.txt", resultado.toString());
    }
}
