package com.vexsoftware.votifier.bungee.util.mysql;

import com.vexsoftware.votifier.bungee.NuVotifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {
	private final boolean debug = NuVotifier.instance.configuration.getBoolean("debug");
	private Connection connection = null;
	
        private final String succesfulSQL = "CREATE TABLE IF NOT EXISTS votesreceived ( id INT (6) NOT NULL AUTO_INCREMENT, date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, protocol VARCHAR (4), address VARCHAR (100) NOT NULL, service VARCHAR (80) NOT NULL, voter VARCHAR (50) NOT NULL, PRIMARY KEY (id));";
        private final String failedSQL = "CREATE TABLE IF NOT EXISTS votesfailed ( id INT (6) NOT NULL AUTO_INCREMENT, date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, protocol VARCHAR (4), address VARCHAR (100) NOT NULL, service VARCHAR (80) NOT NULL, voter VARCHAR (50) NOT NULL, PRIMARY KEY (id));";
	
	/**
	 * Queries for Database
	 * 
	 * @param connection
	 * The connection to the database
	 */
	public Queries(Connection connection) {
            this.connection = connection;
	}
	
	/**
	 * Creates the MySQL tables
	 * 
	 * @return successful
	 */
	public boolean createMySQLTable() {
            boolean successful = false;
            if ((executeUpdate(this.succesfulSQL)) && (executeUpdate(this.failedSQL))) {
                successful = true;
            }
            return successful;
	}
	
	/**
	 * Creates the SQLite tables
	 * 
	 * @return true if successful or false if unsuccessful
	 */
	public boolean createSQLiteTable() {
            boolean successful = false;
            if ((executeUpdate(this.succesfulSQL)) && (executeUpdate(this.failedSQL))) {
                successful = true;
            }
            return successful;
	}
	
	/**
	 * Execute a update against the database
	 * 
	 * @param sql
	 * 			The sql query to execute
	 * 
	 * @return true if successful or false if unsuccessful
	 */
	public boolean executeUpdate(String sql) {
            try {
                Statement statement = this.connection.createStatement();
                statement.executeUpdate(sql);
                return true;
            } catch (SQLException e) {
                if (debug) {
                    e.printStackTrace(); 
                }
                return false;
            }
	}
	
	/**
	 * Execute a query against the database
	 * 
	 * @param sql
	 * 			The sql query to execute
	 * 
	 * @return the ResultSet
	 */
	public ResultSet executeQuery(String sql) {
            try {
                Statement statement = this.connection.createStatement();
                return statement.executeQuery(sql);
            } catch (SQLException e) {
                if (debug) {
                    e.printStackTrace(); 
                }
                return null;
            }
	}
	
	
	/**
	 * Insert a failed vote into the database
         * 
	 * @param timeStamp
	 * 			Date/time stamp vote was registered
	 * @param protocol
	 * 			Protocol version vote was made with
	 * @param address
	 * 			Voting web-site address
         * @param serviceName
         *                      Voting web-site service name
         * @param username
         *                      Voter name
	 * 
	 * @return true if successful or false if unsuccessful
	 */
	public boolean insertVoteFailed(String protocol, String address, String serviceName, String username) {
            PreparedStatement ps;
            try {
                ps = this.connection.prepareStatement("INSERT INTO votesfailed (protocol, address, service, voter) VALUES ('" + protocol + "', ? , '" + serviceName + "', ? );");
                ps.setString(1, address);                
                ps.setString(2, username);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
	}
	
	/**
	 * Insert a successful vote into the database
         * 
	 * @param timeStamp
	 * 			Date/time stamp vote was registered
	 * @param protocol
	 * 			Protocol version vote was made with
	 * @param address
	 * 			Voting web-site address
         * @param serviceName
         *                      Voting web-site service name
         * @param username
         *                      Voter name
	 * 
	 * @return true if successful or false if unsuccessful
	 */
	public boolean insertVoteReceived(String protocol, String address, String serviceName, String username) {
            PreparedStatement ps;
            try {
                ps = this.connection.prepareStatement("INSERT INTO votesreceived (protocol, address, service, voter) VALUES ('" + protocol + "', ? , '" + serviceName + "', ? );");
                ps.setString(1, address);
                ps.setString(2, username);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                if (debug) {
                    e.printStackTrace();
                }
                return false;
            }
	}
	/**
	 * Keeps the connection alive to the database with a basic query
	 */
	public void keepConnectionAlive() {
            String sql = "SELECT COUNT(id) AS Amount FROM votesreceived WHERE protocol = 'v2';"; //Small query to keep the connection open
            executeQuery(sql);
	}
	
	/**
	 * Get all the warps from the database
	 * 
	 * @return all successful votes
	 */
	public ResultSet loadVotesReceived() {
            String sql = "SELECT * FROM votesreceived ORDER BY id DESCENDING;";
            return executeQuery(sql);
	}
	
	/**
	 * Get all the notes from the database for a specific id
	 * 
	 * @return all failed votes
	 */
	public ResultSet loadVotesFailed() {
            String sql = "SELECT * FROM votesfailed ORDER BY id DESCENDING;";
            return executeQuery(sql);
	}
	
	/**
	 * Close the connection to the database
	 * 
	 * @throws SQLException
	 * Thrown if there's a problem closing the connection
	 */
	public void closeConnection() throws SQLException {
            this.connection.close();
	}
}
