MeshPatchbay {
  var <>vertexList;

  *new  {
    ^super.new().init;
    }

  init {
    vertexList = MeshNamedList.new();
  }

  addVertex {|vertexName|
    vertexList.addLast(vertexName, MeshNamedList.new());
  }

  addPatch {|vertexAOut, vertexBIn|
    if (this.hasVertex(vertexAOut)){
      if (this.hasVertex(vertexBIn)){
        (vertexList.at(vertexAOut)).addLast(vertexBIn, MeshPatch.new())
      }{("No such vertex:" ++ vertexBIn).postln};
    }{("No such vertex:" ++ vertexAOut).postln};
  }

  getPatch {|firstVertexName, secondVertexName|
    ^ vertexList.at(firstVertexName).at(secondVertexName);
  }

  hasVertex {|vertexName|
    ^ vertexList.includesKey(vertexName);
  }

}
