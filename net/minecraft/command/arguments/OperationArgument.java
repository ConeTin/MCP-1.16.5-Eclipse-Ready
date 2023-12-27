package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public class OperationArgument implements ArgumentType<OperationArgument.IOperation> {
   private static final Collection<String> EXAMPLES = Arrays.asList("=", ">", "<");
   private static final SimpleCommandExceptionType OPERATION_INVALID = new SimpleCommandExceptionType(new TranslationTextComponent("arguments.operation.invalid"));
   private static final SimpleCommandExceptionType OPERATION_DIVIDE_BY_ZERO = new SimpleCommandExceptionType(new TranslationTextComponent("arguments.operation.div0"));

   public static OperationArgument operation() {
      return new OperationArgument();
   }

   public static OperationArgument.IOperation getOperation(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
      return context.getArgument(name, OperationArgument.IOperation.class);
   }

   public OperationArgument.IOperation parse(StringReader p_parse_1_) throws CommandSyntaxException {
      if (!p_parse_1_.canRead()) {
         throw OPERATION_INVALID.create();
      } else {
         int i = p_parse_1_.getCursor();

         while(p_parse_1_.canRead() && p_parse_1_.peek() != ' ') {
            p_parse_1_.skip();
         }

         return parseOperation(p_parse_1_.getString().substring(i, p_parse_1_.getCursor()));
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_listSuggestions_1_, SuggestionsBuilder p_listSuggestions_2_) {
      return ISuggestionProvider.suggest(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, p_listSuggestions_2_);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   private static OperationArgument.IOperation parseOperation(String name) throws CommandSyntaxException {
      return (name.equals("><") ? (p_197175_0_, p_197175_1_) -> {
         int i = p_197175_0_.getScorePoints();
         p_197175_0_.setScorePoints(p_197175_1_.getScorePoints());
         p_197175_1_.setScorePoints(i);
      } : parseOperation0(name));
   }

   private static OperationArgument.IIntOperation parseOperation0(String name) throws CommandSyntaxException {
      switch(name) {
      case "=":
         return (p_197174_0_, p_197174_1_) -> {
            return p_197174_1_;
         };
      case "+=":
         return (p_197176_0_, p_197176_1_) -> {
            return p_197176_0_ + p_197176_1_;
         };
      case "-=":
         return (p_197183_0_, p_197183_1_) -> {
            return p_197183_0_ - p_197183_1_;
         };
      case "*=":
         return (p_197173_0_, p_197173_1_) -> {
            return p_197173_0_ * p_197173_1_;
         };
      case "/=":
         return (p_197178_0_, p_197178_1_) -> {
            if (p_197178_1_ == 0) {
               throw OPERATION_DIVIDE_BY_ZERO.create();
            } else {
               return MathHelper.intFloorDiv(p_197178_0_, p_197178_1_);
            }
         };
      case "%=":
         return (p_197181_0_, p_197181_1_) -> {
            if (p_197181_1_ == 0) {
               throw OPERATION_DIVIDE_BY_ZERO.create();
            } else {
               return MathHelper.normalizeAngle(p_197181_0_, p_197181_1_);
            }
         };
      case "<":
         return Math::min;
      case ">":
         return Math::max;
      default:
         throw OPERATION_INVALID.create();
      }
   }

   @FunctionalInterface
   interface IIntOperation extends OperationArgument.IOperation {
      int apply(int p_apply_1_, int p_apply_2_) throws CommandSyntaxException;

      default void apply(Score p_apply_1_, Score p_apply_2_) throws CommandSyntaxException {
         p_apply_1_.setScorePoints(this.apply(p_apply_1_.getScorePoints(), p_apply_2_.getScorePoints()));
      }
   }

   @FunctionalInterface
   public interface IOperation {
      void apply(Score p_apply_1_, Score p_apply_2_) throws CommandSyntaxException;
   }
}
