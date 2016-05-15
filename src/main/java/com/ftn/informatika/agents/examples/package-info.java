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
 *         Gets Proposals or Rejects from Slave agents and selects best from sent Proposals.
 *         Sends Accept Proposal to best bid, and Reject Proposal to others.
 * Slave: Gets Call For Proposal from Master, decides to answer or not.
 *        If answer - sends Proposal to Master, and waits for Accept Proposal or Reject Proposal.
 */
package com.ftn.informatika.agents.examples;