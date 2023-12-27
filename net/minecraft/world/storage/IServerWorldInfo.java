package net.minecraft.world.storage;

import java.util.UUID;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;

public interface IServerWorldInfo extends ISpawnWorldInfo {
   String getWorldName();

   void setThundering(boolean thunderingIn);

   int getRainTime();

   void setRainTime(int time);

   void setThunderTime(int time);

   int getThunderTime();

   default void addToCrashReport(CrashReportCategory category) {
      ISpawnWorldInfo.super.addToCrashReport(category);
      category.addDetail("Level name", this::getWorldName);
      category.addDetail("Level game mode", () -> {
         return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.getGameType().getName(), this.getGameType().getID(), this.isHardcore(), this.areCommandsAllowed());
      });
      category.addDetail("Level weather", () -> {
         return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.getRainTime(), this.isRaining(), this.getThunderTime(), this.isThundering());
      });
   }

   int getClearWeatherTime();

   void setClearWeatherTime(int time);

   int getWanderingTraderSpawnDelay();

   void setWanderingTraderSpawnDelay(int delay);

   int getWanderingTraderSpawnChance();

   void setWanderingTraderSpawnChance(int chance);

   void setWanderingTraderID(UUID id);

   GameType getGameType();

   void setWorldBorderSerializer(WorldBorder.Serializer serializer);

   WorldBorder.Serializer getWorldBorderSerializer();

   boolean isInitialized();

   void setInitialized(boolean initializedIn);

   boolean areCommandsAllowed();

   void setGameType(GameType type);

   TimerCallbackManager<MinecraftServer> getScheduledEvents();

   void setGameTime(long time);

   void setDayTime(long time);
}
