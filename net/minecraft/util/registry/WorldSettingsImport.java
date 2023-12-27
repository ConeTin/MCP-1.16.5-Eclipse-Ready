package net.minecraft.util.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DelegatingDynamicOps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSettingsImport<T> extends DelegatingDynamicOps<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final WorldSettingsImport.IResourceAccess resourceAccess;
   private final DynamicRegistries.Impl dynamicRegistries;
   private final Map<RegistryKey<? extends Registry<?>>, WorldSettingsImport.ResultMap<?>> registryToResultMap;
   private final WorldSettingsImport<JsonElement> jsonOps;

   public static <T> WorldSettingsImport<T> create(DynamicOps<T> ops, IResourceManager resourceManager, DynamicRegistries.Impl dynamicRegistries) {
      return create(ops, WorldSettingsImport.IResourceAccess.create(resourceManager), dynamicRegistries);
   }

   public static <T> WorldSettingsImport<T> create(DynamicOps<T> ops, WorldSettingsImport.IResourceAccess resourceAccess, DynamicRegistries.Impl dynamicRegistries) {
      WorldSettingsImport<T> worldsettingsimport = new WorldSettingsImport<>(ops, resourceAccess, dynamicRegistries, Maps.newIdentityHashMap());
      DynamicRegistries.loadRegistryData(dynamicRegistries, worldsettingsimport);
      return worldsettingsimport;
   }

   private WorldSettingsImport(DynamicOps<T> ops, WorldSettingsImport.IResourceAccess resourceAccess, DynamicRegistries.Impl dynamicRegistries, IdentityHashMap<RegistryKey<? extends Registry<?>>, WorldSettingsImport.ResultMap<?>> registryToResultMap) {
      super(ops);
      this.resourceAccess = resourceAccess;
      this.dynamicRegistries = dynamicRegistries;
      this.registryToResultMap = registryToResultMap;
      this.jsonOps = ops == JsonOps.INSTANCE ? (WorldSettingsImport<JsonElement>)this : new WorldSettingsImport<>(JsonOps.INSTANCE, resourceAccess, dynamicRegistries, registryToResultMap);
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> decode(T input, RegistryKey<? extends Registry<E>> registryKey, Codec<E> mapCodec, boolean allowInlineDefinitions) {
      Optional<MutableRegistry<E>> optional = this.dynamicRegistries.func_230521_a_(registryKey);
      if (!optional.isPresent()) {
         return DataResult.error("Unknown registry: " + registryKey);
      } else {
         MutableRegistry<E> mutableregistry = optional.get();
         DataResult<Pair<ResourceLocation, T>> dataresult = ResourceLocation.CODEC.decode(this.ops, input);
         if (!dataresult.result().isPresent()) {
            return !allowInlineDefinitions ? DataResult.error("Inline definitions not allowed here") : mapCodec.decode(this, input).map((p_240874_0_) -> {
               return p_240874_0_.mapFirst((p_240891_0_) -> {
                  return () -> {
                     return p_240891_0_;
                  };
               });
            });
         } else {
            Pair<ResourceLocation, T> pair = dataresult.result().get();
            ResourceLocation resourcelocation = pair.getFirst();
            return this.createRegistry(registryKey, mutableregistry, mapCodec, resourcelocation).map((p_240875_1_) -> {
               return Pair.of(p_240875_1_, pair.getSecond());
            });
         }
      }
   }

   public <E> DataResult<SimpleRegistry<E>> decode(SimpleRegistry<E> simpleRegistry, RegistryKey<? extends Registry<E>> registryKey, Codec<E> mapCodec) {
      Collection<ResourceLocation> collection = this.resourceAccess.getRegistryObjects(registryKey);
      DataResult<SimpleRegistry<E>> dataresult = DataResult.success(simpleRegistry, Lifecycle.stable());
      String s = registryKey.getLocation().getPath() + "/";

      for(ResourceLocation resourcelocation : collection) {
         String s1 = resourcelocation.getPath();
         if (!s1.endsWith(".json")) {
            LOGGER.warn("Skipping resource {} since it is not a json file", (Object)resourcelocation);
         } else if (!s1.startsWith(s)) {
            LOGGER.warn("Skipping resource {} since it does not have a registry name prefix", (Object)resourcelocation);
         } else {
            String s2 = s1.substring(s.length(), s1.length() - ".json".length());
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s2);
            dataresult = dataresult.flatMap((p_240885_4_) -> {
               return this.createRegistry(registryKey, p_240885_4_, mapCodec, resourcelocation1).map((p_240877_1_) -> {
                  return p_240885_4_;
               });
            });
         }
      }

      return dataresult.setPartial(simpleRegistry);
   }

   private <E> DataResult<Supplier<E>> createRegistry(RegistryKey<? extends Registry<E>> registryKey, MutableRegistry<E> mutableRegistry, Codec<E> mapCodec, ResourceLocation id) {
      RegistryKey<E> registrykey = RegistryKey.getOrCreateKey(registryKey, id);
      WorldSettingsImport.ResultMap<E> resultmap = this.getResultMap(registryKey);
      DataResult<Supplier<E>> dataresult = resultmap.resultMap.get(registrykey);
      if (dataresult != null) {
         return dataresult;
      } else {
         Supplier<E> supplier = Suppliers.memoize(() -> {
            E e = mutableRegistry.getValueForKey(registrykey);
            if (e == null) {
               throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + registrykey);
            } else {
               return e;
            }
         });
         resultmap.resultMap.put(registrykey, DataResult.success(supplier));
         DataResult<Pair<E, OptionalInt>> dataresult1 = this.resourceAccess.decode(this.jsonOps, registryKey, registrykey, mapCodec);
         Optional<Pair<E, OptionalInt>> optional = dataresult1.result();
         if (optional.isPresent()) {
            Pair<E, OptionalInt> pair = optional.get();
            mutableRegistry.validateAndRegister(pair.getSecond(), registrykey, pair.getFirst(), dataresult1.lifecycle());
         }

         DataResult<Supplier<E>> dataresult2;
         if (!optional.isPresent() && mutableRegistry.getValueForKey(registrykey) != null) {
            dataresult2 = DataResult.success(() -> {
               return mutableRegistry.getValueForKey(registrykey);
            }, Lifecycle.stable());
         } else {
            dataresult2 = dataresult1.map((p_244339_2_) -> {
               return () -> {
                  return mutableRegistry.getValueForKey(registrykey);
               };
            });
         }

         resultmap.resultMap.put(registrykey, dataresult2);
         return dataresult2;
      }
   }

   private <E> WorldSettingsImport.ResultMap<E> getResultMap(RegistryKey<? extends Registry<E>> key) {
      return (WorldSettingsImport.ResultMap<E>)this.registryToResultMap.computeIfAbsent(key, (p_244344_0_) -> {
         return new WorldSettingsImport.ResultMap();
      });
   }

   protected <E> DataResult<Registry<E>> getRegistryByKey(RegistryKey<? extends Registry<E>> registryKey) {
      return (DataResult)this.dynamicRegistries.func_230521_a_(registryKey).map((p_244337_0_) -> {
         return DataResult.success(p_244337_0_, p_244337_0_.getLifecycle());
      }).orElseGet(() -> {
         return DataResult.error("Unknown registry: " + registryKey);
      });
   }

   public interface IResourceAccess {
      Collection<ResourceLocation> getRegistryObjects(RegistryKey<? extends Registry<?>> registryKey);

      <E> DataResult<Pair<E, OptionalInt>> decode(DynamicOps<JsonElement> jsonOps, RegistryKey<? extends Registry<E>> registryKey, RegistryKey<E> objectKey, Decoder<E> decoder);

      static WorldSettingsImport.IResourceAccess create(final IResourceManager manager) {
         return new WorldSettingsImport.IResourceAccess() {
            public Collection<ResourceLocation> getRegistryObjects(RegistryKey<? extends Registry<?>> registryKey) {
               return manager.getAllResourceLocations(registryKey.getLocation().getPath(), (p_244348_0_) -> {
                  return p_244348_0_.endsWith(".json");
               });
            }

            public <E> DataResult<Pair<E, OptionalInt>> decode(DynamicOps<JsonElement> jsonOps, RegistryKey<? extends Registry<E>> registryKey, RegistryKey<E> objectKey, Decoder<E> decoder) {
               ResourceLocation resourcelocation = objectKey.getLocation();
               ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), registryKey.getLocation().getPath() + "/" + resourcelocation.getPath() + ".json");

               try (
                  IResource iresource = manager.getResource(resourcelocation1);
                  Reader reader = new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8);
               ) {
                  JsonParser jsonparser = new JsonParser();
                  JsonElement jsonelement = jsonparser.parse(reader);
                  return decoder.parse(jsonOps, jsonelement).map((p_244347_0_) -> {
                     return Pair.of(p_244347_0_, OptionalInt.empty());
                  });
               } catch (JsonIOException | JsonSyntaxException | IOException ioexception) {
                  return DataResult.error("Failed to parse " + resourcelocation1 + " file: " + ioexception.getMessage());
               }
            }

            public String toString() {
               return "ResourceAccess[" + manager + "]";
            }
         };
      }

      public static final class RegistryAccess implements WorldSettingsImport.IResourceAccess {
         private final Map<RegistryKey<?>, JsonElement> keyToElementMap = Maps.newIdentityHashMap();
         private final Object2IntMap<RegistryKey<?>> keyToIDMap = new Object2IntOpenCustomHashMap<>(Util.identityHashStrategy());
         private final Map<RegistryKey<?>, Lifecycle> keyToLifecycleMap = Maps.newIdentityHashMap();

         public <E> void encode(DynamicRegistries.Impl dynamicRegistries, RegistryKey<E> key, Encoder<E> encoder, int id, E instance, Lifecycle lifecycle) {
            DataResult<JsonElement> dataresult = encoder.encodeStart(WorldGenSettingsExport.create(JsonOps.INSTANCE, dynamicRegistries), instance);
            Optional<PartialResult<JsonElement>> optional = dataresult.error();
            if (optional.isPresent()) {
               WorldSettingsImport.LOGGER.error("Error adding element: {}", (Object)optional.get().message());
            } else {
               this.keyToElementMap.put(key, dataresult.result().get());
               this.keyToIDMap.put(key, id);
               this.keyToLifecycleMap.put(key, lifecycle);
            }
         }

         public Collection<ResourceLocation> getRegistryObjects(RegistryKey<? extends Registry<?>> registryKey) {
            return this.keyToElementMap.keySet().stream().filter((p_244355_1_) -> {
               return p_244355_1_.isParent(registryKey);
            }).map((p_244354_1_) -> {
               return new ResourceLocation(p_244354_1_.getLocation().getNamespace(), registryKey.getLocation().getPath() + "/" + p_244354_1_.getLocation().getPath() + ".json");
            }).collect(Collectors.toList());
         }

         public <E> DataResult<Pair<E, OptionalInt>> decode(DynamicOps<JsonElement> jsonOps, RegistryKey<? extends Registry<E>> registryKey, RegistryKey<E> objectKey, Decoder<E> decoder) {
            JsonElement jsonelement = this.keyToElementMap.get(objectKey);
            return jsonelement == null ? DataResult.error("Unknown element: " + objectKey) : decoder.parse(jsonOps, jsonelement).setLifecycle(this.keyToLifecycleMap.get(objectKey)).map((p_244353_2_) -> {
               return Pair.of(p_244353_2_, OptionalInt.of(this.keyToIDMap.getInt(objectKey)));
            });
         }
      }
   }

   static final class ResultMap<E> {
      private final Map<RegistryKey<E>, DataResult<Supplier<E>>> resultMap = Maps.newIdentityHashMap();

      private ResultMap() {
      }
   }
}
