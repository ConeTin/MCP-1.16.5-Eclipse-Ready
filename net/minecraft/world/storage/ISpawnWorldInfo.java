package net.minecraft.world.storage;

import net.minecraft.util.math.BlockPos;

public interface ISpawnWorldInfo extends IWorldInfo {
   void setSpawnX(int x);

   void setSpawnY(int y);

   void setSpawnZ(int z);

   void setSpawnAngle(float angle);

   default void setSpawn(BlockPos spawnPoint, float angle) {
      this.setSpawnX(spawnPoint.getX());
      this.setSpawnY(spawnPoint.getY());
      this.setSpawnZ(spawnPoint.getZ());
      this.setSpawnAngle(angle);
   }
}
