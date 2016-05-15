/**
 * @author - Dragan Vidakovic
 * Group of agents for testing Agent Framework:
 * <p>
 * 1. Ping - Pong
 * <p>
 * TestPingPong: Starts Ping and Pong agent, sends Request to Ping agent.
 * Ping: Gets Request from TestPingPong, sends Request to Pong agent, gets Inform from Pong agent.
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
 * TestContractNet: Start ContractNetMaster agent, make Request to ContractNetMaster agent.
 * Master: Gets Request, creates Slave agents and sends Call For Proposal to Slave agents.
 *         Gets Proposal or Reject from Slave agents and selects best (if any).
 *         Sends Accept Proposal to best bid, and Reject Proposal to others.
 * Slave: Gets Call For Proposal from Master, decides to answer or not.
 *        If answers - sends Proposal to Master, and waits for Accept Proposal or Reject Proposal.
 */
package com.ftn.informatika.agents.examples;