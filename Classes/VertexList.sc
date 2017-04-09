VertexList : IdentityDictionary {

	// //this is what makes Vertex(\Name) return the vertex.obj contained
	//
	// at { arg key;
	// 	// because i'm overloading Identity Dictionary here i'm using their code
	// 	var me = array.at(this.scanFor(key) + 1);
	//
	// 	// But I'm returning something different:
	// 	// if the key's not there, I still want to return nil, because nil.obj will throw an exception
	// 	// and i'm using that nil in Vertex.new to decide whether it creates a new vertex or returns an existing Vertex
	// 	// so, if it's a valid entry in the dictionary, I want to return the internal object i.e.
	// 	// vertex.obj instead of just vertex.
	//
	// 	^ me !? {^ me.obj};
	// }
	//
	// // this outputs the vertex type instead of just "A Vertex"
	// printItemsOn { arg stream, itemsPerLine = 5;
	// 	var itemsPerLinem1 = itemsPerLine - 1;
	// 	var last = this.size - 1;
	// 	this.keysValuesDo({ arg key, value, i;
	// 		key.printOn(stream);
	// 		"  ->  ".printOn(stream);
	// 		value.obj.printOn(stream);
	// 		if (i < last, { stream.comma.space;
	// 			if (i % itemsPerLine == itemsPerLinem1, { stream.nl.space.space });
	// 		});
	// 	});
	// }

}