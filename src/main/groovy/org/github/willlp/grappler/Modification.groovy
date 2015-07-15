package org.github.willlp.grappler

class Modification {
	String lineStart
	String lineEnd
	List<String> lines = []
	
	def matcher = [
		added : { it ==~ /^\+.*/ },
		removed : { it ==~ /^-.*/ } 
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
		lines.findAll matcher.added
	}
	
	List<String> getLinesRemoved() {
		lines.findAll matcher.removed
	}
}