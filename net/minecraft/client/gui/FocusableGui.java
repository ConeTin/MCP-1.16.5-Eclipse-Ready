package net.minecraft.client.gui;

import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class FocusableGui extends AbstractGui implements INestedGuiEventHandler {
   @Nullable
   private IGuiEventListener listener;
   private boolean isDragging;

   public final boolean isDragging() {
      return this.isDragging;
   }

   public final void setDragging(boolean dragging) {
      this.isDragging = dragging;
   }

   @Nullable
   public IGuiEventListener getListener() {
      return this.listener;
   }

   public void setListener(@Nullable IGuiEventListener listener) {
      this.listener = listener;
   }
}
