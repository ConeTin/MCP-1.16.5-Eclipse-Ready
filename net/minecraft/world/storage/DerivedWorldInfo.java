package net.minecraft.world.storage;

import java.util.UUID;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;

public class DerivedWorldInfo implements IServerWorldInfo {
   private final IServerConfiguration configuration;
   private final IServerWorldInfo delegate;

   public DerivedWorldInfo(IServerConfiguration configuration, IServerWorldInfo delegate) {
      this.configuration = configuration;
      this.delegate = delegate;
   }

   public int getSpawnX() {
      return this.delegate.getSpawnX();
   }

   public int getSpawnY() {
      return this.delegate.getSpawnY();
   }

   public int getSpawnZ() {
      return this.delegate.getSpawnZ();
   }

   public float getSpawnAngle() {
      return this.delegate.getSpawnAngle();
   }

   public long getGameTime() {
      return this.delegate.getGameTime();
   }

   public long getDayTime() {
      return this.delegate.getDayTime();
   }

   public String getWorldName() {
      return this.configuration.getWorldName();
   }

   public int getClearWeatherTime() {
      return this.delegate.getClearWeatherTime();
   }

   public void setClearWeatherTime(int time) {
   }

   public boolean isThundering() {
      return this.delegate.isThundering();
   }

   public int getThunderTime() {
      return this.delegate.getThunderTime();
   }

   public boolean isRaining() {
      return this.delegate.isRaining();
   }

   public int getRainTime() {
      return this.delegate.getRainTime();
   }

   public GameType getGameType() {
      return this.configuration.getGameType();
   }

   public void setSpawnX(int x) {
   }

   public void setSpawnY(int y) {
   }

   public void setSpawnZ(int z) {
   }

   public void setSpawnAngle(float angle) {
   }

   public void setGameTime(long time) {
   }

   public void setDayTime(long time) {
   }

   public void setSpawn(BlockPos spawnPoint, float angle) {
   }

   public void setThundering(boolean thunderingIn) {
   }

   public void setThunderTime(int time) {
   }

   public void setRaining(boolean isRaining) {
   }

   public void setRainTime(int time) {
   }

   public void setGameType(GameType type) {
   }

   public boolean isHardcore() {
      return this.configuration.isHardcore();
   }

   public boolean areCommandsAllowed() {
      return this.configuration.areCommandsAllowed();
   }

   public boolean isInitialized() {
      return this.delegate.isInitialized();
   }

   public void setInitialized(boolean initializedIn) {
   }

   public GameRules getGameRulesInstance() {
      return this.configuration.getGameRulesInstance();
   }

   public WorldBorder.Serializer getWorldBorderSerializer() {
      return this.delegate.getWorldBorderSerializer();
   }

   public void setWorldBorderSerializer(WorldBorder.Serializer serializer) {
   }

   public Difficulty getDifficulty() {
      return this.configuration.getDifficulty();
   }

   public boolean isDifficultyLocked() {
      return this.configuration.isDifficultyLocked();
   }

   public TimerCallbackManager<MinecraftServer> getScheduledEvents() {
      return this.delegate.getScheduledEvents();
   }

   public int getWanderingTraderSpawnDelay() {
      return 0;
   }

   public void setWanderingTraderSpawnDelay(int delay) {
   }

   public int getWanderingTraderSpawnChance() {
      return 0;
   }

   public void setWanderingTraderSpawnChance(int chance) {
   }

   public void setWanderingTraderID(UUID id) {
   }

   public void addToCrashReport(CrashReportCategory category) {
      category.addDetail("Derived", true);
      this.delegate.addToCrashReport(category);
   }
}
