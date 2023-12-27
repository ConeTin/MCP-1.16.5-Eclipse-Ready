package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class EndNBT implements INBT {
   public static final INBTType<EndNBT> TYPE = new INBTType<EndNBT>() {
      public EndNBT readNBT(DataInput input, int depth, NBTSizeTracker accounter) {
         accounter.read(64L);
         return EndNBT.INSTANCE;
      }

      public String getName() {
         return "END";
      }

      public String getTagName() {
         return "TAG_End";
      }

      public boolean isPrimitive() {
         return true;
      }
   };
   public static final EndNBT INSTANCE = new EndNBT();

   private EndNBT() {
   }

   public void write(DataOutput output) throws IOException {
   }

   public byte getId() {
      return 0;
   }

   public INBTType<EndNBT> getType() {
      return TYPE;
   }

   public String toString() {
      return "END";
   }

   public EndNBT copy() {
      return this;
   }

   public ITextComponent toFormattedComponent(String indentation, int indentDepth) {
      return StringTextComponent.EMPTY;
   }
}
