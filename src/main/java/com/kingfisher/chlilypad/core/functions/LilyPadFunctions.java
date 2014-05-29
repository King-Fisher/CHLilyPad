package com.kingfisher.chlilypad.core.functions;

import com.kingfisher.chlilypad.core.CHLilyPadStatic;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ArgumentValidation;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Optimizable;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CByteArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import lilypad.client.connect.api.ConnectSettings;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.AsProxyRequest;
import lilypad.client.connect.api.request.impl.AsServerRequest;
import lilypad.client.connect.api.request.impl.AuthenticateRequest;
import lilypad.client.connect.api.request.impl.GetDetailsRequest;
import lilypad.client.connect.api.request.impl.GetKeyRequest;
import lilypad.client.connect.api.request.impl.GetPlayersRequest;
import lilypad.client.connect.api.request.impl.GetWhoamiRequest;
import lilypad.client.connect.api.request.impl.MessageRequest;
import lilypad.client.connect.api.request.impl.NotifyPlayerRequest;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.AsProxyResult;
import lilypad.client.connect.api.result.impl.AsServerResult;
import lilypad.client.connect.api.result.impl.AuthenticateResult;
import lilypad.client.connect.api.result.impl.GetDetailsResult;
import lilypad.client.connect.api.result.impl.GetKeyResult;
import lilypad.client.connect.api.result.impl.GetPlayersResult;
import lilypad.client.connect.api.result.impl.GetWhoamiResult;
import lilypad.client.connect.api.result.impl.MessageResult;
import lilypad.client.connect.api.result.impl.NotifyPlayerResult;
import lilypad.client.connect.api.result.impl.RedirectResult;

/**
 *
 * @author KingFisher
 */
public final class LilyPadFunctions {

	private LilyPadFunctions() {
	}

	public static String docs() {
		return "A class of functions to use with LilyPad. It is highly recommended to not run some of this functions on the main thread,"
			+ " but asynchronously with the x_new_thread function. Somes of this functions are in fact request to the network,"
			+ " so they will always return an array, whose one of the keys will be always present, the 'status' key, whose the value"
			+ " can only be one of " + StringUtils.Join(StatusCode.values(), ", ", ", or ", " or ") + ", " + StatusCode.INVALID_ROLE
			+ " means that the request failed because you do not have the required role on the network to make the request, and "
			+ StatusCode.INVALID_GENERIC + " means that the request failed due to the arguments you passed to the request.";
	}

	protected static abstract class LilyPadFunction extends AbstractFunction {

		@Override
		public String getName() {
			return getClass().getSimpleName();
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException};
		}

		@Override
		public boolean isRestricted() {
			return true;
		}

		@Override
		public Boolean runAsync() {
			return false;
		}

		@Override
		public Version since() {
			return CHVersion.V3_3_1;
		}
	}

	@api
	public static final class lp_get_username extends LilyPadFunction implements Optimizable {

		@Override
		public String docs() {
			return "string {} Returns the username to be authenticated with on the network (the one specified in the LilyPad-Connect' pluginyml file).";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHLilyPadStatic.getConnect(t).getSettings().getUsername(), t);
		}

		@Override
		public Set<OptimizationOption> optimizationOptions() {
			return EnumSet.of(
						OptimizationOption.CONSTANT_OFFLINE,
						OptimizationOption.CACHE_RETURN
			);
		}
	}

	@api
	public static final class lp_get_password extends LilyPadFunction implements Optimizable {

		@Override
		public String docs() {
			return "string {} Returns the password to be authenticated with on the network (the one specified in the LilyPad-Connect' pluginyml file).";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHLilyPadStatic.getConnect(t).getSettings().getPassword(), t);
		}

		@Override
		public Set<Optimizable.OptimizationOption> optimizationOptions() {
			return EnumSet.of(
						OptimizationOption.CONSTANT_OFFLINE,
						OptimizationOption.CACHE_RETURN
			);
		}
	}

	@api
	public static final class lp_get_address extends LilyPadFunction implements Optimizable {

		@Override
		public String docs() {
			return "string {} Returns the address of the remote network's server (the one specified in the LilyPad-Connect' pluginyml file).";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHLilyPadStatic.getConnect(t).getSettings().getOutboundAddress().toString(), t);
		}

		@Override
		public Set<Optimizable.OptimizationOption> optimizationOptions() {
			return EnumSet.of(
						OptimizationOption.CONSTANT_OFFLINE,
						OptimizationOption.CACHE_RETURN
			);
		}
	}

	@api
	public static final class lp_is_connected extends LilyPadFunction {

		@Override
		public String docs() {
			return "boolean {} Returns if the server is connected to it's network.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return CBoolean.get(CHLilyPadStatic.getConnect(t).isConnected());
		}
	}

	@api
	public static final class lp_is_closed extends LilyPadFunction {

		@Override
		public String docs() {
			return "boolean {} Returns if the server connexion is closed.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return CBoolean.get(CHLilyPadStatic.getConnect(t).isClosed());
		}
	}

	@api
	public static final class lp_connect extends LilyPadFunction {

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "void {} Connect the server to it's network.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			try {
				CHLilyPadStatic.getConnect(t).connect();
			} catch (Throwable ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static final class lp_disconnect extends LilyPadFunction {

		@Override
		public String docs() {
			return "void {} Disconnect the server from it's network.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHLilyPadStatic.getConnect(t).disconnect();
			return CVoid.VOID;
		}
	}

	@api
	public static final class lp_close extends LilyPadFunction {

		@Override
		public String docs() {
			return "void {} Close to not allow for anymore connections nor disconnections.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHLilyPadStatic.getConnect(t).close();
			return CVoid.VOID;
		}
	}

	@api
	public static final class lp_as_proxy extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{4, 5};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {[ip], port, motd, version, maxPlayers} Asks to have the role of a proxy on the network."
				+ " If ip is not specified, it will be the network's ip. This function is a request.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			try {
				AsProxyResult result = CHLilyPadStatic.getConnect(t).request((args.length == 4) ? new AsProxyRequest(ArgumentValidation.getInt32(args[0], t), args[1].val(), args[2].val(), ArgumentValidation.getInt32(args[3], t)) : new AsProxyRequest(args[0].val(), ArgumentValidation.getInt32(args[1], t), args[2].val(), args[3].val(), ArgumentValidation.getInt32(args[4], t))).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_as_server extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {[ip], port} Asks to have the role of a server on the network."
				+ " If ip is not specified, it will be the network's ip. This function is a request.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			try {
				AsServerResult result = CHLilyPadStatic.getConnect(t).request((args.length == 1) ? new AsServerRequest(ArgumentValidation.getInt32(args[0], t)) : new AsServerRequest(args[0].val(), ArgumentValidation.getInt32(args[1], t))).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				r.set("key", result.getSecurityKey());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_authenticate extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1, 3};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {[username, password], passwordKey} Sends an authentication request to the network. If username and password are not specified,"
				+ " their values will be the same as specified in the config.yml file of the Bukkit-Connect plugin.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String username, password, key;
			if (args.length == 1) {
				ConnectSettings settings = CHLilyPadStatic.getConnect(t).getSettings();
				username = settings.getUsername();
				password = settings.getPassword();
				key = args[0].val();
			} else {
				username = args[0].val();
				password = args[1].val();
				key = args[2].val();
			}
			try {
				AuthenticateResult result = CHLilyPadStatic.getConnect(t).request(new AuthenticateRequest(username, password, key)).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_details extends LilyPadFunction {

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {} Sends a request to the network to get the uniform connection details of the network, not guaranteed to be an accurate representation.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			try {
				GetDetailsResult result = CHLilyPadStatic.getConnect(t).request(new GetDetailsRequest()).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				r.set("ip", result.getIp());
				r.set("motd", result.getMotd());
				r.set("port", new CInt(result.getPort(), t), t);
				r.set("version", result.getVersion());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_get_key extends LilyPadFunction {

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {} Sends a request to receive a shared salt to be used within the authentication process.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			try {
				GetKeyResult result = CHLilyPadStatic.getConnect(t).request(new GetKeyRequest()).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				r.set("key", result.getKey());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_send_message extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{2, 3};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {[recipients], channel, message} Sends a message to the specified recipients. recipients can be null, a string or an array of string,"
				+ " if null or not specified, all the servers will receive the message. message can be a string or a byte array. This functions is a request.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			ArrayList<String> recipients;
			String channel;
			byte[] message;
			if (args.length == 2) {
				recipients = null;
				channel = args[0].val();
				message = args[1] instanceof CByteArray ? ((CByteArray) args[1]).asByteArrayCopy() : args[1].val().getBytes();
			} else {
				if (args[0] instanceof CNull) {
					recipients = null;
				} else if (args[0] instanceof CArray) {
					recipients = new ArrayList<>();
					for (Construct c : ((CArray) args[0]).asList()) {
						recipients.add(c.val());
					}
				} else {
					recipients = new ArrayList<>();
					recipients.add(args[0].val());
				}
				channel = args[1].val();
				message = args[2] instanceof CByteArray ? ((CByteArray) args[2]).asByteArrayCopy() : args[2].val().getBytes();
			}
			try {
				MessageResult result = CHLilyPadStatic.getConnect(t).request(new MessageRequest(recipients, channel, message)).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_add_player extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0, 1};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.PlayerOfflineException};
		}

		@Override
		public String docs() {
			return "array {[playerName]} Sends a notification to the network that a player has been added to our proxy."
				+ " If playerName is not specified, it will be the name of the player running the command. The function will not throw a PlayerOfflineException"
				+ " if the player is not online, so the name must be exact.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String name;
			if (args.length == 0) {
				name = Static.getPlayer(environment, t).getName();
			} else {
				name = args[0].val();
			}
			try {
				NotifyPlayerResult result = CHLilyPadStatic.getConnect(t).request(new NotifyPlayerRequest(true, name)).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_remove_player extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0, 1};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.PlayerOfflineException};
		}

		@Override
		public String docs() {
			return "array {[playerName]} Sends a notification to the network that a player has been removed from our proxy."
				+ " If playerName is not specified, it will be the name of the player running the command. The function will not throw a PlayerOfflineException"
				+ " if the player is not online, so the name must be exact.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String name;
			if (args.length == 0) {
				name = Static.getPlayer(environment, t).getName();
			} else {
				name = args[0].val();
			}
			try {
				NotifyPlayerResult result = CHLilyPadStatic.getConnect(t).request(new NotifyPlayerRequest(false, name)).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_get_players extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0, 1};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {[includeList]} Sends a request to get an accurate representation of all players on the network."
				+ " If includeList is specified and false, the result will not include the list of every player.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			try {
				GetPlayersResult result = CHLilyPadStatic.getConnect(t).request(new GetPlayersRequest((args.length == 0) ? true : ArgumentValidation.getBoolean(args[0], t))).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				r.set("current", new CInt(result.getCurrentPlayers(), t), t);
				r.set("max", new CInt(result.getMaximumPlayers(), t), t);
				CArray players = new CArray(t);
				for (String player : result.getPlayers()) {
					players.push(new CString(player, t));
				}
				r.set("players", players, t);
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_redirect_player extends LilyPadFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.PlayerOfflineException};
		}

		@Override
		public String docs() {
			return "array {[playerName], serverName} Sends a request to the network to redirect the specified player to the specified server."
				+ " If playerName is not specified, it will be the name of the player running the function. The function will not throw a PlayerOfflineException"
				+ " if the player is not online, so the name must be exact.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String player, server;
			if (args.length == 1) {
				player = Static.getPlayer(environment, t).getName();
				server = args[0].val();
			} else {
				player = args[0].val();
				server = args[1].val();
			}
			try {
				RedirectResult result = CHLilyPadStatic.getConnect(t).request(new RedirectRequest(server, player)).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static final class lp_whoami extends LilyPadFunction {

		@Override
		public Exceptions.ExceptionType[] thrown() {
			return new Exceptions.ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		@Override
		public String docs() {
			return "array {} Sends a request to get the current identification on the network.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			try {
				GetWhoamiResult result = CHLilyPadStatic.getConnect(t).request(new GetWhoamiRequest()).awaitUninterruptibly();
				CArray r = new CArray(t);
				r.set("status", result.getStatusCode().name());
				r.set("identification", result.getIdentification());
				return r;
			} catch (RequestException ex) {
				throw new ConfigRuntimeException(ex.getMessage(), Exceptions.ExceptionType.PluginInternalException, t);
			}
		}
	}
}