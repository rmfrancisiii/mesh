on requestingHost

* Vertex.new ( vertexName, vertexType, hostName, meshName, args)

\/

* VertexType<Abstract>.Requestor ( vertexName, vertexHostName, meshName, args)
    sends vertexHost.OSC (/VertexType/request/vertex, vertexName, meshName, [args])

\/

--- OSC MSG to vertexHost----

\/

OSCdef: \VertexTypeRequestVertex (vertexName, meshName, [args])

\/

* VertexType<Abstract>.tryMakeVertex ([msg])

\/

* VertexType.makeVertex (vertexName, vertexMesh, [args])

\/

VertexType.initVertex (vertexName, vertexMesh, [args])

\/

* VertexType.makeVertex

\/

* VertexType<Abstract>.tryMakeVertex (true or false)

\/

sendVertexConfirmation(vertexName, meshName, vertexHost, requestingHost)
