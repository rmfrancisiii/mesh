VertexMessage {
  var arry;

  *new {|requestingHost, msg|
    ^ super.new.init(requestingHost, msg)
  }

  init {|requestingHost, msg|
    arry=Array.with(requestingHost, Mesh.thisHost, *msg)
  }

  requestingHost {
    ^ arry[0]
  }

  vertexHost {
    ^ arry[1]
  }

  path {
    ^ arry[2]
  }

  vertexName {
    ^ arry[3]
  }

  mesh {
    ^ Mesh(arry[4])
  }


// maybe do something (with a dictionary?) for these?
  args {
    ^ Array.with(arry[4..])
  }

}
