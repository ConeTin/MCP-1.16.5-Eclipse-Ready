package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TagRegistryManager {
   private static final Map<ResourceLocation, TagRegistry<?>> idToRegistryMap = Maps.newHashMap();

   public static <T> TagRegistry<T> create(ResourceLocation id, Function<ITagCollectionSupplier, ITagCollection<T>> supplierToCollectionFunction) {
      TagRegistry<T> tagregistry = new TagRegistry<>(supplierToCollectionFunction);
      TagRegistry<?> tagregistry1 = idToRegistryMap.putIfAbsent(id, tagregistry);
      if (tagregistry1 != null) {
         throw new IllegalStateException("Duplicate entry for static tag collection: " + id);
      } else {
         return tagregistry;
      }
   }

   public static void fetchTags(ITagCollectionSupplier supplier) {
      idToRegistryMap.values().forEach((p_242194_1_) -> {
         p_242194_1_.fetchTags(supplier);
      });
   }

   @OnlyIn(Dist.CLIENT)
   public static void fetchTags() {
      idToRegistryMap.values().forEach(TagRegistry::fetchTags);
   }

   public static Multimap<ResourceLocation, ResourceLocation> validateTags(ITagCollectionSupplier supplier) {
      Multimap<ResourceLocation, ResourceLocation> multimap = HashMultimap.create();
      idToRegistryMap.forEach((p_242195_2_, p_242195_3_) -> {
         multimap.putAll(p_242195_2_, p_242195_3_.getTagIdsFromSupplier(supplier));
      });
      return multimap;
   }

   public static void checkHelperRegistrations() {
      TagRegistry[] atagregistry = new TagRegistry[]{BlockTags.collection, ItemTags.collection, FluidTags.collection, EntityTypeTags.tagCollection};
      boolean flag = Stream.of(atagregistry).anyMatch((p_242192_0_) -> {
         return !idToRegistryMap.containsValue(p_242192_0_);
      });
      if (flag) {
         throw new IllegalStateException("Missing helper registrations");
      }
   }
}
