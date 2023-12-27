package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RenderType extends RenderState {
   private static final RenderType SOLID = makeType("solid", DefaultVertexFormats.BLOCK, 7, 2097152, true, false, RenderType.State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).build(true));
   private static final RenderType CUTOUT_MIPPED = makeType("cutout_mipped", DefaultVertexFormats.BLOCK, 7, 131072, true, false, RenderType.State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).alpha(HALF_ALPHA).build(true));
   private static final RenderType CUTOUT = makeType("cutout", DefaultVertexFormats.BLOCK, 7, 131072, true, false, RenderType.State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET).alpha(HALF_ALPHA).build(true));
   private static final RenderType TRANSLUCENT = makeType("translucent", DefaultVertexFormats.BLOCK, 7, 262144, true, true, getTranslucentState());
   private static final RenderType TRANSLUCENT_MOVING__BLOCK = makeType("translucent_moving_block", DefaultVertexFormats.BLOCK, 7, 262144, false, true, getItemEntityState());
   private static final RenderType TRANSLUCENT_NO_CRUMBLING = makeType("translucent_no_crumbling", DefaultVertexFormats.BLOCK, 7, 262144, false, true, getTranslucentState());
   private static final RenderType LEASH = makeType("leash", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP, 7, 256, RenderType.State.getBuilder().texture(NO_TEXTURE).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).build(false));
   private static final RenderType WATER_MASK = makeType("water_mask", DefaultVertexFormats.POSITION, 7, 256, RenderType.State.getBuilder().texture(NO_TEXTURE).writeMask(DEPTH_WRITE).build(false));
   private static final RenderType ARMOR_GLINT = makeType("armor_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).layer(field_239235_M_).build(false));
   private static final RenderType ARMOR_ENTITY_GLINT = makeType("armor_entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).layer(field_239235_M_).build(false));
   private static final RenderType GLINT_TRANSLUCENT = makeType("glint_translucent", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).target(field_241712_U_).build(false));
   private static final RenderType GLINT = makeType("glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
   private static final RenderType GLINT_DIRECT = makeType("glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
   private static final RenderType ENTITY_GLINT = makeType("entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).target(field_241712_U_).texturing(ENTITY_GLINT_TEXTURING).build(false));
   private static final RenderType ENTITY_GLINT_DIRECT = makeType("entity_glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).build(false));
   private static final RenderType LIGHTNING = makeType("lightning", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, RenderType.State.getBuilder().writeMask(COLOR_DEPTH_WRITE).transparency(LIGHTNING_TRANSPARENCY).target(field_239238_U_).shadeModel(SHADE_ENABLED).build(false));
   private static final RenderType TRIPWIRE = makeType("tripwire", DefaultVertexFormats.BLOCK, 7, 262144, true, true, getWeatherState());
   public static final RenderType.Type LINES = makeType("lines", DefaultVertexFormats.POSITION_COLOR, 1, 256, RenderType.State.getBuilder().line(new RenderState.LineState(OptionalDouble.empty())).layer(field_239235_M_).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).writeMask(COLOR_DEPTH_WRITE).build(false));
   private final VertexFormat vertexFormat;
   private final int drawMode;
   private final int bufferSize;
   private final boolean useDelegate;
   private final boolean needsSorting;
   private final Optional<RenderType> renderType;

   public static RenderType getSolid() {
      return SOLID;
   }

   public static RenderType getCutoutMipped() {
      return CUTOUT_MIPPED;
   }

   public static RenderType getCutout() {
      return CUTOUT;
   }

   private static RenderType.State getTranslucentState() {
      return RenderType.State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).transparency(TRANSLUCENT_TRANSPARENCY).target(field_239236_S_).build(true);
   }

   public static RenderType getTranslucent() {
      return TRANSLUCENT;
   }

   private static RenderType.State getItemEntityState() {
      return RenderType.State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).build(true);
   }

   public static RenderType getTranslucentMovingBlock() {
      return TRANSLUCENT_MOVING__BLOCK;
   }

   public static RenderType getTranslucentNoCrumbling() {
      return TRANSLUCENT_NO_CRUMBLING;
   }

   public static RenderType getArmorCutoutNoCull(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).layer(field_239235_M_).build(true);
      return makeType("armor_cutout_no_cull", DefaultVertexFormats.ENTITY, 7, 256, true, false, rendertype$state);
   }

   public static RenderType getEntitySolid(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(true);
      return makeType("entity_solid", DefaultVertexFormats.ENTITY, 7, 256, true, false, rendertype$state);
   }

   public static RenderType getEntityCutout(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(true);
      return makeType("entity_cutout", DefaultVertexFormats.ENTITY, 7, 256, true, false, rendertype$state);
   }

   public static RenderType getEntityCutoutNoCull(ResourceLocation locationIn, boolean outlineIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(outlineIn);
      return makeType("entity_cutout_no_cull", DefaultVertexFormats.ENTITY, 7, 256, true, false, rendertype$state);
   }

   public static RenderType getEntityCutoutNoCull(ResourceLocation locationIn) {
      return getEntityCutoutNoCull(locationIn, true);
   }

   public static RenderType getEntityCutoutNoCullZOffset(ResourceLocation locationIn, boolean outlineIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).layer(field_239235_M_).build(outlineIn);
      return makeType("entity_cutout_no_cull_z_offset", DefaultVertexFormats.ENTITY, 7, 256, true, false, rendertype$state);
   }

   public static RenderType getEntityCutoutNoCullZOffset(ResourceLocation locationIn) {
      return getEntityCutoutNoCullZOffset(locationIn, true);
   }

   public static RenderType getItemEntityTranslucentCull(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(RenderState.COLOR_DEPTH_WRITE).build(true);
      return makeType("item_entity_translucent_cull", DefaultVertexFormats.ENTITY, 7, 256, true, true, rendertype$state);
   }

   public static RenderType getEntityTranslucentCull(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(true);
      return makeType("entity_translucent_cull", DefaultVertexFormats.ENTITY, 7, 256, true, true, rendertype$state);
   }

   public static RenderType getEntityTranslucent(ResourceLocation LocationIn, boolean outlineIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(LocationIn, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(outlineIn);
      return makeType("entity_translucent", DefaultVertexFormats.ENTITY, 7, 256, true, true, rendertype$state);
   }

   public static RenderType getEntityTranslucent(ResourceLocation locationIn) {
      return getEntityTranslucent(locationIn, true);
   }

   public static RenderType getEntitySmoothCutout(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).alpha(HALF_ALPHA).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).shadeModel(SHADE_ENABLED).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).build(true);
      return makeType("entity_smooth_cutout", DefaultVertexFormats.ENTITY, 7, 256, rendertype$state);
   }

   public static RenderType getBeaconBeam(ResourceLocation locationIn, boolean colorFlagIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(colorFlagIn ? TRANSLUCENT_TRANSPARENCY : NO_TRANSPARENCY).writeMask(colorFlagIn ? COLOR_WRITE : COLOR_DEPTH_WRITE).fog(NO_FOG).build(false);
      return makeType("beacon_beam", DefaultVertexFormats.BLOCK, 7, 256, false, true, rendertype$state);
   }

   public static RenderType getEntityDecal(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).depthTest(DEPTH_EQUAL).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(false);
      return makeType("entity_decal", DefaultVertexFormats.ENTITY, 7, 256, rendertype$state);
   }

   public static RenderType getEntityNoOutline(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(COLOR_WRITE).build(false);
      return makeType("entity_no_outline", DefaultVertexFormats.ENTITY, 7, 256, false, true, rendertype$state);
   }

   public static RenderType getEntityShadow(ResourceLocation locationIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_ENABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(COLOR_WRITE).depthTest(DEPTH_LEQUAL).layer(field_239235_M_).build(false);
      return makeType("entity_shadow", DefaultVertexFormats.ENTITY, 7, 256, false, false, rendertype$state);
   }

   public static RenderType getEntityAlpha(ResourceLocation locationIn, float refIn) {
      RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).alpha(new RenderState.AlphaState(refIn)).cull(CULL_DISABLED).build(true);
      return makeType("entity_alpha", DefaultVertexFormats.ENTITY, 7, 256, rendertype$state);
   }

   public static RenderType getEyes(ResourceLocation locationIn) {
      RenderState.TextureState renderstate$texturestate = new RenderState.TextureState(locationIn, false, false);
      return makeType("eyes", DefaultVertexFormats.ENTITY, 7, 256, false, true, RenderType.State.getBuilder().texture(renderstate$texturestate).transparency(ADDITIVE_TRANSPARENCY).writeMask(COLOR_WRITE).fog(BLACK_FOG).build(false));
   }

   public static RenderType getEnergySwirl(ResourceLocation locationIn, float uIn, float vIn) {
      return makeType("energy_swirl", DefaultVertexFormats.ENTITY, 7, 256, false, true, RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).texturing(new RenderState.OffsetTexturingState(uIn, vIn)).fog(BLACK_FOG).transparency(ADDITIVE_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(false));
   }

   public static RenderType getLeash() {
      return LEASH;
   }

   public static RenderType getWaterMask() {
      return WATER_MASK;
   }

   public static RenderType getOutline(ResourceLocation locationIn) {
      return getOutline(locationIn, CULL_DISABLED);
   }

   public static RenderType getOutline(ResourceLocation locationIn, RenderState.CullState cull) {
      return makeType("outline", DefaultVertexFormats.POSITION_COLOR_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).cull(cull).depthTest(DEPTH_ALWAYS).alpha(DEFAULT_ALPHA).texturing(OUTLINE_TEXTURING).fog(NO_FOG).target(OUTLINE_TARGET).func_230173_a_(RenderType.OutlineState.IS_OUTLINE));
   }

   public static RenderType getArmorGlint() {
      return ARMOR_GLINT;
   }

   public static RenderType getArmorEntityGlint() {
      return ARMOR_ENTITY_GLINT;
   }

   public static RenderType getGlintTranslucent() {
      return GLINT_TRANSLUCENT;
   }

   public static RenderType getGlint() {
      return GLINT;
   }

   public static RenderType getGlintDirect() {
      return GLINT_DIRECT;
   }

   public static RenderType getEntityGlint() {
      return ENTITY_GLINT;
   }

   public static RenderType getEntityGlintDirect() {
      return ENTITY_GLINT_DIRECT;
   }

   public static RenderType getCrumbling(ResourceLocation locationIn) {
      RenderState.TextureState renderstate$texturestate = new RenderState.TextureState(locationIn, false, false);
      return makeType("crumbling", DefaultVertexFormats.BLOCK, 7, 256, false, true, RenderType.State.getBuilder().texture(renderstate$texturestate).alpha(DEFAULT_ALPHA).transparency(CRUMBLING_TRANSPARENCY).writeMask(COLOR_WRITE).layer(POLYGON_OFFSET_LAYERING).build(false));
   }

   public static RenderType getText(ResourceLocation locationIn) {
      return makeType("text", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, false, true, RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).alpha(DEFAULT_ALPHA).transparency(TRANSLUCENT_TRANSPARENCY).lightmap(LIGHTMAP_ENABLED).build(false));
   }

   public static RenderType getTextSeeThrough(ResourceLocation locationIn) {
      return makeType("text_see_through", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, false, true, RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).alpha(DEFAULT_ALPHA).transparency(TRANSLUCENT_TRANSPARENCY).lightmap(LIGHTMAP_ENABLED).depthTest(DEPTH_ALWAYS).writeMask(COLOR_WRITE).build(false));
   }

   public static RenderType getLightning() {
      return LIGHTNING;
   }

   private static RenderType.State getWeatherState() {
      return RenderType.State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).transparency(TRANSLUCENT_TRANSPARENCY).target(field_239238_U_).build(true);
   }

   public static RenderType getTripwire() {
      return TRIPWIRE;
   }

   public static RenderType getEndPortal(int iterationIn) {
      RenderState.TransparencyState renderstate$transparencystate;
      RenderState.TextureState renderstate$texturestate;
      if (iterationIn <= 1) {
         renderstate$transparencystate = TRANSLUCENT_TRANSPARENCY;
         renderstate$texturestate = new RenderState.TextureState(EndPortalTileEntityRenderer.END_SKY_TEXTURE, false, false);
      } else {
         renderstate$transparencystate = ADDITIVE_TRANSPARENCY;
         renderstate$texturestate = new RenderState.TextureState(EndPortalTileEntityRenderer.END_PORTAL_TEXTURE, false, false);
      }

      return makeType("end_portal", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, RenderType.State.getBuilder().transparency(renderstate$transparencystate).texture(renderstate$texturestate).texturing(new RenderState.PortalTexturingState(iterationIn)).fog(BLACK_FOG).build(false));
   }

   public static RenderType getLines() {
      return LINES;
   }

   public RenderType(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
      super(nameIn, setupTaskIn, clearTaskIn);
      this.vertexFormat = formatIn;
      this.drawMode = drawModeIn;
      this.bufferSize = bufferSizeIn;
      this.useDelegate = useDelegateIn;
      this.needsSorting = needsSortingIn;
      this.renderType = Optional.of(this);
   }

   public static RenderType.Type makeType(String nameIn, VertexFormat vertexFormatIn, int drawModeIn, int bufferSizeIn, RenderType.State renderStateIn) {
      return makeType(nameIn, vertexFormatIn, drawModeIn, bufferSizeIn, false, false, renderStateIn);
   }

   public static RenderType.Type makeType(String name, VertexFormat vertexFormatIn, int glMode, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, RenderType.State renderStateIn) {
      return RenderType.Type.getOrCreate(name, vertexFormatIn, glMode, bufferSizeIn, useDelegateIn, needsSortingIn, renderStateIn);
   }

   public void finish(BufferBuilder buffer, int cameraX, int cameraY, int cameraZ) {
      if (buffer.isDrawing()) {
         if (this.needsSorting) {
            buffer.sortVertexData((float)cameraX, (float)cameraY, (float)cameraZ);
         }

         buffer.finishDrawing();
         this.setupRenderState();
         WorldVertexBufferUploader.draw(buffer);
         this.clearRenderState();
      }
   }

   public String toString() {
      return this.name;
   }

   public static List<RenderType> getBlockRenderTypes() {
      return ImmutableList.of(getSolid(), getCutoutMipped(), getCutout(), getTranslucent(), getTripwire());
   }

   public int getBufferSize() {
      return this.bufferSize;
   }

   public VertexFormat getVertexFormat() {
      return this.vertexFormat;
   }

   public int getDrawMode() {
      return this.drawMode;
   }

   public Optional<RenderType> getOutline() {
      return Optional.empty();
   }

   public boolean isColoredOutlineBuffer() {
      return false;
   }

   public boolean isUseDelegate() {
      return this.useDelegate;
   }

   public Optional<RenderType> getRenderType() {
      return this.renderType;
   }

   @OnlyIn(Dist.CLIENT)
   static enum OutlineState {
      NONE("none"),
      IS_OUTLINE("is_outline"),
      AFFECTS_OUTLINE("affects_outline");

      private final String name;

      private OutlineState(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class State {
      private final RenderState.TextureState texture;
      private final RenderState.TransparencyState transparency;
      private final RenderState.DiffuseLightingState diffuseLighting;
      private final RenderState.ShadeModelState shadowModel;
      private final RenderState.AlphaState alpha;
      private final RenderState.DepthTestState depthTest;
      private final RenderState.CullState cull;
      private final RenderState.LightmapState lightmap;
      private final RenderState.OverlayState overlay;
      private final RenderState.FogState fog;
      private final RenderState.LayerState layer;
      private final RenderState.TargetState target;
      private final RenderState.TexturingState texturing;
      private final RenderState.WriteMaskState writeMask;
      private final RenderState.LineState line;
      private final RenderType.OutlineState field_230171_p_;
      private final ImmutableList<RenderState> renderStates;

      private State(RenderState.TextureState p_i230053_1_, RenderState.TransparencyState p_i230053_2_, RenderState.DiffuseLightingState p_i230053_3_, RenderState.ShadeModelState p_i230053_4_, RenderState.AlphaState p_i230053_5_, RenderState.DepthTestState p_i230053_6_, RenderState.CullState p_i230053_7_, RenderState.LightmapState p_i230053_8_, RenderState.OverlayState p_i230053_9_, RenderState.FogState p_i230053_10_, RenderState.LayerState p_i230053_11_, RenderState.TargetState p_i230053_12_, RenderState.TexturingState p_i230053_13_, RenderState.WriteMaskState p_i230053_14_, RenderState.LineState p_i230053_15_, RenderType.OutlineState p_i230053_16_) {
         this.texture = p_i230053_1_;
         this.transparency = p_i230053_2_;
         this.diffuseLighting = p_i230053_3_;
         this.shadowModel = p_i230053_4_;
         this.alpha = p_i230053_5_;
         this.depthTest = p_i230053_6_;
         this.cull = p_i230053_7_;
         this.lightmap = p_i230053_8_;
         this.overlay = p_i230053_9_;
         this.fog = p_i230053_10_;
         this.layer = p_i230053_11_;
         this.target = p_i230053_12_;
         this.texturing = p_i230053_13_;
         this.writeMask = p_i230053_14_;
         this.line = p_i230053_15_;
         this.field_230171_p_ = p_i230053_16_;
         this.renderStates = ImmutableList.of(this.texture, this.transparency, this.diffuseLighting, this.shadowModel, this.alpha, this.depthTest, this.cull, this.lightmap, this.overlay, this.fog, this.layer, this.target, this.texturing, this.writeMask, this.line);
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            RenderType.State rendertype$state = (RenderType.State)p_equals_1_;
            return this.field_230171_p_ == rendertype$state.field_230171_p_ && this.renderStates.equals(rendertype$state.renderStates);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(this.renderStates, this.field_230171_p_);
      }

      public String toString() {
         return "CompositeState[" + this.renderStates + ", outlineProperty=" + this.field_230171_p_ + ']';
      }

      public static RenderType.State.Builder getBuilder() {
         return new RenderType.State.Builder();
      }

      @OnlyIn(Dist.CLIENT)
      public static class Builder {
         private RenderState.TextureState texture = RenderState.NO_TEXTURE;
         private RenderState.TransparencyState transparency = RenderState.NO_TRANSPARENCY;
         private RenderState.DiffuseLightingState diffuseLighting = RenderState.DIFFUSE_LIGHTING_DISABLED;
         private RenderState.ShadeModelState shadeModel = RenderState.SHADE_DISABLED;
         private RenderState.AlphaState alpha = RenderState.ZERO_ALPHA;
         private RenderState.DepthTestState depthTest = RenderState.DEPTH_LEQUAL;
         private RenderState.CullState cull = RenderState.CULL_ENABLED;
         private RenderState.LightmapState lightmap = RenderState.LIGHTMAP_DISABLED;
         private RenderState.OverlayState overlay = RenderState.OVERLAY_DISABLED;
         private RenderState.FogState fog = RenderState.FOG;
         private RenderState.LayerState layer = RenderState.NO_LAYERING;
         private RenderState.TargetState target = RenderState.MAIN_TARGET;
         private RenderState.TexturingState texturing = RenderState.DEFAULT_TEXTURING;
         private RenderState.WriteMaskState writeMask = RenderState.COLOR_DEPTH_WRITE;
         private RenderState.LineState line = RenderState.DEFAULT_LINE;

         private Builder() {
         }

         public RenderType.State.Builder texture(RenderState.TextureState p_228724_1_) {
            this.texture = p_228724_1_;
            return this;
         }

         public RenderType.State.Builder transparency(RenderState.TransparencyState p_228726_1_) {
            this.transparency = p_228726_1_;
            return this;
         }

         public RenderType.State.Builder diffuseLighting(RenderState.DiffuseLightingState p_228716_1_) {
            this.diffuseLighting = p_228716_1_;
            return this;
         }

         public RenderType.State.Builder shadeModel(RenderState.ShadeModelState p_228723_1_) {
            this.shadeModel = p_228723_1_;
            return this;
         }

         public RenderType.State.Builder alpha(RenderState.AlphaState p_228713_1_) {
            this.alpha = p_228713_1_;
            return this;
         }

         public RenderType.State.Builder depthTest(RenderState.DepthTestState p_228715_1_) {
            this.depthTest = p_228715_1_;
            return this;
         }

         public RenderType.State.Builder cull(RenderState.CullState p_228714_1_) {
            this.cull = p_228714_1_;
            return this;
         }

         public RenderType.State.Builder lightmap(RenderState.LightmapState p_228719_1_) {
            this.lightmap = p_228719_1_;
            return this;
         }

         public RenderType.State.Builder overlay(RenderState.OverlayState p_228722_1_) {
            this.overlay = p_228722_1_;
            return this;
         }

         public RenderType.State.Builder fog(RenderState.FogState p_228717_1_) {
            this.fog = p_228717_1_;
            return this;
         }

         public RenderType.State.Builder layer(RenderState.LayerState p_228718_1_) {
            this.layer = p_228718_1_;
            return this;
         }

         public RenderType.State.Builder target(RenderState.TargetState p_228721_1_) {
            this.target = p_228721_1_;
            return this;
         }

         public RenderType.State.Builder texturing(RenderState.TexturingState p_228725_1_) {
            this.texturing = p_228725_1_;
            return this;
         }

         public RenderType.State.Builder writeMask(RenderState.WriteMaskState p_228727_1_) {
            this.writeMask = p_228727_1_;
            return this;
         }

         public RenderType.State.Builder line(RenderState.LineState p_228720_1_) {
            this.line = p_228720_1_;
            return this;
         }

         public RenderType.State build(boolean outlineIn) {
            return this.func_230173_a_(outlineIn ? RenderType.OutlineState.AFFECTS_OUTLINE : RenderType.OutlineState.NONE);
         }

         public RenderType.State func_230173_a_(RenderType.OutlineState p_230173_1_) {
            return new RenderType.State(this.texture, this.transparency, this.diffuseLighting, this.shadeModel, this.alpha, this.depthTest, this.cull, this.lightmap, this.overlay, this.fog, this.layer, this.target, this.texturing, this.writeMask, this.line, p_230173_1_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   static final class Type extends RenderType {
      private static final ObjectOpenCustomHashSet<RenderType.Type> TYPES = new ObjectOpenCustomHashSet<>(RenderType.Type.EqualityStrategy.INSTANCE);
      private final RenderType.State renderState;
      private final int hashCode;
      private final Optional<RenderType> outlineRenderType;
      private final boolean field_230170_V_;

      private Type(String p_i225993_1_, VertexFormat p_i225993_2_, int p_i225993_3_, int p_i225993_4_, boolean p_i225993_5_, boolean p_i225993_6_, RenderType.State p_i225993_7_) {
         super(p_i225993_1_, p_i225993_2_, p_i225993_3_, p_i225993_4_, p_i225993_5_, p_i225993_6_, () -> {
            p_i225993_7_.renderStates.forEach(RenderState::setupRenderState);
         }, () -> {
            p_i225993_7_.renderStates.forEach(RenderState::clearRenderState);
         });
         this.renderState = p_i225993_7_;
         this.outlineRenderType = p_i225993_7_.field_230171_p_ == RenderType.OutlineState.AFFECTS_OUTLINE ? p_i225993_7_.texture.texture().map((p_239275_1_) -> {
            return getOutline(p_239275_1_, p_i225993_7_.cull);
         }) : Optional.empty();
         this.field_230170_V_ = p_i225993_7_.field_230171_p_ == RenderType.OutlineState.IS_OUTLINE;
         this.hashCode = Objects.hash(super.hashCode(), p_i225993_7_);
      }

      private static RenderType.Type getOrCreate(String p_228676_0_, VertexFormat p_228676_1_, int p_228676_2_, int p_228676_3_, boolean p_228676_4_, boolean p_228676_5_, RenderType.State p_228676_6_) {
         return TYPES.addOrGet(new RenderType.Type(p_228676_0_, p_228676_1_, p_228676_2_, p_228676_3_, p_228676_4_, p_228676_5_, p_228676_6_));
      }

      public Optional<RenderType> getOutline() {
         return this.outlineRenderType;
      }

      public boolean isColoredOutlineBuffer() {
         return this.field_230170_V_;
      }

      public boolean equals(@Nullable Object p_equals_1_) {
         return this == p_equals_1_;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public String toString() {
         return "RenderType[" + this.renderState + ']';
      }

      @OnlyIn(Dist.CLIENT)
      static enum EqualityStrategy implements Strategy<RenderType.Type> {
         INSTANCE;

         public int hashCode(@Nullable RenderType.Type p_hashCode_1_) {
            return p_hashCode_1_ == null ? 0 : p_hashCode_1_.hashCode;
         }

         public boolean equals(@Nullable RenderType.Type p_equals_1_, @Nullable RenderType.Type p_equals_2_) {
            if (p_equals_1_ == p_equals_2_) {
               return true;
            } else {
               return p_equals_1_ != null && p_equals_2_ != null ? Objects.equals(p_equals_1_.renderState, p_equals_2_.renderState) : false;
            }
         }
      }
   }
}
