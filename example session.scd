Mesh(\mesh1).push;
~testview = MeshView(Mesh(\mesh1).hostManager.hostList);
Mesh(\mesh1).ping;

Mesh(\mesh1).hostManager.addHost(\testHost);

Mesh(\mesh1).hostManager.addHost(\testHost2);

Mesh(\mesh1).hostManager.addHost(\testHost3);

Mesh(\mesh1).hosts;

Quarks.gui
// create a mesh,
//  other hosts should use the same mesh name.

Mesh(\mesh1).hosts;
~mesh1.free;

Mesh.pop;

Mesh(\mesh1).free;


// create another mesh and push it onto the stack:
Mesh(\mesh2).push;

// find out what mesh is active:
Mesh.activeMesh;

// and examine the Mesh stack:
// the last one is the latest (we could reverse this)
Mesh.stack;

// find out what hosts are available in the current mesh:
Mesh(\mesh1).hosts;

// or do the same thing using a shortcut:
~mesh1.hostNames;

// you can also find out info for a different mesh than the current one
Mesh(\mesh1).hostNames;

// but shortcuts only work for the current mesh
~mesh1.hostNames;

// pop the top mesh off of the stack:
Mesh.pop;

UnitTest.gui;

////// STOP HERE //////////

Vertex Syntax:
-----------

Vertex(\Vname, \VType, [arguments])
// eg:


Create a new Vertex:
Vertex.new(\stereoOut, \AudioOut)




Vertex(\stereoOut).setServer(\hostAddr);


// AND:

Vertex(\sinSynth, \Instrument, [\fm, \stereoOut])

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

