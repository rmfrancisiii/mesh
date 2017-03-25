
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

	*new {|mesh, me| ^ super.new.init(mesh, me) }

	//////////////////////////
	// Instance Methods
	//////////////////////////

	init {|mesh, me|

		var name = mesh.meshName;
		// Mesh.me.postln;

		inDefName = ( name ++ "BeaconIn").asSymbol;
		outDefName = (name ++ "BeaconOut").asSymbol;

		// make an OSC Path from the mesh name
		oscPath = ("/" ++ name ++ "Beacon").asSymbol;

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
			// oscPath.postln; me.name.postln;
			broadcastAddr.sendMsg(oscPath, me.name);
			mesh.hostManager.checkTimeouts;
			}, pollPeriod, false);
	}

	makeOSCdefs {|mesh|

		var replyPath = (oscPath ++ "-reply").asSymbol;

		OSCdef(inDefName, {|msg, time, addr, recvPort|
			var newHost = msg[1].asString.asSymbol;
			mesh.hostManager.updateHostList(newHost, addr, time);
			// (newHost ++ ", " ++ addr ++ ", " ++ time ).postln;
		}, replyPath, recvPort: Mesh.me.addr.port);

		OSCdef(outDefName, {|msg, time, addr, recvPort|
			var newHost= msg[1].asString.asSymbol;
			mesh.hostManager.updateHostList(newHost, addr, time);
			// (newHost ++ ", " ++ addr ++ ", " ++ time ).postln;
			addr.sendMsg(replyPath, Mesh.me.name);
		}, oscPath, recvPort: Mesh.me.addr.port);
	}

	start {
		// TODO: This
	}

	stop {
		beaconKeeper.stop;
	}

	free {
		OSCdef(inDefName).free;
		OSCdef(outDefName).free;

		}
}
