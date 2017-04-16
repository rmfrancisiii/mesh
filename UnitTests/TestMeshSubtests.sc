+ TestMesh{
	getMesh {|name|
		^ Mesh.meshDict.at(name.asSymbol);
	}

	makeMesh {|name|
		^ Mesh(name.asSymbol);
	}

	nextNewMeshName {
		var meshNumber = Mesh.meshDict.size + 1;
		^ ("mesh" ++ meshNumber).asSymbol
	}

	getPenultiMesh {
		if (Mesh.stack.size <= 1) { ^ List.new(1)};
		^ this.asMesh(Mesh.stack[(Mesh.stack.size -2)])
	}

	asMesh {|mesh|
		if (mesh.isKindOf(Mesh)) {^mesh};
		if (mesh.isKindOf(Symbol)) {^Mesh(mesh)};
		if (mesh.isKindOf(String)) {^Mesh(mesh.asSymbol)};
		this.assert( false, "Couldn't cast as Mesh" );
	}

	pushMeshOrName {|mesh|
		if (mesh.isKindOf(Mesh)) {mesh.push; ^true};
		if (mesh.isKindOf(Symbol)) {Mesh(mesh).push; ^true};
		this.assert( false,
			"Couldn't Push Mesh" );
	}

	testMeshIsAMesh {|name|
		this.assert( this.getMesh(name).isKindOf(Mesh),
			"A Mesh is a Mesh is a Mesh");
	}

	testNewGetsExistingMesh {|name|
		this.assert( Mesh(name.asSymbol) == this.getMesh(name),
			"Mesh(name) returns mesh as expected");
	}


	testMeIsMeshHost {
		this.assert( Mesh.me.isKindOf(MeshHost),
			"Me is a MeshHost");
	}

	testStackIsStack {
		this.assert( Mesh.stack.isKindOf(Array),
			"Stack is an Array");
	}

	testStackIsEmpty {
		this.assert( Mesh.stack.isEmpty,
			"Stack is empty");
	}

	testMeshListIsList  {
		this.assert( Mesh.list.isKindOf(List),
			"Mesh List is a List");
	}

	testMeshListIsEmpty {
		this.assert( Mesh.list.isEmpty,
			"Mesh List is empty");
	}

	testName {|name|
		this.assert( this.getMesh(name).meshName == name,
			"New Mesh has the right name" );
	}

	testMeshList {|name|
		this.assert( Mesh.list.includes(name),
			"New Mesh contained in the list" );
	}

	testHostManager {|mesh|
		this.assert( mesh.hostManager.isKindOf(MeshHostManager),
			"New Mesh has a host manager" );
	}

	testVertexDict {|mesh|
		this.assert( mesh.vertexDict.isKindOf(VertexDict),
			"New Mesh has a vertex Dict" );
	}

	testMeshView {|mesh|
		this.assert( mesh.meshView.isKindOf(MeshView),
			"New Mesh has a mesh View" );
	}

	testMeshEnvironment{|mesh|
		this.assert( mesh.env.isKindOf(Environment),
			"New Mesh contains an Environment" );
		this.assert( mesh.env.at(\mesh) == mesh.meshName,
			"New Mesh environment contains a shortcut variable for its own name" );
		this.assert( mesh.env.at(\me) == Mesh.me,
			"New Mesh environment contains a shortcut variable for its MeshHost" );
		this.assert( mesh.env.at(\vl) == mesh.vertexDict,
			"New Mesh environment contains a shortcut variable for its Vertex Dictionary" );
		this.assert( mesh.env.at(\win) == mesh.meshView,
			"New Mesh environment contains a shortcut variable for its GUI Window" );
	}

	testMeshCount {|size|
		this.assert( Mesh.list.size == size,
			"Correct number of meshes in List" );
		this.assert( Mesh.meshDict.size == size,
			"Correct number of meshes in Dictionary" );
	}

	testStackSize {|size|
		this.assert( Mesh.stack.size == size,
			"Correct number of meshes in Stack" );
	}

	testMeshPeek {|mesh|
		this.assert( Mesh.peek == this.asMesh(mesh),
			"Correct Mesh is on the top of the stack" );
	}


}



