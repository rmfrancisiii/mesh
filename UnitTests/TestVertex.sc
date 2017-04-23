TestVertex : UnitTest {

	setUp {
	}

	tearDown {
	}

	vertexInitialized {
		this.assert( Vertex.vertexTypeDict.isKindOf(IdentityDictionary),
			"Vertex Type Dictionary created");
	}

}
