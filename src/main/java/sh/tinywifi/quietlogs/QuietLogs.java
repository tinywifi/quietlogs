package sh.tinywifi.quietlogs;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("quietlogs")
public class QuietLogs {
    public static final String MOD_ID = "quietlogs";
    private static final Logger LOGGER = LogManager.getLogger();
    
    public QuietLogs() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("QuietLogs is starting up...");
        
        try {
            org.apache.logging.log4j.core.Logger rootLogger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
            rootLogger.addFilter(new LogFilter());
            LOGGER.info("QuietLogs log filter installed successfully");
        } catch (Exception e) {
            LOGGER.warn("Failed to install log filter, falling back to mixins: {}", e.getMessage());
        }
        
        if (ModList.get().isLoaded("flansmod")) {
            String flansVersion = ModList.get().getModContainerById("flansmod")
                .map(container -> container.getModInfo().getVersion().toString())
                .orElse("unknown");
            LOGGER.info("QuietLogs successfully initialized - targeting Flan's Mod version {}", flansVersion);
        } else {
            LOGGER.info("QuietLogs loaded - will suppress various mod log spam");
        }
    }
}