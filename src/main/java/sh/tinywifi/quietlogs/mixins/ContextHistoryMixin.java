package sh.tinywifi.quietlogs.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.apache.logging.log4j.Logger;
import sh.tinywifi.quietlogs.LogSuppression;

@Pseudo
@Mixin(targets = "com.flansmod.common.actions.contexts.ContextHistory", remap = false)
public class ContextHistoryMixin {
    
    @Inject(method = "GetOrCreate", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V"), cancellable = true, remap = false)
    private void cancelInfoLog(CallbackInfo ci) {
        LogSuppression.incrementSuppressed();
        ci.cancel();
    }
    
    @Inject(method = "GetOrCreate", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V"), cancellable = true, remap = false)
    private void cancelErrorLog(CallbackInfo ci) {
        LogSuppression.incrementSuppressed();
        ci.cancel();
    }
}