TestMesh : UnitTest {

	setUp {
	}

	tearDown {
	}

	test_meshEmptyClassTest{
		this.testMeIsMeshHost;
		this.testStackIsStack;
		this.testStackIsEmpty;
		this.testMeshListIsList;
		this.testMeshListIsEmpty;
		TestVertex.new.testVertexIsInitialized;
		"Mesh empty class tests performed\n\n".inform;
	}

	test_meshMakeManyMeshes{ |number = 3|
		var initialMeshCount = Mesh.meshDict.size;
		number.do({
			this.makeNewMeshAndTest;
		});
		this.testMeshCount(initialMeshCount+number);
		"Make Many Meshes tests performed\n\n".inform;
	}

	test_meshPushAllTheMeshes{
		Mesh.meshDict.keysDo({|name|
			this.pushMeshAndTest(name);
		});
		"Push All the Meshes tests performed\n\n".inform;
	}

	test_meshPopAllTheMeshes{
		Mesh.stack.size.do({
			this.popMeshAndTest;
		});
		this.testStackIsEmpty;
	}

	makeNewMeshAndTest{|name = (this.nextNewMeshName)|
		this.invokeAndTestMesh(name);
		this.testMeshInstanceVariables(name);
		this.testNewGetsExistingMesh(name);
		"Mesh Created and tested.\n\n".inform;
	}

	pushMeshAndTest{ |mesh|
		var initialStackSize = Mesh.stack.size;
		this.pushMeshOrName(mesh);
		this.testMeshPeek(mesh);
		this.testStackSize(initialStackSize + 1);
	}

	popMeshAndTest{
		var penultiMesh = this.getPenultiMesh;
		var initialStackSize = Mesh.stack.size;
		Mesh.pop;

		if (penultiMesh.notNil)
		{	this.testMeshPeek(penultiMesh)}
		{	this.testStackIsEmpty};

		this.testStackSize(initialStackSize - 1);
	}

	invokeAndTestMesh {|name|
		this.makeMesh(name);
		this.testMeshIsAMesh(name);
		this.testName(name);
		this.testMeshList(name);
	}

	testMeshInstanceVariables{|name|
		var mesh = this.getMesh(name);
		this.testHostManager(mesh);
		this.testVertexDict(mesh);
		this.testMeshView(mesh);
		this.testMeshEnvironment(mesh);

	}


	//
	// test_meshFreeAll{
	// 	"\n\n\nFreeAll".postln;
	// 	this.test_meshPushMoreMeshes(10);
	// 	{Mesh.freeAll};
	//
	// 	this.assert( Mesh.peek.isNil,
	// 	"Last Mesh is gone from the stack" );
	//
	// 	this.assert( Mesh.list.isEmpty,
	// 	"Last Mesh is gone from the stack" );
	//
	// }
	//
	//
	// Mesh.list.postln;
	//
	// test_meshInstPopRemovesActiveMeshFromStack{
	// 	Mesh(\mesh2).pop;
	// 	this.assert( Mesh.peek == Mesh(\mesh1),
	// 	"Named Mesh pushed is gone from the stack" );
	// }
	//
	// test_meshFreeFailsForActiveMesh{
	// 	Mesh(\mesh1).free;
	// 	this.assert( Mesh.peek == Mesh(\mesh1),
	// 	"removing the active mesh failed" );
	// }
	//
	// test_meshFree{
	// 	Mesh.pop;
	// 	Mesh(\mesh1).free;
	// 	this.assert( Mesh.list.includes(\mesh1).not,
	// 	"Freed Mesh is gone from the list" );
	// }
	//
	// test_meshFreeAll{
	// 	"freeall".postln;
	// 	Mesh.freeAll;
	// 	Mesh.list.postln;
	// 	this.assert( Mesh.list.includes(\mesh1).not,
	// 	"Freed Mesh is gone from the list" );
	// }

	//
	// test_meshFree{
	// 	var temp = Mesh(\mesh1);
	// 	temp.free;
	// 	this.assert( temp.class == Mesh, "New Mesh is a Mesh");
	// }



}