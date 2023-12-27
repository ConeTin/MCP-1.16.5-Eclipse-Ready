package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Item implements IItemProvider {
   public static final Map<Block, Item> BLOCK_TO_ITEM = Maps.newHashMap();
   protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
   protected static final Random random = new Random();
   protected final ItemGroup group;
   private final Rarity rarity;
   private final int maxStackSize;
   private final int maxDamage;
   private final boolean burnable;
   private final Item containerItem;
   @Nullable
   private String translationKey;
   @Nullable
   private final Food food;

   public static int getIdFromItem(Item itemIn) {
      return itemIn == null ? 0 : Registry.ITEM.getId(itemIn);
   }

   public static Item getItemById(int id) {
      return Registry.ITEM.getByValue(id);
   }

   @Deprecated
   public static Item getItemFromBlock(Block blockIn) {
      return BLOCK_TO_ITEM.getOrDefault(blockIn, Items.AIR);
   }

   public Item(Item.Properties properties) {
      this.group = properties.group;
      this.rarity = properties.rarity;
      this.containerItem = properties.containerItem;
      this.maxDamage = properties.maxDamage;
      this.maxStackSize = properties.maxStackSize;
      this.food = properties.food;
      this.burnable = properties.immuneToFire;
   }

   public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
   }

   public boolean updateItemStackNBT(CompoundNBT nbt) {
      return false;
   }

   public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
      return true;
   }

   public Item asItem() {
      return this;
   }

   public ActionResultType onItemUse(ItemUseContext context) {
      return ActionResultType.PASS;
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      return 1.0F;
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      if (this.isFood()) {
         ItemStack itemstack = playerIn.getHeldItem(handIn);
         if (playerIn.canEat(this.getFood().canEatWhenFull())) {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
         } else {
            return ActionResult.resultFail(itemstack);
         }
      } else {
         return ActionResult.resultPass(playerIn.getHeldItem(handIn));
      }
   }

   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
      return this.isFood() ? entityLiving.onFoodEaten(worldIn, stack) : stack;
   }

   public final int getMaxStackSize() {
      return this.maxStackSize;
   }

   public final int getMaxDamage() {
      return this.maxDamage;
   }

   public boolean isDamageable() {
      return this.maxDamage > 0;
   }

   public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      return false;
   }

   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      return false;
   }

   public boolean canHarvestBlock(BlockState blockIn) {
      return false;
   }

   public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
      return ActionResultType.PASS;
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent getName() {
      return new TranslationTextComponent(this.getTranslationKey());
   }

   public String toString() {
      return Registry.ITEM.getKey(this).getPath();
   }

   protected String getDefaultTranslationKey() {
      if (this.translationKey == null) {
         this.translationKey = Util.makeTranslationKey("item", Registry.ITEM.getKey(this));
      }

      return this.translationKey;
   }

   public String getTranslationKey() {
      return this.getDefaultTranslationKey();
   }

   public String getTranslationKey(ItemStack stack) {
      return this.getTranslationKey();
   }

   public boolean shouldSyncTag() {
      return true;
   }

   @Nullable
   public final Item getContainerItem() {
      return this.containerItem;
   }

   public boolean hasContainerItem() {
      return this.containerItem != null;
   }

   public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
   }

   public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
   }

   public boolean isComplex() {
      return false;
   }

   public UseAction getUseAction(ItemStack stack) {
      return stack.getItem().isFood() ? UseAction.EAT : UseAction.NONE;
   }

   public int getUseDuration(ItemStack stack) {
      if (stack.getItem().isFood()) {
         return this.getFood().isFastEating() ? 16 : 32;
      } else {
         return 0;
      }
   }

   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
   }

   @OnlyIn(Dist.CLIENT)
   public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
   }

   public ITextComponent getDisplayName(ItemStack stack) {
      return new TranslationTextComponent(this.getTranslationKey(stack));
   }

   public boolean hasEffect(ItemStack stack) {
      return stack.isEnchanted();
   }

   public Rarity getRarity(ItemStack stack) {
      if (!stack.isEnchanted()) {
         return this.rarity;
      } else {
         switch(this.rarity) {
         case COMMON:
         case UNCOMMON:
            return Rarity.RARE;
         case RARE:
            return Rarity.EPIC;
         case EPIC:
         default:
            return this.rarity;
         }
      }
   }

   public boolean isEnchantable(ItemStack stack) {
      return this.getMaxStackSize() == 1 && this.isDamageable();
   }

   protected static BlockRayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
      float f = player.rotationPitch;
      float f1 = player.rotationYaw;
      Vector3d vector3d = player.getEyePosition(1.0F);
      float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
      float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      double d0 = 5.0D;
      Vector3d vector3d1 = vector3d.add((double)f6 * 5.0D, (double)f5 * 5.0D, (double)f7 * 5.0D);
      return worldIn.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
   }

   public int getItemEnchantability() {
      return 0;
   }

   public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
      if (this.isInGroup(group)) {
         items.add(new ItemStack(this));
      }

   }

   protected boolean isInGroup(ItemGroup group) {
      ItemGroup itemgroup = this.getGroup();
      return itemgroup != null && (group == ItemGroup.SEARCH || group == itemgroup);
   }

   @Nullable
   public final ItemGroup getGroup() {
      return this.group;
   }

   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
      return false;
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
      return ImmutableMultimap.of();
   }

   public boolean isCrossbow(ItemStack stack) {
      return stack.getItem() == Items.CROSSBOW;
   }

   public ItemStack getDefaultInstance() {
      return new ItemStack(this);
   }

   public boolean isIn(ITag<Item> tagIn) {
      return tagIn.contains(this);
   }

   public boolean isFood() {
      return this.food != null;
   }

   @Nullable
   public Food getFood() {
      return this.food;
   }

   public SoundEvent getDrinkSound() {
      return SoundEvents.ENTITY_GENERIC_DRINK;
   }

   public SoundEvent getEatSound() {
      return SoundEvents.ENTITY_GENERIC_EAT;
   }

   public boolean isImmuneToFire() {
      return this.burnable;
   }

   public boolean isDamageable(DamageSource damageSource) {
      return !this.burnable || !damageSource.isFireDamage();
   }

   public static class Properties {
      private int maxStackSize = 64;
      private int maxDamage;
      private Item containerItem;
      private ItemGroup group;
      private Rarity rarity = Rarity.COMMON;
      private Food food;
      private boolean immuneToFire;

      public Item.Properties food(Food foodIn) {
         this.food = foodIn;
         return this;
      }

      public Item.Properties maxStackSize(int maxStackSizeIn) {
         if (this.maxDamage > 0) {
            throw new RuntimeException("Unable to have damage AND stack.");
         } else {
            this.maxStackSize = maxStackSizeIn;
            return this;
         }
      }

      public Item.Properties defaultMaxDamage(int maxDamageIn) {
         return this.maxDamage == 0 ? this.maxDamage(maxDamageIn) : this;
      }

      public Item.Properties maxDamage(int maxDamageIn) {
         this.maxDamage = maxDamageIn;
         this.maxStackSize = 1;
         return this;
      }

      public Item.Properties containerItem(Item containerItemIn) {
         this.containerItem = containerItemIn;
         return this;
      }

      public Item.Properties group(ItemGroup groupIn) {
         this.group = groupIn;
         return this;
      }

      public Item.Properties rarity(Rarity rarityIn) {
         this.rarity = rarityIn;
         return this;
      }

      public Item.Properties isImmuneToFire() {
         this.immuneToFire = true;
         return this;
      }
   }
}
