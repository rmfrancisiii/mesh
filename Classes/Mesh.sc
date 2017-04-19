Mesh {
	classvar <>all, <>stack, <thisHost;
	var <>name, <>env, <>hostManager, <>vertexDict, <>meshView;

// test
	*initClass {
		stack = [];
		all = IdentityDictionary.new;
		thisHost = thisHost.as(MeshHost);
		Vertex.initVertexTypeDict;
	}

	*at {|key| ^ all.at(key) }

	*new {|key|
		^ all.at(key) ?? { ^ super.new.init(key).addMesh };
	}

	*newFrom { |mesh|
		if(mesh.isKindOf(this)) { ^ mesh };
		^this.new(mesh);
	}

	*isThereActiveMesh {
		if (stack.notNil and: { stack.notEmpty })
		{	^ true}
		{	^ false}
	}

	*activename {
		if (this.isThereActiveMesh)
		{^this.peek.name}
		{("No active mesh").warn; ^List.newClear}
	}

	*activeMesh {
		if (this.isThereActiveMesh)
		{^ this.peek}
		{("No active mesh").warn; ^List.newClear}
	}

	*isThisActiveMesh { |key|
		if (this.isThereActiveMesh)
		{^ Mesh.at(key.asSymbol) == this.peek}
		{("No active mesh").warn; ^List.newClear}
	}

	*isThisKeyInAll { |key| ^ all.includesKey(key) }

	*isThisKeyOnTheStack { |key|
		this.stack.do({|item|
			if (item.name == key) {^true};
		});
		^ false
	 }

	*list { ^ all.keys.asList }

	//*stack { ^stack.collect({ arg item; item.name}) }

	*me { ^thisHost}

	// return the last (top) mesh from the stack
	*peek {
		if (this.isThereActiveMesh)
		{	^ stack[(stack.size-1)]}
		{("No active mesh").warn; ^ stack}
	}

	*pop {
		if (this.isThereActiveMesh)
		{this.peek.pop}
		{("No active mesh").warn};
		^ stack;
	}

	*popAll {
		stack.size.do({Mesh.pop});
		("No active mesh").warn;
		^ stack;
	}

	*popEvery {|key|
		Mesh.at(key).pop;
		Mesh.removeNameFromStack(key);
	}

	*removeNameFromStack {|key|
		stack = stack.reject({|val| val == Mesh.at(key)});
	}

	*freeAll {
		this.popAll;
		Mesh.all.do({|item|
			item.free;
		});
	}

	init {|key|
		name = key.asSymbol;
		this.initializeInstanceVariables(name);
		this.initializeInstanceEnvironment;
		postf("New Mesh Created: % \n", (name));
	}

	initializeInstanceVariables {
		hostManager = MeshHostManager.new(this, thisHost);
		vertexDict = VertexDict.new;
		meshView = MeshView(this);
	}

	initializeInstanceEnvironment {
		env = Environment.make {};
		env.put(name, this);
		this.addEnvironmentShortcuts(env, name);
	}

	addEnvironmentShortcuts {|env|
		env.use({
			~mesh = name;
			~me = thisHost;
			~vl = vertexDict;
			~win = meshView;
		});
	}

	hideCurrentWindow { Mesh.peek.meshView.deactivate }

	showCurrentWindow { Mesh.peek.meshView.activate }

	isThisCurrentMesh{ ^ currentEnvironment === env }

	printOn { |stream| stream << this.class.name << "(" << name << ")" }

	addMesh { all.put(this.name, this) }

	hosts { ^hostManager.hosts }

	at {|key| ^ hostManager.at(key)}

	ping { ^hostManager.beacon.ping(thisHost) }

	push {
		if (this.isThisCurrentMesh) { ^ stack; };
		if (Mesh.isThereActiveMesh) { this.hideCurrentWindow };
		stack = stack.add(this);
		env.push; // push this Mesh's Environment onto the Environment Stack
		("Entering Mesh: " ++ name).inform; // post a confirmation,
		meshView.activate;
		^ stack;
	}

	pop {
		if (this.isThisCurrentMesh)
		{
			this.hideCurrentWindow;
			("Leaving Mesh: " ++ name).inform; // post a confirmation,
			env.pop;
			stack.pop;
			if (Mesh.isThereActiveMesh) {this.showCurrentWindow};
			^ stack;
		}

		{
			(name ++ "is not the current Mesh. Try Mesh.pop").warn;
			^ stack;
		}
	}

	free {

		if (this.isThisCurrentMesh)
		{("Cannot remove current mesh, Try Mesh.pop First!").warn; ^ stack} // post a warning

		{
			hostManager.free(thisHost);
			all.removeAt(this.name);
			meshView.free;
			("removed mesh").warn;
		}
	}

}
