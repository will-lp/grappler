package org.github.willlp.grappler

/**
 * Currently SVN format only
 *
 */
class PatchParser {
	
	enum Token {
		INDEX(/^Index: .*/),
		ADDED(/^\+.*/), 
		BEGIN_MODIFICATION(/^@@.*/), 
		REMOVED(/^-.*/)
		
		String regex
		Token(String regex) { this.regex = regex }
		
		static Token match(String line) {
			Token.values().find { line ==~ it.regex }
		}
	}
	
	PatchFile parse(String content) {
		def file = new PatchFile()
		content.eachLine { line
			def token = Token.match line
			def modification
			
			name
			modifications
			file
			content
			
			def lambda = [
				(Token.INDEX) : {
					file.name = line - "Index: "
				},
				(Token.ADDED) : {
					
				},
				(Token.REMOVED) : {
				},
				(Token.BEGIN_MODIFICATION) : {
				}
			][token]
			
			if (!lambda) {
				assert false, "No matching token for line '$line'"
			}
		}
	}
	
	
	/*
Index: file/test.txt
==================================================================
--- file/test.txt (revision 1)
+++ file/test.txt (revision 2)
@@ -2,1 +2,1 @@
-bbb
+www
	 */
	
}