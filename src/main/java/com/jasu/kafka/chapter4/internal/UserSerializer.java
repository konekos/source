package com.jasu.kafka.chapter4.internal;

import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-10 22:45
 *****************************************/
public class UserSerializer implements Serializer<User> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, User data) {
        if (data == null) {
            return null;
        }
        int age = data.getAge();
        String name = data.getName();
        String address = data.getAddress();

        byte[] naBytes;
        int nameLength = 0;
        if (name != null) {
            naBytes = name.getBytes();
        } else {
            naBytes = new byte[0];
        }
        nameLength = naBytes.length;

        int addrLength = 0;
        byte[] addBytes;

        if (address != null) {
            addBytes = address.getBytes();
        } else {
            addBytes = new byte[0];
        }
        addrLength = addBytes.length;

        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + 4 + nameLength + 4 + addrLength);
        byteBuffer.putInt(age);
        byteBuffer.putInt(nameLength);
        byteBuffer.put(naBytes);
        byteBuffer.putInt(addrLength);
        byteBuffer.put(addBytes);

        return byteBuffer.array();
    }

    @Override
    public void close() {

    }
}
