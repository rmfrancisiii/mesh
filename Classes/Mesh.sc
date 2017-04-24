Mesh {
	classvar <>all, <>stack, <thisHost;
	var <>name, <>environment, <>hosts, <>vertexes, <>window;

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

	*newFrom {|mesh|
		if(mesh.isKindOf(this)) { ^ mesh };
		^ this.new(mesh)
	}

	*current {
		if (Mesh.hasCurrent)
		{	^ stack[(stack.size-1)]}
		{("No current mesh").warn; ^ List.new}
	}

	*hasPrevious {
		^ stack.size > 1
	}

	*previous {
		if (Mesh.hasPrevious)
		{	^ stack[(stack.size-2)]}
		{("No Previous mesh").warn; ^ List.new}
	}

	*isPrevious { |key|
		if (Mesh.hasPrevious.not) {^ false};
		^ Mesh.previous.name == key
	}

	*hasCurrent {
		if (stack.notNil and: { stack.notEmpty })
		{	^ true}
		{	^ false}
	}

	*isCurrent { |key|
		if (Mesh.hasCurrent.not) {^ false};
		^ Mesh.current.name == key
	}

	*exists { |key|
		^ all.includesKey(key)
	}

	*onStack { |key|
		Mesh.stack.do({|mesh|
			if (mesh.name == key) {^true};
		});
		^ false
	}

	*pop {
		if (Mesh.hasCurrent)
		{Mesh.current.pop}
		{"No active mesh".warn};
		^ stack;
	}

	*popAll {
		stack.size.do({Mesh.pop});
		"No active mesh".warn;
		^ stack;
	}

	*popEvery {|key|
		Mesh.at(key).pop;
		Mesh.removeFromStack(key);
	}

	*removeFromStack {|key|
		stack = stack.reject({|mesh|
			mesh == Mesh.at(key)
		});
	}

	*freeAll {
		Mesh.popAll;
		Mesh.all.do({|mesh|
			mesh.free;
		});
	}

	init {|key|
		name = key.asSymbol;
		this.initializeInstanceVariables(name);
		this.initializeInstanceEnvironment;
		postf("New Mesh Created: % \n", (name));
	}

	initializeInstanceVariables {
		hosts = MeshHosts.new(this, thisHost);
		vertexes = VertexDict.new;
		window = MeshView(this);
	}

	initializeInstanceEnvironment {
		environment = Environment.make {};
		environment.put(name, this);
		this.addEnvironmentShortcuts;
	}

	addEnvironmentShortcuts {
		environment.use({
			~mesh = name;
			~me = thisHost;
			~vl = vertexes;
			~win = window;
		});
	}

	hideCurrentWindow { Mesh.current.window.deactivate }

	showCurrentWindow { Mesh.current.window.activate }

	isCurrent { ^ Mesh.current == this }

	printOn { |stream| stream << this.class.name << "(" << name << ")" }

	addMesh { all.put(this.name, this) }

	at {|key| ^ hosts.at(key)}

	ping { ^hosts.ping(thisHost) }

	push {
		if (this.isCurrent) { ^ stack; };
		if (Mesh.hasCurrent) { this.hideCurrentWindow };
		stack = stack.add(this);
		environment.push; // push this Mesh's Environment onto the Environment Stack
		("Entering Mesh: " ++ name).inform; // post a confirmation,
		window.activate;
		^ stack;
	}

	pop {
		if (this.isCurrent)
		{
			this.hideCurrentWindow;
			("Leaving Mesh: " ++ name).inform; // post a confirmation,
			environment.pop;
			stack.pop;
			if (Mesh.hasCurrent) {this.showCurrentWindow};
			^ stack;
		}

		{
			(name ++ "is not the current Mesh.").warn;
			^ stack;
		}
	}

	free {

		if (this.isCurrent)
			{
				("Cannot free an active mesh, Try Mesh.pop First!").warn;
				^ stack
			}


			{
				hosts.free(thisHost);
				all.removeAt(this.name);
				window.free;
				("removed mesh").warn;
			}
	}

}
