package com.kingfisher.chlilypad.abstraction.common.events;

import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.event.RedirectEvent;
import lilypad.client.connect.api.event.ServerAddEvent;
import lilypad.client.connect.api.event.ServerRemoveEvent;

/**
 *
 * @author KingFisher
 */
public class CommonLilyPadListener {

	@EventListener
	public void onMessage(MessageEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "lp_message_received", new CommonLilyPadMessageEvent(event));
	}

	@EventListener
	public void onPlayerRedirection(RedirectEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "lp_player_redirected", new CommonLilyPadRedirectEvent(event));
	}

	@EventListener
	public void onServerAdded(ServerAddEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "lp_server_added", new CommonLilyPadServerAddEvent(event));
	}

	@EventListener
	public void onServerRemoved(ServerRemoveEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "lp_server_removed", new CommonLilyPadServerRemoveEvent(event));
	}
}