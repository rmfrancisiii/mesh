TestMesh : UnitTest {

	setUp {

	}

	tearDown {
	}

	test_meshInitClassTest{

		this.assert( Mesh.me.isKindOf(MeshHost),
			"Me is a MeshHost");

		this.assert( Mesh.stack.isKindOf(Array),
			"Stack is an Array");

		this.assert( Mesh.stack.isEmpty,
			"Stack is empty");

		this.assert( Mesh.list.isKindOf(List),
			"Mesh List is a List");

		this.assert( Mesh.list.isEmpty,
			"Mesh List is empty");

		this.assert( Vertex.vertexTypeDict.isKindOf(IdentityDictionary),
			"Vertex Type Dictionary created");
	}

	test_meshNew{ |name = \mesh1|
		var testMesh = Mesh(name);

		this.assert( testMesh.isKindOf(Mesh),
			"New Mesh is a Mesh");

		this.assert( testMesh.meshName == name,
			"New Mesh has the right name" );

		this.assert( testMesh.hostManager.isKindOf(MeshHostManager),
			"New Mesh has a host manager" );

		this.assert( testMesh.vertexDict.isKindOf(VertexDict),
			"New Mesh has a vertex Dict" );

		this.assert( testMesh.meshView.isKindOf(MeshView),
			"New Mesh has a mesh View" );

		this.assert( Mesh.list.includes(name),
			"New Mesh contained in the list" );

		this.subtest_meshNewMeshHasValidEnvironment(testMesh)
	}


	subtest_meshNewMeshHasValidEnvironment{|testMesh|

		this.assert( testMesh.env.isKindOf(Environment),
			"New Mesh contains an Environment" );

		this.assert( testMesh.env.at(\mesh) == testMesh.meshName,
			"New Mesh environment contains a shortcut variable for its own name" );

		this.assert( testMesh.env.at(\me) == Mesh.me,
			"New Mesh environment contains a shortcut variable for its MeshHost" );

		this.assert( testMesh.env.at(\vl) == testMesh.vertexDict,
			"New Mesh environment contains a shortcut variable for its Vertex Dictionary" );

		this.assert( testMesh.env.at(\win) == testMesh.meshView,
			"New Mesh environment contains a shortcut variable for its GUI Window" );
	}

	test_meshMakeMoreMeshes{ |number = 4|

		var initialMeshListSize = Mesh.list.size;

		number.do({|i|
			var meshNumber = (i + initialMeshListSize + 1);
			var name = ("mesh" ++ meshNumber).asSymbol;
			this.test_meshNew(name)});

		this.assert( Mesh.list.size == (initialMeshListSize + number),
			"Correct number of meshes" );
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

	test_meshPeekReturnsActiveMesh{
		this.assert( Mesh.peek == Mesh(\mesh1),
			"Mesh Peek returns the active mesh" );
	}

	test_meshPushMultipleMeshOnStack{
		Mesh(\mesh2).push;
		Mesh(\mesh3).push;

		this.assert( Mesh.stack.size == 3,
			"More Meshes pushed onto Stack" );
	}

	test_meshPeekReturnsNewActiveMesh{
		this.assert( Mesh.peek == Mesh(\mesh3),
			"Last Mesh pushed is the active mesh" );
	}


	test_meshClassPopRemovesActiveMeshFromStack{
		Mesh.pop;
		this.assert( Mesh.peek == Mesh(\mesh2),
			"Last Mesh pushed is gone from the stack" );
	}

	test_meshInstPopRemovesActiveMeshFromStack{
		Mesh(\mesh2).pop;
		this.assert( Mesh.peek == Mesh(\mesh1),
			"Named Mesh pushed is gone from the stack" );
	}

	test_meshFreeFailsForActiveMesh{
		Mesh(\mesh1).free;
		this.assert( Mesh.peek == Mesh(\mesh1),
			"removing the active mesh failed" );
	}

	test_meshFree{
		Mesh.pop;
		Mesh(\mesh1).free;
		this.assert( Mesh.list.includes(\mesh1).not,
			"Freed Mesh is gone from the list" );
	}

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