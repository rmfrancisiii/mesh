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

-		newFrom : name or  : Mesh
-		If there is not a mesh with the given name, create a new mesh, with that name.
-		If there IS a mesh with that name just return it.
-
-		activeMesh : - : symbol
-		Key of the top mesh on the stack
-		if there is no Active Mesh, Warns and returns Nil.
-
-		list : - : Array
-		Returns an array of available (created) meshes
-
-		stack : - : Array
-
-		me : - : MeshHost
-
-		peek : - : Mesh
-		Returns the top mesh on the stack
-
-		pop : - : IdentityDictionary
-		Removes the top mesh from the stack
-
-		freeAll : - : -
-
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
-		init :  :
-
-		free : Mesh : -
-
-		addMesh :  :
-
-		push : mesh : -
-
-		pop : - : -
-
-		peek : - : -
-
-
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

	// return an array with the mesh stack
	*stack {
		^meshStack.collect({ arg item; item.meshName})
	}

	*me {
		^me
	}

	// return the last (top) mesh from the stack
	*peek {
		^meshStack[(meshStack.size-1)]
	}

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

		// add a shortcut to the Mesh environment so that:
		// ~meshName is equivalent to Mesh[\meshname]
		// this is only in the new Mesh
		// DECIDE: do we want to make these shortcuts available from other meshes?

		env.put(meshName.asSymbol, this);

		env.use({
			~mesh = meshName;
			~me = me;
		});

		// post a confirmation,
		postf("New Mesh Created: % \n", meshName);

	}

	printOn { |stream| stream << this.class.name << "(" << [meshName, hostManager] << ")" }
	//using env creates a recursion...


	// When creating a new Mesh, set the name and add it to the list
	addMesh {
		meshList.put(this.meshName, this);
	}

	meshName {
		^meshName
	}

	name {
		^meshName
	}

	hostManager {
		^hostManager
	}

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
		}
	}

	free {
		// FIXME: freeing a Mesh that doesnt exist Creates it and then removes it.
		// FIXME: Shouldn't be able to remove a mesh on the stack.

		if (currentEnvironment === env)
		{("Cannot remove current mesh").warn} // post a warning
		{
			hostManager.free;

			// list.atFail(this.name) {("No Such Mesh").warn};
			meshList.removeAt(this.meshName);
			("removed mesh").warn;
		}
	}
}
