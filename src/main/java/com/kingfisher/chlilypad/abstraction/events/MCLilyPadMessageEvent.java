package com.kingfisher.chlilypad.abstraction.events;

import com.laytonsmith.core.events.BindableEvent;

/**
 *
 * @author KingFisher
 */
public interface MCLilyPadMessageEvent extends BindableEvent {

	public String getChannel();

	public byte[] getMessage();

	public String getSender();
}