MeshPatch {
  var <>rand;

  *new  { |vertexOut, vertexIn|
    ^super.new.init(vertexOut, vertexIn);
    }

  init {|vertexOut, vertexIn|
    rand = 5000.rand;
    Vertex(vertexOut).patchOut(vertexIn);
    Vertex(vertexIn).patchIn(vertexOut);
    ("Mesh Patch created: " ++ rand).postln;
  }

  printOn { |stream| stream << this.class.name }

}
