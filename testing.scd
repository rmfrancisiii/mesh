
test_meshMakeManyMeshes{ |number = 2|
  var initialMeshCount = Mesh.all.size;
  number.do({
    this.makeNewMeshAndTest;
  });
  this.testMeshCount(initialMeshCount+number);
  "Make Many Meshes tests performed\n\n".inform;
}

test_meshPushAllTheMeshes{
  Mesh.all.keysDo({|name|
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
  var initialMeshCount = Mesh.all.size;
  ("freeing " ++ name).postln;
  this.testMeshIsThisKeyInAll(name);
  Mesh.popEvery(name);
  Mesh.at(name).free;
  this.testMeshIsThisKeyInAll(name);
  this.testMeshCount(initialMeshCount - 1);
}

test_FreeFailsForActiveMesh{|name|
  var initialMeshCount = Mesh.all.size;
  var exists = Mesh.isThisKeyInAll(name);
  Mesh.at(name).push;
  Mesh.at(name).free;
  this.assert( Mesh.isThisKeyInAll(name) == exists,
    "Mesh Key still Exists");
  this.testMeshCount(initialMeshCount);
  this.testMeshIsThisActiveMesh(name);
}

test_pushMeshesRandomly{ |num = 5|
  num.do({
    var rand = this.chooseRandomMesh;
    if (Mesh.activename != rand)
    { ("Pushing  " ++ rand).postln;
      this.pushMeshAndTest(rand);
    }{ "already Active mesh, not pushing".postln;};
  });
  Mesh.stack.postln;
}

test_meshPopEvery{|name = (this.chooseRandomMesh)|
  this.testMeshIsThisKeyOnTheStack(name);
  if (name == Mesh.activename) {Mesh.pop};

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

makeNewMeshAndTest{|name = (this.nextNewname)|
  this.testMeshIsThisKeyInAll(name);
  this.invokeAndTestMesh(name);
  this.allInstanceVariableTests(name);
  this.testNewGetsExistingMesh(name);
  this.testMeshIsThisKeyInAll(name);
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


testNewGetsExistingMesh {|name|
  this.assert( Mesh(name.asSymbol) == this.getMesh(name),
    "Mesh(name) returns mesh as expected");
}



testName {|name|
  this.assert( this.getMesh(name).name == name,
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

testMeshIsThisKeyInAll  { |name|
  var matches = Mesh.all.includesKey(name);
  this.assert( Mesh.isThisKeyInAll(name) == matches,
    "Mesh Dict/Key Test Valid");
}

testMeshIsThisKeyOnTheStack  { |name|
  var matches = Mesh.stack.includes(Mesh.at(name));
  this.assert( Mesh.isThisKeyOnTheStack(name) == matches,
    "Mesh Stack/Key Test Valid");
}


testMeshList {|name|
  this.assert( Mesh.list.includes(name),
    "New Mesh contained in the list" );
}
