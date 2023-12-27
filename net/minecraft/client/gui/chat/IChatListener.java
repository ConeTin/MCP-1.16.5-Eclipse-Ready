package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IChatListener {
   void say(ChatType chatTypeIn, ITextComponent message, UUID sender);
}
