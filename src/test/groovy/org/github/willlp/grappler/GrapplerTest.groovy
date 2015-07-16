package org.github.willlp.grappler

import org.junit.Test

class GrapplerTest {
	
	
	@Test void 'test simple patch' () {
        def raw = '''\
Index: file/test.txt
==================================================================
--- file/test.txt (revision 1)
+++ file/test.txt (revision 2)
@@ -2,1 +2,1 @@
-bbb
+www'''
        
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
    
    
    @Test void 'test file patch' () {
        def diff = resource 'CallFilter.diff'
        def origin = resource 'CallFilter.java'
        def result = resource 'CallFilterPatched.java'
        
        def patch = PatchFactory.from diff
        assert patch.files.size() == 1
        
        def file = patch.files.head()
        assert file.applyTo(origin) == result.text
    }
    
    
    @Test void 'test unmatched conflict' () {
        def raw = '''\
Index: file/alfabetacharlie.txt
==================================================================
--- file/alfabetacharlie.txt (revision 1)
+++ file/alfabetacharlie.txt (revision 2)
@@ -2,1 +2,1 @@
-bbb
+www'''
        
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
    
    
    @Test void 'test two conflicts' () {
    	def diff = resource('alfa.diff')
    	def origin = resource('alfa_old.txt')
    	def result = resource('alfa_new.txt')
    	
    	def patch = PatchFactory.from diff
    	assert patch.files.size() == 1
    	
    	def file = patch.files.head()
    	assert file.modifications.size() == 2
    	
    	assert patch.applyTo(origin) == result.text
    }
    
    
    def resource(String file) { 
    	getClass()
    		.classLoader
    		.getResourceAsStream(file)
    		.text 
    }
    
}