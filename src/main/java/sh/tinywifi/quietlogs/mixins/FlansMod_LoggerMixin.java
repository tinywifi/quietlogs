package sh.tinywifi.quietlogs.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.apache.logging.log4j.Logger;
import sh.tinywifi.quietlogs.LogSuppression;

@Pseudo
@Mixin(targets = "com.flansmod.common.FlansMod", remap = false)
public class FlansMod_LoggerMixin {
    
    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V"), remap = false)
    private static void redirectFlansMod_InfoLog(Logger logger, String message) {
        if (message != null && (message.contains("Context[") && message.contains("] updated from"))) {
            LogSuppression.incrementSuppressed();
        } else {
            logger.info(message);
        }
    }
    
    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V"), remap = false)
    private static void redirectFlansMod_ErrorLog(Logger logger, String message) {
        if (message != null && message.contains("ContextHistory of") && message.contains("is overflowing?")) {
            LogSuppression.incrementSuppressed();
        } else {
            logger.error(message);
        }
    }
}