package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;

public interface IStructureProcessorType<P extends StructureProcessor> {
   IStructureProcessorType<BlockIgnoreStructureProcessor> BLOCK_IGNORE = register("block_ignore", BlockIgnoreStructureProcessor.field_237073_a_);
   IStructureProcessorType<IntegrityProcessor> BLOCK_ROT = register("block_rot", IntegrityProcessor.field_237077_a_);
   IStructureProcessorType<GravityStructureProcessor> GRAVITY = register("gravity", GravityStructureProcessor.field_237081_a_);
   IStructureProcessorType<JigsawReplacementStructureProcessor> JIGSAW_REPLACEMENT = register("jigsaw_replacement", JigsawReplacementStructureProcessor.field_237085_a_);
   IStructureProcessorType<RuleStructureProcessor> RULE = register("rule", RuleStructureProcessor.field_237125_a_);
   IStructureProcessorType<NopProcessor> NOP = register("nop", NopProcessor.field_237097_a_);
   IStructureProcessorType<BlockMosinessProcessor> BLOCK_AGE = register("block_age", BlockMosinessProcessor.field_237062_a_);
   IStructureProcessorType<BlackStoneReplacementProcessor> BLACKSTONE_REPLACE = register("blackstone_replace", BlackStoneReplacementProcessor.field_237057_a_);
   IStructureProcessorType<LavaSubmergingProcessor> LAVA_SUBMERGED_BLOCK = register("lava_submerged_block", LavaSubmergingProcessor.field_241531_a_);
   Codec<StructureProcessor> PROCESSOR_TYPE = Registry.STRUCTURE_PROCESSOR.dispatch("processor_type", StructureProcessor::getType, IStructureProcessorType::codec);
   Codec<StructureProcessorList> field_242920_k = PROCESSOR_TYPE.listOf().xmap(StructureProcessorList::new, StructureProcessorList::func_242919_a);
   Codec<StructureProcessorList> field_242921_l = Codec.either(field_242920_k.fieldOf("processors").codec(), field_242920_k).xmap((p_242923_0_) -> {
      return p_242923_0_.map((p_242926_0_) -> {
         return p_242926_0_;
      }, (p_242925_0_) -> {
         return p_242925_0_;
      });
   }, Either::left);
   Codec<Supplier<StructureProcessorList>> field_242922_m = RegistryKeyCodec.create(Registry.STRUCTURE_PROCESSOR_LIST_KEY, field_242921_l);

   Codec<P> codec();

   static <P extends StructureProcessor> IStructureProcessorType<P> register(String name, Codec<P> codec) {
      return Registry.register(Registry.STRUCTURE_PROCESSOR, name, () -> {
         return codec;
      });
   }
}
