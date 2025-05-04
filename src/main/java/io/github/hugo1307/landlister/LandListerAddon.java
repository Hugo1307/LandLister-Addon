package io.github.hugo1307.landlister;

import io.github.hugo1307.landguard.api.LandGuardApi;
import io.github.hugo1307.landguard.api.managers.contract.LandManager;
import io.github.hugo1307.landguard.data.domain.Land;
import org.bukkit.plugin.java.JavaPlugin;

public final class LandListerAddon extends JavaPlugin {

  @Override
  public void onEnable() {
    // Check if LandGuard is enabled before accessing the API
    if (!getServer().getPluginManager().isPluginEnabled("LandGuard")) {
      getLogger().severe("LandGuard is not enabled! This plugin depends on LandGuard.");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    LandGuardApi api;
    try {
      api = LandGuardApi.get();
    } catch (IllegalStateException e) {
      getLogger().severe("Failed to access LandGuard API: " + e.getMessage());
      return;
    }

    // Access the LandManager from the API to list all lands
    LandManager landManager = api.getLandManager();
    getLogger().info("Listing all registered lands:");

    for (Land land : landManager.getAllLands()) {
      getLogger().info(" - Land Name: " + land.getName());
    }

    getLogger().info("LandLister has finished loading successfully.");
  }

  @Override
  public void onDisable() {
    getLogger().info("LandLister has been disabled.");
  }

}
