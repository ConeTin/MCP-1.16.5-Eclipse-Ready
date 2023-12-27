package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BipedArmorLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
   private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
   private final A modelLeggings;
   private final A modelArmor;

   public BipedArmorLayer(IEntityRenderer<T, M> p_i50936_1_, A p_i50936_2_, A p_i50936_3_) {
      super(p_i50936_1_);
      this.modelLeggings = p_i50936_2_;
      this.modelArmor = p_i50936_3_;
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      this.func_241739_a_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.CHEST, packedLightIn, this.func_241736_a_(EquipmentSlotType.CHEST));
      this.func_241739_a_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.LEGS, packedLightIn, this.func_241736_a_(EquipmentSlotType.LEGS));
      this.func_241739_a_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.FEET, packedLightIn, this.func_241736_a_(EquipmentSlotType.FEET));
      this.func_241739_a_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.HEAD, packedLightIn, this.func_241736_a_(EquipmentSlotType.HEAD));
   }

   private void func_241739_a_(MatrixStack p_241739_1_, IRenderTypeBuffer p_241739_2_, T p_241739_3_, EquipmentSlotType p_241739_4_, int p_241739_5_, A p_241739_6_) {
      ItemStack itemstack = p_241739_3_.getItemStackFromSlot(p_241739_4_);
      if (itemstack.getItem() instanceof ArmorItem) {
         ArmorItem armoritem = (ArmorItem)itemstack.getItem();
         if (armoritem.getEquipmentSlot() == p_241739_4_) {
            this.getEntityModel().setModelAttributes(p_241739_6_);
            this.setModelSlotVisible(p_241739_6_, p_241739_4_);
            boolean flag = this.isLegSlot(p_241739_4_);
            boolean flag1 = itemstack.hasEffect();
            if (armoritem instanceof DyeableArmorItem) {
               int i = ((DyeableArmorItem)armoritem).getColor(itemstack);
               float f = (float)(i >> 16 & 255) / 255.0F;
               float f1 = (float)(i >> 8 & 255) / 255.0F;
               float f2 = (float)(i & 255) / 255.0F;
               this.func_241738_a_(p_241739_1_, p_241739_2_, p_241739_5_, armoritem, flag1, p_241739_6_, flag, f, f1, f2, (String)null);
               this.func_241738_a_(p_241739_1_, p_241739_2_, p_241739_5_, armoritem, flag1, p_241739_6_, flag, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
               this.func_241738_a_(p_241739_1_, p_241739_2_, p_241739_5_, armoritem, flag1, p_241739_6_, flag, 1.0F, 1.0F, 1.0F, (String)null);
            }

         }
      }
   }

   protected void setModelSlotVisible(A modelIn, EquipmentSlotType slotIn) {
      modelIn.setVisible(false);
      switch(slotIn) {
      case HEAD:
         modelIn.bipedHead.showModel = true;
         modelIn.bipedHeadwear.showModel = true;
         break;
      case CHEST:
         modelIn.bipedBody.showModel = true;
         modelIn.bipedRightArm.showModel = true;
         modelIn.bipedLeftArm.showModel = true;
         break;
      case LEGS:
         modelIn.bipedBody.showModel = true;
         modelIn.bipedRightLeg.showModel = true;
         modelIn.bipedLeftLeg.showModel = true;
         break;
      case FEET:
         modelIn.bipedRightLeg.showModel = true;
         modelIn.bipedLeftLeg.showModel = true;
      }

   }

   private void func_241738_a_(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, ArmorItem p_241738_4_, boolean p_241738_5_, A p_241738_6_, boolean p_241738_7_, float p_241738_8_, float p_241738_9_, float p_241738_10_, @Nullable String p_241738_11_) {
      IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(p_241738_2_, RenderType.getArmorCutoutNoCull(this.func_241737_a_(p_241738_4_, p_241738_7_, p_241738_11_)), false, p_241738_5_);
      p_241738_6_.render(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
   }

   private A func_241736_a_(EquipmentSlotType p_241736_1_) {
      return (A)(this.isLegSlot(p_241736_1_) ? this.modelLeggings : this.modelArmor);
   }

   private boolean isLegSlot(EquipmentSlotType slotIn) {
      return slotIn == EquipmentSlotType.LEGS;
   }

   private ResourceLocation func_241737_a_(ArmorItem p_241737_1_, boolean p_241737_2_, @Nullable String p_241737_3_) {
      String s = "textures/models/armor/" + p_241737_1_.getArmorMaterial().getName() + "_layer_" + (p_241737_2_ ? 2 : 1) + (p_241737_3_ == null ? "" : "_" + p_241737_3_) + ".png";
      return ARMOR_TEXTURE_RES_MAP.computeIfAbsent(s, ResourceLocation::new);
   }
}
