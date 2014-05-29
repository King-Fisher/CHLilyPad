package com.kingfisher.chlilypad.abstraction.common.events;

import com.kingfisher.chlilypad.abstraction.events.MCLilyPadServerRemoveEvent;
import com.laytonsmith.abstraction.Implementation.Type;
import com.laytonsmith.annotations.abstraction;
import lilypad.client.connect.api.event.ServerRemoveEvent;

/**
 *
 * @author KingFisher
 */
@abstraction(type = Type.BUKKIT)
public class CommonLilyPadServerRemoveEvent implements MCLilyPadServerRemoveEvent {

	private final ServerRemoveEvent _event;

	public CommonLilyPadServerRemoveEvent(ServerRemoveEvent event) {
		_event = event;
	}

	public CommonLilyPadServerRemoveEvent(Object object) {
		this((ServerRemoveEvent) object);
	}

	@Override
	public ServerRemoveEvent _GetObject() {
		return _event;
	}

	@Override
	public String getServer() {
		return _event.getServer();
	}
}