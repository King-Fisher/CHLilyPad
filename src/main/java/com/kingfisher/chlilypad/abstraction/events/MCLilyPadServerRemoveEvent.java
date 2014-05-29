package com.kingfisher.chlilypad.abstraction.events;

import com.laytonsmith.core.events.BindableEvent;

/**
 *
 * @author KingFisher
 */
public interface MCLilyPadServerRemoveEvent extends BindableEvent {

	public String getServer();
}