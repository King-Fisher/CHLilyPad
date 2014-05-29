package com.kingfisher.chlilypad.abstraction;

import com.laytonsmith.PureUtilities.ClassLoading.ClassDiscovery;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.annotations.abstraction;
import com.laytonsmith.core.InternalException;
import lilypad.client.connect.api.Connect;

/**
 *
 * @author KingFisher
 */
public final class CHLilyPadStaticLayer {

	private static final CHLilyPadConvertor _convertor = getConvertorInstance();

	private CHLilyPadStaticLayer() {
	}

	private static CHLilyPadConvertor getConvertorInstance() {
		CHLilyPadConvertor convertor = null;
		for (Class<CHLilyPadConvertor> clazz : ClassDiscovery.getDefaultInstance().loadClassesWithAnnotationThatExtend(abstraction.class, CHLilyPadConvertor.class)) {
			if (clazz.getAnnotation(abstraction.class).type().equals(Implementation.GetServerType())) {
				if (convertor == null) {
					try {
						convertor = clazz.newInstance();
					} catch (InstantiationException | IllegalAccessException ex) {
						throw (RuntimeException) new InternalException().initCause(ex);
					}
				} else {
					throw new InternalException("More than one CHLilyPadConvertor implementation detected.");
				}
			}
		}
		if (convertor != null) {
			return convertor;
		} else {
			throw new InternalException("No CHLilyPadConvertor implementation detected.");
		}
	}

	public static CHLilyPadConvertor getConvertor() {
		return _convertor;
	}

	public static void startup() {
		_convertor.startup();
	}

	public static void shutdown() {
		_convertor.shutdown();
	}

	public static Connect getConnect() {
		return _convertor.getConnect();
	}
}