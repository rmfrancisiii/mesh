VertexPatch {
  var <>note, <>rand;

  *new  {
    ^super.new().init;
    }

  init {
    note = \hello;
    rand = 5.rand;
    ("Vertex Patch created: " ++ note).postln;
  }

  printOn { |stream| stream << this.class.name }

}
