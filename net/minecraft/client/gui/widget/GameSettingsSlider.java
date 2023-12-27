package net.minecraft.client.gui.widget;

import net.minecraft.client.GameSettings;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GameSettingsSlider extends AbstractSlider {
   protected final GameSettings settings;

   protected GameSettingsSlider(GameSettings settings, int x, int y, int width, int height, double defaultValue) {
      super(x, y, width, height, StringTextComponent.EMPTY, defaultValue);
      this.settings = settings;
   }
}
