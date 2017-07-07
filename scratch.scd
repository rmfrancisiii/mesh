MeshDebugMon.on;
MeshCommandParser.on;
~win.deactivate;
~meshLocalTesting = true;

UnitTest.gui;
Mesh.test;
Vertex.test;
MeshPatchbay.test;
MeshHostsManager.test;

// Sample Code:
///////////////

// create a mesh,
//  other hosts should use the same mesh name.
Mesh(\mesh1, true).push;
Mesh.current.hosts.beacon.start;
// add a server to the mesh
Vertex(\server1, \server, \rose, \mesh1);
Vertex(\pattern1, \pattern, \rose, \mesh1);
Vertex(\pattern1).patternDict.postln;

Mesh(\mesh1).patchbay.addPatch(\pattern1, \server1);


//boot the server
Vertex(\server1).boot;

//list available meshes
Mesh.list;

//list available vertexes
Mesh(\mesh1).vertexes.postln;

//list patches
Mesh(\mesh1).patchbay.vertexList.postln;


//see current mesh
Mesh.current;

// and examine the Mesh stack:
// the last one is the latest (we could reverse this)
Mesh.stack;

//pop it off the stack:
Mesh.pop;

// free it
Mesh(\mesh1).free;

// find out what hosts are available in the current mesh:
Mesh(\mesh1).hosts;

// ping all hosts
Mesh(\mesh1).ping;


// or do the same thing using a shortcut:
~mesh1.hosts;



Mesh(\mesh1).patchbay.getPatch(\server1, \server2).rand.postln;
Mesh(\mesh1).patchbay.getPatch(\server3, \server4).rand.postln;


Mesh(\mesh1).patchbay.vertexList.at(\server1).at(\server2).rand.postln;
Mesh(\mesh1).patchbay.vertexList.at(\server2).postln;

Mesh(\mesh1).patchbay.getPatch(\server1, \server2).postln;




Mesh(\mesh1).patchbay.vertexList.postln;
Mesh(\mesh1).patchbay.vertexList.keys.postln;

//Vertex(\server1).setOptions(\maxLogins, 12);
Vertex(\server1).ping;
Vertex(\server1).kill;
Vertex(\server1).free;


Vertex(\synth1, \synth, \mesh1,
	{ | out, freq = 440, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var audio = Blip.ar(freq, nharms, amp);
    var env = Linen.kr(gate, doneAction: 2);
		OffsetOut.ar(out, Pan2.ar(audio, pan, env) )});

Vertex(\pattern1, \pattern, \mesh1,
	    \instrument, \test,
	[ \server, Server.default,
      \nharms, Pseq([4, 10, 40], inf),
      \dur, Pseq([1, 1, 2, 1]/10, inf),
      #[freq, sustain], Ptuple([
		 // assignment to multiple keys
         Pseq( (1..16) * 50, 4),
			Pseq([1/10, 0.5, 1, 2], inf)])]);

Patch(\synth1, \server1);
Patch(\pattern1, \synth1);







s = Server.local;

Vertex(\serverOne).reboot;
Vertex(\serverOne).connect;
Vertex(\serverOne).ping;

r = Server.remote(\river, NetAddr(Mesh(\mesh1)[\river].addr.ip, 57110));
Vertex(\serverTwo, \server, Mesh(\mesh1)[\river], r);
Vertex(\serverTwo).connect;
Vertex(\serverTwo).ping;

OSCdef.all;

////// STOP HERE //////////

Vertex(\stereoOut, \audioOut);
Vertex(\stereoOut).setServer(\hostAddr);
Vertex(\sinSynth, \instrument, \sin);
Vertex(\fmSynth, \instrument, \fm);
Vertex(\fmSynth).setOut(\stereoOut);



// or:
Vertex(\fmSynth, \Instrument, \fm, \stereoOut,
	[
		freq=440,
		carPartial = 1,
		modPartial = 1,
		index = 3,
		dur=1.0,
		amp=0.25,
		pan=0,
		att=0.15,
		mod1=0.001,
		mod2=0.001
	]
)

// or:
Vertex(\sinSynth, \Instrument,
	{var mod = SinOsc.ar(freq * modPartial, 0,
		 freq * index * LFNoise1.kr(5.reciprocal).abs);
	 var car = SinOsc.ar((freq * carPartial) + mod, 0, amp);
	 var env = Env([0.001, 0.9, 0.001], [att, 1.0-att], [\sin, \sin]);
	 var envGen = EnvGen.kr(env, doneAction: 2, timeScale: dur);
	Out.ar(outBus, Pan2.ar(car * envGen, pan))})

// AND:

Vertex(\sinPattern, \PVert, trunk: \sinSynth)
