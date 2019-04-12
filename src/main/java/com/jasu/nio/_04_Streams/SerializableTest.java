package com.jasu.nio._04_Streams;

import org.junit.Test;

import java.io.*;

/**
 * @author @Jasu
 * @date 2018-07-31 18:02
 */
public class SerializableTest {
    private final static String FILE_PATH = "E:\\SpringSourceCode\\src\\main\\resources\\image.txt";
    @Test
    public void testSerializable() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            oos = new ObjectOutputStream(fos);
            Employee emp = new Employee("abc", 88);
            oos.writeObject(emp);
            oos.close();
            oos = null;
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ois = new ObjectInputStream(fis);
            emp = (Employee)ois.readObject();
            ois.close();
            System.out.println(emp.getName());
            System.out.println(emp.getAge());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
