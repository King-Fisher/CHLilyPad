package com.kingfisher.chlilypad.abstraction.common.events;

import com.kingfisher.chlilypad.abstraction.events.MCLilyPadMessageEvent;
import com.laytonsmith.abstraction.Implementation.Type;
import com.laytonsmith.annotations.abstraction;
import lilypad.client.connect.api.event.MessageEvent;

/**
 *
 * @author KingFisher
 */
@abstraction(type = Type.BUKKIT)
public class CommonMCLilyPadMessageEvent implements MCLilyPadMessageEvent {

	private final MessageEvent _event;

	public CommonMCLilyPadMessageEvent(MessageEvent event) {
		_event = event;
	}

	public CommonMCLilyPadMessageEvent(Object object) {
		this((MessageEvent) object);
	}

	@Override
	public MessageEvent _GetObject() {
		return _event;
	}

	@Override
	public String getChannel() {
		return _event.getChannel();
	}

	@Override
	public byte[] getMessage() {
		return _event.getMessage();
	}

	@Override
	public String getSender() {
		return _event.getSender();
	}
}