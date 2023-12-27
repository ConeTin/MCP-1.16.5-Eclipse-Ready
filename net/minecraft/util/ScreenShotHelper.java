package net.minecraft.util;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ScreenShotHelper {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

   public static void saveScreenshot(File gameDirectory, int width, int height, Framebuffer buffer, Consumer<ITextComponent> messageConsumer) {
      saveScreenshot(gameDirectory, (String)null, width, height, buffer, messageConsumer);
   }

   public static void saveScreenshot(File gameDirectory, @Nullable String screenshotName, int width, int height, Framebuffer buffer, Consumer<ITextComponent> messageConsumer) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            saveScreenshotRaw(gameDirectory, screenshotName, width, height, buffer, messageConsumer);
         });
      } else {
         saveScreenshotRaw(gameDirectory, screenshotName, width, height, buffer, messageConsumer);
      }

   }

   private static void saveScreenshotRaw(File gameDirectory, @Nullable String screenshotName, int width, int height, Framebuffer buffer, Consumer<ITextComponent> messageConsumer) {
      NativeImage nativeimage = createScreenshot(width, height, buffer);
      File file1 = new File(gameDirectory, "screenshots");
      file1.mkdir();
      File file2;
      if (screenshotName == null) {
         file2 = getTimestampedPNGFileForDirectory(file1);
      } else {
         file2 = new File(file1, screenshotName);
      }

      Util.getRenderingService().execute(() -> {
         try {
            nativeimage.write(file2);
            ITextComponent itextcomponent = (new StringTextComponent(file2.getName())).mergeStyle(TextFormatting.UNDERLINE).modifyStyle((p_238335_1_) -> {
               return p_238335_1_.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
            });
            messageConsumer.accept(new TranslationTextComponent("screenshot.success", itextcomponent));
         } catch (Exception exception) {
            LOGGER.warn("Couldn't save screenshot", (Throwable)exception);
            messageConsumer.accept(new TranslationTextComponent("screenshot.failure", exception.getMessage()));
         } finally {
            nativeimage.close();
         }

      });
   }

   public static NativeImage createScreenshot(int width, int height, Framebuffer framebufferIn) {
      width = framebufferIn.framebufferTextureWidth;
      height = framebufferIn.framebufferTextureHeight;
      NativeImage nativeimage = new NativeImage(width, height, false);
      RenderSystem.bindTexture(framebufferIn.func_242996_f());
      nativeimage.downloadFromTexture(0, true);
      nativeimage.flip();
      return nativeimage;
   }

   private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
      String s = DATE_FORMAT.format(new Date());
      int i = 1;

      while(true) {
         File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");
         if (!file1.exists()) {
            return file1;
         }

         ++i;
      }
   }
}
