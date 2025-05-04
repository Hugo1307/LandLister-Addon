# LandLister Addon

Example of a LandGuard addon that lists all the land in the world using the LandGuard API.

The LandGuard API allows developers to build custom addons for the LandGuard protection system. These addons can query, modify, and extend core functionalities like lands, access groups, member roles, and protection flags.

This example demonstrates how to create a basic plugin that integrates with the LandGuard API to retrieve and log the names of all lands defined on the server.

## 📦 Maven Dependency

To use the LandGuard API in your project, add the following dependency to your pom.xml. The API is published on Maven Central:

```xml
<dependency>
    <groupId>io.github.hugo1307</groupId>
    <artifactId>landguard-api</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

## 📄 plugin.yml

Make sure your plugin declares a dependency on LandGuard in the plugin.yml file:

```yaml
name: LandLister-Addon
version: '1.0-SNAPSHOT'
main: io.github.hugo1307.landlister.LandListerAddon
api-version: '1.18'
prefix: LandLister-Addon
author: Hugo1307
description: An example addon for LandGuard plugin. Created using LandGuard API.
depend:
  - LandGuard
```

## 💻 Plugin Class: LandListerPlugin.java

```java
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
```

## 📌 Next Steps

This is just a simple example to show you how to get started with the LandGuard API. You can extend this example to create more complex functionalities, such as:

- Listen to Bukkit events (e.g. land creation, player interaction).
- Modify land flags or access groups dynamically.
- Create custom commands that interact with LandGuard lands.
- Build user interfaces or admin tools using the API.