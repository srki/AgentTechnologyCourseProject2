/**
 * @author - Dragan Vidakovic
 * Group of agents for testing Agent Framework:
 * <p>
 * 1. Ping - Pong
 * <p>
 * Test: Starts agents, sends Request to Ping agent.
 * Ping: Gets Request from Test, sends Request to Pong agent, gets Inform from Pong agent.
 * Pong: Gets Request from Ping agent, sends Inform to Ping agent.
 * <p>
 * <p>
 * 2. MapReduce
 * <p>
 * Test: Starts Master agent, sends Request to Master agent.
 * Master: Gets Request from Test, creates Slave agents and delegates tasks for every Slave agent,
 * gets Inform from every Slave.
 * Slave: Gets Request from Master agent, executes given task, and sends Inform to Master agent.
 * <p>
 * <p>
 * 3. Contract Net Protocol
 * <p>
 * Test: Starts Master agent, sends Request to Master agent.
 * Master: Gets Request from Test, creates Slave agents and Calls For Proposal to Slave agents.
 * Gets bids from Slave agents and selects best. Sends Accept to best bid, and Reject to others.
 * Slave: Gets Call For Proposal from Master, decides to answer or not.
 * If answered, gets Accept or Reject from Master.
 */
package com.ftn.informatika.agents.examples;