package com.kingfisher.chlilypad;

import com.kingfisher.chlilypad.abstraction.CHLilyPadStaticLayer;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import lilypad.client.connect.api.Connect;

/**
 *
 * @author KingFisher
 */
@MSExtension("CHLilyPad")
public final class CHLilyPad extends AbstractExtension {

	private static final Version VERSION = new SimpleVersion(0, 0, 1);

	private static Connect _connect;

	@Override
	public Version getVersion() {
		return VERSION;
	}

	@Override
	public void onStartup() {
		_connect = CHLilyPadStaticLayer.getConnect();
		if (_connect != null) {
			CHLilyPadStaticLayer.startup();
			Static.getLogger().info(String.format("%s enabled.", getName()));
		} else {
			Static.getLogger().severe(String.format("Plugin %s seems to be missing, none of the %s functions will work.", "Bukkit-Connect", getName()));
		}
	}

	@Override
	public void onShutdown() {
		if (_connect != null) {
			CHLilyPadStaticLayer.shutdown();
			_connect = null;
			Static.getLogger().info(String.format("%s disabled.", getName()));
		}
	}

	public static Connect getConnect() {
		return _connect;
	}
}