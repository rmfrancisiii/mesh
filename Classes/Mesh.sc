/*
----------------------
- Class: Mesh
----------------------
-   Class Variables : Type
-
- 		meshList : IdentityDictionary
-		Dictionary of created meshes
-
-		meshStack : Array
-		A Stack of references to the meshes
-
-		me : MeshHost
-		This machine as a host (should be the same in every mesh, i.e. same Hostname/Port/IP address)
-
-	Class Methods : Args : Return Type
-
-		initClass	:	:	Class
-		Initializes the Class Variables
-
-		at : key : Mesh
-		returns the Mesh by name from the meshList
-
-		new : name : Mesh
-		If there is not a mesh with the given name, create a new mesh, with that name.
-		If there IS a mesh with that name just return it.
-		Side Effect: Creates/Starts Mesh's Beacon
-		Side Effect: Creates/Starts Mesh's OSCdefs
-
-		newFrom : name or  : Mesh
-		If there is not a mesh with the given name, create a new mesh, with that name.
-		If there IS a mesh with that name just return it.
-
-		activeMesh : - : symbol
-		Returns key of the top mesh on the stack
-		if there is no Active Mesh, Warns and returns Nil.
-
-		list : - : Array
-		Returns an array of available (created) meshes
-
-		stack : - : Array
-		Returns an array of meshes in the order they have been pushed onto the stack
-
-		me : - : MeshHost
-		Returns this Computer as a host
-
-		peek : - : Mesh
-		Returns the top mesh on the stack
-
-		pop : - : IdentityDictionary
-		Removes the top mesh from the stack
-		Side Effect: Environment Changes
-
-		freeAll : - : -
-		Frees all of the current meshes
-
----------------------
- 	Instance Variables : Type : Description
-
-		meshName : Symbol : key for the mesh
-
-		env : Environment : A place to put things, esp. patterns, etc.
-
-		hostManager : MeshHostManager : This contains the host list, timeout list, beacon
-
-	Instance Methods : Args : Return Type
-
-		meshName :  : symbol
-		name :  : symbol
-		Returns the name of this Mesh as a Symbol
-
-		hostManager :  :
-		Returns the hostmanager for this Mesh
-
-		push : mesh : Mesh
-		Pushes this mesh onto the meshStack
-		Side Effect: Environment Changes
-
-		pop : - : -
-		Removes this mesh from the top of the stack
-		IF it is the current mesh. otherwise returns nil
-		Side Effect: Environment Changes
-
-		free : Mesh : -
-		Frees a created Mesh.
-		Side Effect: Stops Mesh's Beacon
-		Side Effect: Frees Mesh's OSCdefs
-
-	Private Methods
-
-		init : name : Mesh
-
-		printOn :  :
-
-		addMesh : mesh : Mesh
----------------------
*/

Mesh {
	classvar meshList, meshStack, me;
	var meshName, env, hostManager;

	*initClass {
		meshStack = [];
		meshList = IdentityDictionary.new;
		me = me.as(MeshHost);
	}

	*at {|name| ^ this.meshList.at(name)}


	*new {|name|
		^ meshList.at(name) ?? {^ super.new.init(name).addMesh};
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

	*list {^meshList.keys.asArray}

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

	// Instance Methods
	init {|name|

		meshName = name.asSymbol;
		hostManager = MeshHostManager.new(this, me);
		env = Environment.make {};
		env.put(meshName.asSymbol, this);

		env.use({
			~mesh = meshName;
			~me = me;
		});

		// post a confirmation,
		postf("New Mesh Created: % \n", meshName);

	}

	printOn { |stream| stream << this.class.name << "(" << meshName << ")" }

	addMesh { meshList.put(this.meshName, this) }

	meshName { ^meshName }

	name { ^meshName }

	hostManager { ^hostManager }

	hosts { ^hostManager.hosts }

	ping { ^hostManager.beacon.ping(me) }

	push {
		// activates this Mesh and pushes its environment onto the stack.
		if (currentEnvironment === env) {
			// if this Mesh's Environment is already the current Environment,
			"Mesh Already Current Environment!".warn; // warn the user
			^this; // do nothing else and return the current Mesh
		};

		// otherwise:
		meshStack = meshStack.add(this);
		env.push; // push this Mesh's Environment onto the Environment Stack
		("Entering Mesh: " ++ meshName).inform; // post a confirmation,
	}

	pop {
		// deactivates the current Mesh by name, and removes its environment from the Environment Stack.
		// generally i would use Mesh.pop instead
		if (currentEnvironment === env)
		{
			("Leaving Mesh: " ++ meshName).inform; // post a confirmation,
			env.pop;
			meshStack.pop;
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
			hostManager.free;

			// list.atFail(this.name) {("No Such Mesh").warn};
			meshList.removeAt(this.meshName);
			("removed mesh").warn;
		}
	}
}
