package it.netgrid.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

public class SerializableUtils {
    public static String serializeBase64(Object session) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(session);
            
            return Base64.encodeBase64URLSafeString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }
    public static Object deserializeBase64(String sessionStr) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(sessionStr));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }
}