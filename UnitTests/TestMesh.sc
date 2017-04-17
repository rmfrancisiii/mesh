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

	test_meshMakeManyMeshes{ |number = 2|
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

	test_meshPopAll{
		this.test_meshPushAllTheMeshes;
		Mesh.popAll;
		this.testStackIsEmpty;
	}

	test_freeOneMesh{|name = (this.chooseRandomMesh)|
		var initialMeshCount = Mesh.meshDict.size;
		("freeing " ++ name).postln;
		this.testMeshIsThisKeyInMeshDict(name);
		Mesh.popEvery(name);
		Mesh.at(name).free;
		this.testMeshIsThisKeyInMeshDict(name);
		this.testMeshCount(initialMeshCount - 1);
	}

	test_FreeFailsForActiveMesh{|name|
		var initialMeshCount = Mesh.meshDict.size;
		var exists = Mesh.isThisKeyInMeshDict(name);
		Mesh.at(name).push;
		Mesh.at(name).free;
		this.assert( Mesh.isThisKeyInMeshDict(name) == exists,
			"Mesh Key still Exists");
		this.testMeshCount(initialMeshCount);
		this.testMeshIsThisActiveMesh(name);
	}

	test_pushMeshesRandomly{ |num = 5|
		num.do({
			var rand = this.chooseRandomMesh;
			if (Mesh.activeMeshName != rand)
			{ ("Pushing  " ++ rand).postln;
				this.pushMeshAndTest(rand);
			}{ "already Active mesh, not pushing".postln;};
		});
		Mesh.stack.postln;
	}

	test_meshPopEvery{|name = (this.chooseRandomMesh)|
		this.testMeshIsThisKeyOnTheStack(name);
		if (name == Mesh.activeMeshName) {Mesh.pop};

		Mesh.popEvery(name);
		this.testMeshIsThisKeyOnTheStack(name);
	}

	test_meshFreeAll{
		this.test_meshPopAll;
		Mesh.freeAll;
		this.testMeshCount(0);
		this.testStackIsEmpty;
		this.testMeshListIsEmpty;
	}

		getMesh {|name|
		^ Mesh.meshDict.at(name.asSymbol);
	}

	makeMesh {|name|
		^ Mesh(name.asSymbol);
	}

	nextNewMeshName {
		var meshNumber = Mesh.meshDict.size + 1;
		^ ("mesh" ++ meshNumber).asSymbol
	}

	getPenultiMesh {
		if (Mesh.stack.size <= 1) { ^ List.new(1)};
		^ this.asMesh(Mesh.stack[(Mesh.stack.size -2)])
	}

	chooseRandomMesh { ^ Mesh.list.choose }


	asMesh {|mesh|
		if (mesh.isKindOf(Mesh)) {^mesh};
		if (mesh.isKindOf(Symbol)) {^Mesh(mesh)};
		if (mesh.isKindOf(String)) {^Mesh(mesh.asSymbol)};
		this.assert( false, "Couldn't cast as Mesh" );
	}

	pushMeshOrName {|mesh|
		if (mesh.isKindOf(Mesh)) {mesh.push; ^true};
		if (mesh.isKindOf(Symbol)) {Mesh(mesh).push; ^true};
		this.assert( false,
			"Couldn't Push Mesh" );
	}

	makeNewMeshAndTest{|name = (this.nextNewMeshName)|
		this.testMeshIsThisKeyInMeshDict(name);
		this.invokeAndTestMesh(name);
		this.allInstanceVariableTests(name);
		this.testNewGetsExistingMesh(name);
		this.testMeshIsThisKeyInMeshDict(name);
		"Mesh Created and tested.\n\n".inform;
	}

	pushMeshAndTest{ |name|
		var initialStackSize = Mesh.stack.size;
		this.testMeshIsThisActiveMesh(name);
		this.pushMeshOrName(name);
		this.testMeshPeek(name);
		this.testMeshIsThisActiveMesh(name);
		this.testStackSize(initialStackSize + 1);
	}

	popMeshAndTest{
		var penultiMesh = this.getPenultiMesh;
		var initialStackSize = Mesh.stack.size;
		Mesh.pop;

		if (penultiMesh.isKindOf(List).not)
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

	allInstanceVariableTests{|name|
		var mesh = this.getMesh(name);
		this.testHostManager(mesh);
		this.testVertexDict(mesh);
		this.testMeshView(mesh);
		this.testMeshEnvironment(mesh);
	}

	testMeshIsAMesh {|name|
		this.assert( this.getMesh(name).isKindOf(Mesh),
			"A Mesh is a Mesh is a Mesh");
	}

	testNewGetsExistingMesh {|name|
		this.assert( Mesh(name.asSymbol) == this.getMesh(name),
			"Mesh(name) returns mesh as expected");
	}


	testMeIsMeshHost {
		this.assert( Mesh.me.isKindOf(MeshHost),
			"Me is a MeshHost");
	}

	testStackIsStack {
		this.assert( Mesh.stack.isKindOf(Array),
			"Stack is an Array");
	}

	testStackIsEmpty {
		this.assert( Mesh.stack.isEmpty,
			"Stack is empty");
	}

	testMeshListIsList  {
		this.assert( Mesh.list.isKindOf(List),
			"Mesh List is a List");
	}

	testMeshListIsEmpty {
		this.assert( Mesh.list.isEmpty,
			"Mesh List is empty");
	}

	testName {|name|
		this.assert( this.getMesh(name).meshName == name,
			"New Mesh has the right name" );
	}

	testMeshIsThisActiveMesh  { |name|
		var matches;
		if (Mesh.isThereActiveMesh)
		{ matches = (Mesh.at(name) == Mesh.peek) }
		{ matches = List.newClear};
		this.assert( Mesh.isThisActiveMesh(name) == matches,
			"Active Mesh Test Valid");
	}

	testMeshIsThisKeyInMeshDict  { |name|
		var matches = Mesh.meshDict.includesKey(name);
		this.assert( Mesh.isThisKeyInMeshDict(name) == matches,
			"Mesh Dict/Key Test Valid");
	}

	testMeshIsThisKeyOnTheStack  { |name|
		var matches = Mesh.meshStack.includes(Mesh.at(name));
		this.assert( Mesh.isThisKeyOnTheStack(name) == matches,
			"Mesh Stack/Key Test Valid");
	}


	testMeshList {|name|
		this.assert( Mesh.list.includes(name),
			"New Mesh contained in the list" );
	}

	testHostManager {|mesh|
		this.assert( mesh.hostManager.isKindOf(MeshHostManager),
			"New Mesh has a host manager" );
	}

	testVertexDict {|mesh|
		this.assert( mesh.vertexDict.isKindOf(VertexDict),
			"New Mesh has a vertex Dict" );
	}

	testMeshView {|mesh|
		this.assert( mesh.meshView.isKindOf(MeshView),
			"New Mesh has a mesh View" );
	}

	testMeshEnvironment{|mesh|
		this.assert( mesh.env.isKindOf(Environment),
			"New Mesh contains an Environment" );
		this.assert( mesh.env.at(\mesh) == mesh.meshName,
			"New Mesh environment contains a shortcut variable for its own name" );
		this.assert( mesh.env.at(\me) == Mesh.me,
			"New Mesh environment contains a shortcut variable for its MeshHost" );
		this.assert( mesh.env.at(\vl) == mesh.vertexDict,
			"New Mesh environment contains a shortcut variable for its Vertex Dictionary" );
		this.assert( mesh.env.at(\win) == mesh.meshView,
			"New Mesh environment contains a shortcut variable for its GUI Window" );
	}

	testMeshCount {|size|
		this.assert( Mesh.list.size == size,
			"Correct number of meshes in List" );
		this.assert( Mesh.meshDict.size == size,
			"Correct number of meshes in Dictionary" );
	}

	testStackSize {|size|
		this.assert( Mesh.stack.size == size,
			"Correct number of meshes in Stack" );
	}

	testMeshPeek {|name|
		this.assert( Mesh.peek == this.asMesh(name),
			"Correct Mesh is on the top of the stack" );
	}

}