// create a mesh,
//  other hosts should use the same mesh name.
Mesh(\mesh1).push;
Mesh(\mesh1).hostManager.beacon.start;

Mesh.at(\some)
Mesh.at(\mesh1)

// create another mesh and push it onto the stack:
Mesh(\mesh3).push;

Mesh(\mesh3).free;

// create a third mesh without pushing it onto the stack:
Mesh(\mesh3);

Mesh.list;
Mesh.peek;

// find out what mesh is active:
Mesh.activeMesh;

// and examine the Mesh stack:
// the last one is the latest (we could reverse this)
Mesh.stack;

//pop it off the stack:
Mesh.pop;

// free it
Mesh(\mesh1).free;

// find out what hosts are available in the current mesh:
Mesh(\mesh1).hosts;

// or do the same thing using a shortcut:
~mesh1.hosts;

// you can also find out info for a different mesh than the current one
Mesh(\mesh3).hostNames;

// but shortcuts only work for the current mesh
~mesh3.hostNames;

// pop the top mesh off of the stack:
Mesh.pop;

UnitTest.gui;

Mesh(\mesh1).hosts;
Mesh(\mesh1).ping;



Vertex Syntax:
-----------
New:

MeshDebugMon.on;
MeshCommandParser.on;
~win.deactivate;
//**


Mesh(\mesh1).push;

Vertex(\serverOne, \server, \rose, \mesh1, \arg1);
Vertex(\serverTwo, \server, \rose, \mesh2, \arg2);

Vertex(\serverOne);
Vertex(\serverOne).test;
Vertex(\serverOne).ping;
Vertex(\serverTwo, meshName: \mesh2).test;

Mesh(\mesh2).push;
Vertex(\serverTwo).test;


Mesh(\mesh1).vertexList;
Mesh(\mesh2).vertexList;

Mesh.peek;

Mesh.pop;

Mesh.peek;


s = Server.local;

Vertex(\serverOne).reboot;
Vertex(\serverOne).connect;
Vertex(\serverOne).ping;
Mesh(\mesh1).vertexList;
Mesh.me.sendMsg("/VertexServer/spawn");

r = Server.remote(\river, NetAddr(Mesh(\mesh1)[\river].addr.ip, 57110));
Vertex(\serverTwo, \server, Mesh(\mesh1)[\river], r);
Vertex(\serverTwo).connect;
Vertex(\serverTwo).ping;

OSCdef.all;

Mesh(\mesh1)[\river].sendMsg("/VertexServer/spawn");
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



