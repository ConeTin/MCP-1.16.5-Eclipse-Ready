package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.CryptException;
import net.minecraft.util.CryptManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SEncryptionRequestPacket implements IPacket<IClientLoginNetHandler> {
   private String hashedServerId;
   private byte[] publicKey;
   private byte[] verifyToken;

   public SEncryptionRequestPacket() {
   }

   public SEncryptionRequestPacket(String p_i244727_1_, byte[] p_i244727_2_, byte[] p_i244727_3_) {
      this.hashedServerId = p_i244727_1_;
      this.publicKey = p_i244727_2_;
      this.verifyToken = p_i244727_3_;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.hashedServerId = buf.readString(20);
      this.publicKey = buf.readByteArray();
      this.verifyToken = buf.readByteArray();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeString(this.hashedServerId);
      buf.writeByteArray(this.publicKey);
      buf.writeByteArray(this.verifyToken);
   }

   public void processPacket(IClientLoginNetHandler handler) {
      handler.handleEncryptionRequest(this);
   }

   @OnlyIn(Dist.CLIENT)
   public String getServerId() {
      return this.hashedServerId;
   }

   @OnlyIn(Dist.CLIENT)
   public PublicKey getPublicKey() throws CryptException {
      return CryptManager.decodePublicKey(this.publicKey);
   }

   @OnlyIn(Dist.CLIENT)
   public byte[] getVerifyToken() {
      return this.verifyToken;
   }
}
