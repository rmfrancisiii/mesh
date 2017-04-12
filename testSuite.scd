UnitTest.runTest("TestMesh:test_meshInitClass");

UnitTest.gui;

try { ...code... } { |error|
    if( test: can I handle the error? ) {
        handle gracefully
    } { error.throw }
};