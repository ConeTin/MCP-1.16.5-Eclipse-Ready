package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScorePlayerTeam extends Team {
   private final Scoreboard scoreboard;
   private final String name;
   private final Set<String> membershipSet = Sets.newHashSet();
   private ITextComponent displayName;
   private ITextComponent prefix = StringTextComponent.EMPTY;
   private ITextComponent suffix = StringTextComponent.EMPTY;
   private boolean allowFriendlyFire = true;
   private boolean canSeeFriendlyInvisibles = true;
   private Team.Visible nameTagVisibility = Team.Visible.ALWAYS;
   private Team.Visible deathMessageVisibility = Team.Visible.ALWAYS;
   private TextFormatting color = TextFormatting.RESET;
   private Team.CollisionRule collisionRule = Team.CollisionRule.ALWAYS;
   private final Style field_237499_m_;

   public ScorePlayerTeam(Scoreboard scoreboardIn, String name) {
      this.scoreboard = scoreboardIn;
      this.name = name;
      this.displayName = new StringTextComponent(name);
      this.field_237499_m_ = Style.EMPTY.setInsertion(name).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(name)));
   }

   public String getName() {
      return this.name;
   }

   public ITextComponent getDisplayName() {
      return this.displayName;
   }

   public IFormattableTextComponent func_237501_d_() {
      IFormattableTextComponent iformattabletextcomponent = TextComponentUtils.wrapWithSquareBrackets(this.displayName.deepCopy().mergeStyle(this.field_237499_m_));
      TextFormatting textformatting = this.getColor();
      if (textformatting != TextFormatting.RESET) {
         iformattabletextcomponent.mergeStyle(textformatting);
      }

      return iformattabletextcomponent;
   }

   public void setDisplayName(ITextComponent name) {
      if (name == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.displayName = name;
         this.scoreboard.onTeamChanged(this);
      }
   }

   public void setPrefix(@Nullable ITextComponent p_207408_1_) {
      this.prefix = p_207408_1_ == null ? StringTextComponent.EMPTY : p_207408_1_;
      this.scoreboard.onTeamChanged(this);
   }

   public ITextComponent getPrefix() {
      return this.prefix;
   }

   public void setSuffix(@Nullable ITextComponent p_207409_1_) {
      this.suffix = p_207409_1_ == null ? StringTextComponent.EMPTY : p_207409_1_;
      this.scoreboard.onTeamChanged(this);
   }

   public ITextComponent getSuffix() {
      return this.suffix;
   }

   public Collection<String> getMembershipCollection() {
      return this.membershipSet;
   }

   public IFormattableTextComponent func_230427_d_(ITextComponent p_230427_1_) {
      IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("")).append(this.prefix).append(p_230427_1_).append(this.suffix);
      TextFormatting textformatting = this.getColor();
      if (textformatting != TextFormatting.RESET) {
         iformattabletextcomponent.mergeStyle(textformatting);
      }

      return iformattabletextcomponent;
   }

   public static IFormattableTextComponent func_237500_a_(@Nullable Team p_237500_0_, ITextComponent p_237500_1_) {
      return p_237500_0_ == null ? p_237500_1_.deepCopy() : p_237500_0_.func_230427_d_(p_237500_1_);
   }

   public boolean getAllowFriendlyFire() {
      return this.allowFriendlyFire;
   }

   public void setAllowFriendlyFire(boolean friendlyFire) {
      this.allowFriendlyFire = friendlyFire;
      this.scoreboard.onTeamChanged(this);
   }

   public boolean getSeeFriendlyInvisiblesEnabled() {
      return this.canSeeFriendlyInvisibles;
   }

   public void setSeeFriendlyInvisiblesEnabled(boolean friendlyInvisibles) {
      this.canSeeFriendlyInvisibles = friendlyInvisibles;
      this.scoreboard.onTeamChanged(this);
   }

   public Team.Visible getNameTagVisibility() {
      return this.nameTagVisibility;
   }

   public Team.Visible getDeathMessageVisibility() {
      return this.deathMessageVisibility;
   }

   public void setNameTagVisibility(Team.Visible visibility) {
      this.nameTagVisibility = visibility;
      this.scoreboard.onTeamChanged(this);
   }

   public void setDeathMessageVisibility(Team.Visible visibility) {
      this.deathMessageVisibility = visibility;
      this.scoreboard.onTeamChanged(this);
   }

   public Team.CollisionRule getCollisionRule() {
      return this.collisionRule;
   }

   public void setCollisionRule(Team.CollisionRule rule) {
      this.collisionRule = rule;
      this.scoreboard.onTeamChanged(this);
   }

   public int getFriendlyFlags() {
      int i = 0;
      if (this.getAllowFriendlyFire()) {
         i |= 1;
      }

      if (this.getSeeFriendlyInvisiblesEnabled()) {
         i |= 2;
      }

      return i;
   }

   @OnlyIn(Dist.CLIENT)
   public void setFriendlyFlags(int flags) {
      this.setAllowFriendlyFire((flags & 1) > 0);
      this.setSeeFriendlyInvisiblesEnabled((flags & 2) > 0);
   }

   public void setColor(TextFormatting color) {
      this.color = color;
      this.scoreboard.onTeamChanged(this);
   }

   public TextFormatting getColor() {
      return this.color;
   }
}
