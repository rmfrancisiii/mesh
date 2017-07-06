MeshPatch {
  var <>rand;

  *new  { |vertexOut, vertexIn|
    ^super.new.init(vertexOut, vertexIn);
    }

  init {|vertexOut, vertexIn|
    rand = 5000.rand;
    vertexOut.postln;
    vertexIn.postln;
    Vertex(vertexOut).patchOut;
    Vertex(vertexIn).patchIn;
    ("Mesh Patch created: " ++ rand).postln;
  }

  printOn { |stream| stream << this.class.name }

}
