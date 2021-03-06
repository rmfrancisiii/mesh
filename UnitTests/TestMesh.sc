TestMesh : UnitTest {

	setUp {
	}

	tearDown {
	}

	test_mesh{
		var number = 6;
		this.classInitialized;
		this.makeMeshes(number);
		this.pushAll;
		Mesh.stack.postln;
		this.popAll;
		this.pushMeshesRandomly((number*5));
		this.popMeshesRandomly((number/2));
		this.freeOneMesh;
		this.freeAll;
	}

	freeOneMesh{|key = (this.chooseMeshFromAll.name)|
		var initialStackSize = this.countStack;
		var initialCount = this.countMeshes;
		var numKeyInStack = this.numKeyInStack(key);
		Mesh(key).free;
		this.checkNotOnStack(key);
		this.checkStackSize(initialStackSize - numKeyInStack);
		this.checkMeshCount(initialCount - 1);
	}

	freeAll{
		Mesh.freeAll;
		this.classInitialized;
	}

	pushMeshesRandomly{|number = 5|
		var initialStackSize = this.countStack;
		number.do({
			var rand = this.chooseMeshFromAll;
			while ({rand == Mesh.current},
				{rand = this.chooseMeshFromAll});
			this.pushAndTest(rand.name);
		});
		this.checkStackSize(initialStackSize + number);
	}

	popMeshesRandomly{|number = 5|
		var initialStackSize = this.countStack;
		number.do({
			var rand = this.chooseMeshFromStack;
			this.popEveryAndTest(rand.name);
		});
	}

	makeMesh{|key = (this.nextNewName)|
		var initialCount = this.countMeshes;
		var mesh = Mesh.new(key);
		this.meshInitialized(key, mesh);
		this.checkMeshCount(initialCount + 1);
		^ mesh;
	}

	pushAll{
		Mesh.all.keysDo({|key|
			this.pushAndTest(key);
		});
	}

	popAll{
		Mesh.stack.size.do({
			this.popAndTest
		});

		this.stackIsEmpty;
	}

	popAndTest{
		var initialStackSize = this.countStack;
		var next = Mesh.previous;
		Mesh.pop;
		if (next != List.new) {this.checkCurrent(next.name)};
		this.checkStackSize(initialStackSize - 1);
	}

	popEveryAndTest{|key|
		var initialStackSize = this.countStack;
		var numKeyInStack = Mesh.stack.count({ arg item; item.name == key });
		// probably breaks if previous is SAME as current
		var next = Mesh.previous;
		Mesh.popEvery(key);
		this.checkNotOnStack(key);
		this.checkStackSize(initialStackSize - numKeyInStack);
	}

	numKeyInStack {|key|
		^ Mesh.stack.count({ arg item; item.name == key })
	}

	pushAndTest{ |key|
		var initialStackSize = this.countStack;
		var mesh = this.getMesh(key);
		var previous = Mesh.current;
		mesh.push;
		this.checkCurrent(key);
		this.checkOnStack(key);
		this.checkStackSize(initialStackSize + 1);
		this.checkPrevious(previous);
	}

	makeMeshes {|count|
		var initialCount = this.countMeshes;
		count.do({this.makeMesh});
		this.checkMeshCount(initialCount + count);
	}

	classInitialized {
		this.hostInitialized;
		this.stackInitialized;
		this.allInitialized;
		TestVertex.new.vertexInitialized;
	}

	hostInitialized {
		this.thisHostIsMeshHost;
		// test IP and hostName?
	}

	stackInitialized {
		this.stackIsArray;
		this.stackIsEmpty;
	}

	allInitialized {
		this.allIsIdentityDict;
		this.allIsEmpty;
	}

	meshInitialized {|key, mesh|
		this.meshIsAMesh(mesh);
		this.checkName(key, mesh);
		this.hostsIsMeshHostsManager(mesh);
		this.vertexesIsVertexDict(mesh);
		this.windowIsMeshView(mesh);
		this.meshEnvironmentInitialized(mesh);
		this.checkMeshExists(key);
		this.checkNewGetsMesh(key);
	}

	nextNewName {
		var meshNumber = Mesh.all.size + 1;
		^ ("mesh" ++ meshNumber).asSymbol
	}

	getMesh {|key|
		^ Mesh.all.at(key)
	}

	chooseMeshFromAll {
		^ Mesh.all.values.choose
	}

	chooseMeshFromStack {
		^ Mesh.stack.choose
	}

	countMeshes {
		^ Mesh.all.size
	}

	countStack {
		^ Mesh.stack.size
	}

	meshIsAMesh {|mesh|
		if (Mesh.exists(mesh.name).not) {	^ List.new };
		this.assert( mesh.isKindOf(Mesh),
			"A Mesh is a Mesh is a Mesh");
	}

	checkName {|key, mesh|
		this.assert( mesh.name == key,
			"New Mesh has the right name" );
	}

	hostsIsMeshHostsManager {|mesh|
		this.assert( mesh.hosts.isKindOf(MeshHostsManager),
			"New Mesh has a host manager" );
	}

	vertexesIsVertexDict {|mesh|
		this.assert( mesh.vertexes.isKindOf(VertexDict),
			"New Mesh has a vertex Dict" );
	}

	windowIsMeshView {|mesh|
		this.assert( mesh.window.isKindOf(MeshView),
			"New Mesh has a mesh View" );
	}

	meshEnvironmentInitialized{|mesh|
		this.assert( mesh.environment.isKindOf(Environment),
			"New Mesh contains an Environment" );
		this.assert( mesh.environment.at(\mesh) == mesh.name,
			"New Mesh environment contains a shortcut variable for its own name" );
		this.assert( mesh.environment.at(\me) == Mesh.thisHost,
			"New Mesh environment contains a shortcut variable for its MeshHost" );
		this.assert( mesh.environment.at(\vl) == mesh.vertexes,
			"New Mesh environment contains a shortcut variable for its Vertex Dictionary" );
		this.assert( mesh.environment.at(\win) == mesh.window,
			"New Mesh environment contains a shortcut variable for its GUI Window" );
	}

	thisHostIsMeshHost{
		this.assert( Mesh.thisHost.isKindOf(MeshHost),
			"Mesh.thisHost is a MeshHost");
	}

	stackIsArray{
		this.assert( Mesh.stack.isKindOf(Array),
			"Mesh.stack is an Array");
	}

	stackIsEmpty {
		this.assert( Mesh.stack.isEmpty,
			"Mesh.stack is empty");
	}

	allIsIdentityDict {
		this.assert(Mesh.all.isKindOf(IdentityDictionary),
			"Mesh.all is an IdentityDictionary")
	}

	allIsEmpty {
		this.assert(Mesh.all.isEmpty,
			"Mesh.all is empty")
	}

	checkCurrent {|key|
		this.assert( Mesh.current.name == key,
			"Correct Mesh is on the top of the stack" );
	}

	checkStackSize {|size|
		this.assert( Mesh.stack.size == size,
			"Correct number of meshes in Stack" );
	}

	checkMeshCount {|size|
		this.assert( Mesh.all.size == size,
			"Correct number of meshes in all" );
	}

	checkMeshExists{|key|
		this.assert( Mesh.exists(key),
			"Mesh exists in Mesh.all" );
	}

	checkNewGetsMesh {|key|
		this.assert( Mesh(key) == this.getMesh(key),
			"Mesh(key) returns mesh as expected");
	}

	checkOnStack {|key|
		this.assert( Mesh.onStack(key),
			"Mesh is on stack as expected");
	}

	checkNotOnStack {|key|
		this.assert( Mesh.onStack(key).not,
			"Mesh is NOT on stack as expected");
	}

	checkPrevious{|previous|
		this.assert( Mesh.previous == previous,
			"Mesh.previous returns previous mesh as expected");
	}

}
