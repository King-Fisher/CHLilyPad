package com.kingfisher.chlilypad.abstraction.common.events;

import com.kingfisher.chlilypad.abstraction.events.MCLilyPadRedirectEvent;
import com.laytonsmith.abstraction.Implementation.Type;
import com.laytonsmith.annotations.abstraction;
import lilypad.client.connect.api.event.RedirectEvent;

/**
 *
 * @author KingFisher
 */
@abstraction(type = Type.BUKKIT)
public class CommonLilyPadRedirectEvent implements MCLilyPadRedirectEvent {

	private final RedirectEvent _event;

	public CommonLilyPadRedirectEvent(RedirectEvent event) {
		_event = event;
	}

	public CommonLilyPadRedirectEvent(Object object) {
		this((RedirectEvent) object);
	}

	@Override
	public RedirectEvent _GetObject() {
		return _event;
	}

	@Override
	public String getPlayer() {
		return _event.getPlayer();
	}

	@Override
	public String getServer() {
		return _event.getServer();
	}
}