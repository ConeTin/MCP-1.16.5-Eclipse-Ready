package net.minecraft.world.storage;

import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

public class WorldSummary implements Comparable<WorldSummary> {
   private final WorldSettings settings;
   private final VersionData versionData;
   private final String fileName;
   private final boolean requiresConversion;
   private final boolean locked;
   private final File iconFile;
   @Nullable
   @OnlyIn(Dist.CLIENT)
   private ITextComponent description;

   public WorldSummary(WorldSettings settings, VersionData versionData, String directoryName, boolean requiresConversion, boolean locked, File iconFile) {
      this.settings = settings;
      this.versionData = versionData;
      this.fileName = directoryName;
      this.locked = locked;
      this.iconFile = iconFile;
      this.requiresConversion = requiresConversion;
   }

   @OnlyIn(Dist.CLIENT)
   public String getFileName() {
      return this.fileName;
   }

   @OnlyIn(Dist.CLIENT)
   public String getDisplayName() {
      return StringUtils.isEmpty(this.settings.getWorldName()) ? this.fileName : this.settings.getWorldName();
   }

   @OnlyIn(Dist.CLIENT)
   public File getIconFile() {
      return this.iconFile;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean requiresConversion() {
      return this.requiresConversion;
   }

   @OnlyIn(Dist.CLIENT)
   public long getLastTimePlayed() {
      return this.versionData.getLastPlayed();
   }

   public int compareTo(WorldSummary p_compareTo_1_) {
      if (this.versionData.getLastPlayed() < p_compareTo_1_.versionData.getLastPlayed()) {
         return 1;
      } else {
         return this.versionData.getLastPlayed() > p_compareTo_1_.versionData.getLastPlayed() ? -1 : this.fileName.compareTo(p_compareTo_1_.fileName);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public GameType getEnumGameType() {
      return this.settings.getGameType();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isHardcoreModeEnabled() {
      return this.settings.isHardcoreEnabled();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean getCheatsEnabled() {
      return this.settings.isCommandsAllowed();
   }

   @OnlyIn(Dist.CLIENT)
   public IFormattableTextComponent getVersionName() {
      return (IFormattableTextComponent)(net.minecraft.util.StringUtils.isNullOrEmpty(this.versionData.getName()) ? new TranslationTextComponent("selectWorld.versionUnknown") : new StringTextComponent(this.versionData.getName()));
   }

   public VersionData getVersionData() {
      return this.versionData;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean markVersionInList() {
      return this.askToOpenWorld() || !SharedConstants.getVersion().isStable() && !this.versionData.isSnapshot() || this.askToCreateBackup();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean askToOpenWorld() {
      return this.versionData.getID() > SharedConstants.getVersion().getWorldVersion();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean askToCreateBackup() {
      return this.versionData.getID() < SharedConstants.getVersion().getWorldVersion();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isLocked() {
      return this.locked;
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent getDescription() {
      if (this.description == null) {
         this.description = this.createDescription();
      }

      return this.description;
   }

   @OnlyIn(Dist.CLIENT)
   private ITextComponent createDescription() {
      if (this.isLocked()) {
         return (new TranslationTextComponent("selectWorld.locked")).mergeStyle(TextFormatting.RED);
      } else if (this.requiresConversion()) {
         return new TranslationTextComponent("selectWorld.conversion");
      } else {
         IFormattableTextComponent iformattabletextcomponent = (IFormattableTextComponent)(this.isHardcoreModeEnabled() ? (new StringTextComponent("")).append((new TranslationTextComponent("gameMode.hardcore")).mergeStyle(TextFormatting.DARK_RED)) : new TranslationTextComponent("gameMode." + this.getEnumGameType().getName()));
         if (this.getCheatsEnabled()) {
            iformattabletextcomponent.appendString(", ").append(new TranslationTextComponent("selectWorld.cheats"));
         }

         IFormattableTextComponent iformattabletextcomponent1 = this.getVersionName();
         IFormattableTextComponent iformattabletextcomponent2 = (new StringTextComponent(", ")).append(new TranslationTextComponent("selectWorld.version")).appendString(" ");
         if (this.markVersionInList()) {
            iformattabletextcomponent2.append(iformattabletextcomponent1.mergeStyle(this.askToOpenWorld() ? TextFormatting.RED : TextFormatting.ITALIC));
         } else {
            iformattabletextcomponent2.append(iformattabletextcomponent1);
         }

         iformattabletextcomponent.append(iformattabletextcomponent2);
         return iformattabletextcomponent;
      }
   }
}
