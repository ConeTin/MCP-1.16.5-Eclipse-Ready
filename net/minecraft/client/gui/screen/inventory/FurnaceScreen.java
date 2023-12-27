package net.minecraft.client.gui.screen.inventory;

import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FurnaceScreen extends AbstractFurnaceScreen<FurnaceContainer> {
   private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");

   public FurnaceScreen(FurnaceContainer container, PlayerInventory playerInventory, ITextComponent title) {
      super(container, new FurnaceRecipeGui(), playerInventory, title, FURNACE_GUI_TEXTURES);
   }
}
