MockMesh {
	var <>name;

	*new {|name|
		^ super.new.init(name)
	}

	init {|name|
		name = name;
		postf("Mock Mesh Created: % \n", (name));
	}

}
