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

		Mesh(\mesh1).postln;
		Mesh(\mesh1).meshName.class;
		Mesh(\mesh1).name;
		Mesh(\mesh1).hostManager;
		Mesh(\mesh1).push.class;
		Mesh(\mesh2).pop;
		Mesh(\mesh2).free;
		Mesh.list;

		Mesh(\mesh1).push;

		Mesh.pop;

		Mesh(\mesh1).free;
		// make it active:


	}
}