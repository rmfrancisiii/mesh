/*
----------------------
- Class: MeshHostManager
----------------------
-	Class Methods : Args : Return Type
-		new : - : MeshHostManager
-
----------------------
- 	Instance Variables : Type : Description
- 		hostList : MeshHostList : Dictionary of hosts
-		timeoutList : IdentityDictionary : Dictionary of (hosts -> last time Pinged)
-		beacon : Beacon : manages network communication with other hosts
-
-	Instance Methods : Args : Return Type
-		init : - : MeshHostManager
-		free : MeshHostManager : -
-		checkTimeouts :  :
-		addHost : mesh : MeshHostList
-		hostNames : - : Array
-		hosts : - : -
- 		update : - : -

----------------------

*/

MeshHostManager {
	var <hostList, <>hostview, <>timeoutList, <>beacon;

	*new {|mesh, me| ^ super.new.init(mesh, me) }

	init {|mesh, me|
		hostList = IdentityDictionary.new;
		timeoutList = IdentityDictionary.new;
		beacon = Beacon.new(mesh, me);
		this.addHost(me);
		hostview = MeshView(hostList);
	}

	at {|name| ^ this.hostList.at(name)}

	checkTimeouts {
		var now;
		now = Main.elapsedTime;
		timeoutList.keysValuesDo({|name, lastHeardFrom|
			if((now - lastHeardFrom) > (beacon.pollPeriod * 2), {
				if (hostList[name].online == true)
					{"Host % seems to be offline\n".postf(name)};
				hostList[name].online = false;
				hostList.changed(\offlineHost, hostList[name]);
			});
		});
	}

	addHost {|host|
		host = host.as(MeshHost);
		hostList[host.name] = host;
		hostList.changed(\addedHost, host);
	}

	hostNames {^hostList.keys.asArray}

	hosts {
		"Available hosts:".postln;
		hostList.keysValuesDo {|key, value|
			(key ++ " : " ++ if(value.online, "online", "offline")).postln;
		}
	}

	updateHostList {|name, addr, time|

		var host = this[name];

		// new host!
		if (host.isNil)
		{
			"Host % has joined the mesh!!\n ".postf(name);
			host = MeshHost(name, addr);
			this.addHost(host);
			timeoutList[name] = time;
		}

		{   // host exists, check it!
			if(host.addr.matches(addr).not)
			{   // host is in the list, BUT the address doesn't match!
				// this probably means the peer recompiled and has a different port
				host.addr_(addr);
				host.online = true;
				hostList.changed(\rejoinedHost, hostList[name]);
				timeoutList[name] = time;
				"Host % rejoined the mesh\n".postf(name);
			}

			{   // host is in the list and the address matches, eveything is OK!
				if (host.online == false) {
					"Host % rejoined the mesh\n".postf(name);
					hostList.changed(\offlineHost, hostList[name]);
				};
				host.online = true;
				timeoutList[name] = time;
			};
		}
	}

	free {
		|me|
		this[me.name].online = false;
		hostList.changed(\offlineHost, hostList[me.name]);
		beacon.stop;
		beacon.free;
	}

}
