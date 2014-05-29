package com.kingfisher.chlilypad.abstraction.bukkit;

import com.kingfisher.chlilypad.abstraction.common.CHLilyPadAbstractCommonConvertor;
import com.laytonsmith.abstraction.Implementation.Type;
import com.laytonsmith.annotations.abstraction;
import lilypad.client.connect.api.Connect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 *
 * @author KingFisher
 */
@abstraction(type = Type.BUKKIT)
public final class CHLilyPadBukkitConvertor extends CHLilyPadAbstractCommonConvertor {

	@Override
	public Connect getConnect() {
		RegisteredServiceProvider<Connect> provider = Bukkit.getServer().getServicesManager().getRegistration(Connect.class);
		return provider != null ? provider.getProvider() : null;
	}
}