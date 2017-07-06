MeshPatch {
  var <>rand;

  *new  { |vertexOut, vertexIn|
    ^super.new(vertexOut, vertexIn).init;
    }

  init {|vertexOut, vertexIn|
    rand = 5000.rand;
    //Vertex(vertexOut).patchOut;
    //Vertex(vertexIn).patchIn;    
    ("Mesh Patch created: " ++ rand).postln;
  }

  printOn { |stream| stream << this.class.name }

}
