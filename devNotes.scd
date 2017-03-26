/* Development notes

Ok, I think this phase is nearing completion,

Goal is: No Vertexes yet, but stable meshes.

For Classes:
. Mesh
. MeshManager
. MeshHost
. Beacon
. MeshHostAddr

1. Quark Install Working
2. Making and unmaking Meshes
3. Pushing, Popping them
Monitoring other Hosts in a gui
Basic help files for all classes,
Inline Documentation
Unit Tests for all classes/methods
Basic Readme and Guide files


Conceptual Note:
Mesh should emulate as closely as possible a broader method of communication protocol.
While this is currently based on SC, I'd like to try to keep it abstract enough to use
for IOT devices, etc. down the road, where no SC is even in the graph.

so the goal is, Vertexes, once added to a Mesh should say hello to one another, and negotiate
connections WITHOUT an omniscient manager. admittedly v.0 may not be quite like that, but
i'd like to base the grammar/syntax on this concept.

Vertexes



*/