VertexPatchBay {
  var <>vertexList;

  *new  {
    ^super.new.init;
    }

  init {
    vertexList = NamedList.new;
  }

  addVertex {|vertexName|
    vertexList.addLast(vertexName, NamedList.new);
  }

  addPatch {|firstVertexName, secondVertexName|
    vertexList.at(firstVertexName).addLast(secondVertexName, VertexPatch.new);
  }

  getPatch {|firstVertexName, secondVertexName|
    ^ vertexList.at(firstVertexName).at(secondVertexName);
  }

  hasVertex {|vertexName|
    ^ vertexList.includes(vertexName);
  }

}
