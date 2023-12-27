package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DownloadTerrainScreen extends Screen {
   private static final ITextComponent field_243307_a = new TranslationTextComponent("multiplayer.downloadingTerrain");

   public DownloadTerrainScreen() {
      super(NarratorChatListener.EMPTY);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderDirtBackground(0);
      drawCenteredString(matrixStack, this.font, field_243307_a, this.width / 2, this.height / 2 - 50, 16777215);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }

   public boolean isPauseScreen() {
      return false;
   }
}
