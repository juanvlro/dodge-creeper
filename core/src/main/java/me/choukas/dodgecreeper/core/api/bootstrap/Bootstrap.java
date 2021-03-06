package me.choukas.dodgecreeper.core.api.bootstrap;

import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import fr.minuskube.inv.InventoryManager;
import me.choukas.dodgecreeper.api.listener.ListenerRegisterer;
import me.choukas.dodgecreeper.api.translation.TranslationRegisterer;
import me.choukas.dodgecreeper.api.world.WorldManager;
import me.choukas.dodgecreeper.core.CoreModule;
import me.choukas.dodgecreeper.core.api.APIModule;
import me.choukas.dodgecreeper.core.bukkit.BukkitModule;
import org.bukkit.plugin.java.JavaPlugin;

public class Bootstrap {

    public Injector bootstrap(JavaPlugin plugin) throws CreationException {
        Injector injector = Guice.createInjector(Stage.PRODUCTION,
                new BukkitModule(plugin),
                new APIModule(),
                new CoreModule()
        );

        injector.getInstance(InventoryManager.class).init();

        injector.getInstance(ListenerRegisterer.class).registerListeners();
        injector.getInstance(TranslationRegisterer.class).registerTranslations();

        injector.getInstance(WorldManager.class).init();

        return injector;
    }
}
