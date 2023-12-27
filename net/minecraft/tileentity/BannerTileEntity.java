package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BannerTileEntity extends TileEntity implements INameable {
   @Nullable
   private ITextComponent name;
   @Nullable
   private DyeColor baseColor = DyeColor.WHITE;
   @Nullable
   private ListNBT patterns;
   private boolean patternDataSet;
   @Nullable
   private List<Pair<BannerPattern, DyeColor>> patternList;

   public BannerTileEntity() {
      super(TileEntityType.BANNER);
   }

   public BannerTileEntity(DyeColor baseColor) {
      this();
      this.baseColor = baseColor;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public static ListNBT getPatternData(ItemStack stack) {
      ListNBT listnbt = null;
      CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
      if (compoundnbt != null && compoundnbt.contains("Patterns", 9)) {
         listnbt = compoundnbt.getList("Patterns", 10).copy();
      }

      return listnbt;
   }

   @OnlyIn(Dist.CLIENT)
   public void loadFromItemStack(ItemStack stack, DyeColor color) {
      this.patterns = getPatternData(stack);
      this.baseColor = color;
      this.patternList = null;
      this.patternDataSet = true;
      this.name = stack.hasDisplayName() ? stack.getDisplayName() : null;
   }

   public ITextComponent getName() {
      return (ITextComponent)(this.name != null ? this.name : new TranslationTextComponent("block.minecraft.banner"));
   }

   @Nullable
   public ITextComponent getCustomName() {
      return this.name;
   }

   public void setName(ITextComponent name) {
      this.name = name;
   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      if (this.patterns != null) {
         compound.put("Patterns", this.patterns);
      }

      if (this.name != null) {
         compound.putString("CustomName", ITextComponent.Serializer.toJson(this.name));
      }

      return compound;
   }

   public void read(BlockState state, CompoundNBT nbt) {
      super.read(state, nbt);
      if (nbt.contains("CustomName", 8)) {
         this.name = ITextComponent.Serializer.getComponentFromJson(nbt.getString("CustomName"));
      }

      if (this.hasWorld()) {
         this.baseColor = ((AbstractBannerBlock)this.getBlockState().getBlock()).getColor();
      } else {
         this.baseColor = null;
      }

      this.patterns = nbt.getList("Patterns", 10);
      this.patternList = null;
      this.patternDataSet = true;
   }

   @Nullable
   public SUpdateTileEntityPacket getUpdatePacket() {
      return new SUpdateTileEntityPacket(this.pos, 6, this.getUpdateTag());
   }

   public CompoundNBT getUpdateTag() {
      return this.write(new CompoundNBT());
   }

   public static int getPatterns(ItemStack stack) {
      CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
      return compoundnbt != null && compoundnbt.contains("Patterns") ? compoundnbt.getList("Patterns", 10).size() : 0;
   }

   @OnlyIn(Dist.CLIENT)
   public List<Pair<BannerPattern, DyeColor>> getPatternList() {
      if (this.patternList == null && this.patternDataSet) {
         this.patternList = getPatternColorData(this.getBaseColor(this::getBlockState), this.patterns);
      }

      return this.patternList;
   }

   @OnlyIn(Dist.CLIENT)
   public static List<Pair<BannerPattern, DyeColor>> getPatternColorData(DyeColor color, @Nullable ListNBT nbtList) {
      List<Pair<BannerPattern, DyeColor>> list = Lists.newArrayList();
      list.add(Pair.of(BannerPattern.BASE, color));
      if (nbtList != null) {
         for(int i = 0; i < nbtList.size(); ++i) {
            CompoundNBT compoundnbt = nbtList.getCompound(i);
            BannerPattern bannerpattern = BannerPattern.byHash(compoundnbt.getString("Pattern"));
            if (bannerpattern != null) {
               int j = compoundnbt.getInt("Color");
               list.add(Pair.of(bannerpattern, DyeColor.byId(j)));
            }
         }
      }

      return list;
   }

   public static void removeBannerData(ItemStack stack) {
      CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
      if (compoundnbt != null && compoundnbt.contains("Patterns", 9)) {
         ListNBT listnbt = compoundnbt.getList("Patterns", 10);
         if (!listnbt.isEmpty()) {
            listnbt.remove(listnbt.size() - 1);
            if (listnbt.isEmpty()) {
               stack.removeChildTag("BlockEntityTag");
            }

         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getItem(BlockState state) {
      ItemStack itemstack = new ItemStack(BannerBlock.forColor(this.getBaseColor(() -> {
         return state;
      })));
      if (this.patterns != null && !this.patterns.isEmpty()) {
         itemstack.getOrCreateChildTag("BlockEntityTag").put("Patterns", this.patterns.copy());
      }

      if (this.name != null) {
         itemstack.setDisplayName(this.name);
      }

      return itemstack;
   }

   public DyeColor getBaseColor(Supplier<BlockState> bannerBlockStateSupplier) {
      if (this.baseColor == null) {
         this.baseColor = ((AbstractBannerBlock)bannerBlockStateSupplier.get().getBlock()).getColor();
      }

      return this.baseColor;
   }
}
