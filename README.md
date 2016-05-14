# Agent Center

### Description
Second course project for **Distributed Artificial Intelligence and Intelligent Agents** Course.
<br/>
Authors:
<ul>
  <li>Srđan Milaković RA 14/2012</li>
  <li>Dragan Vidaković RA 134/2012</li>
</ul>
University of Novi Sad Faculty of Technical Science

## Configuration
Start JBoss with standalone-full.xml
<br />
```
-Djboss.server.default.config=standalone-full.xml
```

#### Required
For slave nodes add
```
-Dmaster=x.x.x.x:port (Master Node IP Address and Port)
```

#### Optional
Set Local IP Address and port 
```
-Dlocal=x.x.x.x:port
```
Set Host Name
```
-Dalias=alias_value
```

### Example
```bash
# Master Node
.\standalone.bat -Djboss.server.default.config=standalone-full.xml -Dlocal="192.168.0.1:8080" -Dalias=MasterNode -b 0.0.0.0

# Slave Node 1
.\standalone.bat -Djboss.server.default.config=standalone-full.xml -Dlocal="192.168.0.1:8180" -Dmaster="192.168.0.1:8080" -Dalias=SlaveNode1 -Djboss.socket.binding.port-offset=100 -b 0.0.0.0

# Slave Node 2
.\standalone.bat -Djboss.server.default.config=standalone-full.xml -Dlocal="192.168.0.1:8280" -Dmaster="192.168.0.1:8080" -Dalias=SlaveNode2 -Djboss.socket.binding.port-offset=200 -b 0.0.0.0

```
