package net.minecraft.tags;

import java.util.stream.Collectors;

public class TagCollectionManager {
   private static volatile ITagCollectionSupplier manager = ITagCollectionSupplier.getTagCollectionSupplier(ITagCollection.getTagCollectionFromMap(BlockTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, (p_242183_0_) -> {
      return p_242183_0_;
   }))), ITagCollection.getTagCollectionFromMap(ItemTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, (p_242182_0_) -> {
      return p_242182_0_;
   }))), ITagCollection.getTagCollectionFromMap(FluidTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, (p_242181_0_) -> {
      return p_242181_0_;
   }))), ITagCollection.getTagCollectionFromMap(EntityTypeTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, (p_242179_0_) -> {
      return p_242179_0_;
   }))));

   public static ITagCollectionSupplier getManager() {
      return manager;
   }

   public static void setManager(ITagCollectionSupplier managerIn) {
      manager = managerIn;
   }
}
