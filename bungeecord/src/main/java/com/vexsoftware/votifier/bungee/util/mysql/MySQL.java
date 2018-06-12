package com.vexsoftware.votifier.bungee.util.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.md_5.bungee.api.plugin.Plugin;


/**
 * Connects to and uses a MySQL database
*/

public class MySQL extends Database {
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;


	/**
	 * Creates a new MySQL instance
	 * 
	 * @param plugin
	 *            Plugin instance
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param database
	 *            Database name
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	public MySQL(Plugin plugin, String hostname, String port, String database,
			String username, String password) {
		super(plugin);
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		if (checkConnection()) {
			return this.connection;
		}
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection("jdbc:mysql://"
				+ this.hostname + ":" + this.port + "/" + this.database,
				this.user, this.password);
		return this.connection;
	}
}