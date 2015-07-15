package org.github.willlp.grappler

import org.junit.Test

class GrapplerTest {
    
	@Test
    void 'test simple patch' () {
        def raw = '''\
Index: file/test.txt
==================================================================
--- file/test.txt (revision 1)
+++ file/test.txt (revision 2)
@@ -2,1 +2,1 @@
-bbb
+www
'''
        
        def patch = PatchFactory.from raw
        assert patch instanceof Patch
        assert patch.files.size() == 1
        
        def file = patch.files.head()
        assert file.name == 'file/test.txt'
        assert file.applyTo('''\
aaa
bbb
ccc''') == '''\
aaa
www
ccc'''
    }
    
    
    @Test
    void 'test file patch' () {
        def diff = new File('resources/CallFilter.diff')
        def origin = new File('resources/CallFilter.java')
        def result = new File('resources/CallFilterPatched.java')
        
        def patch = PatchFactory.from diff
        assert patch.files.size() == 1
        
        def file = patch.files.head()
        assert file.applyTo(origin) == result.text
    }
    
    
    @Test
    void 'test unmatched conflict' () {
        def raw = '''\
Index: file/alfabetacharlie.txt
==================================================================
--- file/alfabetacharlie.txt (revision 1)
+++ file/alfabetacharlie.txt (revision 2)
@@ -2,1 +2,1 @@
-bbb
+www
'''
        
        def patch = PatchFactory.from raw
        assert patch.files.size() == 1
        
        def file = patch.files.head()
        assert file.name == 'file/alfabetacharlie.txt'
        assert file.applyTo('''\
aaa
jjj
hhh''') == '''\
aaa
>>>>>>>>>>>>>>>>>>>
www
<<<<<<<<<<<<<<<<<<<
jjj
hhh'''
        
    }
    
    
}
