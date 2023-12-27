package net.minecraft.world.storage;

import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class WorldSavedData {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String name;
   private boolean dirty;

   public WorldSavedData(String name) {
      this.name = name;
   }

   public abstract void read(CompoundNBT nbt);

   public abstract CompoundNBT write(CompoundNBT compound);

   public void markDirty() {
      this.setDirty(true);
   }

   public void setDirty(boolean isDirty) {
      this.dirty = isDirty;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public String getName() {
      return this.name;
   }

   public void save(File fileIn) {
      if (this.isDirty()) {
         CompoundNBT compoundnbt = new CompoundNBT();
         compoundnbt.put("data", this.write(new CompoundNBT()));
         compoundnbt.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());

         try {
            CompressedStreamTools.writeCompressed(compoundnbt, fileIn);
         } catch (IOException ioexception) {
            LOGGER.error("Could not save data {}", this, ioexception);
         }

         this.setDirty(false);
      }
   }
}
