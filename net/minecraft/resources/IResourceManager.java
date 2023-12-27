package net.minecraft.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IResourceManager {
   @OnlyIn(Dist.CLIENT)
   Set<String> getResourceNamespaces();

   IResource getResource(ResourceLocation resourceLocationIn) throws IOException;

   @OnlyIn(Dist.CLIENT)
   boolean hasResource(ResourceLocation path);

   List<IResource> getAllResources(ResourceLocation resourceLocationIn) throws IOException;

   Collection<ResourceLocation> getAllResourceLocations(String pathIn, Predicate<String> filter);

   @OnlyIn(Dist.CLIENT)
   Stream<IResourcePack> getResourcePackStream();

   public static enum Instance implements IResourceManager {
      INSTANCE;

      @OnlyIn(Dist.CLIENT)
      public Set<String> getResourceNamespaces() {
         return ImmutableSet.of();
      }

      public IResource getResource(ResourceLocation resourceLocationIn) throws IOException {
         throw new FileNotFoundException(resourceLocationIn.toString());
      }

      @OnlyIn(Dist.CLIENT)
      public boolean hasResource(ResourceLocation path) {
         return false;
      }

      public List<IResource> getAllResources(ResourceLocation resourceLocationIn) {
         return ImmutableList.of();
      }

      public Collection<ResourceLocation> getAllResourceLocations(String pathIn, Predicate<String> filter) {
         return ImmutableSet.of();
      }

      @OnlyIn(Dist.CLIENT)
      public Stream<IResourcePack> getResourcePackStream() {
         return Stream.of();
      }
   }
}
