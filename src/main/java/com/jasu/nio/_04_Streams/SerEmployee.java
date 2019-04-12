package com.jasu.nio._04_Streams;

import java.io.Serializable;

/**
 * @author @Jasu
 * @date 2018-08-01 10:18
 */
public class SerEmployee extends Employee1 implements Serializable {
    SerEmployee(String name)
    {
        super(name);
    }
}
