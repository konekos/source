package com.jasu.kafka.chapter4.internal;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-10 22:54
 *****************************************/
public class UserDeserializer implements Deserializer<User> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //do nothing
    }

    @Override
    public User deserialize(String topic, byte[] data) {

        if (data == null) {
            return null;
        }
        if (data.length < 12) {
            throw new SerializationException("error");
        }

        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        int age = byteBuffer.getInt();

        int nameLength = byteBuffer.getInt();
        byte[] nameBytes = new byte[nameLength];
        byteBuffer.get(nameBytes);
        String name = new String(nameBytes);


        int addrLength = byteBuffer.getInt();
        byte[] addrBytes = new byte[addrLength];
        byteBuffer.get(addrBytes);
        String address = new String(addrBytes);

        return new User(age,name,address);
    }

    @Override
    public void close() {

    }
}
