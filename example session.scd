// create a mesh,
//  other hosts should use the same mesh name.
Mesh(\mesh1);

// make it active:
Mesh(\mesh1).push;

// create another mesh and push it onto the stack:
Mesh(\mesh2).push;

// find out what mesh is active:
Mesh.activeMesh;

// and examine the Mesh stack:
// the last one is the latest (we could reverse this)
Mesh.stack;

// find out what hosts are available in the current mesh:
Mesh(\mesh2).hosts;

// or do the same thing using a shortcut:
~mesh2.hostNames;

// you can also find out info for a different mesh than the current one
Mesh(\mesh1).hostNames;

// but shortcuts only work for the current mesh
~mesh1.hostNames;

// pop the top mesh off of the stack:
Mesh.pop;

// shortcuts should work for the current mesh
~mesh1.hostNames;
~me.postln;
~hosts[\rose].online.postln;




TestDepClassVar(\test1, "test 1");
TestDepClassVar(\test2, "test 2");
TestDepClassVar(\test1, "test changed");

