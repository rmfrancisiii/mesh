Mesh
  *meshDict       Mesh.all            IdentityDictionary
  *meshStack      Mesh.stack          Array of Symbols
  *me             Mesh.thisHost       MeshHost

  meshName        this.name           Symbol
  env             this.environment    Environment
  hostManager     this.hosts          HostManager
  vertexDict      this.vertexes       VertexDict
  meshView        this.window         MeshView
                  this.beacon         Beacon
                  this.broadCastAddr  MeshHostAddr


Syntax:

Mesh.new(\name)     Returns Mesh
Mesh.at(\name)      Returns Mesh
Mesh.stack          Returns Array
Mesh.Peek           Mesh.current      Returns Mesh
                    eg.  Mesh.current.name
*isThereActiveMesh  Mesh.active       Returns Boolean
                    Mesh.all          Returns IdentityDictionary
                    eg.  Mesh.all.add
                         Mesh.all.includesKey
                    Mesh.popAll
                    Mesh.popEvery
                    Mesh.pop
                    Mesh(\name).push




Mesh.at(\name).hosts.at(\name) >> Mesh(\name)[\hostName]
Mesh.at(\name).vertexes.at(\name) >> Vertex(\name)


  *initClass {}
	*at {}
	*new {}
	*newFrom {}

  Mesh.current <<	*activeMesh << *peek
	Mesh.current.name << *activeMeshName
	Mesh.isCurrent(\name) << *isThisActiveMesh {}
	Mesh.exists(\name) << *isThisKeyInMeshDict {}
	Mesh.onStack(\name) <<  *isThisKeyOnTheStack {}
	Mesh.names  << *list {}
	Mesh.stack {}
	Mesh.thisHost << *me {}

	*pop {}

Public
  Mesh.popAll {}
	Mesh.popEvery {}
	Mesh.freeAll {}


	this.hideWindow {}
	this.showWindow {}
	this.isCurrent {}
	this.name {}
	this.hosts {}
	this.ping {}
	this.push {}
	this.pop {}
	this.free {}
  this.vertexes.

  this.at << hosts.at OR vertexes.at

Private
init {}
  Mesh.removeNameFromStack {}
  addMesh {}
  initializeInstanceVariables {}
  initializeInstanceEnvironment {}
  addEnvironmentShortcuts {}
  printOn {}

MeshHostManager

MeshHost

MeshHostAddr

VertexAbstract
