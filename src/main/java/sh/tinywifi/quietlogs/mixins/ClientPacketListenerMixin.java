package sh.tinywifi.quietlogs.mixins;

import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.apache.logging.log4j.Logger;
import sh.tinywifi.quietlogs.LogSuppression;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    
    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"), remap = false)
    private void redirectUnknownPacketWarning(Logger logger, String message, Object identifier) {
        if (message != null && message.contains("Unknown custom packet identifier") && 
            identifier != null && identifier.toString().contains("moremobvariants:main")) {
            LogSuppression.incrementSuppressed();
        } else {
            logger.warn(message, identifier);
        }
    }
}