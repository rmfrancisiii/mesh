// create a mesh,
//  other hosts should use the same mesh name.
Mesh(\mesh1).push;

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



Mesh(\mesh1).push;

s = Server.local(\Local, Mesh.me);

// Vertex(\vertexName, \vertexType, \arg1 ...args);
// this is the same as Vertex(\vertexName).new(\vertexType, \arg1 ...args);

Vertex(\serverOne, \server, Mesh.me, s);

Mesh.me.sendMsg("/VertexServer/spawn");

Vertex(\serverOne).host;
Vertex(\serverOne).reboot;
Vertex(\serverOne).protocol;
Vertex(\serverOne).maxLogins;
Vertex(\serverOne).maxLogins_(8);
Vertex(\serverOne).maxLogins;
Vertex(\serverOne).status;
Vertex(\serverOne).addr;
Vertex(\serverOne).makeWindow;
Vertex(\serverOne).connect;
Vertex(\serverOne).ping;

Mesh(\mesh1).vertexList;


r = Server.remote(\river, NetAddr(Mesh(\mesh1)[\river].addr.ip, 57110));
Vertex(\serverTwo, \server, Mesh(\mesh1)[\river], r);

Vertex(\serverTwo).host.name;
Vertex(\serverTwo).protocol;
Vertex(\serverTwo).maxLogins;
Vertex(\serverTwo).status;
Vertex(\serverTwo).addr;
Vertex(\serverTwo).makeWindow;
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
Vertex(\sinSynth, \Instrument, [\fm, \stereoOut, freq=440, carPartial = 1, modPartial = 1, index = 3, dur=1.0, amp=0.25, pan=0, att=0.15, mod1=0.001, mod2=0.001])

// or:
Vertex(\sinSynth, \Instrument, [
	{var mod = SinOsc.ar(freq * modPartial, 0,
    freq * index * LFNoise1.kr(5.reciprocal).abs);
	var car = SinOsc.ar((freq * carPartial) + mod, 0, amp);
	var env = Env([0.001, 0.9, 0.001], [att, 1.0-att], [\sin, \sin]);
	var envGen = EnvGen.kr(env, doneAction: 2, timeScale: dur);
	Out.ar(outBus, Pan2.ar(car * envGen, pan))},
	\stereoOut, freq=440, carPartial = 1, modPartial = 1, index = 3, dur=1.0,
	amp=0.25, pan=0, att=0.15, mod1=0.001, mod2=0.001])

// AND:

Vertex(\sinPattern, \PVert, [trunk: \sinSynth])




TestDepClassVar(\test1, "test 1");
TestDepClassVar(\test2, "test 2");
TestDepClassVar(\test1, "test changed");



/*

// Get the local server's options

o = ServerOptions.new;
o.protocol_(\tcp);
o.maxLogins_(8)

t = Server(\Local2, NetAddr("127.0.0.1", 57111), o);

t.makeWindow;
s.makeWindow;
t.boot;
t.quit;

~win.deactivate;


		s.makeWindow;
		s.addr.connect;
		s.ping;

		r.options.protocol_(\tcp);
		r.options.maxLogins_(8);

		r.makeWindow;
		r.addr.connect;
		r.ping;

		r.addr;

		// FIXME: add filter to prevent or pass boot messages over network?
		^ server.boot
*/