package net.minecraft.util;

public class ActionResult<T> {
   private final ActionResultType type;
   private final T result;

   public ActionResult(ActionResultType typeIn, T resultIn) {
      this.type = typeIn;
      this.result = resultIn;
   }

   public ActionResultType getType() {
      return this.type;
   }

   public T getResult() {
      return this.result;
   }

   public static <T> ActionResult<T> resultSuccess(T type) {
      return new ActionResult<>(ActionResultType.SUCCESS, type);
   }

   public static <T> ActionResult<T> resultConsume(T type) {
      return new ActionResult<>(ActionResultType.CONSUME, type);
   }

   public static <T> ActionResult<T> resultPass(T type) {
      return new ActionResult<>(ActionResultType.PASS, type);
   }

   public static <T> ActionResult<T> resultFail(T type) {
      return new ActionResult<>(ActionResultType.FAIL, type);
   }

   public static <T> ActionResult<T> func_233538_a_(T type, boolean isRemote) {
      return isRemote ? resultSuccess(type) : resultConsume(type);
   }
}
