package net.minecraft.network.login.client;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.IServerLoginNetHandler;
import net.minecraft.util.CryptException;
import net.minecraft.util.CryptManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CEncryptionResponsePacket implements IPacket<IServerLoginNetHandler> {
   private byte[] secretKeyEncrypted = new byte[0];
   private byte[] verifyTokenEncrypted = new byte[0];

   public CEncryptionResponsePacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CEncryptionResponsePacket(SecretKey secret, PublicKey key, byte[] verifyToken) throws CryptException {
      this.secretKeyEncrypted = CryptManager.encryptData(key, secret.getEncoded());
      this.verifyTokenEncrypted = CryptManager.encryptData(key, verifyToken);
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.secretKeyEncrypted = buf.readByteArray();
      this.verifyTokenEncrypted = buf.readByteArray();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeByteArray(this.secretKeyEncrypted);
      buf.writeByteArray(this.verifyTokenEncrypted);
   }

   public void processPacket(IServerLoginNetHandler handler) {
      handler.processEncryptionResponse(this);
   }

   public SecretKey getSecretKey(PrivateKey key) throws CryptException {
      return CryptManager.decryptSharedKey(key, this.secretKeyEncrypted);
   }

   public byte[] getVerifyToken(PrivateKey key) throws CryptException {
      return CryptManager.decryptData(key, this.verifyTokenEncrypted);
   }
}
