package net.minecraft.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CryptManager {
   @OnlyIn(Dist.CLIENT)
   public static SecretKey createNewSharedKey() throws CryptException {
      try {
         KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
         keygenerator.init(128);
         return keygenerator.generateKey();
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   public static KeyPair generateKeyPair() throws CryptException {
      try {
         KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
         keypairgenerator.initialize(1024);
         return keypairgenerator.generateKeyPair();
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   public static byte[] getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey) throws CryptException {
      try {
         return func_244731_a(serverId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   private static byte[] func_244731_a(byte[]... p_244731_0_) throws Exception {
      MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");

      for(byte[] abyte : p_244731_0_) {
         messagedigest.update(abyte);
      }

      return messagedigest.digest();
   }

   @OnlyIn(Dist.CLIENT)
   public static PublicKey decodePublicKey(byte[] encodedKey) throws CryptException {
      try {
         EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(encodedKey);
         KeyFactory keyfactory = KeyFactory.getInstance("RSA");
         return keyfactory.generatePublic(encodedkeyspec);
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   public static SecretKey decryptSharedKey(PrivateKey key, byte[] secretKeyEncrypted) throws CryptException {
      byte[] abyte = decryptData(key, secretKeyEncrypted);

      try {
         return new SecretKeySpec(abyte, "AES");
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static byte[] encryptData(Key key, byte[] data) throws CryptException {
      return cipherOperation(1, key, data);
   }

   public static byte[] decryptData(Key key, byte[] data) throws CryptException {
      return cipherOperation(2, key, data);
   }

   private static byte[] cipherOperation(int opMode, Key key, byte[] data) throws CryptException {
      try {
         return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   private static Cipher createTheCipherInstance(int opMode, String transformation, Key key) throws Exception {
      Cipher cipher = Cipher.getInstance(transformation);
      cipher.init(opMode, key);
      return cipher;
   }

   public static Cipher createNetCipherInstance(int opMode, Key key) throws CryptException {
      try {
         Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
         cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
         return cipher;
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }
}
