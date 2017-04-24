TestMesh : UnitTest {

	setUp {
	}

	tearDown {
	}

	test_mesh{
		this.classInitialized;
		this.makeMeshes(3);
		this.pushAllMeshes;
	}

	makeMesh{|key = (this.nextNewName)|
		var initialCount = this.countMeshes;
		var mesh = Mesh.new(key);
		this.meshInitialized(key, mesh);
		this.checkMeshCount(initialCount + 1);
		^ mesh;
	}

	pushAllMeshes{
		Mesh.all.keysDo({|key|
			this.pushMeshAndTest(key);
		});
	}

	pushMeshAndTest{ |key|
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
		this.hostsIsMeshHosts(mesh);
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

	chooseRandomMesh {
		^ Mesh.values.choose
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

	hostsIsMeshHosts {|mesh|
		this.assert( mesh.hosts.isKindOf(MeshHosts),
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
			"Mesh(key) returns mesh as expected");
	}

	checkPrevious{|previous|
		this.assert( Mesh.previous == previous,
			"Mesh.previous returns previous mesh as expected");
	}


}
