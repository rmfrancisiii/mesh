TestMesh : UnitTest {

	setUp {

	}

	tearDown {
	}

	test_meshInitClass{

		this.assert( Mesh.stack.isEmpty,
			"Stack is empty");

		this.assert( Mesh.list.isEmpty,
			"list is empty");

		this.assert( Mesh.me.isKindOf(MeshHost),
			"Me is host");



	}

	test_createMesh{
		var res = Mesh(\mesh1);
		this.assert( res.class == Mesh, "New Mesh is a Mesh");

	}


}