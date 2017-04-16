Mesh {
	classvar <>meshDict, <>meshStack, me;
	var meshName, <>env, hostManager, <>vertexDict, <>meshView;

	*initClass {
		meshStack = [];
		meshDict = IdentityDictionary.new;
		me = me.as(MeshHost);
		Vertex.initVertexTypeDict;
	}

	*at {|name| ^ meshDict.at(name)}


	*new {|name|
		^ meshDict.at(name) ?? {^ super.new.init(name).addMesh};
	}

	*newFrom { |mesh|
		if(mesh.isKindOf(this)) { ^mesh };
		^this.new(mesh);
	}

	*isThereActiveMesh {
		if (meshStack.notNil and: { meshStack.notEmpty })
		{	^ true}
		{	^ false}
	}

	*activeMeshName {
		if (this.isThereActiveMesh)
		{^this.peek.meshName}
		{("No active mesh").warn; ^List.newClear}
	}

	*activeMesh {
		if (this.isThereActiveMesh)
		{^ this.peek}
		{("No active mesh").warn; ^List.newClear}
	}

	*isThisActiveMesh { |name|
		if (this.isThereActiveMesh)
		{^ Mesh.at(name.asSymbol) == this.peek}
		{("No active mesh").warn; ^List.newClear}
	}

	*list {^meshDict.keys.asList}

	*stack { ^meshStack.collect({ arg item; item.meshName}) }

	*me { ^me }

	// return the last (top) mesh from the stack
	*peek {
		if (this.isThereActiveMesh)
		{	^ meshStack[(meshStack.size-1)]}
		{("No active mesh").warn; ^List.newClear}
	}

	*pop {
		if (meshStack.notNil and: { meshStack.notEmpty })
		{this.peek.pop}
		{("No active mesh").warn}
	}

	*popAll {
		meshStack.size.do({Mesh.pop});
		("No active mesh").warn;
	}

	*popEvery {|name|
		// pops every instance of this mesh off of the stack and removes its environment from the Environment Stack.
	}

	init {|name|
		name = name.asSymbol;
		this.initializeInstanceVariables(name);
		this.initializeInstanceEnvironment;
		postf("New Mesh Created: % \n", (name));
	}

	initializeInstanceVariables {|name|
		meshName = name;
		hostManager = MeshHostManager.new(this, me);
		vertexDict = VertexDict.new;
		meshView = MeshView(this);
	}

	initializeInstanceEnvironment {
		env = Environment.make {};
		env.put(meshName, this);
		this.addEnvironmentShortcuts(env, meshName);
	}

	addEnvironmentShortcuts {|env|
		env.use({
			~mesh = meshName;
			~me = me;
			~vl = vertexDict;
			~win = meshView;
		});
	}

	printOn { |stream| stream << this.class.name << "(" << meshName << ")" }

	addMesh { meshDict.put(this.meshName, this) }

	meshName { ^meshName }

	name { ^meshName }

	hostManager { ^hostManager }

	hosts { ^hostManager.hosts }

	at {|name| ^ hostManager.at(name)}

	ping { ^hostManager.beacon.ping(me) }

	push {
		if (currentEnvironment === env) {
			"Mesh Already Current Environment!".warn;
			^this;
		};

		// otherwise:
		if (meshStack.notNil and: { meshStack.notEmpty })
		{Mesh.peek.meshView.deactivate}; //.meshView.postln; //deactivate;

		meshStack = meshStack.add(this);
		env.push; // push this Mesh's Environment onto the Environment Stack
		("Entering Mesh: " ++ meshName).inform; // post a confirmation,
		meshView.activate;
	}

	pop {
		// pops this mesh off of the stack and removes its environment from the Environment Stack.
		// generally i would use Mesh.pop instead

		// DECIDE: should this remove it from the stack entirely?
		if (currentEnvironment === env)
		{
			meshView.deactivate;
			("Leaving Mesh: " ++ meshName).inform; // post a confirmation,
			env.pop;
			meshStack.pop;
			if (meshStack.notNil and: { meshStack.notEmpty })
			{Mesh.peek.meshView.activate};
		}

		{
			(meshName ++ "is not the current Mesh.").warn;
			^nil
		}
	}




	free {
		// FIXME: freeing a Mesh that doesnt exist Creates it and then removes it.
		// FIXME: Shouldn't be able to remove a mesh on the stack.

		if (currentEnvironment === env)
		{("Cannot remove current mesh").warn; ^nil} // post a warning
		{
			hostManager.free(me);
			meshDict.removeAt(this.meshName);
			meshView.free;
			("removed mesh").warn;
		}
	}


		// * freeAll {
	//
	// 	"freeAll Started".postln;
	//
	// 	this.list.size.postln;
	// 	this.list.postln;
	// 	this.stack.size.postln;
	// 	this.stack.postln;
	//
	// 	this.popAll;
	// 	{
	// 		this.list.do({|item|
	// 			("removing mesh:  " ++ item.meshName).warn;
	// 			item.hostManager.free;
	// 			item.meshView.free;
	// 			meshDict.removeAt(item.meshName);
	// 			"removed".postln;
	// 		});
	// 	}.defer(10)
	//
	// }
}
