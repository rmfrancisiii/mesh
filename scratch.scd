{ this.sendError(msg, Error("VertexName already in use."))}



  msg.methodName = \error;
  msg.sendResponse;

  "ERROR".postln
}
{
  vertex.tryPerform(method, msg.args);
};


try {vertex.perform(method, msg.args)}
{|error|
  "error".postln;
//        msg.methodName = \error;
//        msg.args = "failed";
//        msg.postln;
//        msg.sendResponse;
};
