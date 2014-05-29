package com.kingfisher.chlilypad.core;

import com.kingfisher.chlilypad.CHLilyPad;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions;
import lilypad.client.connect.api.Connect;

/**
 *
 * @author Hector
 */
public final class CHLilyPadStatic {

	private CHLilyPadStatic() {
	}

	public static Connect getConnect(Target target) {
		Connect conncect = CHLilyPad.getConnect();
		if (conncect != null) {
			return conncect;
		} else {
			throw new ConfigRuntimeException("Needed plugin Bukkit-Connect not found.", Exceptions.ExceptionType.InvalidPluginException, target);
		}
	}
}