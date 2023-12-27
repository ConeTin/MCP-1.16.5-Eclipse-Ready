package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NormalChatListener implements IChatListener {
   private final Minecraft mc;

   public NormalChatListener(Minecraft minecraft) {
      this.mc = minecraft;
   }

   public void say(ChatType chatTypeIn, ITextComponent message, UUID sender) {
      if (chatTypeIn != ChatType.CHAT) {
         this.mc.ingameGUI.getChatGUI().printChatMessage(message);
      } else {
         this.mc.ingameGUI.getChatGUI().func_238495_b_(message);
      }

   }
}
