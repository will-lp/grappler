package org.github.willlp.grappler

class Modification {
	String lineStart
	String lineEnd
	List<String> lines = []
	
	def matcher = [
		added : { it ==~ /^\+.*/ },
		removed : { it ==~ /^-.*/ },
		filterAndStrip : { Closure closure -> 
			lines.findAll closure collect { it[1..-1] } 
		}
	]
	
	List<String> getLinesBefore() {
		def index = lines.findIndexOf matched.added
		lines[0..index - 1]
	}
	
	List<String> getLinesAfter() {
		def index = lines.findIndexOf matched.removed
		lines[index..-1]
	}
	
	List<String> getLinesAdded() {
		matcher.filterAndStrip matcher.added
	}
	
	List<String> getLinesRemoved() {
		matcher.filterAndStrip matcher.removed
	}
	
}