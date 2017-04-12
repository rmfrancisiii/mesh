TestMesh : UnitTest {
	classvar tempMesh;

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
		TestMesh.tempMesh = Mesh(\mesh1);
		this.assert( res.class == Mesh, "New Mesh is a Mesh");
	}

	test_meshNewReturnsExisting{
		var res = Mesh(\mesh1);
		this.assert( res.class == Mesh, "New Mesh is a Mesh");
	}

}