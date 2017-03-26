TestMesh : UnitTest {

	setUp {

	}

	tearDown {
	}

	test_Mesh {
		Mesh.(\mesh1);
		Mesh.newFrom(\mesh2);
		Mesh.activeMesh;
		Mesh.list.class;
		Mesh.list;
		Mesh.stack;
		Mesh.peek;

	}
}