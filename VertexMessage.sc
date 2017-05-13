VertexMessage {
  var arry;

  *new {|requestingHost, msg|
    ^ super.new.init(requestingHost, msg)
  }

  init {|requestingHost, msg|
    arry=Array.with(requestingHost, *msg)
  }

  requestingHost {
    ^ arry[0]
  }

  path {
    ^ arry[1]
  }

  vertexName {
    ^ arry[2]
  }

  mesh {
    ^ arry[3]
  }

  args {
    ^ Array.with(arry[3..])
  }

}
