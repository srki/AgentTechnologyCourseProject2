Group of agents for testing Agent Framework:

    1. Ping - Pong

        Test: Starts agents, sends Request to Ping agent.
        Ping: Gets Request from Test, sends Request to Pong agent, gets Inform from Pong agent.
        Pong: Gets Request from Ping agent, sends Inform to Ping agent.


    2. MapReduce

        Test: Starts Master agent, sends Request to Master agent.
        Master: Gets Request from Test, creates Slave agents and delegates tasks for every Slave agent,
                gets Inform from every Slave.
        Slave: Gets Request from Master agent, executes given task, and sends Inform to Master agent.


    3. Contract Net Protocol

        Test: Starts Master agent, sends Request to Master agent.
        Master: Gets Request from Test, creates Slave agents and Calls For Proposal to Slave agents.
                Gets bids from Slave agents and selects best. Sends Accept to best bid, and Reject to others.
        Slave: Gets Call For Proposal from Master, decides to answer or not.
               If answered, gets Accept or Reject from Master.