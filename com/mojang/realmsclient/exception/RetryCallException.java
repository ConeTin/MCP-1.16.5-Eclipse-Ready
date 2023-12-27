package com.mojang.realmsclient.exception;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RetryCallException extends RealmsServiceException {
   public final int field_224985_e;

   public RetryCallException(int p_i244512_1_, int p_i244512_2_) {
      super(p_i244512_2_, "Retry operation", -1, "");
      if (p_i244512_1_ >= 0 && p_i244512_1_ <= 120) {
         this.field_224985_e = p_i244512_1_;
      } else {
         this.field_224985_e = 5;
      }

   }
}
