InterfaceDef {
  var <>transaction, <>object, <>method;

  *new {|trans, obj, meth|
    ^ super.newCopyArgs(trans, obj, meth)
  }

}
