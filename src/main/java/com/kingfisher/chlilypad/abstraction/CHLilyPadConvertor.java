package com.kingfisher.chlilypad.abstraction;

import lilypad.client.connect.api.Connect;

/**
 *
 * @author KingFisher
 */
public interface CHLilyPadConvertor {

	public void startup();

	public void shutdown();

	public Connect getConnect();
}