
/*
----------------------
- Class: Beacon
----------------------
-	Class Methods : Args : Return Type
-		new : - : MeshHostList
-
----------------------
- 	Instance Variables : Type : Description
-
-		oscPath : :
-		pollPeriod : :
-		broadcastAddr : :
-		beaconKeeper : :
-		inDefName : :
-		outDefName : :

-	Instance Methods : Args : Return Type
-		init : Mesh :
-		makeOSCdefs : Mesh :
-		start : :
-		stop : :
-		free : :

----------------------

*/

Beacon {

	var oscPath, <pollPeriod, broadcastAddr, beaconKeeper, inDefName, outDefName;

	*new {|mesh| ^ super.new.init(mesh) }

	//////////////////////////
	// Instance Methods
	//////////////////////////

	init {|mesh|
		// Mesh.me.postln;

		inDefName = (mesh.meshName ++ "BeaconIn").asSymbol;
		outDefName = (mesh.meshName  ++ "BeaconOut").asSymbol;

		// make an OSC Path from the mesh name
		oscPath = ("/" ++ mesh.meshName ++ "Beacon").asSymbol;

		// Make the OSC responder definitions.
		// these are OSC Defs, so they can be managed
		// globally, eg after creating a mesh, try: OSCdef.all;
		this.makeOSCdefs(mesh);

		// Set a broadcast address.
		// Use the IP address 255.255.255.255 to send a message to
		// all other nodes on the local network
		broadcastAddr = MeshHostAddr("255.255.255.255", 57120 + (0..7));

		// set the broadcast flag (whether or not broadcast messages can be sent)
		NetAddr.broadcastFlag = true;

		// set the frequency of testing to see if hosts are online
		pollPeriod = 1.0;

		// Start a function that periodically checks whether hosts are still online.
		// This continues running, even after Cmd+Period
		beaconKeeper = SkipJack({
			broadcastAddr.sendMsg(oscPath, Mesh.me.name);
			this.checkOnline; //hostManager
			}, pollPeriod, false);
	}

	makeOSCdefs {|mesh|

		var replyPath = (oscPath ++ "-reply").asSymbol;

		OSCdef(inDefName, {|msg, time, addr, recvPort|
			var newHost= msg[1].asString.asSymbol;
			this.updateHost(newHost, addr, time);
			// (newHost ++ ", " ++ addr ++ ", " ++ time ).postln;
		}, replyPath, recvPort: mesh.me.addr.port);

		OSCdef(outDefName, {|msg, time, addr, recvPort|
			var newHost= msg[1].asString.asSymbol;
			this.updateHost(newHost, addr, time);
			// (newHost ++ ", " ++ addr ++ ", " ++ time ).postln;
			addr.sendMsg(replyPath, mesh.me.name);
		}, oscPath, recvPort: mesh.me.addr.port);
	}

	start {
		// TODO: This
	}

	stop {
		// TODO: This
	}

	free {
		this.stop;
		OSCdef(inDefName).free;
		OSCdef(outDefName).free;

		}
}
