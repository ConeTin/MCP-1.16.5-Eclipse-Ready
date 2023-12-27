package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class STagsListPacket implements IPacket<IClientPlayNetHandler> {
   private ITagCollectionSupplier tags;

   public STagsListPacket() {
   }

   public STagsListPacket(ITagCollectionSupplier p_i242087_1_) {
      this.tags = p_i242087_1_;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.tags = ITagCollectionSupplier.readTagCollectionSupplierFromBuffer(buf);
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      this.tags.writeTagCollectionSupplierToBuffer(buf);
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleTags(this);
   }

   @OnlyIn(Dist.CLIENT)
   public ITagCollectionSupplier getTags() {
      return this.tags;
   }
}
