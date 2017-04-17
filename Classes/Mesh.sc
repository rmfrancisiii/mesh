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

	*isThisKeyInMeshDict { |name|
		^ meshDict.includesKey(name)
	}

	*isThisKeyOnTheStack { |name|
		^ this.stack.includes(name);
	}

	*list {^meshDict.keys.asList}

	*stack { ^meshStack.collect({ arg item; item.meshName}) }

	*me { ^me }

	// return the last (top) mesh from the stack
	*peek {
		if (this.isThereActiveMesh)
		{	^ meshStack[(meshStack.size-1)]}
		{("No active mesh").warn; ^ meshStack}
	}

	*pop {
		if (this.isThereActiveMesh)
		{this.peek.pop}
		{("No active mesh").warn}
		^ meshStack;
	}

	*popAll {
		meshStack.size.do({Mesh.pop});
		("No active mesh").warn;
		^ meshStack;
	}

	*popEvery {|name|
		Mesh.at(name).pop;
		Mesh.removeNameFromStack(name);
	}

	*removeNameFromStack {|name|
		meshStack = meshStack.reject({|val| val == Mesh.at(name)});
	}

	*freeAll {
		this.popAll;
		Mesh.meshDict.do({|item|
			item.free;
		});
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

	hideCurrentWindow { Mesh.peek.meshView.deactivate }

	showCurrentWindow { Mesh.peek.meshView.activate }

	isThisCurrentMesh{
		^ currentEnvironment === env
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
		if (this.isThisCurrentMesh) { ^ meshStack; };
		if (Mesh.isThereActiveMesh) { this.hideCurrentWindow };
		meshStack = meshStack.add(this);
		env.push; // push this Mesh's Environment onto the Environment Stack
		("Entering Mesh: " ++ meshName).inform; // post a confirmation,
		meshView.activate;
		^ meshStack;
	}


	pop {
		if (this.isThisCurrentMesh)
		{
			this.hideCurrentWindow;
			("Leaving Mesh: " ++ meshName).inform; // post a confirmation,
			env.pop;
			meshStack.pop;
			if (Mesh.isThereActiveMesh) {this.showCurrentWindow};
			^ meshStack;
		}

		{
			(meshName ++ "is not the current Mesh. Try Mesh.pop").warn;
			^ meshStack;
		}
	}




	free {

		if (this.isThisCurrentMesh)
		{("Cannot remove current mesh, Try Mesh.pop First!").warn; ^ meshStack} // post a warning

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
