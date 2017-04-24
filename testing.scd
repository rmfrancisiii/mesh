
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



popMeshAndTest{
  var penultiMesh = this.getPenultiMesh;
  var initialStackSize = Mesh.stack.size;
  Mesh.pop;

  if (penultiMesh.isKindOf(List).not)
  {	this.testMeshPeek(penultiMesh)}
  {	this.testStackIsEmpty};

  this.testStackSize(initialStackSize - 1);
}

