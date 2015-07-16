package org.github.willlp.grappler

/**
 * Currently SVN format only
 *
 */
@groovy.util.logging.Log4j2
class PatchParser {
	
	enum Token {
		INDEX        (/^Index: .*/), 
		SEPARATOR    ( /^==================.*/ ), 
		FILE_REMOVED ( /^\+{3} .*/ ),
		FILE_ADDED   ( /^--- .*/ ),
		BEGIN_MODIFICATION( /^@@.*/ )
		                     
		private String regex;
		Token(String regex) { this.regex = regex }
		
		static Token match(String line) {
			Token.values().find { line ==~ it.regex }
		}
	}
	
	Patch parse(String content) {
		
		Patch patch = new Patch(content: content)
		PatchedFile file
		Token lastToken
		Modification modification
		
		content.eachLine { line ->
			def token = Token.match line
			
			lastToken = (token == null) ? lastToken : token
			
			def lambda = [
				(Token.INDEX) : {
					file = new PatchedFile(name: (line - "Index: "))
					patch.files << file
				},
				(Token.SEPARATOR) : {
					// ignore
				},
				(Token.FILE_ADDED) : {
					// what to do?
				},
				(Token.FILE_REMOVED) : {
					// what to do?
				},
				(Token.BEGIN_MODIFICATION) : {
					modification = new Modification()
					file.modifications << modification
				}
			][token]
			
			if (!lambda) {
				if (lastToken == Token.BEGIN_MODIFICATION) {
					modification.lines << line
				}
			} else {
				lambda()
			}
			
			log.error "parsed '$line' as '$token', lastToken='$lastToken', modification='$modification'"
		}
		
		return patch
	}
	
	/*
Index: file/test.txt
==================================================================
--- file/test.txt (revision 1)
+++ file/test.txt (revision 2)
@@ -2,1 +2,1 @@
aaa
-bbb
+www
	 */
	 
}