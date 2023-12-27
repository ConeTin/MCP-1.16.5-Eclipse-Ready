package net.minecraft.resources;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public interface IPackNameDecorator {
   IPackNameDecorator PLAIN = func_232629_a_();
   IPackNameDecorator BUILTIN = create("pack.source.builtin");
   IPackNameDecorator WORLD = create("pack.source.world");
   IPackNameDecorator SERVER = create("pack.source.server");

   ITextComponent decorate(ITextComponent p_decorate_1_);

   static IPackNameDecorator func_232629_a_() {
      return (p_232631_0_) -> {
         return p_232631_0_;
      };
   }

   static IPackNameDecorator create(String source) {
      ITextComponent itextcomponent = new TranslationTextComponent(source);
      return (p_232632_1_) -> {
         return (new TranslationTextComponent("pack.nameAndSource", p_232632_1_, itextcomponent)).mergeStyle(TextFormatting.GRAY);
      };
   }
}
