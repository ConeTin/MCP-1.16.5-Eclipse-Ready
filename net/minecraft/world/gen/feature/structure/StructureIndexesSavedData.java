package net.minecraft.world.gen.feature.structure;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class StructureIndexesSavedData extends WorldSavedData {
   private LongSet all = new LongOpenHashSet();
   private LongSet remaining = new LongOpenHashSet();

   public StructureIndexesSavedData(String name) {
      super(name);
   }

   public void read(CompoundNBT nbt) {
      this.all = new LongOpenHashSet(nbt.getLongArray("All"));
      this.remaining = new LongOpenHashSet(nbt.getLongArray("Remaining"));
   }

   public CompoundNBT write(CompoundNBT compound) {
      compound.putLongArray("All", this.all.toLongArray());
      compound.putLongArray("Remaining", this.remaining.toLongArray());
      return compound;
   }

   public void addStructureIndex(long chunkPos) {
      this.all.add(chunkPos);
      this.remaining.add(chunkPos);
   }

   public boolean hasStructureIndexInAll(long chunkPos) {
      return this.all.contains(chunkPos);
   }

   public boolean hasStructureIndexInRemaining(long chunkPos) {
      return this.remaining.contains(chunkPos);
   }

   public void removeStructureIndex(long chunkPos) {
      this.remaining.remove(chunkPos);
   }

   public LongSet getAll() {
      return this.all;
   }
}
