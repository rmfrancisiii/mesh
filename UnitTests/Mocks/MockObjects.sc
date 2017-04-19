MockMesh {
	var <>meshName;

	*new {|name|
		^ super.new.init(name)
	}

	init {|name|
		meshName = name;
		postf("Mock Mesh Created: % \n", (name));
	}

	name { ^meshName }


}
