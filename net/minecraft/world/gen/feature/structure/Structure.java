package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Structure<C extends IFeatureConfig> {
   public static final BiMap<String, Structure<?>> NAME_STRUCTURE_BIMAP = HashBiMap.create();
   private static final Map<Structure<?>, GenerationStage.Decoration> STRUCTURE_DECORATION_STAGE_MAP = Maps.newHashMap();
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Structure<VillageConfig> PILLAGER_OUTPOST = register("Pillager_Outpost", new PillagerOutpostStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<MineshaftConfig> MINESHAFT = register("Mineshaft", new MineshaftStructure(MineshaftConfig.field_236541_a_), GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
   public static final Structure<NoFeatureConfig> WOODLAND_MANSION = register("Mansion", new WoodlandMansionStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<NoFeatureConfig> JUNGLE_PYRAMID = register("Jungle_Pyramid", new JunglePyramidStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<NoFeatureConfig> DESERT_PYRAMID = register("Desert_Pyramid", new DesertPyramidStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<NoFeatureConfig> IGLOO = register("Igloo", new IglooStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<RuinedPortalFeature> RUINED_PORTAL = register("Ruined_Portal", new RuinedPortalStructure(RuinedPortalFeature.field_236627_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<ShipwreckConfig> SHIPWRECK = register("Shipwreck", new ShipwreckStructure(ShipwreckConfig.field_236634_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final SwampHutStructure SWAMP_HUT = register("Swamp_Hut", new SwampHutStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<NoFeatureConfig> STRONGHOLD = register("Stronghold", new StrongholdStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.STRONGHOLDS);
   public static final Structure<NoFeatureConfig> MONUMENT = register("Monument", new OceanMonumentStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<OceanRuinConfig> OCEAN_RUIN = register("Ocean_Ruin", new OceanRuinStructure(OceanRuinConfig.field_236561_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<NoFeatureConfig> FORTRESS = register("Fortress", new FortressStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.UNDERGROUND_DECORATION);
   public static final Structure<NoFeatureConfig> END_CITY = register("EndCity", new EndCityStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<ProbabilityConfig> BURIED_TREASURE = register("Buried_Treasure", new BuriedTreasureStructure(ProbabilityConfig.CODEC), GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
   public static final Structure<VillageConfig> VILLAGE = register("Village", new VillageStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final Structure<NoFeatureConfig> NETHER_FOSSIL = register("Nether_Fossil", new NetherFossilStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.UNDERGROUND_DECORATION);
   public static final Structure<VillageConfig> BASTION_REMNANT = register("Bastion_Remnant", new BastionRemantsStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
   public static final List<Structure<?>> field_236384_t_ = ImmutableList.of(PILLAGER_OUTPOST, VILLAGE, NETHER_FOSSIL);
   private static final ResourceLocation JIGSAW = new ResourceLocation("jigsaw");
   private static final Map<ResourceLocation, ResourceLocation> OLD_TO_NEW_NAMING_MAP = ImmutableMap.<ResourceLocation, ResourceLocation>builder().put(new ResourceLocation("nvi"), JIGSAW).put(new ResourceLocation("pcp"), JIGSAW).put(new ResourceLocation("bastionremnant"), JIGSAW).put(new ResourceLocation("runtime"), JIGSAW).build();
   private final Codec<StructureFeature<C, Structure<C>>> field_236386_w_;

   private static <F extends Structure<?>> F register(String name, F structure, GenerationStage.Decoration decorationStage) {
      NAME_STRUCTURE_BIMAP.put(name.toLowerCase(Locale.ROOT), structure);
      STRUCTURE_DECORATION_STAGE_MAP.put(structure, decorationStage);
      return Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structure);
   }

   public Structure(Codec<C> codec) {
      this.field_236386_w_ = codec.fieldOf("config").xmap((p_236395_1_) -> {
         return new StructureFeature<>(this, p_236395_1_);
      }, (p_236390_0_) -> {
         return p_236390_0_.field_236269_c_;
      }).codec();
   }

   public GenerationStage.Decoration getDecorationStage() {
      return STRUCTURE_DECORATION_STAGE_MAP.get(this);
   }

   public static void init() {
   }

   @Nullable
   public static StructureStart<?> deserializeStructureStart(TemplateManager manager, CompoundNBT nbt, long seed) {
      String s = nbt.getString("id");
      if ("INVALID".equals(s)) {
         return StructureStart.DUMMY;
      } else {
         Structure<?> structure = Registry.STRUCTURE_FEATURE.getOrDefault(new ResourceLocation(s.toLowerCase(Locale.ROOT)));
         if (structure == null) {
            LOGGER.error("Unknown feature id: {}", (Object)s);
            return null;
         } else {
            int i = nbt.getInt("ChunkX");
            int j = nbt.getInt("ChunkZ");
            int k = nbt.getInt("references");
            MutableBoundingBox mutableboundingbox = nbt.contains("BB") ? new MutableBoundingBox(nbt.getIntArray("BB")) : MutableBoundingBox.getNewBoundingBox();
            ListNBT listnbt = nbt.getList("Children", 10);

            try {
               StructureStart<?> structurestart = structure.createStructureStart(i, j, mutableboundingbox, k, seed);

               for(int l = 0; l < listnbt.size(); ++l) {
                  CompoundNBT compoundnbt = listnbt.getCompound(l);
                  String s1 = compoundnbt.getString("id").toLowerCase(Locale.ROOT);
                  ResourceLocation resourcelocation = new ResourceLocation(s1);
                  ResourceLocation resourcelocation1 = OLD_TO_NEW_NAMING_MAP.getOrDefault(resourcelocation, resourcelocation);
                  IStructurePieceType istructurepiecetype = Registry.STRUCTURE_PIECE.getOrDefault(resourcelocation1);
                  if (istructurepiecetype == null) {
                     LOGGER.error("Unknown structure piece id: {}", (Object)resourcelocation1);
                  } else {
                     try {
                        StructurePiece structurepiece = istructurepiecetype.load(manager, compoundnbt);
                        structurestart.getComponents().add(structurepiece);
                     } catch (Exception exception) {
                        LOGGER.error("Exception loading structure piece with id {}", resourcelocation1, exception);
                     }
                  }
               }

               return structurestart;
            } catch (Exception exception1) {
               LOGGER.error("Failed Start with id {}", s, exception1);
               return null;
            }
         }
      }
   }

   public Codec<StructureFeature<C, Structure<C>>> getFeatureCodec() {
      return this.field_236386_w_;
   }

   public StructureFeature<C, ? extends Structure<C>> withConfiguration(C p_236391_1_) {
      return new StructureFeature<>(this, p_236391_1_);
   }

   @Nullable
   public BlockPos func_236388_a_(IWorldReader world, StructureManager manager, BlockPos p_236388_3_, int radius, boolean skipExistingChunks, long seed, StructureSeparationSettings separationSettings) {
      int i = separationSettings.func_236668_a_();
      int j = p_236388_3_.getX() >> 4;
      int k = p_236388_3_.getZ() >> 4;
      int l = 0;

      for(SharedSeedRandom sharedseedrandom = new SharedSeedRandom(); l <= radius; ++l) {
         for(int i1 = -l; i1 <= l; ++i1) {
            boolean flag = i1 == -l || i1 == l;

            for(int j1 = -l; j1 <= l; ++j1) {
               boolean flag1 = j1 == -l || j1 == l;
               if (flag || flag1) {
                  int k1 = j + i * i1;
                  int l1 = k + i * j1;
                  ChunkPos chunkpos = this.getChunkPosForStructure(separationSettings, seed, sharedseedrandom, k1, l1);
                  IChunk ichunk = world.getChunk(chunkpos.x, chunkpos.z, ChunkStatus.STRUCTURE_STARTS);
                  StructureStart<?> structurestart = manager.getStructureStart(SectionPos.from(ichunk.getPos(), 0), this, ichunk);
                  if (structurestart != null && structurestart.isValid()) {
                     if (skipExistingChunks && structurestart.isRefCountBelowMax()) {
                        structurestart.incrementRefCount();
                        return structurestart.getPos();
                     }

                     if (!skipExistingChunks) {
                        return structurestart.getPos();
                     }
                  }

                  if (l == 0) {
                     break;
                  }
               }
            }

            if (l == 0) {
               break;
            }
         }
      }

      return null;
   }

   protected boolean func_230365_b_() {
      return true;
   }

   public final ChunkPos getChunkPosForStructure(StructureSeparationSettings separationSettings, long seed, SharedSeedRandom rand, int x, int z) {
      int i = separationSettings.func_236668_a_();
      int j = separationSettings.func_236671_b_();
      int k = Math.floorDiv(x, i);
      int l = Math.floorDiv(z, i);
      rand.setLargeFeatureSeedWithSalt(seed, k, l, separationSettings.func_236673_c_());
      int i1;
      int j1;
      if (this.func_230365_b_()) {
         i1 = rand.nextInt(i - j);
         j1 = rand.nextInt(i - j);
      } else {
         i1 = (rand.nextInt(i - j) + rand.nextInt(i - j)) / 2;
         j1 = (rand.nextInt(i - j) + rand.nextInt(i - j)) / 2;
      }

      return new ChunkPos(k * i + i1, l * i + j1);
   }

   protected boolean func_230363_a_(ChunkGenerator p_230363_1_, BiomeProvider p_230363_2_, long p_230363_3_, SharedSeedRandom p_230363_5_, int p_230363_6_, int p_230363_7_, Biome p_230363_8_, ChunkPos p_230363_9_, C p_230363_10_) {
      return true;
   }

   private StructureStart<C> createStructureStart(int p_236387_1_, int p_236387_2_, MutableBoundingBox p_236387_3_, int refCount, long seed) {
      return this.getStartFactory().create(this, p_236387_1_, p_236387_2_, p_236387_3_, refCount, seed);
   }

   public StructureStart<?> func_242785_a(DynamicRegistries dynamicRegistries, ChunkGenerator generator, BiomeProvider provider, TemplateManager templateManager, long seed, ChunkPos pos, Biome biome, int refCount, SharedSeedRandom rand, StructureSeparationSettings settings, C config) {
      ChunkPos chunkpos = this.getChunkPosForStructure(settings, seed, rand, pos.x, pos.z);
      if (pos.x == chunkpos.x && pos.z == chunkpos.z && this.func_230363_a_(generator, provider, seed, rand, pos.x, pos.z, biome, chunkpos, config)) {
         StructureStart<C> structurestart = this.createStructureStart(pos.x, pos.z, MutableBoundingBox.getNewBoundingBox(), refCount, seed);
         structurestart.func_230364_a_(dynamicRegistries, generator, templateManager, pos.x, pos.z, biome, config);
         if (structurestart.isValid()) {
            return structurestart;
         }
      }

      return StructureStart.DUMMY;
   }

   public abstract Structure.IStartFactory<C> getStartFactory();

   public String getStructureName() {
      return NAME_STRUCTURE_BIMAP.inverse().get(this);
   }

   public List<MobSpawnInfo.Spawners> getSpawnList() {
      return ImmutableList.of();
   }

   public List<MobSpawnInfo.Spawners> getCreatureSpawnList() {
      return ImmutableList.of();
   }

   public interface IStartFactory<C extends IFeatureConfig> {
      StructureStart<C> create(Structure<C> p_create_1_, int p_create_2_, int p_create_3_, MutableBoundingBox p_create_4_, int p_create_5_, long p_create_6_);
   }
}
