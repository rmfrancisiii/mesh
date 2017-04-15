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


	test_meshMakeManyMeshes{ |number = 5|
		var initialMeshCount = Mesh.meshDict.size;
		number.do({
			this.testMeshNew(this.nextNewMeshName)
		});
		this.testMeshCount(initialMeshCount+number);
		"Make Many Meshes tests performed\n\n".inform;
	}

	test_meshPush{ |mesh = (this.asMesh(this.nextNewMeshName))|
		var stackSize = Mesh.stack.size;
		this.pushMeshOrName(mesh);
		this.testMeshPeek(mesh);
		this.testStackSize(stackSize+1);
	}


	// test_meshStackPopAll{
	// 	Mesh.popAll;
	// 	this.assert( Mesh.meshStack.isEmpty,
	// 	"Nothing on the stack" );
	// }
	//
	//
	// test_meshActiveMeshIsNil{
	// 	this.assert( Mesh.activeMesh.isNil,
	// 	"No initially active mesh" );
	// }
	//
	// pushOneMeshOntoStack{|testMesh|
	//
	// 	testMesh.push;
	//
	// 	this.assert( Mesh.peek == testMesh,
	// 	"Mesh Peek returns the active mesh" );
	// }
	//
	// test_meshPushMoreMeshes{ |number = 4|
	//
	// 	var initialMeshStackSize = Mesh.stack.size;
	//
	// 	number.do({|i|
	// 		var meshNumber = (i + initialMeshStackSize + 1);
	// 		var name = ("mesh" ++ meshNumber).asSymbol;
	// 		this.pushOneMeshOntoStack(Mesh(name));
	// 		this.assert( Mesh.peek == Mesh(name),
	// 		"Last Mesh pushed is the active mesh" );
	// 	});
	//
	// 	this.assert( Mesh.stack.size == (initialMeshStackSize + number),
	// 	"Correct number of meshes" );
	// }
	//
	// test_meshClassPopRemovesActiveMeshFromStack{
	// 	var penultimateMesh = Mesh.at(Mesh.stack[Mesh.stack.size-2]);
	// 	Mesh.pop;
	// 	this.assert( Mesh.peek == penultimateMesh,
	// 	"Last Mesh pushed is gone from the stack" );
	// }
	//
	// test_meshClassPopEach{
	// 	Mesh.stack.do({this.test_meshClassPopRemovesActiveMeshFromStack});
	// 	this.assert( Mesh.peek.isNil,
	// 	"Last Mesh is gone from the stack" );
	// }
	//
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
	//
	// test_meshNewReturnsExisting{
	// 	var temp = Mesh(\mesh1);
	// 	this.assert( temp == Mesh(\mesh1), "recalling a mesh by name");
	// }


}