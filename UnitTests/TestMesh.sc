TestMesh : UnitTest {

	setUp {

	}

	tearDown {
	}

	test_meshInitClassMe{
		this.assert( Mesh.me.isKindOf(MeshHost),
			"Me is a MeshHost");
	}

	test_meshInitClassStackIsEmpty{
		this.assert( Mesh.stack.isEmpty,
			"Stack is empty");
	}

	test_meshInitClassStackIsArray{
		this.assert( Mesh.stack.class == Array,
			"Stack is empty");
	}

	test_meshInitClassMeshListisList{
		this.assert( Mesh.list.class == List,
			"list is empty");
	}

	test_meshInitClassMeshListisEmpty{
		this.assert( Mesh.list.isEmpty,
			"list is empty");
	}

	test_meshInitClassInitsVertex{
		this.assert( Vertex.vertexTypeDict.class == IdentityDictionary,
			"Vertex Type Dictionary created");
	}

	test_meshNewReturnsType{
		var temp = Mesh(\mesh1);
		this.assert( temp.class == Mesh,
			"New Mesh is a Mesh");
	}

	test_meshNewAddsMoreMeshes{
		Mesh(\mesh2);
		Mesh(\mesh3);
		Mesh(\mesh4);
		this.assert( Mesh.list.size == 4,
			"Many New Meshes");
	}

	// meshView = MeshView(this);
	test_meshNewHasName{
		this.assert( Mesh(\mesh1).meshName == \mesh1,
			"New Mesh has the right name" );
	}

	test_meshNewHasHostManager{
		this.assert( Mesh(\mesh1).hostManager.isKindOf(MeshHostManager),
			"New Mesh has a host manager" );
	}

	test_meshNewHasVertexDict{
		this.assert( Mesh(\mesh1).vertexDict.isKindOf(VertexDict),
			"New Mesh has a vertex Dict" );
	}

	test_meshNewHasMeshView{
		this.assert( Mesh(\mesh1).meshView.isKindOf(MeshView),
			"New Mesh has a mesh View" );
	}

	test_meshNewAddsToMeshList{
		this.assert( Mesh.list.includes(\mesh1),
			"New Mesh contained in the list" );
	}

	test_meshNewMeshHasEnvironment{
		this.assert( Mesh(\mesh1).env.isKindOf(Environment),
			"New Mesh contains an Environment" );
	}

	test_meshNewMeshEnvironmentHasNameShortcut{
		this.assert( Mesh(\mesh1).env.at(\mesh) == Mesh(\mesh1).meshName,
			"New Mesh environment contains a shortcut variable for its own name" );
	}

	test_meshNewMeshEnvironmentHasMeHostShortcut{
		this.assert( Mesh(\mesh1).env.at(\me) == Mesh.me,
			"New Mesh environment contains a shortcut variable for its MeshHost" );
	}

	test_meshNewMeshEnvironmentHasVertexDictShortcut{
		this.assert( Mesh(\mesh1).env.at(\vl) == Mesh(\mesh1).vertexDict,
			"New Mesh environment contains a shortcut variable for its Vertex Dictionary" );
	}

	test_meshNewMeshEnvironmentHasViewWindowShortcut{
		this.assert( Mesh(\mesh1).env.at(\win) == Mesh(\mesh1).meshView,
			"New Mesh environment contains a shortcut variable for its GUI Window" );
	}

	test_meshActiveMeshIsNil{
		this.assert( Mesh.activeMesh.isNil,
			"No initially active mesh" );
	}

	test_meshPushAddsToStack{
		Mesh(\mesh1).push;
		this.assert( Mesh.stack.includes(\mesh1),
			"New Mesh is on the stack" );
	}

	test_meshPushMakesMeshActive{
		this.assert( Mesh.activeMesh == \mesh1,
			"New Mesh is now Active" );
	}

	test_meshPeekReturnsActiveMesh{
		this.assert( Mesh.peek == Mesh(\mesh1),
			"Mesh Peek returns the active mesh" );
	}

	//
	// test_meshFree{
	// 	var temp = Mesh(\mesh1);
	// 	temp.free;
	// 	this.assert( temp.class == Mesh, "New Mesh is a Mesh");
	// }
	//
	// test_meshNewReturnsExisting{
	// 	var temp = Mesh(\mesh1);
	// 	this.assert( temp == Mesh(\mesh1), "recalling a mesh by name");
	// }


}