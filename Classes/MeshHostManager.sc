MeshHosts {
	var <all, <>timeouts, <>beacon;

	*new {|mesh, thisHost| ^ super.new.init(mesh, thisHost) }

	init {|mesh, thisHost|
		all = IdentityDictionary.new;
		timeouts = IdentityDictionary.new;
		beacon = Beacon.new(mesh, thisHost);
		this.addHost(thisHost);
	}

	addHost {|host|
		host = host.as(MeshHost);
		all.put(host.name, host);
		all.changed(\addedHost, host);
		this.setOnline(host);
	}

	now { ^ Main.elapsedTime }

	at {|key| ^ all.at(key) }

	names { ^ all.keys.asArray }

	exists {|key| ^ all.includesKey(key) }

	isOnline {|key| ^ all.at(key).online }

	checkTimeouts {
		timeouts.keysValuesDo({|key, lastHeardFrom|
			var host = all.at(key);
			if (host.online){
				if (this.timedOut(lastHeardFrom))
					 {this.setOffline(host)}
			}
		})
	}

	setOffline {|host|
		host.online = false;
		"Host % left the mesh\n".postf(host.name);
		all.changed(\offlineHost, host);
	}

	setOnline {|host|
		host.online = true;
		"Host % joined the mesh\n".postf(host.name);
		all.changed(\onlineHost, host);
	}

	timedOut {|lastHeardFrom|
		var now = Main.elapsedTime;
		^ (now - lastHeardFrom) > (beacon.pollPeriod * 2)
	}

	printOn { |stream|
		 stream << "Available hosts: \n" << this.allPrettyHosts()
	}

	prettyHost {|key, stream|
		var host = all.at(key);
		stream << "(" << key << " : ";
		if (host.online)
			 {stream << "online"}
			 {stream << "offline"};
		stream << " : " << host.ip << ") \n";
		^ stream;
	}

	allPrettyHosts {|stream|
		all.keysDo {|key| stream << prettyHost(key) };
		^ stream;
	}

	resetTimeout {|key|
		timeouts[key] = this.now;
	}

	makeNewHost {|key, addr|
		var host = MeshHost(key, addr);
		this.addHost(host);
		this.resetTimeout(key);
	}

	changeHostAddr {|host, addr|
	 	host.addr = addr;
		this.resetTimeout(host.name);
		this.setOnline(host);
	}

	checkHost {|key, addr|
		if (this.exists(key).not)
		{ this.makeNewHost(key, addr) }
		{
			var host = all.at(key);
			if (host.addr.matches(addr).not)
			{ this.changeHostAddr(host, addr) }

			{ if (host.online == false)
					{ this.setOnline(host) };
				this.resetTimeout(key);
			};
		}
	}

	free {|host|
		this.setOffline(host);
		beacon.stop;
		beacon.free;
	}

}
