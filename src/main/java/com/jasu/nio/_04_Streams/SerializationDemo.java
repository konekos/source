package com.jasu.nio._04_Streams;

/**
 * @author @Jasu
 * @date 2018-08-01 10:13
 */

import java.io.*;


public class SerializationDemo {

    private final static String PATH = "E:\\SpringSourceCode\\src\\main\\resources\\image.txt";

    public static void main(String[] args) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(PATH));
            SerEmployee se = new SerEmployee("John Doe");
            System.out.println(se);
            oos.writeObject(se);
            oos.close();
            oos = null;
            System.out.println("se object written to file");
            ois = new ObjectInputStream(new FileInputStream(PATH));
            se = (SerEmployee) ois.readObject();
            System.out.println("se object read from file");
            System.out.println(se);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException ioe) {
                    assert false; // shouldn't happen in this context
                }
            if (ois != null)
                try {
                    ois.close();
                } catch (IOException ioe) {
                    assert false; // shouldn't happen in this context
                }
        }
    }

}

