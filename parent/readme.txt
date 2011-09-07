commons -----

bean
	String buildBeanPath(String... fields);
	String[] splitBeanPath(String beanPath);

loady -----

load/de/serialise/save/ from csv,excel,xml
automatically join entities

syncy -----

automatically sync via svn,mercurial,git,rsync

testy -----

creates generated test data
creates generated test data for given fields
creates generated test data for given fields with generated id

(environment specific - so will need to be configurable)
preStartupTest
postStartupTest
onSuccessfulPreStartupTest
onFailedPreStartup(Excpetinos)
onSuccessfulPostStartupTest ... etc
Notify listeners

external "health" tests
internal "health" tests (self test)
built in application tests - e.g. on startup, 
check required servers are up, 
ports are connectable, 
services are available (e.g. http/soap/etc), 
database is available, tables exist, 
internet (server) is available.
config files exist and are accessible/writable?
- logs failed dependencies
- optional dependencies - system fail if exception encountered (-fail fast- vs *fail slow* vs dont' fail)
- waitFor (timeout)
- run os script - Runtime.exec (get output)

performance testing?

display - logs vs mbean vs webpage hosted/filesystem / html xmlreport filesystem / vs webservice vs maven pass/fail (e.g. exit System -1)?
- service publishing
xmpp interface for system config?


acceptance-testy -----

front end testing utils?

