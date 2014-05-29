package com.kingfisher.chlilypad.abstraction.common;

import com.kingfisher.chlilypad.CHLilyPad;
import com.kingfisher.chlilypad.abstraction.CHLilyPadConvertor;
import com.kingfisher.chlilypad.abstraction.common.events.CommonLilyPadListener;

/**
 *
 * @author KingFisher
 */
public abstract class CHLilyPadAbstractCommonConvertor implements CHLilyPadConvertor {

	private CommonLilyPadListener _listener;

	@Override
	public void startup() {
		_listener = new CommonLilyPadListener();
		CHLilyPad.getConnect().registerEvents(_listener);
	}

	@Override
	public void shutdown() {
		CHLilyPad.getConnect().unregisterEvents(_listener);
		_listener = null;
	}
}