package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BannerPatternItem extends Item {
   private final BannerPattern pattern;

   public BannerPatternItem(BannerPattern pattern, Item.Properties builder) {
      super(builder);
      this.pattern = pattern;
   }

   public BannerPattern getBannerPattern() {
      return this.pattern;
   }

   @OnlyIn(Dist.CLIENT)
   public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
      tooltip.add(this.func_219981_d_().mergeStyle(TextFormatting.GRAY));
   }

   @OnlyIn(Dist.CLIENT)
   public IFormattableTextComponent func_219981_d_() {
      return new TranslationTextComponent(this.getTranslationKey() + ".desc");
   }
}
