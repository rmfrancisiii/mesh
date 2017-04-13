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

	*activeMesh {
		if (meshStack.notNil and: { meshStack.notEmpty })
		{^this.peek.meshName}
		{("No active mesh").warn; ^nil}
	}

	*list {^meshDict.keys.asList} // as List?

	*stack { ^meshStack.collect({ arg item; item.meshName}) }

	*me { ^me }

	// return the last (top) mesh from the stack
	*peek { ^meshStack[(meshStack.size-1)] }

	// remove the current mesh from the stack,
	// so the previous mesh is active
	*pop {
		if (meshStack.notNil and: { meshStack.notEmpty })
		{this.peek.pop}
		{("No active mesh").warn}
	}

	* freeAll {
		// TODO: this.
	}

	init {|name|

		meshName = name.asSymbol;
		hostManager = MeshHostManager.new(this, me);
		vertexDict = VertexDict.new;
		meshView = MeshView(this);
		env = Environment.make {};
		env.put(meshName.asSymbol, this);

		// TODO: consider expanding these, to what? vertexes? hosts? etc.
		env.use({
			~mesh = meshName;
			~me = me;
			~vl = vertexDict;
			~win = meshView;
		});
		postf("New Mesh Created: % \n", meshName);

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

		// pushes this mesh onto the stack and activates its environment.
		if (currentEnvironment === env) {
			// if this Mesh's Environment is already the current Environment,
			"Mesh Already Current Environment!".warn; // warn the user
			^this; // do nothing else and return the current Mesh
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
}
