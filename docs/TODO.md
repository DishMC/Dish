# Dish Server TODO

- Add tests (maybe JUnit?) to workspaces
- Add a permission system for commands
- Add event listeners for
  - TradeWithVillager, cancellable
  - StartSmelting, cancellable
  - OpenContainer, cancellable (Includes inventory? Or make seperate event listener, not sure yet) (ServerPlayer.java:openMenu() -- maybe this)
  - GenerateLoot, cancellable (Called when a player opens a chest) - half done
  - ChangeDimension, cancellable (Not sure if it should be cancelled) (ServerPlayer.java:changeDimension())
  - ChangeGameMode, cancellable (ServerPlayer.java:setGameMode())
  - WeatherChange, cancellable (ServerLevel.java:advanceWeatherCycle())
  - EnchantItem, cancellable (EnchantmentMenu.java:clickMenuButton())
  - Enter and LeaveCombat (ServerPlayer.java:onEnterCombat())
  - PlayerDrop, cancellable (ServerPlayer.java:drop())
  - PlayerPickupItem, cancellable (ServerPlayer.java:onItemPickup())
- Maybe add a way to allow plugins to change villager trades for a specific villager? (ServerPlayer.java:sendMerchantOffers())
- Implement API for inventories
- Look into a way for plugins to register structures
- Add Item wrapper classes
- Add Dish error handling
- Add logger for plugins
- Add proper documentation
