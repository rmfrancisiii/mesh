TestMesh : UnitTest {

	setUp {
		// create a mesh,
		//  other hosts should use the same mesh name.
		Mesh(\mesh1);

		// make it active:
		Mesh(\mesh1).push;

		// create another mesh and push it onto the stack:
		Mesh(\mesh2).push;

	}

	tearDown {
		Mesh.pop;
		Mesh.pop;
		Mesh(\mesh1).free;
		Mesh(\mesh2).free;
	}

	test_Mesh {
		~testMesh1 = \testMesh1;
		~test2Mesh1 = \test2Mesh1;
		UnitTest.new.assert(2 == 2, "Two does equal two.");
		UnitTest.new.assert(2 == 2.00001, "Two does equal two.");
	}
}