package com.kingfisher.chlilypad.abstraction.events;

import com.laytonsmith.core.events.BindableEvent;
import java.net.InetSocketAddress;

/**
 *
 * @author Hector
 */
public interface MCLilyPadServerAddEvent extends BindableEvent {

	public InetSocketAddress getAddress();

	public String getSecurityKey();

	public String getServer();
}