TestMesh : UnitTest {

	setUp {
	}

	tearDown {
	}

	test_meshEmptyClassTest{
		this.meIsMeshHost;
		this.meshStackIsStack;
		this.meshStackIsEmpty;
		this.meshListIsList;
		this.meshListIsEmpty;
		this.vertexIsInitialized;
		"Mesh empty class tests performed\n\n".inform;
	}


	test_meshMakeManyMeshes{ |number = 3|
		var initialMeshCount = Mesh.meshDict.size;
		number.do({
			this.testMeshNew(this.nextNewMeshName)
		});
		this.testMeshCount(initialMeshCount+number);
		"Make Many Meshes tests performed\n\n".inform;
	}

	// NEED TO REWRITE THIS. maybe: testMeshNew returns mesh?
	// test_meshNewReturnsExisting{
	// 	var name = this.nextNewMeshName;
	// 	this.testMeshNew(name);
	// 	this.assert( mesh == Mesh(name),
	// 	"recalling a mesh by name");
	// }

	test_meshPush{ |mesh = (this.asMesh(this.nextNewMeshName))|
		var initialStackSize = Mesh.stack.size;
		this.pushMeshOrName(mesh);
		this.testMeshPeek(mesh);
		this.testStackSize(initialStackSize + 1);
	}

	test_meshPop{
		var penultiMesh = this.getPenultiMesh;
		var initialStackSize = Mesh.stack.size;
		Mesh.pop;
		this.testMeshPeek(penultiMesh);
		this.testStackSize(initialStackSize - 1);
	}

	test_meshPushAll{
		Mesh.meshDict.keysDo({|name|
			var initialStackSize = Mesh.stack.size;
			this.pushMeshOrName(name);
			this.testMeshPeek(name);
			this.testStackSize(initialStackSize + 1);
		});
	}

	test_meshPopAll{
		Mesh.stack.size.do({
			this.test_meshPop;
		});
		this.meshStackIsEmpty;
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