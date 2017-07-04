MeshPatch {
  var <>rand;

  *new  {
    ^super.new.init;
    }

  init {
    rand = 5000.rand;
    ("Mesh Patch created: " ++ rand).postln;
  }

  printOn { |stream| stream << this.class.name }

}
