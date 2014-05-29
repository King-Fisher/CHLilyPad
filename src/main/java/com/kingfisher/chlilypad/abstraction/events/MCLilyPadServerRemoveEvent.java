package com.kingfisher.chlilypad.abstraction.events;

import com.laytonsmith.core.events.BindableEvent;

/**
 *
 * @author Hector
 */
public interface MCLilyPadServerRemoveEvent extends BindableEvent {

	public String getServer();
}