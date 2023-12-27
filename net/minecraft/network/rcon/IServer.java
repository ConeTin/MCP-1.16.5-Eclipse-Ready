package net.minecraft.network.rcon;

import net.minecraft.server.dedicated.ServerProperties;

public interface IServer {
   ServerProperties getServerProperties();

   String getHostname();

   int getPort();

   String getMotd();

   String getMinecraftVersion();

   int getCurrentPlayerCount();

   int getMaxPlayers();

   String[] getOnlinePlayerNames();

   String func_230542_k__();

   String getPlugins();

   String handleRConCommand(String command);
}
