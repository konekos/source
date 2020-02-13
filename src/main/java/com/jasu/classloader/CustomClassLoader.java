package com.jasu.classloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-15 20:58
 *****************************************/
public class CustomClassLoader extends ClassLoader{
    private final static String DEFAULT_DIR = "C:/app";

    public CustomClassLoader() {
        super();
    }

    public static void main(String[] args) {
        System.out.println(new CustomClassLoader().getParent());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path classPath = Paths.get(DEFAULT_DIR, "/", name.replace(".", "/"));
        if (!Files.exists(classPath)) {
            throw new ClassNotFoundException("file not exist: " + name);
        }
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(classPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == bytes || bytes.length == 0) {
            throw new ClassNotFoundException("load error:" + name);
        }
        return this.defineClass(name,bytes,0,bytes.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = null;

        if (name.startsWith("java.")) {
            try {
                clazz = ClassLoader.getSystemClassLoader().loadClass(name);
                if (clazz != null) {
                    if (resolve) {
                        resolveClass(clazz);
                    }
                }
            } catch (Exception e) {
                //ignore
            }
        }

        try {
            clazz = findClass(name);
        } catch (Exception e) {
            //...
        }

        if (clazz == null && getParent() != null) {
            getParent().loadClass(name);
        }
        return clazz;
    }
}
