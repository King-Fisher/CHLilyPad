package com.kingfisher.chlilypad.core.events;

import com.kingfisher.chlilypad.abstraction.events.MCLilyPadMessageEvent;
import com.kingfisher.chlilypad.abstraction.events.MCLilyPadRedirectEvent;
import com.kingfisher.chlilypad.abstraction.events.MCLilyPadServerAddEvent;
import com.kingfisher.chlilypad.abstraction.events.MCLilyPadServerRemoveEvent;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CByteArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.Prefilters;
import com.laytonsmith.core.events.Prefilters.PrefilterType;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import java.util.Map;

/**
 *
 * @author KingFisher
 */
public final class LilyPadEvents {

	private LilyPadEvents() {
	}

	public static String docs() {
		return "A class of events to use with LilyPad.";
	}

	protected static abstract class LilyPadEvent extends AbstractEvent {

		@Override
		public String getName() {
			return getClass().getSimpleName();
		}

		@Override
		public Version since() {
			return CHVersion.V3_3_1;
		}

		@Override
		public Driver driver() {
			return Driver.EXTENSION;
		}

		@Override
		public boolean modifyEvent(String key, Construct value, BindableEvent event) {
			return false;
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			throw new ConfigRuntimeException("This operation is not supported yet.", ExceptionType.BindException, t);
		}
	}

	@api
	public static final class lp_message_received extends LilyPadEvent {

		@Override
		public String docs() {
			return "{channel: <macro> | sender: <macro>}"
				+ "Triggered when a message has been received."
				+ "{channel: the channel | sender: the sender of the message | message: the message, a byte_array}"
				+ "{}"
				+ "{}";
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent bindableEvent) throws PrefilterNonMatchException {
			if (bindableEvent instanceof MCLilyPadMessageEvent) {
				MCLilyPadMessageEvent event = (MCLilyPadMessageEvent) bindableEvent;
				Prefilters.match(prefilter, "channel", event.getChannel(), PrefilterType.MACRO);
				Prefilters.match(prefilter, "sender", event.getSender(), PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent bindableEvent) throws EventException {
			MCLilyPadMessageEvent event = (MCLilyPadMessageEvent) bindableEvent;
			Map<String, Construct> eventMap = evaluate_helper(event);
			eventMap.put("channel", new CString(event.getChannel(), Target.UNKNOWN));
			eventMap.put("sender", new CString(event.getSender(), Target.UNKNOWN));
			eventMap.put("message", CByteArray.wrap(event.getMessage(), Target.UNKNOWN));
			return eventMap;
		}
	}

	@api
	public static final class lp_player_redirected extends LilyPadEvent {

		@Override
		public String docs() {
			return "{player: <macro> | server: <macro>}"
				+ "Triggered when a player is redirected to a server."
				+ "{player: the player | server: the name of the server}"
				+ "{}"
				+ "{}";
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent bindableEvent) throws PrefilterNonMatchException {
			if (bindableEvent instanceof MCLilyPadRedirectEvent) {
				MCLilyPadRedirectEvent event = (MCLilyPadRedirectEvent) bindableEvent;
				Prefilters.match(prefilter, "player", event.getPlayer(), PrefilterType.MACRO);
				Prefilters.match(prefilter, "server", event.getServer(), PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent bindableEvent) throws EventException {
			MCLilyPadRedirectEvent event = (MCLilyPadRedirectEvent) bindableEvent;
			Map<String, Construct> eventMap = evaluate_helper(event);
			eventMap.put("player", new CString(event.getPlayer(), Target.UNKNOWN));
			eventMap.put("server", new CString(event.getServer(), Target.UNKNOWN));
			return eventMap;
		}
	}

	@api
	public static final class lp_server_added extends LilyPadEvent {

		@Override
		public String docs() {
			return "{server: <macro>}"
				+ "Triggered when a server has been added to the network."
				+ "{server: the name of the server | address: the address of the server | security_key: the security key that the server used to connect to the network}"
				+ "{}"
				+ "{}";
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent bindableEvent) throws PrefilterNonMatchException {
			if (bindableEvent instanceof MCLilyPadServerAddEvent) {
				Prefilters.match(prefilter, "server", ((MCLilyPadServerAddEvent) bindableEvent).getServer(), PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent bindableEvent) throws EventException {
			MCLilyPadServerAddEvent event = (MCLilyPadServerAddEvent) bindableEvent;
			Map<String, Construct> eventMap = evaluate_helper(event);
			eventMap.put("server", new CString(event.getServer(), Target.UNKNOWN));
			eventMap.put("address", new CString(event.getAddress().toString(), Target.UNKNOWN));
			eventMap.put("security_key", new CString(event.getSecurityKey(), Target.UNKNOWN));
			return eventMap;
		}
	}

	@api
	public static final class lp_server_removed extends LilyPadEvent {

		@Override
		public String docs() {
			return "{server: <macro>}"
				+ "Triggered when a server has been removed to the network."
				+ "{server: the name of the server}"
				+ "{}"
				+ "{}";
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent bindableEvent) throws PrefilterNonMatchException {
			if (bindableEvent instanceof MCLilyPadServerRemoveEvent) {
				Prefilters.match(prefilter, "server", ((MCLilyPadServerRemoveEvent) bindableEvent).getServer(), PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent bindableEvent) throws EventException {
			Map<String, Construct> eventMap = evaluate_helper(bindableEvent);
			eventMap.put("server", new CString(((MCLilyPadServerRemoveEvent) bindableEvent).getServer(), Target.UNKNOWN));
			return eventMap;
		}
	}
}