package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CEditBookPacket implements IPacket<IServerPlayNetHandler> {
   private ItemStack stack;
   private boolean updateAll;
   private int field_244707_c;

   public CEditBookPacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CEditBookPacket(ItemStack p_i244520_1_, boolean p_i244520_2_, int p_i244520_3_) {
      this.stack = p_i244520_1_.copy();
      this.updateAll = p_i244520_2_;
      this.field_244707_c = p_i244520_3_;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.stack = buf.readItemStack();
      this.updateAll = buf.readBoolean();
      this.field_244707_c = buf.readVarInt();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeItemStack(this.stack);
      buf.writeBoolean(this.updateAll);
      buf.writeVarInt(this.field_244707_c);
   }

   public void processPacket(IServerPlayNetHandler handler) {
      handler.processEditBook(this);
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public boolean shouldUpdateAll() {
      return this.updateAll;
   }

   public int func_244708_d() {
      return this.field_244707_c;
   }
}
