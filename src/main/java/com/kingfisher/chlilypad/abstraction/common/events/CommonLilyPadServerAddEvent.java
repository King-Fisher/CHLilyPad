package com.kingfisher.chlilypad.abstraction.common.events;

import com.kingfisher.chlilypad.abstraction.events.MCLilyPadServerAddEvent;
import com.laytonsmith.abstraction.Implementation.Type;
import com.laytonsmith.annotations.abstraction;
import java.net.InetSocketAddress;
import lilypad.client.connect.api.event.ServerAddEvent;

/**
 *
 * @author KingFisher
 */
@abstraction(type = Type.BUKKIT)
public class CommonLilyPadServerAddEvent implements MCLilyPadServerAddEvent {

	private final ServerAddEvent _event;

	public CommonLilyPadServerAddEvent(ServerAddEvent event) {
		_event = event;
	}

	public CommonLilyPadServerAddEvent(Object object) {
		this((ServerAddEvent) object);
	}

	@Override
	public ServerAddEvent _GetObject() {
		return _event;
	}

	@Override
	public InetSocketAddress getAddress() {
		return _event.getAddress();
	}

	@Override
	public String getSecurityKey() {
		return _event.getSecurityKey();
	}

	@Override
	public String getServer() {
		return _event.getServer();
	}
}