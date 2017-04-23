TestMesh : UnitTest {

	setUp {
	}

	tearDown {
	}

	test_OneMesh{
		var mesh;
		var key = this.nextNewName;
		this.classInitialized;
		mesh = this.makeMesh(key);
		this.meshInitialized(key, mesh);

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
	}

	makeMesh {|key = (this.nextNewName)|
	  ^ Mesh(key);
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

	meshIsAMesh {|mesh|
		if (Mesh.exists(mesh.name).not) {	^ List.new };
	  this.assert( mesh.isKindOf(Mesh),
	    "A Mesh is a Mesh is a Mesh");
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

	checkName {|key, mesh|
	  this.assert( mesh.name == key,
	    "New Mesh has the right name" );
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

}
