package com.kingfisher.chlilypad.abstraction.events;

import com.laytonsmith.core.events.BindableEvent;

/**
 *
 * @author KingFisher
 */
public interface MCLilyPadRedirectEvent extends BindableEvent {

	public String getPlayer();

	public String getServer();
}